import { setActivePinia, createPinia } from 'pinia'
import { describe, it, expect, beforeEach, afterEach, vi } from 'vitest'

// Mutable mock storage state
let mockStorage = {
  accessToken: 'mock-token',
  userData: { name: 'Test User', subscriptionFlag: false },
  isSubscribed: false,
  userSurId: '123'
}

function resetMockStorage() {
  mockStorage.accessToken = 'mock-token'
  mockStorage.userData = { name: 'Test User', subscriptionFlag: false }
  mockStorage.isSubscribed = false
  mockStorage.userSurId = '123'
}

// Mock storage before store is imported
vi.mock('../../services/storage', () => ({
  getUserFromLocalStorage: vi.fn(() => mockStorage),
  saveUserToLocalStorage: vi.fn((data) => {
    mockStorage.userData = data
    mockStorage.accessToken = data.accessToken
    mockStorage.isSubscribed = !!data.subscriptionFlag
  }),
  clearUserFromLocalStorage: vi.fn(() => {
    mockStorage.accessToken = null
    mockStorage.userData = null
    mockStorage.isSubscribed = false
  }),
  isLoggedIn: vi.fn(() => !!mockStorage.accessToken),
  isSubscribed: vi.fn(() => !!mockStorage.isSubscribed)
}))

// Mock API services
vi.mock('../../services/api', () => ({
  checkMobileExists: vi.fn(),
  sendOtpExistingUser: vi.fn(),
  sendOtpNewUser: vi.fn(),
  verifyOtpExistingUser: vi.fn(),
  verifyOtpNewUser: vi.fn(),
  registerNewUser: vi.fn()
}))

import { useSubscriptionStore } from '../subscription'
import * as api from '../../services/api'
import * as storage from '../../services/storage'

