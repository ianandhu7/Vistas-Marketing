<template>
  <!-- ======================================================= -->
  <!--   DESKTOP LAYOUT (md+): unchanged grid layout           -->
  <!-- ======================================================= -->
  <div class="hidden md:flex flex-1 flex-col relative overflow-y-auto md:overflow-hidden scrollbar-hide">
    <div class="flex-grow flex flex-col items-center px-4 md:px-6 py-2 min-h-0 relative z-10">
      <div class="home-layout-container w-full max-w-[1300px] h-full flex flex-col gap-3 md:gap-1.5 min-h-0">
        
        <div class="headline-container text-center shrink-0 w-full mb-1 md:mb-0.5">
          <h1 class="text-[20px] md:text-[26px] font-black leading-tight tracking-tight text-[var(--text-primary)]">
            Complete Learning Support. <span class="text-[var(--accent-gold)]">Better Marks. Bright Future.</span>
          </h1>
          <div class="subline-container flex flex-col md:flex-row items-center justify-center gap-1 md:gap-4 text-[var(--text-secondary)] text-[9px] md:text-[11px] font-bold uppercase tracking-wider mt-1 md:mt-0.5">
            <span class="text-center">Spoken English • Coding • Communication • Creativity • Future Skills</span>
            <span class="text-[var(--text-primary)] font-serif italic normal-case text-[11px] md:text-[13px] opacity-70">— All in One Platform</span>
          </div>
        </div>

        <div class="middle-row flex flex-col md:flex-row gap-5 h-auto md:h-[52%] min-h-0 shrink-0">
          <div class="video-container-wrapper w-full md:w-[58%] h-[300px] md:h-full min-h-0 overflow-hidden">
            <VideoPlayer />
          </div>
          <div class="features-container-wrapper w-full md:w-[42%] h-auto md:h-full min-h-0 overflow-hidden">
            <FeaturesColumn />
          </div>
        </div>

        <div class="bottom-row flex flex-col md:flex-row gap-5 h-auto md:h-[32%] min-h-0 shrink-0 mb-8 md:mb-0">
          <div class="pricing-container-wrapper w-full md:w-[72%] h-auto md:h-full min-h-0 flex flex-col gap-3">
            <div class="flex items-center justify-center gap-4 shrink-0 mt-1 mb-1">
              <div class="h-px bg-[var(--border-light)] flex-grow"></div>
              <h3 class="text-[var(--text-primary)] text-[11px] font-black uppercase tracking-widest opacity-80">
                Choose the <span class="text-[var(--accent-gold)]">Best Plan</span> for Your Child
              </h3>
              <div class="h-px bg-[var(--border-light)] flex-grow"></div>
            </div>
            <div id="pricing-plans" class="flex-grow min-h-0 md:overflow-y-auto scrollbar-hide">
              <PricingCards />
            </div>
          </div>
          
          <div class="trust-container-wrapper w-full md:w-[28%] h-auto md:h-full min-h-0 rounded-[14px] p-3 flex flex-col shadow-xl overflow-hidden relative transition-colors duration-300"
               :style="{ 
                 background: themeStore.theme === 'dark'
                   ? 'linear-gradient(160deg, #1a0a3d 0%, #2A1154 60%, #1a0a3d 100%)'
                   : 'linear-gradient(160deg, #ffffff 0%, #f3e8ff 60%, #ffffff 100%)',
                 border: themeStore.theme === 'dark' ? '1px solid rgba(255, 255, 255, 0.08)' : '1px solid rgba(139, 92, 246, 0.2)'
               }">
            <div class="trust-dots" :class="themeStore.theme === 'dark' ? 'opacity-100' : 'opacity-30 invert'"></div>
            <div class="relative z-10 flex items-center gap-2 mb-3">
              <div class="h-px flex-1" style="background: linear-gradient(90deg, transparent, rgba(251,191,36,0.5))"></div>
              <h3 class="trust-heading text-[8px] font-black uppercase tracking-widest text-center shrink-0">WHY THOUSANDS TRUST US?</h3>
              <div class="h-px flex-1" style="background: linear-gradient(90deg, rgba(251,191,36,0.5), transparent)"></div>
            </div>
            <ul class="trust-list relative z-10 flex flex-row flex-wrap justify-center items-center content-center gap-x-2 gap-y-3 flex-grow min-h-0 py-2">
              <li v-for="t in trustPoints" :key="t.text" class="trust-item flex flex-col items-center gap-1.5 flex-1 min-w-[50px] max-w-[64px] group">
                <div class="hex-icon relative flex items-center justify-center">
                  <svg width="42" height="48" viewBox="0 0 52 60" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M26 2L49 15V45L26 58L3 45V15L26 2Z" stroke="rgba(124,58,237,0.7)" stroke-width="1.5" :fill="themeStore.theme === 'dark' ? 'rgba(124,58,237,0.18)' : 'rgba(124,58,237,0.1)'"/>
                  </svg>
                  <span class="material-symbols-outlined absolute text-[18px] text-[var(--accent-purple)] group-hover:text-[#FBBF24] transition-colors duration-300" style="font-variation-settings: 'FILL' 1">{{ t.icon }}</span>
                </div>
                <span class="trust-text text-center text-[6.5px] font-bold leading-tight mt-0.5"
                      :class="themeStore.theme === 'dark' ? 'text-white/80' : 'text-gray-700'">{{ t.text }}</span>
              </li>
            </ul>
            <button @click="scrollToPlans" class="trust-cta relative z-10 mt-3 w-full py-2.5 rounded-[10px] text-[9px] font-black uppercase tracking-widest flex items-center justify-center gap-1.5 shrink-0 group">
              Start your journey
              <span class="material-symbols-outlined text-[13px] group-hover:translate-x-0.5 transition-transform">arrow_forward</span>
            </button>
          </div>
        </div>

      </div>
    </div>
    <SubscriptionModals />
  </div>

  <!-- ======================================================= -->
  <!--   MOBILE LAYOUT (<md): Video first, then pricing        -->
  <!-- ======================================================= -->
  <div class="md:hidden flex-1 flex flex-col relative overflow-y-auto scrollbar-hide">

    <!-- Video Section — scrolls away normally -->
    <div class="px-3 pt-2 pb-3">
      <div class="mobile-video-section">
        <VideoPlayer />
      </div>
    </div>

    <!-- PRICING SECTION -->
    <div class="mobile-pricing-section px-6 py-8 reveal-on-scroll"
         :style="{
           background: themeStore.theme === 'dark'
             ? '#0f0a1f'
             : '#f4edff'
         }">
      <!-- Plans heading -->
      <div class="flex items-center justify-center gap-3 mb-6">
        <div class="h-px bg-[var(--border-light)] flex-grow"></div>
        <div class="text-center">
          <h2 class="text-[var(--text-primary)] text-[13px] font-black leading-tight">
            Choose the <span class="text-[var(--accent-gold)]">Best Plan</span> for Your Child
          </h2>
          <p class="text-[var(--text-secondary)] text-[9px] font-semibold mt-0.5">All plans include unlimited access to our learning platform</p>
        </div>
        <div class="h-px bg-[var(--border-light)] flex-grow"></div>
      </div>

      <!-- 3 Pricing Cards side-by-side, no scroll -->
      <PricingCards />

      <!-- Secure badge -->
      <div class="flex items-center justify-center gap-1.5 mt-6">
        <span class="material-symbols-outlined text-[13px] text-[var(--text-secondary)]">lock</span>
        <span class="text-[var(--text-secondary)] text-[9px] font-semibold">Secure Payments. Cancel Anytime.</span>
      </div>
    </div>

    <!-- REST OF CONTENT — scrolls naturally below the pricing -->
    <div class="px-3 flex flex-col gap-5 pb-8">

      <!-- Features Grid -->
      <div class="mobile-features-section reveal-on-scroll">
        <FeaturesColumn />
      </div>

      <!-- Trust Section -->
      <div class="mobile-trust-section reveal-on-scroll rounded-[14px] p-4 flex flex-col shadow-xl overflow-hidden relative transition-colors duration-300"
           :style="{ 
             background: themeStore.theme === 'dark'
               ? 'linear-gradient(160deg, #1a0a3d 0%, #2A1154 60%, #1a0a3d 100%)'
               : 'linear-gradient(160deg, #ffffff 0%, #f3e8ff 60%, #ffffff 100%)',
             border: themeStore.theme === 'dark' ? '1px solid rgba(255, 255, 255, 0.08)' : '1px solid rgba(139, 92, 246, 0.2)'
           }">
        <div class="trust-dots" :class="themeStore.theme === 'dark' ? 'opacity-100' : 'opacity-30 invert'"></div>
        <div class="relative z-10 flex items-center gap-2 mb-3">
          <div class="h-px flex-1" style="background: linear-gradient(90deg, transparent, rgba(251,191,36,0.5))"></div>
          <h3 class="trust-heading text-[9px] font-black uppercase tracking-widest text-center shrink-0">WHY THOUSANDS TRUST US?</h3>
          <div class="h-px flex-1" style="background: linear-gradient(90deg, rgba(251,191,36,0.5), transparent)"></div>
        </div>
        <ul class="trust-list relative z-10 flex flex-row flex-wrap justify-center items-center gap-x-3 gap-y-3 py-2">
          <li v-for="t in trustPoints" :key="t.text" class="trust-item flex flex-col items-center gap-1.5 w-[18%] min-w-[52px] group">
            <div class="hex-icon relative flex items-center justify-center">
              <svg width="42" height="48" viewBox="0 0 52 60" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M26 2L49 15V45L26 58L3 45V15L26 2Z" stroke="rgba(124,58,237,0.7)" stroke-width="1.5" :fill="themeStore.theme === 'dark' ? 'rgba(124,58,237,0.18)' : 'rgba(124,58,237,0.1)'"/>
              </svg>
              <span class="material-symbols-outlined absolute text-[18px] text-[var(--accent-purple)] group-hover:text-[#FBBF24] transition-colors duration-300" style="font-variation-settings: 'FILL' 1">{{ t.icon }}</span>
            </div>
            <span class="trust-text text-center text-[6.5px] font-bold leading-tight mt-0.5"
                  :class="themeStore.theme === 'dark' ? 'text-white/80' : 'text-gray-700'">{{ t.text }}</span>
          </li>
        </ul>
        <button @click="scrollToPlans" class="trust-cta relative z-10 mt-4 w-full py-3 rounded-[12px] text-[11px] font-black uppercase tracking-widest flex items-center justify-center gap-2 shrink-0">
          Start your journey
          <span class="material-symbols-outlined text-[15px]">arrow_forward</span>
        </button>
      </div>

    </div>

    <!-- Floating Support Button (Mobile only) -->
    <a 
      href="https://wa.me/919745974709" 
      target="_blank"
      class="md:hidden fixed bottom-6 right-6 z-50 flex items-center justify-center w-14 h-14 rounded-full bg-green-500/80 backdrop-blur-md border border-white/20 text-white shadow-2xl transition-all duration-300 active:scale-90"
      style="box-shadow: 0 0 20px rgba(34, 197, 94, 0.4);"
    >
      <span class="absolute inset-0 rounded-full bg-green-500/30 animate-ping pointer-events-none"></span>
      <span class="material-symbols-outlined text-[28px] font-bold">chat</span>
    </a>

    <SubscriptionModals />
  </div>
