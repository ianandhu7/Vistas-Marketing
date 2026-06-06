import { defineStore } from 'pinia'
import axios from 'axios'
import { auth } from '../lib/firebase'
import { RecaptchaVerifier, signInWithPhoneNumber } from 'firebase/auth'

// Use the configured VITE_API_URL if it exists
// If in production mode, fallback to the Render backend URL
// If in development mode, fallback to local backend URL
const API_URL = `${import.meta.env.VITE_API_URL}/api/v1`

if (!import.meta.env.VITE_API_URL) {
  throw new Error('[Vistas] VITE_API_URL is not defined. Add it to your .env file.')
}
const IS_DEMO_MODE = import.meta.env.VITE_DEMO_MODE === 'true'

// Automatically bypass ngrok landing warnings for API requests
// axios.defaults.headers.common['ngrok-skip-browser-warning'] = 'true'

// Response interceptor to automatically refresh access token on 401 Unauthorized
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

    if (response && response.status === 401 && !originalRequest._retry && !originalRequest.url.includes('/auth/refresh')) {
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
        const refreshRes = await axios.post(`${API_URL}/auth/refresh`, {}, { withCredentials: true })
        const newToken = refreshRes.data.data?.token

        if (newToken) {
          localStorage.setItem('authToken', newToken)

          // Dynamically fetch and update the Pinia store state
          const store = useSubscriptionStore()
          store.authToken = newToken

          onRefreshed(newToken)
          originalRequest.headers['Authorization'] = `Bearer ${newToken}`
          isRefreshing = false
          return axios(originalRequest)
        }
      } catch (refreshErr) {
        isRefreshing = false
        // If refreshing fails, clear session states and log user out
        localStorage.removeItem('authToken')
        localStorage.removeItem('user')
        const store = useSubscriptionStore()
        store.authToken = null
        store.user = null
        return Promise.reject(refreshErr)
      }
    }
    return Promise.reject(error)
  }
)

