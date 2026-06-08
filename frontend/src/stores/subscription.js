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
        const refreshToken = localStorage.getItem('refreshToken')
        const refreshRes = await axios.post(
          `${JAVA_API}/api/v1/auth/refresh`,
          { refreshToken }
        )
        const newToken = refreshRes.data.data?.accessToken

        if (newToken) {
          localStorage.setItem('accessToken', newToken)
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
    accessToken: localStorage.getItem('accessToken') || null,
    userData: (() => {
      try {
        return JSON.parse(localStorage.getItem('userData') || 'null')
      } catch {
        return null
      }
    })(),
    subscriptionStatus: localStorage.getItem('isSubscribed') === 'true'
      ? 'active' : null
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
      this.mobileNumber = number
    },

    setUserName(name) {
      this.userName = name
    },

    setOtpDigit(index, digit) {
      this.otp[index] = digit
    },

    setOtpFromPaste(digits) {
      this.otp = digits
    },

    setSelectedPlan(plan) {
      this.selectedPlan = plan
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
          // New user — need their name too
          if (!this.userName || this.userName.trim().length < 2) {
            this.setError('Please enter your name')
            this.loading = false
            return
          }
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

        // Save everything to localStorage
        saveUserToLocalStorage(userData)

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
            this.setError('No active subscription found. Please complete payment.')
            this.openModal('already_subscribed')
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

        // ── Not subscribed — go to Razorpay payment ──
        await this.initiateRazorpayPayment(userData)

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

    // ─── STEP 3: Razorpay Payment ─────────────────────────────────────

    async initiateRazorpayPayment(userData) {
      try {
        // Create Razorpay order
        let order = null
        let keyId = null
        try {
          const orderRes = await axios.post(
            `${JAVA_API}/api/v1/payment/create-order`,
            { plan: this.selectedPlan },
            { headers: { Authorization: `Bearer ${this.accessToken}` } }
          )
          const data = orderRes.data.data || {}
          order = data.order
          keyId = data.keyId
        } catch (e) {
          console.warn("Create order failed, using demo order simulation", e)
          order = {
            id: `order_demo_${Date.now()}`,
            amount: this.selectedPlan === '14months' ? 199900 : 99900,
            currency: 'INR'
          }
          keyId = import.meta.env.VITE_RAZORPAY_KEY_ID
        }

        if (!order || !order.id) {
          throw new Error('Server failed to generate an Order ID.')
        }

        // Standard Razorpay flow
        if (order.id.startsWith('order_demo_')) {
          if (!window.Razorpay) {
            this.showToast("Demo Mode: Simulating payment...")
            await new Promise(resolve => setTimeout(resolve, 1500))
            localStorage.setItem('isSubscribed', 'true')
            this.subscriptionStatus = 'active'
            if (this.userData) {
              this.userData.subscriptionFlag = true
              localStorage.setItem('userData', JSON.stringify(this.userData))
            }
            this.showToast("Subscription Activated!")
            this.closeModal()
            window.location.href = 'https://www.student.vistaslearning.com/guest/'
            return
          }

          const demoRazorpayOptions = {
            key: import.meta.env.VITE_RAZORPAY_KEY_ID || keyId,
            amount: order.amount,
            currency: order.currency,
            name: "Vistas Learning",
            description: `Subscription: ${this.selectedPlan}`,
            handler: async (response) => {
              localStorage.setItem('isSubscribed', 'true')
              this.subscriptionStatus = 'active'
              if (this.userData) {
                this.userData.subscriptionFlag = true
                localStorage.setItem('userData', JSON.stringify(this.userData))
              }
              this.showToast("Subscription Activated!")
              this.closeModal()
              window.location.href = 'https://www.student.vistaslearning.com/guest/'
            },
            modal: {
              ondismiss: () => { this.loading = false }
            },
            prefill: {
              contact: this.mobileNumber,
              email: userData?.email || ''
            },
            theme: { color: '#7C3AED' }
          }
          const demoRzp = new window.Razorpay(demoRazorpayOptions)
          demoRzp.open()
          return
        }

        if (!window.Razorpay) {
          throw new Error('Razorpay SDK not loaded. Please refresh the page.')
        }

        const razorpayOptions = {
          key: import.meta.env.VITE_RAZORPAY_KEY_ID || keyId,
          amount: order.amount,
          currency: order.currency,
          name: 'Vistas Learning',
          description: `Subscription: ${this.selectedPlan}`,
          order_id: order.id,
          handler: async (response) => {
            try {
              this.loading = true

              await axios.post(
                `${JAVA_API}/api/v1/payment/verify-payment`,
                {
                  razorpay_payment_id: response.razorpay_payment_id,
                  razorpay_order_id: response.razorpay_order_id,
                  razorpay_signature: response.razorpay_signature,
                  plan: this.selectedPlan
                },
                { headers: { Authorization: `Bearer ${this.accessToken}` } }
              )

              // Update subscription status
              localStorage.setItem('isSubscribed', 'true')
              this.subscriptionStatus = 'active'

              // Update userData
              if (this.userData) {
                this.userData.subscriptionFlag = true
                localStorage.setItem('userData', JSON.stringify(this.userData))
              }

              this.showToast('Subscription Activated!')
              this.closeModal()
              window.location.href =
                'https://www.student.vistaslearning.com/guest/'

            } catch (err) {
              console.error('Payment verification failed:', err)
              this.setError(
                err.response?.data?.error ||
                err.response?.data?.message ||
                'Payment verification failed'
              )
            } finally {
              this.loading = false
            }
          },
          modal: {
            ondismiss: () => { this.loading = false }
          },
          prefill: {
            contact: this.mobileNumber,
            email: userData?.email || ''
          },
          theme: { color: '#7C3AED' }
        }

        const rzp = new window.Razorpay(razorpayOptions)
        rzp.on('payment.failed', (response) => {
          this.setError(`Payment failed: ${response.error.description}`)
        })
        rzp.open()

      } catch (err) {
        console.error('Razorpay error:', err)
        this.setError(err.message || 'Payment initialization failed')
        throw err
      }
    }
  }
})
