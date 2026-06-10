import { defineStore } from 'pinia'
import axios from 'axios'
import {
  checkMobileExists,
  sendOtpExistingUser,
  sendOtpNewUser,
  verifyOtpExistingUser,
  verifyOtpNewUser,
  registerNewUser
} from '../services/api'
import {
  saveUserToLocalStorage,
  clearUserFromLocalStorage,
  getUserFromLocalStorage,
  isLoggedIn,
  isSubscribed
} from '../services/storage'

const JAVA_API = 'https://api-prod.vistaslearning.com'

// Axios interceptor for token refresh
let isRefreshing = false
let refreshSubscribers = []

function subscribeTokenRefresh(cb) {
  refreshSubscribers.push(cb)
}

function onRefreshed(token) {
  refreshSubscribers.map(cb => cb(token))
  refreshSubscribers = []
}

axios.interceptors.response.use(
  (response) => response,
  async (error) => {
    const { config, response } = error
    const originalRequest = config

    if (
      response &&
      response.status === 401 &&
      !originalRequest._retry &&
      !originalRequest.url.includes('/auth/refresh')
    ) {
      if (isRefreshing) {
        return new Promise((resolve) => {
          subscribeTokenRefresh((token) => {
            originalRequest.headers['Authorization'] = `Bearer ${token}`
            resolve(axios(originalRequest))
          })
        })
      }

      originalRequest._retry = true
      isRefreshing = true

      try {
        const auth = getUserFromLocalStorage()
        const refreshToken = auth.refreshToken
        const refreshRes = await axios.post(
          `${JAVA_API}/api/v1/auth/refresh`,
          { refreshToken }
        )
        const newToken = refreshRes.data.data?.accessToken

        if (newToken) {
          auth.accessToken = newToken
          localStorage.setItem('vista-auth', JSON.stringify(auth))
          const store = useSubscriptionStore()
          store.accessToken = newToken
          onRefreshed(newToken)
          originalRequest.headers['Authorization'] = `Bearer ${newToken}`
          isRefreshing = false
          return axios(originalRequest)
        }
      } catch (refreshErr) {
        isRefreshing = false
        clearUserFromLocalStorage()
        const store = useSubscriptionStore()
        store.accessToken = null
        store.userData = null
        return Promise.reject(refreshErr)
      }
    }
    return Promise.reject(error)
  }
)

