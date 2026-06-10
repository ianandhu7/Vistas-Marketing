<script setup>
import { ref, onMounted, onUnmounted, computed } from 'vue'
import { useSubscriptionStore } from '../../stores/subscription'

const props = defineProps({
  plan: {
    type: Object,
    required: true
  }
})

const subscriptionStore = useSubscriptionStore()

const isBestValue = (id) => id === '14months'
const isSelected = computed(() => subscriptionStore.selectedPlan === props.plan.id)

const isMobile = ref(false)
const isExpanded = ref(false)

const checkMobile = () => {
  isMobile.value = window.innerWidth < 768
}

onMounted(() => {
  checkMobile()
  window.addEventListener('resize', checkMobile)
})

onUnmounted(() => {
  window.removeEventListener('resize', checkMobile)
})

const handleClick = () => {
  subscriptionStore.handlePlanSelect(props.plan.id)
}
</script>

<template>
  <div 
    :id="'plan-' + plan.id"
    :class="[
      'pricing-plan-card relative flex flex-col rounded-[16px] border h-full overflow-hidden transition-all duration-300 shadow-xl group/card bg-[var(--bg-card)] w-full max-w-[300px] md:max-w-none mx-auto',
      isMobile && isSelected
        ? (isBestValue(plan.id) 
            ? 'selected-card-best border-[#F5A623] border-[3px]' 
            : 'selected-card-standard border-[#a855f7] border-[3px]')
        : (isBestValue(plan.id) 
            ? 'best-value-card border-[#F5A623] border-[3px]' 
            : 'border-[var(--border-light)] hover:border-[#7B2FBE]/40')
    ]"
  >
    <!-- Top Section (Price & CTA) -->
    <div class="flex flex-col p-5 pb-3 md:p-2.5 md:pb-2 shrink-0 relative overflow-hidden text-center">
      <!-- Background Gradients -->
      <div 
        v-if="!isBestValue(plan.id)" 
        class="price-overlay absolute top-0 left-0 right-0 h-32 bg-gradient-to-b from-[#7B2FBE]/50 via-[#7B2FBE]/10 to-transparent pointer-events-none"
      ></div>
      <div 
        v-if="isBestValue(plan.id)" 
        class="price-overlay absolute top-0 left-0 right-0 h-32 bg-gradient-to-b from-[#F5A623]/40 via-[#F5A623]/10 to-transparent pointer-events-none"
      ></div>

      <!-- Best Value Badge (Image Style) -->
      <div 
        v-if="isBestValue(plan.id)" 
        class="best-value-badge absolute top-0 right-0 bg-[#F5A623] text-black text-[10px] md:text-[9px] font-black px-3.5 py-1.5 md:px-3 md:py-1 rounded-bl-[12px] md:rounded-bl-[10px] uppercase tracking-tighter shadow-lg z-20"
      >
        BEST VALUE
      </div>

      <!-- Selected State Indicator for Mobile -->
      <div 
        v-if="isMobile && isSelected"
        class="absolute top-2 left-2 z-20 flex items-center justify-center w-5 h-5 rounded-full shadow-md"
        :class="isBestValue(plan.id) ? 'bg-[#F5A623] text-black' : 'bg-[#a855f7] text-white'"
      >
        <span class="material-symbols-outlined text-[14px] font-black">check</span>
      </div>

      <!-- Plan Name -->
      <div class="plan-name-wrapper flex flex-col items-center mb-1 md:mb-0.5 relative z-10">
        <h4 
          :class="[
            'text-[15px] md:text-[12px] font-black tracking-tight',
            isBestValue(plan.id) ? 'text-[#F5A623]' : 'text-[var(--text-primary)]'
          ]"
        >
          {{ plan.durationName }} Plan
        </h4>
      </div>

      <!-- Price -->
      <div class="flex items-center justify-center gap-1 mb-2 md:mb-1 relative z-10">
        <span 
          :class="[
            'text-[32px] md:text-[28px] font-black leading-none tracking-tighter',
            isBestValue(plan.id) ? 'text-[#F5A623]' : 'text-[var(--text-primary)]'
          ]"
        >
          {{ plan.price }}
        </span>
      </div>
      
      <!-- Subscribe Button or Active Subscription Info -->
      <template v-if="subscriptionStore.subscriptionCheckDone && subscriptionStore.existingSubscription">
        <div class="relative z-10 w-full flex flex-col items-center gap-2 mt-2">
          <span class="text-green-500 text-[11px] md:text-[9px] font-black uppercase tracking-tight">
            You already have an active subscription!
          </span>
          <a 
            href="https://www.student.vistaslearning.com/guest/"
            class="cta-button w-full py-2.5 md:py-1.5 text-[11px] md:text-[9px] font-black tracking-widest transition-all duration-300 rounded-full text-center bg-green-600 text-white hover:bg-green-700 hover:shadow-green-600/50 shadow-xl border border-transparent flex items-center justify-center"
          >
            GO TO PORTAL
          </a>
        </div>
      </template>
      <button 
        v-else
        @click="handleClick"
        :class="[
          'cta-button w-full py-2.5 md:py-1.5 text-[11px] md:text-[9px] font-black tracking-widest transition-all duration-300 relative z-10 border border-transparent shadow-xl rounded-full overflow-hidden',
          !isBestValue(plan.id) ? 'bg-[#9333ea] text-white hover:shadow-[#9333ea]/50' : 'bg-[#F5A623] text-black hover:shadow-[#F5A623]/50'
        ]"
      >
        SUBSCRIBE NOW
        <!-- Shine Animation Overlay -->
        <span class="button-shine-effect absolute inset-0 w-full h-full pointer-events-none"></span>
      </button>
    </div>

    <!-- Bottom Section (Features) -->
    <div class="flex flex-col flex-grow min-h-0 p-5 pt-2 pb-6 md:p-2 md:pt-1 md:pb-4">
      <ul class="grid grid-cols-1 gap-y-2.5 md:grid-cols-2 md:gap-x-3 md:gap-y-2">
        <li 
          v-for="(feature, idx) in (isMobile && !isExpanded ? plan.features.slice(0, 3) : plan.features)" 
          :key="idx" 
          class="flex items-center gap-2.5 md:gap-1.5"
        >
          <span 
            v-if="feature.included"
            :class="[
              'material-symbols-outlined text-[15px] md:text-[12px] font-bold',
              isBestValue(plan.id) ? 'text-[#F5A623]' : 'text-green-500'
            ]"
          >
            check
          </span>
          <span 
            v-else
            class="material-symbols-outlined text-[15px] md:text-[12px] font-bold text-red-500/80"
          >
            close
          </span>
          <span 
            :class="[
              'text-[11px] md:text-[8px] font-bold leading-none truncate',
              !feature.included ? 'text-[var(--text-secondary)] opacity-30' : 'text-[var(--text-secondary)]'
            ]"
          >
            {{ feature.text }}
          </span>
        </li>
      </ul>

      <!-- Show All Features Toggle for Mobile -->
      <button 
        v-if="isMobile && plan.features.length > 3" 
        @click="isExpanded = !isExpanded" 
        class="mt-4 text-[10px] font-black uppercase tracking-wider text-[var(--accent-purple)] flex items-center justify-center gap-1 mx-auto focus:outline-none"
      >
        {{ isExpanded ? 'Hide Details' : 'Show All Features' }}
        <span class="material-symbols-outlined text-[14px] transition-transform duration-300" :class="{ 'rotate-180': isExpanded }">
          keyboard_arrow_down
        </span>
      </button>
    </div>
  </div>
