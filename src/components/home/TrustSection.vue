<script setup>
import { useSubscriptionStore } from '../../stores/subscription'

const subscriptionStore = useSubscriptionStore()

const handleStartJourney = () => {
  subscriptionStore.handlePlanSelect('14months')
}

const trustPoints = [
  { text: "Designed for K–10 Students", icon: "school" },
  { text: "Karnataka State Board + CBSE Support", icon: "verified_user" },
  { text: "Kannada & English Medium", icon: "language" },
  { text: "Concept Based Learning", icon: "lightbulb" },
  { text: "Builds Confidence & Communication", icon: "groups" },
]
</script>

<template>
  <div class="trust-wrapper h-full flex flex-col justify-between rounded-[20px] p-5 shadow-sm overflow-hidden relative">
    <!-- Background noise texture overlay -->
    <div class="noise-overlay"></div>

    <!-- Section title -->
    <div class="relative z-10">
      <div class="flex justify-center mb-4">
        <div class="title-divider-left"></div>
        <h3 class="trust-title text-center px-3 text-[13px] font-black uppercase tracking-widest">
          WHY THOUSANDS TRUST US?
        </h3>
        <div class="title-divider-right"></div>
      </div>

      <!-- Trust points: horizontal icon layout -->
      <div class="flex flex-row justify-around gap-1.5 flex-wrap">
        <div 
          v-for="(point, index) in trustPoints" 
          :key="index" 
          class="trust-item flex flex-col items-center gap-1.5 flex-1 min-w-0 cursor-default group"
        >
          <!-- Hexagon icon wrapper -->
          <div class="hex-wrapper relative flex items-center justify-center shrink-0">
            <svg class="hex-svg" viewBox="0 0 52 60" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path 
                d="M26 2L49 15V45L26 58L3 45V15L26 2Z" 
                stroke="rgba(124,58,237,0.7)" 
                stroke-width="1.5"
                fill="rgba(124,58,237,0.15)"
              />
            </svg>
            <span 
              class="material-symbols-outlined absolute text-[14px] text-[var(--accent-purple)] group-hover:text-[var(--accent-gold)] transition-colors duration-300"
              style="font-variation-settings: 'FILL' 1"
            >{{ point.icon }}</span>
          </div>
          <span class="trust-point-text text-center text-[6.5px] font-bold leading-tight">{{ point.text }}</span>
        </div>
      </div>
    </div>

    <!-- CTA Button -->
    <div class="relative z-10 mt-4">
      <button @click="handleStartJourney" class="cta-btn w-full py-3 rounded-xl font-black text-[11px] uppercase tracking-widest flex items-center justify-center gap-2 transition-all group shadow-xl">
        START YOUR JOURNEY
        <span class="material-symbols-outlined text-[16px] group-hover:translate-x-1 transition-transform duration-300">arrow_forward</span>
      </button>
    </div>
  </div>
</template>

<style scoped>
/* Main wrapper — deep dark purple like the reference */
.trust-wrapper {
  background: linear-gradient(160deg, #1a0a3d 0%, #2A1154 60%, #1a0a3d 100%);
  border: 1px solid rgba(255, 255, 255, 0.08);
  position: relative;
}

[data-theme='light'] .trust-wrapper {
  background: linear-gradient(160deg, #1a0a3d 0%, #2A1154 60%, #1a0a3d 100%);
  border: 1px solid rgba(124, 58, 237, 0.3);
}

/* Subtle dot pattern overlay */
.noise-overlay {
  position: absolute;
  inset: 0;
  background-image: radial-gradient(rgba(255,255,255,0.04) 1px, transparent 1px);
  background-size: 16px 16px;
  border-radius: 20px;
  pointer-events: none;
  z-index: 0;
}

/* Gold title */
.trust-title {
  color: #FBBF24;
  text-shadow: 0 0 20px rgba(251, 191, 36, 0.4);
  white-space: nowrap;
}

/* Divider lines beside title */
.title-divider-left,
.title-divider-right {
  flex: 1;
  height: 1px;
  background: linear-gradient(90deg, transparent, rgba(251, 191, 36, 0.4));
  align-self: center;
}

.title-divider-right {
  background: linear-gradient(90deg, rgba(251, 191, 36, 0.4), transparent);
}

/* Hexagon SVG */
.hex-wrapper {
  width: 36px;
  height: 42px;
}

.hex-svg {
  width: 36px;
  height: 42px;
  position: absolute;
  top: 0;
  left: 0;
}

/* Trust point text */
.trust-point-text {
  color: rgba(255, 255, 255, 0.8);
  max-width: 60px;
}

/* CTA Button — big gold like the reference */
.cta-btn {
  background: linear-gradient(135deg, #FBBF24 0%, #F59E0B 50%, #D97706 100%);
  color: #1a0a3d;
  border: none;
  letter-spacing: 0.12em;
  box-shadow: 0 6px 24px rgba(251, 191, 36, 0.4), 0 2px 8px rgba(0,0,0,0.3);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.cta-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 10px 32px rgba(251, 191, 36, 0.55), 0 4px 12px rgba(0,0,0,0.3);
}

.cta-btn:active {
  transform: translateY(0);
}
</style>
