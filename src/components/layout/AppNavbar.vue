<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useThemeStore } from '../../stores/theme'
import { useSubscriptionStore } from '../../stores/subscription'

const themeStore = useThemeStore()
const subscriptionStore = useSubscriptionStore()

const isDropdownOpen = ref(false)
const boards = ['Karnataka', 'CBSE']

// Close dropdown when clicking outside
const closeDropdown = (e) => {
  if (!e.target.closest('.board-dropdown-container')) {
    isDropdownOpen.value = false
  }
}

const scrollToPlans = () => {
  isDropdownOpen.value = false
  subscriptionStore.handlePlanSelect('14months')
}

onMounted(() => {
  window.addEventListener('click', closeDropdown)
})

onUnmounted(() => {
  window.removeEventListener('click', closeDropdown)
})
</script>

<template>
  <header class="h-[52px] min-h-[52px] px-6 flex items-center bg-transparent z-50">
    <div class="flex items-center w-full relative">
      <!-- Left: Logo -->
      <div class="flex-1 flex items-center gap-2">
        <div class="h-9 flex items-center">
          <img 
            :src="themeStore.theme === 'dark' ? '/logo.webp' : '/light-logo.webp'" 
            alt="Vistas Learning" 
            width="108"
            height="36"
            fetchpriority="high"
            class="h-full w-auto object-contain transition-opacity duration-300" 
          />
        </div>
        <div 
          v-if="subscriptionStore.user?.isSubscribed"
          class="bg-gradient-to-r from-amber-400 to-amber-600 text-black px-2 py-0.5 rounded text-[8px] font-black uppercase tracking-tighter"
        >
          PREMIUM
        </div>
      </div>

      <!-- Center: Badges (Mathematically Centered) - Hidden on Mobile -->
      <div class="hidden md:flex items-center justify-center gap-3 text-center">
        <div class="flux-pill !bg-[var(--bg-secondary)] !border-[var(--border-light)] !text-[var(--text-primary)] shadow-xl !py-1 !px-4 !text-[9px] !rounded-full text-center flex items-center justify-center whitespace-nowrap">
           FOR <span class="text-[var(--accent-purple)] font-black ml-1 mr-1">K–10</span> STUDENTS
        </div>
        <div class="flux-pill !bg-[var(--bg-secondary)] !border-[var(--border-light)] !text-[var(--accent-purple)] uppercase tracking-widest !text-[9px] font-black shadow-xl !py-1 !px-4 !rounded-full text-center flex items-center justify-center whitespace-nowrap">
           ACADEMICS &amp; FUTURE SKILLS
        </div>
      </div>

      <!-- Right: Chips & Toggle -->
      <div class="flex-1 flex items-center justify-end gap-3">
        <!-- Theme Toggle -->
        <button 
          @click="themeStore.toggleTheme"
          aria-label="Toggle theme"
          class="w-8 h-8 rounded-full bg-[var(--bg-secondary)] border border-[var(--border-light)] flex items-center justify-center text-[var(--text-primary)] hover:scale-110 transition-transform"
        >
          <span class="material-symbols-outlined text-[18px]">
            {{ themeStore.theme === 'dark' ? 'light_mode' : 'dark_mode' }}
          </span>
        </button>

        <!-- Already Subscribed Button -->
        <button 
          @click="subscriptionStore.verifySubscriptionAndAccess"
          class="flex items-center gap-0.5 sm:gap-2 bg-transparent text-[var(--text-primary)] border border-[#8B5CF6] px-1.5 sm:px-4 py-1 sm:py-1.5 rounded-[12px] text-[7.5px] xs:text-[9px] sm:text-[10px] font-black uppercase tracking-tight hover:bg-[#8B5CF6]/10 active:scale-95 transition-all text-center leading-tight"
        >
          <span class="material-symbols-outlined text-[10px] sm:text-[13px] text-[#A78BFA] shrink-0" style="font-variation-settings: 'FILL' 1">star</span>
          <span class="max-w-[70px] xs:max-w-[90px] sm:max-w-none">Already Subscribed? Click Here</span>
        </button>

        <button 
          @click="scrollToPlans"
          class="bg-[#8B5CF6] text-white px-4 py-1.5 rounded-[8px] text-[10px] font-black uppercase tracking-tight shadow-lg hover:brightness-110 active:scale-95 transition-all"
        >
          Get Started
        </button>

        <!-- Modern Board Dropdown -->
        <div class="relative board-dropdown-container">
          <button 
            @click.stop="isDropdownOpen = !isDropdownOpen"
            aria-label="Select board or syllabus"
            class="flex items-center gap-2 bg-[#8B5CF6] text-white px-2 sm:px-4 py-1.5 rounded-[8px] text-[10px] font-black uppercase tracking-tight transition-all duration-300 shadow-lg hover:brightness-110 active:scale-95"
          >
            <span class="material-symbols-outlined text-[14px]">school</span>
            <span class="hidden xs:inline">Board / Syllabus</span>
            <span 
              class="material-symbols-outlined text-[14px] transition-transform duration-300"
              :class="{ 'rotate-180': isDropdownOpen }"
            >
              expand_more
            </span>
          </button>

          <!-- Dropdown Menu -->
          <Transition name="dropdown">
            <div 
              v-if="isDropdownOpen"
              class="absolute top-full right-0 mt-2 w-40 bg-[var(--bg-card)] border border-[var(--border-light)] rounded-xl shadow-2xl overflow-hidden z-[100] backdrop-blur-xl"
            >
              <div class="p-1.5 flex flex-col gap-1">
                <div 
                  v-for="board in boards" 
                  :key="board"
                  @click="scrollToPlans"
                  class="flex items-center justify-between w-full px-3 py-2 rounded-lg text-[10px] font-bold uppercase tracking-widest text-[var(--text-primary)] hover:bg-[var(--accent-purple)]/10 transition-all duration-200 text-left cursor-pointer"
                >
                  {{ board }}
                  <span class="material-symbols-outlined text-[14px] text-[var(--accent-purple)]">verified</span>
                </div>
              </div>
            </div>
          </Transition>
        </div>
      </div>
    </div>
  </header>
</template>

<style scoped>
.dropdown-enter-active,
.dropdown-leave-active {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.dropdown-enter-from,
.dropdown-leave-to {
  opacity: 0;
  transform: translateY(-10px) scale(0.95);
}

/* Glassmorphism subtle glow */
.board-dropdown-container:hover button {
  border-color: rgba(168, 85, 247, 0.4);
}

@media (max-width: 768px) {
  header {
    padding-left: 0.75rem !important;
    padding-right: 0.75rem !important;
  }
  .flex-1.flex.items-center.justify-end.gap-3 {
    gap: 0.5rem !important;
  }
}
</style>