</template>


<style scoped>
@keyframes goldPulseMobile {
  0%, 100% {
    filter: drop-shadow(0 0 8px rgba(245, 166, 35, 0.25));
  }
  50% {
    filter: drop-shadow(0 0 20px rgba(245, 166, 35, 0.65));
  }
}

@keyframes buttonShineAnimation {
  0% { transform: translateX(-100%) skewX(-15deg); }
  50% { transform: translateX(100%) skewX(-15deg); }
  100% { transform: translateX(100%) skewX(-15deg); }
}

.button-shine-effect {
  background: linear-gradient(
    90deg,
    transparent,
    rgba(255, 255, 255, 0.25),
    transparent
  );
  transform: translateX(-100%) skewX(-15deg);
}

.cta-button:hover .button-shine-effect {
  animation: buttonShineAnimation 1.5s infinite;
}

.pricing-plan-card {
  will-change: transform;
  transition: transform 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275),
              box-shadow 0.4s ease,
              background-color 0.3s ease !important;
}

/* Hover Lift & Glow for standard plans */
.pricing-plan-card:hover {
  position: relative;
  z-index: 10;
  transform: translateY(-6px) scale(1.005) !important;
  border-color: rgba(168, 85, 247, 0.5) !important;
  box-shadow: 0 20px 40px -5px rgba(124, 58, 237, 0.22), 
              0 8px 16px -8px rgba(124, 58, 237, 0.15) !important;
}

