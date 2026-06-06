<script setup>
import { ref, watch, onUnmounted } from 'vue'
import { useSubscriptionStore } from '../../stores/subscription'

const store = useSubscriptionStore()
const otpInputs = ref([])
const showTermsText = ref(false)
const handleClose = () => {
  store.closeModal()
}


// Handle OTP auto-focus
const handleOtpInput = (e, index) => {
  const val = e.target.value
  if (val && index < 5) {
    const nextInput = e.target.nextElementSibling
    if (nextInput) nextInput.focus()
  }
}

const handleOtpKeyDown = (e, index) => {
  if (e.key === 'Backspace' && !store.otp[index] && index > 0) {
    const prevInput = e.target.previousElementSibling
    if (prevInput) prevInput.focus()
  }
}

const onPaste = (e) => {
  const pasted = e.clipboardData.getData('text').trim()
  if (/^\d{6}$/.test(pasted)) {
    const digits = pasted.split('')
    digits.forEach((d, i) => {
      store.setOtpDigit(i, d)
    })
    otpInputs.value[5]?.focus()
  }
  e.preventDefault()
}

// Timer for Resend OTP
const timer = ref(30)
const timerInterval = ref(null)

const startTimer = () => {
  timer.value = 30
  if (timerInterval.value) clearInterval(timerInterval.value)
  timerInterval.value = setInterval(() => {
    if (timer.value > 0) timer.value--
    else clearInterval(timerInterval.value)
  }, 1000)
}

onUnmounted(() => {
  if (timerInterval.value) {
    clearInterval(timerInterval.value)
    timerInterval.value = null
  }
})

watch(() => store.currentModal, (newModal) => {
  if (newModal === 'otp') {
    startTimer()
    // Small delay to ensure DOM is ready
    setTimeout(() => {
      if (otpInputs.value[0]) otpInputs.value[0].focus()
    }, 100)
  }
})

const switchTo14Months = () => {
  // Set selected plan to 14months and go directly to purchase flow
  store.setSelectedPlan('14months')
  store.openModal('mobile')
}
</script>

