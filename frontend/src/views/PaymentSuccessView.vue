<script setup>
import { onMounted, onUnmounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useThemeStore } from '../stores/theme'

const route = useRoute()
const router = useRouter()
const themeStore = useThemeStore()

const plan = ref('14 Months Plan')
const orderId = ref('order_Sv5KqFDBuxiXOt')
const countdown = ref(5)

let intervalId = null

onMounted(() => {
  intervalId = setInterval(() => {
    countdown.value--
    if (countdown.value <= 0) {
      clearInterval(intervalId)
      router.push({ name: 'home' })
    }
  }, 1000)
})

onUnmounted(() => {
  if (intervalId) {
    clearInterval(intervalId)
  }
})
</script>

<template>
  <div 
    class="absolute inset-0 w-full h-full font-sans transition-colors duration-300 overflow-y-auto flex flex-col items-center justify-start p-4"
    :class="themeStore.theme === 'dark' ? 'bg-[#060309]' : 'bg-[#F8F9FA]'"
  >
    <!-- Background Dot Pattern (Subtle) -->
    <div class="absolute inset-0 z-0 pointer-events-none"
         :style="{
           backgroundImage: themeStore.theme === 'dark' 
             ? 'radial-gradient(rgba(255,255,255,0.02) 1px, transparent 1px)'
             : 'radial-gradient(rgba(0,0,0,0.03) 1px, transparent 1px)',
           backgroundSize: '24px 24px'
         }">
    </div>

    <!-- Background Glow -->
    <div class="absolute inset-0 z-0 flex items-center justify-center pointer-events-none">
      <div 
        class="w-[500px] h-[500px] rounded-full blur-[100px] opacity-35 transition-colors duration-300 animate-pulse"
        :class="themeStore.theme === 'dark' ? 'bg-[#3A146F]/30' : 'bg-[#A855F7]/10'"
      ></div>
    </div>

    <!-- Success Card -->
    <div 
      class="relative z-10 w-full max-w-[420px] rounded-[24px] p-6 my-auto md:my-12 shrink-0 transition-all duration-300 shadow-2xl border"
      :class="themeStore.theme === 'dark' ? 'bg-[#0C0617] border-[#29174A]' : 'bg-white border-gray-100'"
      :style="{
        boxShadow: themeStore.theme === 'dark' ? '0 20px 60px rgba(13, 6, 23, 0.95), 0 0 40px rgba(139,92,246,0.08)' : '0 15px 40px rgba(0,0,0,0.05)'
      }"
    >
      
      <!-- Top Dots Grid Left -->
      <div class="absolute top-6 left-6 grid grid-cols-4 gap-1 opacity-20">
        <div v-for="i in 16" :key="'dl-'+i" class="w-0.75 h-0.75 bg-[#8B5CF6] rounded-full"></div>
      </div>

      <!-- Top Dots Grid Right -->
      <div class="absolute top-6 right-6 grid grid-cols-4 gap-1 opacity-20">
        <div v-for="i in 16" :key="'dr-'+i" class="w-0.75 h-0.75 bg-[#8B5CF6] rounded-full"></div>
      </div>

      <!-- Top Logo Circle with Glow -->
      <div class="absolute -top-[48px] left-1/2 -translate-x-1/2">
        <div 
          class="relative w-[96px] h-[96px] rounded-full flex items-center justify-center transition-colors duration-300 border-2"
          :class="themeStore.theme === 'dark' ? 'bg-[#0C0617] border-[#4C2893] shadow-[0_0_25px_rgba(139,92,246,0.4)]' : 'bg-white border-gray-100 shadow-[0_0_20px_rgba(139,92,246,0.1)]'"
        >
          <!-- Inner circle layer -->
          <div 
            class="absolute inset-[5px] rounded-full border flex items-center justify-center overflow-hidden transition-colors duration-300"
            :class="themeStore.theme === 'dark' ? 'bg-[#140A26] border-[#8B5CF6]/30' : 'bg-white border-gray-50'"
          >
             <img src="/logo.png" alt="Vistas Learning" class="w-12 h-12 object-contain" />
          </div>
          
          <!-- Verified Badge -->
          <div 
            class="absolute bottom-0 right-0 w-[26px] h-[26px] bg-[#8B5CF6] rounded-full flex items-center justify-center border-[2.5px] transition-colors duration-300 shadow-md"
            :class="themeStore.theme === 'dark' ? 'border-[#0C0617]' : 'border-white'"
          >
            <span class="material-symbols-outlined text-[13px] text-white font-bold">check</span>
          </div>
        </div>
      </div>

      <!-- Main Content -->
      <div class="mt-11 text-center">
        <h1 class="text-[23px] font-black tracking-tight mb-2 transition-colors duration-300" :class="themeStore.theme === 'dark' ? 'text-white' : 'text-gray-900'">
          Thank you <span class="text-[#A78BFA]">for choosing us</span>
        </h1>
        <p class="text-[13px] leading-relaxed mb-4 transition-colors duration-300 px-4" :class="themeStore.theme === 'dark' ? 'text-[#9A8BB5]' : 'text-gray-500'">
          Your subscription is now active. We're glad to have you on board.
        </p>
        <p class="text-[11px] font-bold mb-6 transition-colors duration-300" :class="themeStore.theme === 'dark' ? 'text-[#A78BFA]' : 'text-purple-600'">
          Redirecting to home in {{ countdown }}s...
        </p>

        <!-- Divider with Sparkle Star -->
        <div class="relative flex items-center justify-center mb-6">
          <div class="w-full h-px transition-colors duration-300" :class="themeStore.theme === 'dark' ? 'bg-gradient-to-r from-transparent via-[#4C2893] to-transparent' : 'bg-gradient-to-r from-transparent via-gray-200 to-transparent'"></div>
          <div class="absolute">
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M12 2L14.5 9.5L22 12L14.5 14.5L12 22L9.5 14.5L2 12L9.5 9.5L12 2Z" fill="#A855F7" />
            </svg>
          </div>
        </div>

        <!-- Details Cards List -->
        <div class="space-y-3 text-left">
          
          <!-- Browse your plans Card -->
          <div 
            class="flex items-center justify-between py-3.5 px-4 rounded-[14px] border transition-colors duration-300 shadow-[0_0_15px_rgba(139,92,246,0.12)]"
            :class="themeStore.theme === 'dark' ? 'border-[#8B5CF6]/40 bg-gradient-to-r from-[#1D0E35]/90 to-[#2E1254]/70' : 'border-purple-300 bg-gradient-to-r from-[#FAF6FF] to-[#F3E5FF] shadow-purple-100/50'"
          >
            <div class="flex items-center gap-3 shrink min-w-0">
              <div 
                class="w-9 h-9 rounded-[10px] flex items-center justify-center transition-colors duration-300 shrink-0 border"
                :class="themeStore.theme === 'dark' ? 'bg-[#8B5CF6]/20 border-[#8B5CF6]/30' : 'bg-[#EFEBFF] border-purple-200'"
              >
                <span class="material-symbols-outlined text-[19px] text-[#A78BFA]">card_giftcard</span>
              </div>
              <div class="flex flex-col min-w-0">
                <span class="text-[13px] font-bold truncate" :class="themeStore.theme === 'dark' ? 'text-white' : 'text-gray-800'">Browse your plans</span>
                <span class="text-[10.5px] leading-tight mt-0.5 truncate" :class="themeStore.theme === 'dark' ? 'text-[#9A8BB5]' : 'text-gray-500'">Explore more plans that suit your learning goals.</span>
              </div>
            </div>
            <a 
              href="https://vistaslearning.com/" 
              target="_blank" 
              rel="noopener noreferrer"
              class="text-[12px] font-bold text-[#A78BFA] hover:text-white transition-all flex items-center gap-0.5 whitespace-nowrap ml-2 px-3 py-1.5 rounded-lg border"
              :class="themeStore.theme === 'dark' ? 'bg-[#7C3AED]/20 hover:bg-[#7C3AED]/40 border-[#7C3AED]/30' : 'bg-purple-100 hover:bg-purple-200 border-purple-200 text-purple-700'"
            >
              Browse Plans
              <span class="material-symbols-outlined text-[13px] font-bold">arrow_outward</span>
            </a>
          </div>

          <!-- Plan Card -->
          <div 
            class="flex items-center justify-between py-3 px-4 rounded-[14px] border transition-colors duration-300"
            :class="themeStore.theme === 'dark' ? 'border-[#261546] bg-[#120723]/60' : 'border-gray-100 bg-white'"
          >
            <div class="flex items-center gap-3 shrink-0">
              <div 
                class="w-9 h-9 rounded-[10px] flex items-center justify-center transition-colors duration-300 shrink-0"
                :class="themeStore.theme === 'dark' ? 'bg-[#1E0F37]' : 'bg-[#F3E8FF]'"
              >
                <span class="material-symbols-outlined text-[19px] text-[#A78BFA]">description</span>
              </div>
              <span class="text-[13px] font-bold" :class="themeStore.theme === 'dark' ? 'text-[#E2E8F0]' : 'text-gray-700'">Plan</span>
            </div>
            <span class="font-bold text-[13px]" :class="themeStore.theme === 'dark' ? 'text-white' : 'text-gray-900'">{{ plan }}</span>
          </div>

          <!-- Status Card -->
          <div 
            class="flex items-center justify-between py-3 px-4 rounded-[14px] border transition-colors duration-300"
            :class="themeStore.theme === 'dark' ? 'border-[#261546] bg-[#120723]/60' : 'border-gray-100 bg-white'"
          >
            <div class="flex items-center gap-3 shrink-0">
              <div 
                class="w-9 h-9 rounded-[10px] flex items-center justify-center transition-colors duration-300 shrink-0"
                :class="themeStore.theme === 'dark' ? 'bg-[#1E0F37]' : 'bg-[#F3E8FF]'"
              >
                <span class="material-symbols-outlined text-[19px] text-[#A78BFA]">shield</span>
              </div>
              <span class="text-[13px] font-bold" :class="themeStore.theme === 'dark' ? 'text-[#E2E8F0]' : 'text-gray-700'">Status</span>
            </div>
            <div 
              class="px-2.5 py-0.5 rounded-full text-[11.5px] font-bold flex items-center gap-1 border transition-colors duration-300"
              :class="themeStore.theme === 'dark' ? 'bg-[#064E3B]/20 border-[#059669]/30 text-[#34D399]' : 'bg-[#ECFDF5] border-[#A7F3D0] text-[#059669]'"
            >
              <span class="material-symbols-outlined text-[14px]" style="font-variation-settings: 'FILL' 1">check_circle</span>
              Active
            </div>
          </div>

          <!-- Order ref Card -->
          <div 
            class="flex items-center justify-between py-3 px-4 rounded-[14px] border transition-colors duration-300"
            :class="themeStore.theme === 'dark' ? 'border-[#261546] bg-[#120723]/60' : 'border-gray-100 bg-white'"
          >
            <div class="flex items-center gap-3 shrink-0">
              <div 
                class="w-9 h-9 rounded-[10px] flex items-center justify-center transition-colors duration-300 shrink-0"
                :class="themeStore.theme === 'dark' ? 'bg-[#1E0F37]' : 'bg-[#F3E8FF]'"
              >
                <span class="material-symbols-outlined text-[19px] text-[#A78BFA]">calendar_today</span>
              </div>
              <span class="text-[13px] font-bold" :class="themeStore.theme === 'dark' ? 'text-[#E2E8F0]' : 'text-gray-700'">Order ref</span>
            </div>
            <span class="font-bold text-[13px] tracking-wide font-mono" :class="themeStore.theme === 'dark' ? 'text-[#E2E8F0]' : 'text-gray-900'">{{ orderId }}</span>
          </div>

        </div>

      </div>
    </div>
  </div>
</template>

<style scoped>
/* Custom mini-dot style */
.w-0\.75 {
  width: 3px;
}
.h-0\.75 {
  height: 3px;
}
</style>