describe('Subscription Store - Comprehensive Coverage', () => {
  beforeEach(() => {
    setActivePinia(createPinia())
    vi.clearAllMocks()
    resetMockStorage()
    vi.useFakeTimers()
    
    // Reset window location mock
    delete window.location
    window.location = { href: '' }

    // Mock global fetch
    vi.stubGlobal('fetch', vi.fn().mockResolvedValue({
      ok: true,
      json: async () => ({
        statusCode: 200,
        data: {
          paymentParameters: {
            paytmUrl: 'https://securegw-stage.paytm.in/order/process',
            MID: 'TestMID'
          }
        }
      })
    }))

    // Spy/Mock form submit
    vi.spyOn(HTMLFormElement.prototype, 'submit').mockImplementation(() => {})
  })

  afterEach(() => {
    vi.useRealTimers()
  })

  it('initializes with default states', () => {
    const store = useSubscriptionStore()
    expect(store.currentModal).toBeNull()
    expect(store.selectedPlan).toBeNull()
    expect(store.mobileNumber).toBe('')
    expect(store.accessToken).toBe('mock-token')
  })

  it('sets phone number and username correctly, stripping invalid characters', () => {
    const store = useSubscriptionStore()
    store.setMobileNumber('9876543210abc')
    store.setUserName('Alice123')
    expect(store.mobileNumber).toBe('9876543210')
    expect(store.userName).toBe('Alice')
  })

  it('opens and closes modals correctly', () => {
    const store = useSubscriptionStore()
    store.openModal('mobile', '6months')
    expect(store.currentModal).toBe('mobile')
    expect(store.selectedPlan).toBe('6months')

    store.closeModal()
    expect(store.currentModal).toBeNull()
    expect(store.loading).toBe(false)
  })

  it('sets individual OTP digits and resets correctly', () => {
    const store = useSubscriptionStore()
    store.setOtpDigit(0, '1')
    store.setOtpDigit(1, '2')
    expect(store.otp[0]).toBe('1')
    expect(store.otp[1]).toBe('2')

    store.closeModal()
    expect(store.otp).toEqual(['', '', '', '', '', ''])
  })

  it('validates mobile number length before sending OTP', async () => {
    const store = useSubscriptionStore()
    store.setUserName('John Doe')
    store.setMobileNumber('123') // Invalid
    await store.sendOtp()
    expect(store.error).toBe('Please enter a valid 10-digit mobile number')
  })

  it('sends OTP for existing user successfully', async () => {
    const store = useSubscriptionStore()
    store.setUserName('John Doe')
    store.setMobileNumber('9876543210')
    
    api.checkMobileExists.mockResolvedValue({ data: true })
    api.sendOtpExistingUser.mockResolvedValue({ status: true })

    await store.sendOtp()

    expect(api.checkMobileExists).toHaveBeenCalledWith('9876543210')
    expect(api.sendOtpExistingUser).toHaveBeenCalledWith('9876543210')
    expect(store.currentModal).toBe('otp')
  })

  it('sends OTP for new user successfully when name is provided', async () => {
    const store = useSubscriptionStore()
    store.setMobileNumber('9876543210')
    store.setUserName('John Doe')
    
    api.checkMobileExists.mockResolvedValue({ data: false })
    api.sendOtpNewUser.mockResolvedValue({ data: true })

    await store.sendOtp()

    expect(api.checkMobileExists).toHaveBeenCalledWith('9876543210')
    expect(api.sendOtpNewUser).toHaveBeenCalledWith('9876543210', 'John Doe')
    expect(store.currentModal).toBe('otp')
  })

  it('fails to send OTP for new user if name is missing', async () => {
    const store = useSubscriptionStore()
    store.setMobileNumber('9876543210')
    store.setUserName('') // Missing

    api.checkMobileExists.mockResolvedValue({ data: false })

    await store.sendOtp()

    expect(store.error).toBe('Please enter your name')
    expect(store.currentModal).toBeNull()
  })

  it('fails to send OTP for new user if name contains numbers', async () => {
    const store = useSubscriptionStore()
    store.setMobileNumber('9876543210')
    store.$state.userName = 'John123' // Bypass the setter filter

    api.checkMobileExists.mockResolvedValue({ data: false })

    await store.sendOtp()

    expect(store.error).toBe('Name cannot contain numbers')
    expect(store.currentModal).toBeNull()
  })

  it('handles API errors when sending OTP and parses JSON messages', async () => {
    const store = useSubscriptionStore()
    store.setUserName('John Doe')
    store.setMobileNumber('9876543210')
    
    api.checkMobileExists.mockResolvedValue({ data: true })
    const jsonError = JSON.stringify({ message: 'Server overloaded' })
    api.sendOtpExistingUser.mockRejectedValue(new Error(jsonError))

    await store.sendOtp()

    expect(store.error).toBe('Server overloaded')
  })

  it('logs out and clears storage', () => {
    const store = useSubscriptionStore()
    store.logout()
    expect(storage.clearUserFromLocalStorage).toHaveBeenCalled()
    expect(store.accessToken).toBeNull()
    expect(store.userData).toBeNull()
    expect(store.subscriptionStatus).toBeNull()
  })

  // verifyOtpAndCheckout tests

  it('blocks checkout if terms are not accepted', async () => {
    const store = useSubscriptionStore()
    store.termsAccepted = false
    await store.verifyOtpAndCheckout()
    expect(store.error).toBe('Please agree to the Terms & Conditions to proceed')
  })

  it('blocks checkout if OTP is not 6 digits', async () => {
    const store = useSubscriptionStore()
    store.termsAccepted = true
    store.otp = ['1', '2', '3'] // incomplete
    await store.verifyOtpAndCheckout()
    expect(store.error).toBe('Enter a valid 6-digit OTP')
  })

  it('processes checkout for existing user successfully', async () => {
    const store = useSubscriptionStore()
    store.termsAccepted = true
    store.otp = ['1', '2', '3', '4', '5', '6']
    store.isExistingUser = true
    store.selectedProductObj = { idProduct: 123 }
    
    const mockUserData = { accessToken: 'new-session-token', subscriptionFlag: false }
    api.verifyOtpExistingUser.mockResolvedValue({ status: true, data: mockUserData })

    const submitSpy = vi.spyOn(HTMLFormElement.prototype, 'submit')

    await store.verifyOtpAndCheckout()

    expect(api.verifyOtpExistingUser).toHaveBeenCalledWith(store.mobileNumber, '123456')
    expect(store.accessToken).toBe('new-session-token')
    expect(submitSpy).toHaveBeenCalled()
  })

  it('handles verification failure for existing user', async () => {
    const store = useSubscriptionStore()
    store.termsAccepted = true
    store.otp = ['1', '2', '3', '4', '5', '6']
    store.isExistingUser = true

    api.verifyOtpExistingUser.mockResolvedValue({ status: false, message: 'Invalid OTP' })

    await store.verifyOtpAndCheckout()

    expect(store.error).toBe('Invalid OTP')
    expect(store.subscriptionStatus).toBeNull()
  })

  it('processes checkout for new user successfully', async () => {
    const store = useSubscriptionStore()
    store.termsAccepted = true
    store.otp = ['1', '2', '3', '4', '5', '6']
    store.isExistingUser = false
    store.mobileNumber = '9876543210'
    store.userName = 'Jane Doe'
    store.selectedProductObj = { idProduct: 123 }

    api.verifyOtpNewUser.mockResolvedValue({ data: true })
    api.registerNewUser.mockResolvedValue({
      statusCode: 200,
      data: { accessToken: 'new-user-token', subscriptionFlag: false }
    })

    const submitSpy = vi.spyOn(HTMLFormElement.prototype, 'submit')

    await store.verifyOtpAndCheckout()

    expect(api.verifyOtpNewUser).toHaveBeenCalledWith('9876543210', '123456')
    expect(api.registerNewUser).toHaveBeenCalledWith('9876543210', 'Jane Doe', '123456')
    expect(store.accessToken).toBe('new-user-token')
    expect(submitSpy).toHaveBeenCalled()
  })

  it('handles verifyOtp failure for new user', async () => {
    const store = useSubscriptionStore()
    store.termsAccepted = true
    store.otp = ['1', '2', '3', '4', '5', '6']
    store.isExistingUser = false

    api.verifyOtpNewUser.mockResolvedValue({ data: false })

    await store.verifyOtpAndCheckout()

    expect(store.error).toBe('Invalid OTP. Please try again.')
  })

  it('handles registration failure for new user', async () => {
    const store = useSubscriptionStore()
    store.termsAccepted = true
    store.otp = ['1', '2', '3', '4', '5', '6']
    store.isExistingUser = false
    store.mobileNumber = '9876543210'
    store.userName = 'Jane Doe'

    api.verifyOtpNewUser.mockResolvedValue({ data: true })
    api.registerNewUser.mockResolvedValue({
      statusCode: 500,
      message: 'Database connection error'
    })

    await store.verifyOtpAndCheckout()

    expect(store.error).toBe('Database connection error')
  })

  it('verifies active subscription in status checking mode', async () => {
    const store = useSubscriptionStore()
    store.termsAccepted = true
    store.otp = ['1', '2', '3', '4', '5', '6']
    store.isExistingUser = true
    store.isStatusChecking = true

    const mockUserData = { accessToken: 'token', subscriptionFlag: { subscribedFlag: true } }
    api.verifyOtpExistingUser.mockResolvedValue({ status: true, data: mockUserData })

    await store.verifyOtpAndCheckout()

    expect(store.subscriptionStatus).toBe('active')
    expect(store.currentModal).toBe('already_subscribed')
  })

  it('verifies non-active subscription in status checking mode', async () => {
    const store = useSubscriptionStore()
    store.termsAccepted = true
    store.otp = ['1', '2', '3', '4', '5', '6']
    store.isExistingUser = true
    store.isStatusChecking = true

    const mockUserData = { accessToken: 'token', subscriptionFlag: false }
    api.verifyOtpExistingUser.mockResolvedValue({ status: true, data: mockUserData })

    await store.verifyOtpAndCheckout()

    expect(store.error).toBe('No active subscription found. Please complete payment.')
    expect(store.currentModal).toBe('already_subscribed')
  })

  it('skips checkout if user is already subscribed (not in status checking mode)', async () => {
    const store = useSubscriptionStore()
    store.termsAccepted = true
    store.otp = ['1', '2', '3', '4', '5', '6']
    store.isExistingUser = true
    store.isStatusChecking = false

    const mockUserData = { accessToken: 'token', subscriptionFlag: { subscribedFlag: true } }
    api.verifyOtpExistingUser.mockResolvedValue({ status: true, data: mockUserData })

    await store.verifyOtpAndCheckout()

    expect(store.subscriptionStatus).toBe('active')
    expect(store.currentModal).toBe('already_subscribed')
  })

  it('activates subscription directly on localStorage when userData in store is missing', async () => {
    const store = useSubscriptionStore()
    store.termsAccepted = true
    store.otp = ['1', '2', '3', '4', '5', '6']
    store.isExistingUser = true
    store.selectedProductObj = { idProduct: 123 }
    
    // Force userData to be missing in store by using defineProperty
    const mockUserData = { accessToken: 'token', subscriptionFlag: false }
    api.verifyOtpExistingUser.mockResolvedValue({ status: true, data: mockUserData })

    Object.defineProperty(store, 'userData', {
      get: () => null,
      set: () => {},
      configurable: true
    })

    const submitSpy = vi.spyOn(HTMLFormElement.prototype, 'submit')

    await store.verifyOtpAndCheckout()

    expect(submitSpy).toHaveBeenCalled()
  })

  it('handles checkout try-catch error and formats error message', async () => {
    const store = useSubscriptionStore()
    store.termsAccepted = true
    store.otp = ['1', '2', '3', '4', '5', '6']
    store.isExistingUser = true

    api.verifyOtpExistingUser.mockRejectedValue(new Error('Network Fail'))

    await store.verifyOtpAndCheckout()

    expect(store.error).toBe('Network Fail')
  })

  describe('checkExistingSubscription', () => {
    it('sets subscriptionCheckDone to true and returns if userSurId is missing', async () => {
      const store = useSubscriptionStore()
      mockStorage.userSurId = null

      await store.checkExistingSubscription()

      expect(store.subscriptionCheckDone).toBe(true)
      expect(store.existingSubscription).toBe(false)
    })

    it('sets existingSubscription to true if active and not expired', async () => {
      const store = useSubscriptionStore()
      mockStorage.userSurId = '123'
      mockStorage.accessToken = 'test-token'

      global.fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => ({
          statusCode: 200,
          data: {
            existingSubscription: true,
            expiredSubscription: false
          }
        })
      })

      await store.checkExistingSubscription()

      expect(store.existingSubscription).toBe(true)
      expect(store.subscriptionExpired).toBe(false)
      expect(store.subscriptionCheckDone).toBe(true)
    })

    it('sets subscriptionExpired to true if expired', async () => {
      const store = useSubscriptionStore()
      mockStorage.userSurId = '123'
      mockStorage.accessToken = 'test-token'

      global.fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => ({
          statusCode: 200,
          data: {
            existingSubscription: false,
            expiredSubscription: true
          }
        })
      })

      await store.checkExistingSubscription()

      expect(store.existingSubscription).toBe(false)
      expect(store.subscriptionExpired).toBe(true)
      expect(store.subscriptionCheckDone).toBe(true)
    })

    it('fails silently and sets subscriptionCheckDone to true on error', async () => {
      const store = useSubscriptionStore()
      mockStorage.userSurId = '123'
      mockStorage.accessToken = 'test-token'

      global.fetch.mockRejectedValueOnce(new Error('Network Error'))

      await store.checkExistingSubscription()

      expect(store.subscriptionCheckDone).toBe(true)
    })
  })

  describe('fetchSubscriptionProducts', () => {
    it('fetches and maps products correctly', async () => {
      const store = useSubscriptionStore()
      
      global.fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => ({
          data: [
            { duration: 90, durationCode: 'QUARTER', amount: 399, productCd: 'AD_FREE_SUBSCRIPTION' },
            { duration: 180, durationCode: 'HALF_YEAR', amount: 699, productCd: 'AD_FREE_SUBSCRIPTION' },
            { duration: 365, durationCode: 'ANNUAL', productCd: 'AD_FREE_SUBSCRIPTION', amount: 1999 }
          ]
        })
      })

      await store.fetchSubscriptionProducts()

      expect(store.productList.length).toBe(3)
      expect(store.productMap['3months'].amount).toBe(399)
      expect(store.productMap['6months'].amount).toBe(699)
      expect(store.productMap['14months'].amount).toBe(1999)
    })

    it('skips fetch if productList is already populated', async () => {
      const store = useSubscriptionStore()
      store.productList = [{ duration: 90 }]
      
      await store.fetchSubscriptionProducts()
      expect(global.fetch).not.toHaveBeenCalled()
    })
  })

  describe('handlePlanSelect', () => {
    it('updates selectedPlan, selects product object, and opens modal', () => {
      const store = useSubscriptionStore()
      const mockProduct = { idProduct: 10 }
      store.productMap = { '3months': mockProduct, '14months': { idProduct: 20 } }

      store.handlePlanSelect('3months')
      expect(store.selectedPlan).toBe('3months')
      expect(store.selectedProductObj).toStrictEqual(mockProduct)
      expect(store.currentModal).toBe('comparison')

      store.handlePlanSelect('14months')
      expect(store.selectedPlan).toBe('14months')
      expect(store.currentModal).toBe('mobile')
    })
  })

  describe('setSelectedPlan', () => {
    it('updates selectedPlan and selectedProductObj correctly', () => {
      const store = useSubscriptionStore()
      const mockProduct = { idProduct: 10 }
      store.productMap = { '3months': mockProduct }

      store.setSelectedPlan('3months')
      expect(store.selectedPlan).toBe('3months')
      expect(store.selectedProductObj).toStrictEqual(mockProduct)

      store.setSelectedPlan('invalid-id')
      expect(store.selectedPlan).toBe('invalid-id')
      expect(store.selectedProductObj).toBeNull()
    })
  })

  describe('toast and error helpers', () => {
    it('sets toastMessage and clears it after timer', () => {
      const store = useSubscriptionStore()
      store.showToast('Test Toast')
      expect(store.toastMessage).toBe('Test Toast')
      
      vi.advanceTimersByTime(3000)
      expect(store.toastMessage).toBeNull()
    })
  })
})