</template>

<script setup>
import { onMounted } from 'vue'
import VideoPlayer from '../components/home/VideoPlayer.vue'
import FeaturesColumn from '../components/home/FeaturesColumn.vue'
import PricingCards from '../components/home/PricingCards.vue'
import SubscriptionModals from '../components/home/SubscriptionModals.vue'
import { useSubscriptionStore } from '../stores/subscription'
import { useThemeStore } from '../stores/theme'

const subscriptionStore = useSubscriptionStore()
const themeStore = useThemeStore()

const scrollToPlans = () => {
  subscriptionStore.handlePlanSelect('14months')
}

const trustPoints = [
  { text: "Designed for K–10 Students", icon: "school" },
  { text: "Karnataka State Board + CBSE Support", icon: "verified" },
  { text: "Kannada & English Medium", icon: "translate" },
  { text: "Concept Based Learning", icon: "lightbulb" },
  { text: "Builds Confidence & Communication", icon: "psychology" }
]

onMounted(() => {
  const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
      if (entry.isIntersecting) {
        entry.target.classList.add('is-revealed')
      }
    })
  }, { threshold: 0.05 })

  document.querySelectorAll('.reveal-on-scroll').forEach((el) => {
    observer.observe(el)
  })
})
</script>

<style scoped>
/* Dot pattern texture */
.trust-dots {
  position: absolute;
  inset: 0;
  background-image: radial-gradient(rgba(255,255,255,0.04) 1px, transparent 1px);
  background-size: 14px 14px;
  border-radius: 14px;
  pointer-events: none;
  z-index: 0;
}

