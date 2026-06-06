<script setup>
import { pricingPlans } from '../../data/pricingPlans'
import PricingCard from './PricingCard.vue'
import { useSubscriptionStore } from '../../stores/subscription'
import { useThemeStore } from '../../stores/theme'

const subscriptionStore = useSubscriptionStore()
const themeStore = useThemeStore()
</script>

<template>
  <div class="pricing-cards-grid grid grid-cols-1 md:grid-cols-3 gap-6 md:gap-4 h-auto lg:h-full">
    <PricingCard 
      v-for="(plan, index) in pricingPlans" 
      :key="plan.id"
      :plan="plan"
      class="pricing-card-item"
      v-motion
      :initial="{ opacity: 0, y: 50 }"
      :enter="{ opacity: 1, y: 0, transition: { delay: index * 150, type: 'spring', stiffness: 250, damping: 25 } }"
    />
  </div>
</template>

<style scoped>
@keyframes shake {
  0%   { transform: translateX(0); }
  15%  { transform: translateX(-8px); }
  30%  { transform: translateX(8px); }
  45%  { transform: translateX(-8px); }
  60%  { transform: translateX(8px); }
  75%  { transform: translateX(-6px); }
  90%  { transform: translateX(6px); }
  100% { transform: translateX(0); }
}


.shake-row {
  animation: shake 0.6s ease-in-out;
}
</style>
