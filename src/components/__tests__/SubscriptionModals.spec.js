import { mount } from '@vue/test-utils'
import { describe, it, expect, beforeEach, vi } from 'vitest'
import { createPinia, setActivePinia } from 'pinia'
import SubscriptionModals from '../home/SubscriptionModals.vue'
import { useSubscriptionStore } from '../../stores/subscription'

describe('SubscriptionModals Component', () => {
  beforeEach(() => {
    setActivePinia(createPinia())
  })

  it('renders nothing when currentModal is null', () => {
    const store = useSubscriptionStore()
    store.currentModal = null

    const wrapper = mount(SubscriptionModals)
    expect(wrapper.find('.p-8').exists()).toBe(false)
    expect(wrapper.find('.p-6').exists()).toBe(false)
  })

  it('renders mobile input modal when currentModal is mobile, and triggers sendOtp on submit click', async () => {
    const store = useSubscriptionStore()
    store.currentModal = 'mobile'
    store.userName = 'Alice'
    store.mobileNumber = '9876543210'

    const sendOtpSpy = vi.spyOn(store, 'sendOtp').mockImplementation(() => Promise.resolve())

    const wrapper = mount(SubscriptionModals)

    expect(wrapper.text()).toContain('Verify Mobile')
    expect(wrapper.find('input[placeholder="Enter your full name"]').exists()).toBe(true)
    expect(wrapper.find('input[placeholder="Enter 10 digit number"]').exists()).toBe(true)

    const submitBtn = wrapper.find('.p-8 button')
    expect(submitBtn.text()).toContain('Send OTP')

    await submitBtn.trigger('click')
    expect(sendOtpSpy).toHaveBeenCalled()
  })

  it('renders OTP modal when currentModal is otp, and triggers verifyOtpAndCheckout on submit click', async () => {
    const store = useSubscriptionStore()
    store.currentModal = 'otp'
    store.termsAccepted = true
    store.otp = ['1', '2', '3', '4', '5', '6']

    const verifySpy = vi.spyOn(store, 'verifyOtpAndCheckout').mockImplementation(() => Promise.resolve())

    const wrapper = mount(SubscriptionModals)

    expect(wrapper.text()).toContain('Enter OTP')
    const otpInputs = wrapper.findAll('input[type="text"]')
    expect(otpInputs.length).toBe(6)

    const submitBtn = wrapper.find('.p-8 button')
    expect(submitBtn.text()).toContain('VERIFY & PAY')

    await submitBtn.trigger('click')
    expect(verifySpy).toHaveBeenCalled()
  })

  it('renders already_subscribed modal when currentModal is already_subscribed', () => {
    const store = useSubscriptionStore()
    store.currentModal = 'already_subscribed'
    store.error = null

    const wrapper = mount(SubscriptionModals)
    expect(wrapper.text()).toContain('Already Subscribed!')
    expect(wrapper.text()).toContain('Go to Course Signup')
  })

  it('renders terms popup when showTermsText is true', async () => {
    const store = useSubscriptionStore()
    store.currentModal = 'otp'

    const wrapper = mount(SubscriptionModals)
    expect(wrapper.text()).not.toContain('Platform & Content Access')

    // Click terms link
    const termsLink = wrapper.find('a.underline')
    await termsLink.trigger('click')

    expect(wrapper.text()).toContain('Terms & Conditions')
    expect(wrapper.text()).toContain('Platform & Content Access')
  })

  it('shows error messages when store.error is active', () => {
    const store = useSubscriptionStore()
    store.currentModal = 'mobile'
    store.error = 'API connection error'

    const wrapper = mount(SubscriptionModals)
    expect(wrapper.text()).toContain('API connection error')
  })

  it('shows loading spinners when store.loading is true', () => {
    const store = useSubscriptionStore()
    store.currentModal = 'mobile'
    store.loading = true

    const wrapper = mount(SubscriptionModals)
    expect(wrapper.find('.animate-spin').exists()).toBe(true)
    expect(wrapper.find('.p-8 button').text()).toContain('Sending...')
  })
})