/* Gold heading glow */
.trust-heading {
  color: #FBBF24;
  text-shadow: 0 0 14px rgba(251, 191, 36, 0.4);
}

/* CTA Button */
.trust-cta {
  background: linear-gradient(135deg, #A855F7 0%, #8b31f7 100%);
  color: #ffffff;
  border: none;
  box-shadow: 0 4px 18px rgba(168, 85, 247, 0.4), 0 2px 6px rgba(0,0,0,0.3);
  font-weight: 900;
  letter-spacing: 0.1em;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.trust-cta:hover {
  transform: translateY(-1px);
  box-shadow: 0 8px 24px rgba(168, 85, 247, 0.55);
}

/* Mobile pricing panel */
.mobile-pricing-section {
  border-bottom: 1px solid rgba(139, 92, 246, 0.15);
  box-shadow: 0 4px 24px rgba(0, 0, 0, 0.25);
}

/* Mobile video player height */
.mobile-video-section {
  height: 220px;
  border-radius: 16px;
  overflow: hidden;
}

.reveal-on-scroll {
  opacity: 0;
  transform: translateY(25px);
  transition: opacity 0.8s cubic-bezier(0.16, 1, 0.3, 1), transform 0.8s cubic-bezier(0.16, 1, 0.3, 1);
}

.reveal-on-scroll.is-revealed {
  opacity: 1;
  transform: translateY(0);
}
</style>