/* Hover Lift & Glow for Best Value plan */
.pricing-plan-card.best-value-card:hover {
  position: relative;
  z-index: 10;
  transform: translateY(-6px) scale(1.005) !important;
  border-color: #F5A623 !important;
  box-shadow: 0 20px 50px -5px rgba(245, 166, 35, 0.32), 
              0 10px 20px -8px rgba(245, 166, 35, 0.22), 
              inset 0 0 20px rgba(245,166,35,0.15) !important;
}

/* Scale price backgrounds on hover */
.pricing-plan-card .price-overlay {
  transition: opacity 0.4s ease, transform 0.4s ease;
}
.pricing-plan-card:hover .price-overlay {
  transform: scale(1.06);
  opacity: 0.9;
}

/* Scale CTA buttons on hover */
.pricing-plan-card button {
  transition: transform 0.3s cubic-bezier(0.175, 0.885, 0.32, 1.275), 
              box-shadow 0.3s ease, 
              filter 0.3s ease !important;
}
.pricing-plan-card:hover button {
  transform: scale(1.04) !important;
  box-shadow: 0 8px 24px rgba(139, 92, 246, 0.4) !important;
}
.pricing-plan-card.best-value-card:hover button {
  box-shadow: 0 8px 24px rgba(245, 166, 35, 0.45) !important;
}

/* Animate checkmarks on hover */
.pricing-plan-card ul li span.material-symbols-outlined {
  transition: transform 0.3s cubic-bezier(0.175, 0.885, 0.32, 1.275), color 0.3s ease;
}
.pricing-plan-card:hover ul li span.material-symbols-outlined {
  transform: scale(1.2) rotate(4deg);
}


@media (max-width: 768px) {
  /* Tap scale effect for cards on mobile */
  .pricing-plan-card:active {
    transform: scale(0.97) !important;
    transition: transform 0.1s ease !important;
  }

  /* Automatically animate shine on mobile buttons periodically */
  .button-shine-effect {
    animation: buttonShineAnimation 3s infinite ease-in-out;
  }

  /* Best Value card pulse glow on mobile — uses GPU-composited filter */
  .pricing-plan-card.best-value-card,
  .pricing-plan-card.selected-card-best {
    animation: goldPulseMobile 3s infinite ease-in-out;
    border-color: #F5A623;
  }

  /* Standard card selected glow on mobile */
  .pricing-plan-card.selected-card-standard {
    box-shadow: 0 0 25px rgba(168, 85, 247, 0.4), inset 0 0 15px rgba(168, 85, 247, 0.1) !important;
  }

  /* Purple border for standard cards on mobile */
  .pricing-plan-card:not(.best-value-card):not(.selected-card-standard) {
    border-color: rgba(139, 92, 246, 0.45) !important;
  }
}
</style>