<template>
  <div v-if="store.currentModal" class="fixed inset-0 z-[100] flex items-center justify-center p-4">
    <!-- Backdrop -->
    <div class="absolute inset-0 bg-black/70 backdrop-blur-sm" @click="handleClose"></div>
    
    <!-- Modal Container -->
    <div 
      class="relative w-full max-w-[420px] rounded-[24px] shadow-2xl overflow-hidden animate-in fade-in zoom-in duration-300"
      :class="store.currentModal === 'terms' ? 'bg-[#190934] border border-[#2D1B4E]' : 'bg-[var(--bg-card)] border border-[var(--border-light)]'"
    >
      
      <!-- Close Button -->
      <button 
        @click="handleClose" 
        class="absolute top-4 right-4 text-[#A78BFA] hover:text-white transition-colors z-10"
      >
        <span class="material-symbols-outlined">close</span>
      </button>

      <!-- 1. Comparison Modal -->
      <div v-if="store.currentModal === 'comparison'" class="p-6">
        <h3 class="text-xl font-bold text-[var(--text-primary)] mb-2 text-center">Wait! Check This Out 💰</h3>
        <p class="text-[12px] text-[var(--text-secondary)] text-center mb-6">You're choosing a short-term plan. Look at what you're missing!</p>
        
        <div class="overflow-hidden rounded-xl border border-[var(--border-light)] bg-[var(--bg-secondary)] mb-6">
          <table class="w-full text-[11px] text-left border-collapse">
            <thead>
              <tr class="bg-[var(--accent-purple)]/5">
                <th class="p-3 text-[var(--text-secondary)] font-medium">Feature</th>
                <th class="p-3 text-[var(--text-secondary)] font-medium">Short Term</th>
                <th class="p-3 text-[var(--accent-gold)] font-bold">14 Months</th>
              </tr>
            </thead>
            <tbody class="text-[var(--text-primary)]/80">
              <tr class="border-t border-[var(--border-light)]">
                <td class="p-3">Price/month</td>
                <td class="p-3">₹999+</td>
                <td class="p-3 text-[var(--accent-gold)] font-bold">₹143/mo</td>
              </tr>
              <tr class="border-t border-[var(--border-light)]">
                <td class="p-3">Duration</td>
                <td class="p-3">Short</td>
                <td class="p-3">14 Months</td>
              </tr>
              <tr class="border-t border-[var(--border-light)]">
                <td class="p-3">Spoken English</td>
                <td class="p-3">Limited</td>
                <td class="p-3">Full Access</td>
              </tr>
              <tr class="border-t border-[var(--border-light)]">
                <td class="p-3">Future Skills</td>
                <td class="p-3 text-red-500">No</td>
                <td class="p-3 text-green-500 font-bold">Yes</td>
              </tr>
              <tr class="border-t border-[var(--border-light)] bg-[var(--accent-gold)]/10">
                <td class="p-3 font-bold">Savings</td>
                <td class="p-3">₹0</td>
                <td class="p-3 text-[var(--accent-gold)] font-black">₹11,987</td>
              </tr>
            </tbody>
          </table>
        </div>

        <div class="flex flex-col gap-3">
          <button @click="switchTo14Months" class="w-full py-3 bg-[var(--accent-gold)] text-black font-black uppercase tracking-tighter rounded-xl hover:brightness-110 shadow-lg transition-all">
            Switch to 14 Month Plan
          </button>
          <button @click="store.openModal('mobile')" class="w-full py-3 text-white font-black uppercase tracking-tighter rounded-xl hover:brightness-110 shadow-lg transition-all text-xs" style="background-color: #9B41F3;">
            CONTINUE WITH PURCHASE
          </button>
        </div>
      </div>

      <!-- 2. Mobile Number Modal -->
      <div v-if="store.currentModal === 'mobile'" class="p-8">
        <div class="text-center mb-8">
          <div class="w-16 h-16 bg-[var(--accent-purple)]/10 rounded-full flex items-center justify-center mx-auto mb-4">
            <span class="material-symbols-outlined text-[var(--accent-purple)] text-3xl">phone_iphone</span>
          </div>
          <h3 class="text-2xl font-bold text-[var(--text-primary)] mb-2">Verify Mobile</h3>
          <p class="text-sm text-[var(--text-secondary)]">Enter your mobile number to continue with the payment</p>
        </div>

        <div class="space-y-6">
          <div class="relative">
            <div class="absolute left-4 top-1/2 -translate-y-1/2 flex items-center gap-2 border-r border-[var(--border-light)] pr-3">
              <span class="text-[var(--text-primary)]/40 text-sm">🇮🇳</span>
              <span class="text-[var(--text-primary)] font-bold text-sm">+91</span>
            </div>
            <input 
              :value="store.mobileNumber" 
              @input="store.setMobileNumber($event.target.value)"
              type="tel" 
              maxlength="10"
              placeholder="Enter 10 digit number"
              class="w-full bg-[var(--bg-secondary)] border border-[var(--border-light)] rounded-xl py-4 pl-24 pr-4 text-[var(--text-primary)] font-black tracking-[2px] focus:border-[var(--accent-purple)] focus:ring-1 focus:ring-[var(--accent-purple)] outline-none transition-all"
            />
          </div>

          <div v-if="store.error" class="text-red-500 text-xs text-center font-bold bg-red-500/10 py-2 rounded-lg">
            {{ store.error }}
          </div>

          <button 
            @click="store.sendOtp('recaptcha-container')"
            :disabled="store.loading"
            class="w-full py-4 bg-[var(--accent-purple)] text-white font-black uppercase tracking-widest rounded-xl hover:brightness-110 shadow-lg transition-all disabled:opacity-50 flex items-center justify-center gap-2"
          >
            <span v-if="store.loading" class="w-4 h-4 border-2 border-white/30 border-t-white rounded-full animate-spin"></span>
            {{ store.loading ? 'Sending...' : 'Send OTP' }}
          </button>
        </div>
      </div>

      <!-- 3. OTP Modal -->
      <div v-if="store.currentModal === 'otp'" class="p-8">
        <div class="text-center mb-8">
          <div class="w-16 h-16 bg-[var(--accent-purple)]/10 rounded-full flex items-center justify-center mx-auto mb-4">
            <span class="material-symbols-outlined text-[var(--accent-purple)] text-3xl">shield_lock</span>
          </div>
          <h3 class="text-2xl font-bold text-[var(--text-primary)] mb-2">Enter OTP</h3>
          <p class="text-sm text-[var(--text-secondary)]">Sent to +91 {{ store.mobileNumber }}</p>
        </div>

        <div class="space-y-8">
          <div class="flex justify-between gap-2">
            <input 
              v-for="i in 6" 
              :key="i"
              ref="otpInputs"
              :value="store.otp[i-1]" 
              type="text" 
              maxlength="1"
              class="w-12 h-14 bg-[var(--bg-secondary)] border border-[var(--border-light)] rounded-xl text-center text-xl font-black text-[var(--accent-purple)] focus:border-[var(--accent-purple)] focus:ring-1 focus:ring-[var(--accent-purple)] outline-none transition-all"
              @input="store.setOtpDigit(i-1, $event.target.value); handleOtpInput($event, i-1)"
              @keydown="handleOtpKeyDown($event, i-1)"
              @paste="onPaste"
            />
          </div>
           <!-- Terms & Conditions checkbox block matching reference image -->
          <div 
            class="w-full flex items-center gap-3 p-3 border border-[var(--border-light)] rounded-[12px] bg-[#140b2b]/60 cursor-pointer hover:bg-[#1A0B2E]/60 transition-colors"
            @click="store.toggleTermsAccepted()"
          >
            <!-- Custom Checkbox -->
            <div 
              class="w-5 h-5 rounded-[6px] border-[1.5px] flex items-center justify-center transition-all shrink-0"
              :class="store.termsAccepted ? 'bg-[#8B5CF6] border-[#8B5CF6]' : 'border-[#6D28D9]/70 bg-transparent'"
            >
              <span v-if="store.termsAccepted" class="material-symbols-outlined text-[13px] text-white font-black">check</span>
            </div>
            <span class="text-[#E2E8F0] text-[11px] font-bold leading-normal">
              I have read and agree to the 
              <a href="#" @click.stop="showTermsText = true" class="text-[#8B5CF6] hover:text-[#A78BFA] underline underline-offset-2 font-black">Terms & Conditions</a>
            </span>
          </div>

          <div v-if="store.error" class="text-red-500 text-xs text-center font-bold bg-red-500/10 py-2 rounded-lg">
            {{ store.error }}
          </div>

          <button 
            @click="store.verifyOtpAndCheckout()"
            :disabled="store.loading || !store.termsAccepted"
            class="w-full py-4 bg-[var(--accent-purple)] text-white font-black uppercase tracking-widest rounded-xl hover:brightness-110 shadow-lg transition-all disabled:opacity-50 flex items-center justify-center gap-2"
          >
            <span v-if="store.loading" class="w-4 h-4 border-2 border-white/30 border-t-white rounded-full animate-spin"></span>
            {{ store.loading ? 'VERIFYING...' : 'VERIFY & PAY' }}
          </button>

          <div class="text-center">
            <p v-if="timer > 0" class="text-[11px] text-[var(--text-secondary)]">Resend OTP in <span class="text-[var(--text-primary)] font-black">{{ timer }}s</span></p>
            <button v-else @click="store.sendOtp('recaptcha-container')" class="text-[11px] text-[var(--accent-purple)] font-black hover:underline">Resend OTP Now</button>
          </div>
        </div>
      </div>

      <!-- 5. Already Subscribed Modal -->
      <div v-if="store.currentModal === 'already_subscribed'" class="p-8 text-center">
        <div class="w-20 h-20 bg-green-500/10 rounded-full flex items-center justify-center mx-auto mb-6" v-if="!store.error">
          <span class="material-symbols-outlined text-green-500 text-4xl">check_circle</span>
        </div>
        <div class="w-20 h-20 bg-red-500/10 rounded-full flex items-center justify-center mx-auto mb-6" v-else>
          <span class="material-symbols-outlined text-red-500 text-4xl">error</span>
        </div>
        <h3 class="text-2xl font-bold text-[var(--text-primary)] mb-2">
          {{ store.error ? 'Subscription Check' : 'Already Subscribed!' }}
        </h3>
        <p class="text-[14px] text-[var(--text-secondary)] mb-8">
          {{ store.error ? store.error : 'This mobile number has an active subscription plan. You can continue learning on our course platform.' }}
        </p>
        <a 
          v-if="!store.error"
          href="https://www.student.vistaslearning.com/guest/"
          class="w-full py-4 bg-[var(--accent-purple)] text-white font-black uppercase tracking-widest rounded-xl hover:brightness-110 shadow-lg transition-all flex items-center justify-center gap-2"
        >
          Go to Course Signup
          <span class="material-symbols-outlined text-[18px]">open_in_new</span>
        </a>
        <button 
          v-else
          @click="handleClose"
          class="w-full py-4 bg-[var(--accent-purple)] text-white font-black uppercase tracking-widest rounded-xl hover:brightness-110 shadow-lg transition-all flex items-center justify-center gap-2"
        >
          Close
        </button>
      </div>

    </div>
  </div>

  <!-- Permanent reCAPTCHA Container -->
  <div id="recaptcha-container" class="fixed bottom-0 right-0 opacity-0 pointer-events-none"></div>

  <!-- Global Toast -->
  <div 
    v-if="store.toastMessage"
    class="fixed bottom-8 left-1/2 -translate-x-1/2 z-[200] px-6 py-3 bg-[var(--bg-card)] border border-[var(--accent-purple)]/50 rounded-full shadow-2xl animate-in slide-in-from-bottom-10 duration-300"
  >
    <p class="text-[var(--text-primary)] text-xs font-black tracking-wide flex items-center gap-2">
      <span class="material-symbols-outlined text-[16px] text-[var(--accent-purple)]">info</span>
      {{ store.toastMessage }}
    </p>
  </div>

  <!-- Secondary Terms & Conditions Popup Overlay -->
  <div v-if="showTermsText" class="fixed inset-0 z-[110] flex items-center justify-center p-4">
    <!-- Inner Backdrop -->
    <div class="absolute inset-0 bg-black/60 backdrop-blur-xs" @click="showTermsText = false"></div>
    
    <!-- Popup Container -->
    <div 
      class="relative w-full max-w-[400px] max-h-[80vh] rounded-[24px] bg-[var(--bg-card)] border border-[var(--border-light)] shadow-2xl flex flex-col overflow-hidden animate-in fade-in zoom-in duration-300"
      :style="{ backdropFilter: 'blur(20px)' }"
    >
      <!-- Header -->
      <div class="p-5 border-b border-[var(--border-light)] flex justify-between items-center shrink-0">
        <h3 class="text-base font-black text-[var(--text-primary)] uppercase tracking-wide">Terms & Conditions</h3>
        <button @click="showTermsText = false" class="text-[var(--text-secondary)] hover:text-[var(--text-primary)] transition-colors">
          <span class="material-symbols-outlined text-[20px]">close</span>
        </button>
      </div>
      
      <!-- Scrollable Content -->
      <div class="p-5 overflow-y-auto text-left text-[11px] text-[var(--text-secondary)] leading-relaxed space-y-4 scrollbar-hide">
        <p class="font-bold text-[var(--text-primary)]">Welcome to Vistas Learning! By subscribing to our learning plans, you agree to the following terms and conditions:</p>
        
        <div>
          <h4 class="font-black text-[var(--text-primary)] uppercase tracking-wider mb-1 text-[10px]">1. Platform & Content Access</h4>
          <p>Your subscription grants you full access to concepts, recorded classes, study materials, and tests based on your selected syllabus (Karnataka State Board / CBSE) for K-10 academics and future skill classes including Coding, Spoken English, and Creative Learning.</p>
        </div>

        <div>
          <h4 class="font-black text-[var(--text-primary)] uppercase tracking-wider mb-1 text-[10px]">2. Device Compatibility</h4>
          <p>Courses can be accessed on compatible smartphones, tablets, computers, and smart TVs. Simultaneous login is restricted to prevent unauthorized usage and ensure account security.</p>
        </div>

        <div>
          <h4 class="font-black text-[var(--text-primary)] uppercase tracking-wider mb-1 text-[10px]">3. Payment & Subscription Policy</h4>
          <p>Subscriptions are billed in advance on a recurring or one-time basis according to the plan selected (e.g. 14 Months Plan). Active plans are non-refundable and non-transferable once payment is processed.</p>
        </div>

        <div>
          <h4 class="font-black text-[var(--text-primary)] uppercase tracking-wider mb-1 text-[10px]">4. User Account Security</h4>
          <p>You are responsible for maintaining the confidentiality of your mobile number and OTP. All actions taken under your account are your sole responsibility.</p>
        </div>

        <div>
          <h4 class="font-black text-[var(--text-primary)] uppercase tracking-wider mb-1 text-[10px]">5. Changes to Terms</h4>
          <p>Vistas Learning reserves the right to modify these terms and update the syllabus content, features, and pricing structure at any time to enhance the student learning experience.</p>
        </div>
      </div>
      
      <!-- Footer Button -->
      <div class="p-4 border-t border-[var(--border-light)] bg-[var(--bg-secondary)] flex justify-end shrink-0">
        <button 
          @click="showTermsText = false" 
          class="px-5 py-2 bg-[var(--accent-purple)] text-white text-[10px] font-black uppercase tracking-wider rounded-lg hover:brightness-110 active:scale-95 transition-all shadow-md"
        >
          I Understand
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.animate-in {
  animation: fadeIn 0.3s ease-out forwards;
}

@keyframes fadeIn {
  from { opacity: 0; transform: scale(0.95) translateY(10px); }
  to { opacity: 1; transform: scale(1) translateY(0); }
}
</style>