export const useSubscriptionStore = defineStore('subscription', {
  state: () => ({
    currentModal: null,
    selectedPlan: null,
    mobileNumber: '',
    otp: ['', '', '', '', '', ''],
    loading: false,
    error: null,
    confirmationResult: null,
    toastMessage: null,
    termsAccepted: false,
    termsError: false,
    authToken: localStorage.getItem('authToken') || null,
    user: (() => {
      const val = localStorage.getItem('user')
      if (!val || val === 'undefined') return null
      try {
        return JSON.parse(val)
      } catch (e) {
        return null
      }
    })(),
    subscriptionStatus: localStorage.getItem('subscriptionStatus') || null,
    isStatusChecking: false
  }),

  actions: {
    async verifySubscriptionAndAccess() {
      this.isStatusChecking = true
      this.termsAccepted = true // bypass terms acceptance requirement for quick verification
      // Always prompt user to input and verify mobile number first (re-verifying session)
      this.openModal('mobile')
    },

    clearRecaptcha() {
      if (window.recaptchaVerifier) {
        window.recaptchaVerifier.clear()
        window.recaptchaVerifier = null
      }
    },

    logout() {
      this.authToken = null
      this.user = null
      this.subscriptionStatus = null
      localStorage.removeItem('authToken')
      localStorage.removeItem('userPhone')
      localStorage.removeItem('user')
      localStorage.removeItem('subscriptionStatus')
    },

    setMobileNumber(number) {
      this.mobileNumber = number
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

      // Open the Terms error modal
      this.openModal('terms')

      // Scroll to T&C section if not in view
      const tcSection = document.querySelector('.pricing-shell')
      if (tcSection) {
        tcSection.scrollIntoView({ behavior: 'smooth', block: 'center' })
      }
      setTimeout(() => {
        this.termsError = false
      }, 3000)
    },

    openModal(type, planId = null) {
      this.currentModal = type
      if (planId) this.selectedPlan = planId
      this.error = null

      if (type === 'terms') {
        this.termsAccepted = false
      }
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
      setTimeout(() => {
        this.toastMessage = null
      }, 3000)
    },

    handlePlanSelect(planId) {
      this.selectedPlan = planId
      this.termsAccepted = false

      if (planId === '3months' || planId === '6months') {
        this.openModal('comparison', planId)
      } else {
        this.openModal('mobile')
      }
    },

    async sendOtp(recaptchaContainerId) {
      if (this.loading) return
      this.clearRecaptcha()
      if (this.mobileNumber.length !== 10) {
        this.error = "Please enter a valid 10-digit mobile number"
        return
      }

      this.loading = true
      this.error = null

      // DEMO MODE: When VITE_DEMO_MODE=true, bypass Firebase OTP for all numbers.
      // This must NEVER be true in production — set VITE_DEMO_MODE=false in .env.production
      if (IS_DEMO_MODE) {
        console.warn("DEMO MODE ENABLED — Firebase OTP bypassed for all numbers")
        this.confirmationResult = { isDemo: true }
        this.openModal('otp')
        this.showToast("Demo Mode: Use OTP 123456")
        this.loading = false
        return
      }

      try {
        // Ensure container exists before initializing
        const container = document.getElementById(recaptchaContainerId)
        if (!container) {
          throw new Error("Recaptcha container not found. Please refresh.")
        }

        if (window.recaptchaVerifier) {
          try {
            window.recaptchaVerifier.clear()
            const container = document.getElementById(recaptchaContainerId)
            if (container) container.innerHTML = '' // Force clear container
          } catch (e) {
            console.warn("Error clearing reCAPTCHA:", e)
          }
          window.recaptchaVerifier = null
        }

        window.recaptchaVerifier = new RecaptchaVerifier(auth, recaptchaContainerId, {
          size: 'invisible'
        })

        const phoneNumber = `+91${this.mobileNumber}`
        this.confirmationResult = await signInWithPhoneNumber(auth, phoneNumber, window.recaptchaVerifier)

        this.openModal('otp')
        this.showToast("OTP sent to your mobile")
      } catch (err) {
        console.error("Firebase Send OTP Error:", err)
        const msg = err.message || ""

        if (IS_DEMO_MODE) {
          console.warn("DEMO MODE ACTIVATED: Firebase bypass triggered due to:", err)
          this.confirmationResult = { isDemo: true }
          this.openModal('otp')
          this.showToast("Demo Mode: Use OTP 123456")
          return
        }

        this.error = err.code === 'auth/billing-not-enabled'
          ? "SMS service is temporarily unavailable. Please contact support."
          : (err.message || "Failed to send OTP")
      } finally {
        this.loading = false
      }
    },

    async verifyOtpAndCheckout() {
      if (this.loading) return
      if (!this.termsAccepted) {
        this.error = "Please agree to the Terms & Conditions to proceed"
        return
      }

      const fullOtp = this.otp.join('')
      if (fullOtp.length !== 6) {
        this.error = "Enter a valid 6-digit OTP"
        return
      }

      this.loading = true
      this.error = null

      try {
        // 1. Verify OTP
        let idToken = null;
        if (this.confirmationResult?.isDemo) {
          if (fullOtp === '123456') idToken = "demo-token"
          else throw new Error("Invalid Demo OTP. Use 123456")
        } else {
          if (!this.confirmationResult) throw new Error("Session expired.")
          const result = await this.confirmationResult.confirm(fullOtp)
          idToken = await result.user.getIdToken()
        }

        this.clearRecaptcha()

        // 2. Sync with Backend
        const authRes = await axios.post(`${API_URL}/auth/verify-otp`, {
          idToken,
          phoneNumber: this.mobileNumber,
          otp: fullOtp,
          planId: this.selectedPlan
        })
        const token = authRes.data.data?.token
        const userObject = authRes.data.data?.user
        this.authToken = token
        this.user = userObject
        localStorage.setItem('authToken', token)
        localStorage.setItem('user', JSON.stringify(userObject))

        // Reset error state in case previous validation set it
        this.error = null

        // Differentiate Checkout vs Status Checking Verification Flow
        if (this.isStatusChecking) {
          try {
            const res = await axios.post(`${API_URL}/payment/verify-status`, {}, {
              headers: { Authorization: `Bearer ${this.authToken}` }
            })

            if (res.data.data?.isSubscribed) {
              this.user.isSubscribed = true
              this.user.subscriptionPlan = res.data.data.plan
              localStorage.setItem('user', JSON.stringify(this.user))
              this.subscriptionStatus = 'active'
              localStorage.setItem('subscriptionStatus', 'active')

              this.showToast("Subscription Verified!")
              
              // Open already_subscribed success layout showing the access link button
              this.openModal('already_subscribed')
            } else {
              this.error = "No active subscription found. Please complete payment or contact support."
              this.openModal('already_subscribed')
            }
          } catch (err) {
            console.error("Subscription verification failed:", err)
            this.error = err.response?.data?.error || err.response?.data?.message || "No active subscription found. Please complete payment or contact support."
            this.openModal('already_subscribed')
          } finally {
            this.loading = false
          }
          return
        }

        // If user already has an active subscription, show already_subscribed modal
        if (this.user.isSubscribed) {
          this.openModal('already_subscribed')
          this.loading = false
          return
        }

        // 3. Create Razorpay Order
        const orderRes = await axios.post(`${API_URL}/payment/create-order`,
          { plan: this.selectedPlan },
          { headers: { Authorization: `Bearer ${this.authToken}` } }
        )
        const { order, keyId } = orderRes.data.data || {}

        if (!order || !order.id) {
          throw new Error("Server failed to generate an Order ID. Check backend logs.")
        }

        const orderId = order.id
        const amount = order.amount
        const currency = order.currency

        // 4. Razorpay Checkout
        // DEMO MODE: Backend returned a demo order (placeholder Razorpay credentials).
        // Open Razorpay in standard checkout mode using the frontend test key — no order_id required.
        if (orderId.startsWith('order_demo_')) {
          console.warn('DEMO MODE: Opening Razorpay in standard checkout mode (no backend order ID)')

          if (!window.Razorpay) {
            // Razorpay script not loaded at all — full simulation fallback
            this.showToast("Demo Mode: Simulating payment...")
            await new Promise(resolve => setTimeout(resolve, 1500))
            if (this.user) {
              this.user.isSubscribed = true
              this.user.subscriptionPlan = this.selectedPlan
              localStorage.setItem('user', JSON.stringify(this.user))
            }
            this.subscriptionStatus = 'active'
            localStorage.setItem('subscriptionStatus', 'active')
            this.showToast("Demo: Subscription Activated! 🎉")
            this.closeModal()
            return
          }

          // Open real Razorpay modal without order_id (standard checkout mode)
          const demoRazorpayOptions = {
            key: import.meta.env.VITE_RAZORPAY_KEY_ID,
            amount: order.amount || 99900,
            currency: order.currency || 'INR',
            name: "Vistas Learning",
            description: `Subscription: ${this.selectedPlan}`,
            // No order_id — Razorpay standard checkout mode
            handler: async (response) => {
              try {
                this.loading = true
                console.warn('DEMO MODE: Payment completed, activating subscription locally')
                // Demo mode — skip verify-payment backend call, activate locally
                if (this.user) {
                  this.user.isSubscribed = true
                  this.user.subscriptionPlan = this.selectedPlan
                  localStorage.setItem('user', JSON.stringify(this.user))
                }
                this.subscriptionStatus = 'active'
                localStorage.setItem('subscriptionStatus', 'active')
                this.showToast("Subscription Activated!")
                this.closeModal()
                window.location.href = 'https://www.student.vistaslearning.com/guest/'
              } catch (err) {
                this.error = "Payment activation failed"
              } finally {
                this.loading = false
              }
            },
            modal: {
              ondismiss: () => { this.loading = false }
            },
            prefill: {
              contact: this.mobileNumber,
              email: this.user?.email || ""
            },
            theme: { color: "#7C3AED" }
          }

          const demoRzp = new window.Razorpay(demoRazorpayOptions)
          demoRzp.on('payment.failed', (response) => {
            console.error("PAYMENT FAILURE RESPONSE:", response.error)
            this.error = `Payment failed: ${response.error.description}`
          })
          demoRzp.open()
          return
        }

        // Real Razorpay Checkout — only reached when real credentials are configured
        if (!window.Razorpay) {
          throw new Error("Razorpay SDK not loaded. Please check your internet or refresh the page.")
        }

        const razorpayOptions = {
          key: import.meta.env.VITE_RAZORPAY_KEY_ID || keyId,
          amount,
          currency,
          name: "Vistas Learning",
          description: `Subscription: ${this.selectedPlan}`,
          order_id: orderId,
          handler: async (response) => {
            try {
              this.loading = true
              await axios.post(`${API_URL}/payment/verify-payment`,
                {
                  razorpay_payment_id: response.razorpay_payment_id,
                  razorpay_order_id: response.razorpay_order_id,
                  razorpay_signature: response.razorpay_signature,
                  plan: this.selectedPlan
                },
                { headers: { Authorization: `Bearer ${this.authToken}` } }
              )

              if (this.user) {
                this.user.isSubscribed = true
                this.user.subscriptionPlan = this.selectedPlan
                localStorage.setItem('user', JSON.stringify(this.user))
              }

              const status = 'active'
              this.subscriptionStatus = status
              localStorage.setItem('subscriptionStatus', status)

              this.showToast("Subscription Activated!")
              this.closeModal()

              // Redirect directly to external website signup page
              window.location.href = 'https://www.student.vistaslearning.com/guest/'

            } catch (err) {
              console.error("PAYMENT VERIFICATION FAILURE:", err)
              this.error = err.response?.data?.error || err.response?.data?.message || "Payment verification failed"
            } finally {
              this.loading = false
            }
          },
          modal: {
            ondismiss: () => {
              this.loading = false
            }
          },
          prefill: {
            contact: this.mobileNumber,
            email: this.user?.email || ""
          },
          theme: { color: "#7C3AED" }
        }

        const rzp = new window.Razorpay(razorpayOptions)

        rzp.on('payment.failed', (response) => {
          console.error("PAYMENT FAILURE RESPONSE:", response.error)
          this.error = `Payment failed: ${response.error.description}`
        })

        rzp.open()

      } catch (err) {
        console.error("Verification/Checkout Error:", err)
        this.error = err.response?.data?.error || err.response?.data?.message || err.message || "Action failed"
      } finally {
        this.loading = false
      }
    }
  }
})