export const useSubscriptionStore = defineStore('subscription', {
  state: () => ({
    // Modal control — keeps all your existing modal names
    currentModal: null,
    // null | 'comparison' | 'mobile' | 'otp' | 'already_subscribed' | 'terms'

    selectedPlan: null,
    mobileNumber: '',
    userName: '',
    otp: ['', '', '', '', '', ''],

    loading: false,
    error: null,
    toastMessage: null,
    termsAccepted: false,
    termsError: false,

    // Flow control
    isExistingUser: false,
    isStatusChecking: false,

    // Auth — loaded from localStorage on startup
    accessToken: getUserFromLocalStorage().accessToken,
    userData: getUserFromLocalStorage().userData,
    subscriptionStatus: getUserFromLocalStorage().isSubscribed ? 'active' : null,

    // Products & Plans
    productList: [],
    productMap: {},
    selectedProductObj: null,

    // Subscription status check
    subscriptionCheckDone: false,
    existingSubscription: false,
    subscriptionExpired: false
  }),

  actions: {

    // Helper to set error with auto-dismiss after 5 seconds
    setError(msg) {
      this.error = msg
      if (msg) {
        setTimeout(() => {
          if (this.error === msg) {
            this.error = null
          }
        }, 5000)
      }
    },

    // ─── Modal helpers ──────────────────────────────────────────────

    openModal(type, planId = null) {
      this.currentModal = type
      if (planId) this.selectedPlan = planId
      this.error = null
      if (type === 'terms') this.termsAccepted = false
    },

    closeModal() {
      this.currentModal = null
      this.otp = ['', '', '', '', '', '']
      this.error = null
      this.loading = false
      this.isStatusChecking = false
    },

    showToast(msg) {
      this.toastMessage = msg
      setTimeout(() => { this.toastMessage = null }, 3000)
    },

    setMobileNumber(number) {
      this.mobileNumber = number.replace(/[^0-9]/g, '')
    },

    setUserName(name) {
      this.userName = name.replace(/[0-9]/g, '')
    },

    setOtpDigit(index, digit) {
      this.otp[index] = digit
    },

    setOtpFromPaste(digits) {
      this.otp = digits
    },

    setSelectedPlan(plan) {
      this.selectedPlan = plan
      this.selectedProductObj = this.productMap[plan] || null
    },

    toggleTermsAccepted() {
      this.termsAccepted = !this.termsAccepted
    },

    triggerValidation() {
      this.termsError = true
      this.openModal('terms')
      const tcSection = document.querySelector('.pricing-shell')
      if (tcSection) {
        tcSection.scrollIntoView({ behavior: 'smooth', block: 'center' })
      }
      setTimeout(() => { this.termsError = false }, 3000)
    },

    // ─── Plan selection ──────────────────────────────────────────────

    handlePlanSelect(planId) {
      this.selectedPlan = planId
      this.selectedProductObj = this.productMap[planId] || null
      this.termsAccepted = false

      if (planId === '3months' || planId === '6months') {
        this.openModal('comparison', planId)
      } else {
        this.openModal('mobile')
      }
    },

    // ─── Logout ──────────────────────────────────────────────────────

    logout() {
      clearUserFromLocalStorage()
      this.accessToken = null
      this.userData = null
      this.subscriptionStatus = null
    },

    // ─── Verify subscription for already-logged-in users ─────────────

    async verifySubscriptionAndAccess() {
      this.isStatusChecking = true
      this.openModal('mobile')
    },

    // ─── STEP 1: Send OTP ─────────────────────────────────────────────

    async sendOtp() {
      if (this.loading) return

      if (!this.userName || this.userName.trim().length < 2) {
        this.setError('Please enter your name')
        return
      }

      if (/[0-9]/.test(this.userName)) {
        this.setError('Name cannot contain numbers')
        return
      }

      if (this.mobileNumber.length !== 10) {
        this.setError('Please enter a valid 10-digit mobile number')
        return
      }

      this.loading = true
      this.error = null

      try {
        // Check if this mobile number already exists in the system
        const checkResult = await checkMobileExists(this.mobileNumber)
        this.isExistingUser = checkResult.data === true

        if (this.isExistingUser) {
          // Existing registered user — use login OTP endpoint
          const res = await sendOtpExistingUser(this.mobileNumber)
          if (res && (res.status === false || res.statusCode === 500)) {
            throw new Error(res.message || 'Failed to send OTP')
          }
        } else {
          // New user — send OTP directly (name already validated above)
          const res = await sendOtpNewUser(this.mobileNumber, this.userName)
          if (res && (res.data === false || res.statusCode === 500)) {
            throw new Error(res.message || 'Failed to send OTP')
          }
        }

        this.openModal('otp')
        this.showToast('OTP sent to your mobile')

      } catch (err) {
        console.error('Send OTP error:', err)
        let errMsg = err.message || 'Failed to send OTP. Please try again.'
        try {
          const parsed = JSON.parse(errMsg)
          if (parsed && parsed.message) {
            errMsg = parsed.message
          }
        } catch (e) {
          // Keep original string if not JSON
        }
        this.setError(errMsg)
      } finally {
        this.loading = false
      }
    },

    // ─── STEP 2: Verify OTP and proceed to checkout ───────────────────

    async verifyOtpAndCheckout() {
      if (this.loading) return

      if (!this.termsAccepted) {
        this.setError('Please agree to the Terms & Conditions to proceed')
        return
      }

      const fullOtp = this.otp.join('')
      if (fullOtp.length !== 6) {
        this.setError('Enter a valid 6-digit OTP')
        return
      }

      this.loading = true
      this.error = null

      try {
        let userData = null

        if (this.isExistingUser) {
          // Existing user — verify OTP and get token directly
          const result = await verifyOtpExistingUser(
            this.mobileNumber,
            fullOtp
          )

          // Backend returns status:true or statusCode:200 on success
          if (!result.status && result.statusCode !== 200) {
            this.setError(result.message || 'Invalid OTP. Please try again.')
            this.loading = false
            return
          }

          userData = result.data

        } else {
          // New user — first verify OTP
          const verifyResult = await verifyOtpNewUser(
            this.mobileNumber,
            fullOtp
          )

          if (!verifyResult.data) {
            this.setError('Invalid OTP. Please try again.')
            this.loading = false
            return
          }

          // Then register the user
          const registerResult = await registerNewUser(
            this.mobileNumber,
            this.userName,
            fullOtp
          )

          if (registerResult.statusCode !== 200) {
            this.setError(registerResult.message || 'Registration failed.')
            this.loading = false
            return
          }

          userData = registerResult.data
        }

        // Save everything to localStorage asynchronously to avoid blocking main thread
        setTimeout(() => {
          saveUserToLocalStorage(userData)
        }, 0)

        // Update store state
        this.accessToken = userData.accessToken
        this.userData = userData

        const subFlag = userData.subscriptionFlag
        const alreadySubscribed =
          subFlag === true ||
          (subFlag && subFlag.subscribedFlag === true)

        this.error = null

        // ── Handle status checking flow (verify subscription) ──
        if (this.isStatusChecking) {
          if (alreadySubscribed) {
            this.subscriptionStatus = 'active'
            this.showToast('Subscription Verified!')
            this.openModal('already_subscribed')
          } else {
            this.openModal('already_subscribed')
            this.setError('No active subscription found. Please complete payment.')
          }
          this.loading = false
          return
        }

        // ── Already subscribed — skip payment ──
        if (alreadySubscribed) {
          this.subscriptionStatus = 'active'
          this.openModal('already_subscribed')
          this.loading = false
          return
        }

        // ── Activate subscription directly ──
        try {
          const auth = getUserFromLocalStorage()
          const token = auth.accessToken

          if (!this.selectedProductObj) {
            this.setError('Plan details not found. Please try again.')
            this.loading = false
            return
          }

          // Call backend to generate Paytm transaction
          const payRes = await fetch(
            'https://api-prod.vistaslearning.com/api/v2/subscription/user-newsubscription',
            {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
              },
              body: JSON.stringify(this.selectedProductObj)
            }
          )

          const payData = await payRes.json()

          if (!payRes.ok || !payData.data || !payData.data.paymentParameters) {
            this.setError(payData.message || 'Payment initiation failed. Please try again.')
            this.loading = false
            return
          }

          // Build hidden form and submit to Paytm gateway
          const params = payData.data.paymentParameters
          const form = document.createElement('form')
          form.method = 'POST'
          form.action = import.meta.env.VITE_PAYTM_URL || 'https://securegw.paytm.in/order/process'

          Object.entries(params).forEach(([key, value]) => {
            if (key !== 'paytmUrl' && key !== 'txnUrl' && key !== 'gatewayUrl') {
              const input = document.createElement('input')
              input.type = 'hidden'
              input.name = key
              input.value = value
              form.appendChild(input)
            }
          })

          document.body.appendChild(form)
          form.submit()

        } catch (err) {
          console.error('Payment initiation error:', err)
          this.setError('Payment initiation failed. Please try again.')
          this.loading = false
        }

      } catch (err) {
        console.error('Verify OTP error:', err)
        let errMsg = err.message || 'Something went wrong. Please try again.'
        try {
          const parsed = JSON.parse(errMsg)
          if (parsed && parsed.message) {
            errMsg = parsed.message
          }
        } catch (e) {
          // Keep original string if not JSON
        }
        this.setError(errMsg)
      } finally {
        this.loading = false
      }
    },

    async fetchSubscriptionProducts() {
      if (this.productList.length > 0) return
      try {
        const res = await fetch('https://api-prod.vistaslearning.com/api/v2/Product/user-subscription-product?productCd=AD_FREE_SUBSCRIPTION')
        const json = await res.json()
        if (json && Array.isArray(json.data)) {
          this.productList = json.data
          const mapping = {}
          json.data.forEach(product => {
            if (product.duration === 90 || product.durationCode === 'QUARTER') {
              mapping['3months'] = product
            } else if (product.duration === 180 || product.durationCode === 'HALF_YEAR') {
              mapping['6months'] = product
            } else if (
              (product.duration === 365 || product.durationCode === 'ANNUAL') &&
              product.productCd === 'AD_FREE_SUBSCRIPTION'
            ) {
              mapping['14months'] = product
            }
          })
          this.productMap = mapping
        }
      } catch (err) {
        console.error('Error fetching subscription products:', err)
      }
    },

    async checkExistingSubscription() {
      if (this.subscriptionCheckDone) return
      try {
        const userSurId = getUserFromLocalStorage().userSurId
        if (!userSurId) {
          this.subscriptionCheckDone = true
          return
        }
        const accessToken = getUserFromLocalStorage().accessToken
        const res = await fetch(
          `https://api-prod.vistaslearning.com/api/v1/subscription/check-user-subscription?userSurId=${userSurId}`,
          {
            headers: {
              'Authorization': `Bearer ${accessToken}`
            }
          }
        )
        const json = await res.json()
        if (json && json.data) {
          if (json.data.existingSubscription === true && json.data.expiredSubscription === false) {
            this.existingSubscription = true
          }
          if (json.data.expiredSubscription === true) {
            this.subscriptionExpired = true
          }
        }
      } catch (err) {
        // fail silently
      } finally {
        this.subscriptionCheckDone = true
      }
    }
  }
})
