<script setup>
import { onMounted } from 'vue'
import { pricingPlans } from '../../data/pricingPlans'
import PricingCard from './PricingCard.vue'
import { useSubscriptionStore } from '../../stores/subscription'
import { useThemeStore } from '../../stores/theme'

const subscriptionStore = useSubscriptionStore()
const themeStore = useThemeStore()

onMounted(() => {
  subscriptionStore.fetchSubscriptionProducts()
  subscriptionStore.checkExistingSubscription()
})
</script>

<template>
  <div class="pricing-cards-grid grid grid-cols-1 md:grid-cols-3 gap-6 md:gap-4 h-auto lg:h-full">
    <template v-if="subscriptionStore.productList.length === 0">
      <div 
        v-for="i in 3" 
        :key="'skeleton-' + i" 
        class="relative flex flex-col rounded-[16px] border border-[var(--border-light)] bg-[var(--bg-card)] p-6 h-[380px] w-full max-w-[300px] md:max-w-none mx-auto animate-pulse"
      >
        <div class="h-6 bg-gray-700/50 rounded w-2/3 mx-auto mb-4"></div>
        <div class="h-10 bg-gray-700/50 rounded w-1/2 mx-auto mb-6"></div>
        <div class="h-8 bg-gray-700/50 rounded-full w-full mb-8"></div>
        <div class="space-y-3 flex-grow">
          <div class="h-4 bg-gray-700/50 rounded w-full"></div>
          <div class="h-4 bg-gray-700/50 rounded w-5/6"></div>
          <div class="h-4 bg-gray-700/50 rounded w-4/5"></div>
        </div>
      </div>
    </template>
    <template v-else>
      <PricingCard 
        v-for="(plan, index) in pricingPlans" 
        :key="plan.id"
        :plan="plan"
        class="pricing-card-item"
        :style="{ animationDelay: `${index * 120}ms` }"
      />
    </template>
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
