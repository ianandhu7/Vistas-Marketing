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
      :style="{ animationDelay: `${index * 120}ms` }"
    />
  </div>
</template>

<style scoped>
@keyframes fadeSlideUp {
  from {
    opacity: 0;
    transform: translateY(40px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.pricing-card-item {
  animation: fadeSlideUp 0.55s cubic-bezier(0.16, 1, 0.3, 1) both;
}

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
