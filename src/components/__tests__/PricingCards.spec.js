import { mount } from '@vue/test-utils'
import { describe, it, expect, beforeEach, vi } from 'vitest'
import { createPinia, setActivePinia } from 'pinia'
import PricingCards from '../home/PricingCards.vue'
import { useSubscriptionStore } from '../../stores/subscription'

// Mock child components
vi.mock('../home/PricingCard.vue', () => ({
  default: {
    name: 'PricingCard',
    props: ['plan'],
    template: '<div class="mock-pricing-card">{{ plan.id }}</div>'
  }
}))

describe('PricingCards Component', () => {
  beforeEach(() => {
    setActivePinia(createPinia())
  })

  it('calls fetchSubscriptionProducts and checkExistingSubscription on mount and renders child cards', () => {
    const store = useSubscriptionStore()
    store.productList = [{ duration: 90 }]
    const fetchProductsSpy = vi.spyOn(store, 'fetchSubscriptionProducts').mockImplementation(() => Promise.resolve())
    const checkSubSpy = vi.spyOn(store, 'checkExistingSubscription').mockImplementation(() => Promise.resolve())

    const wrapper = mount(PricingCards)

    expect(fetchProductsSpy).toHaveBeenCalled()
    expect(checkSubSpy).toHaveBeenCalled()

    const cards = wrapper.findAll('.mock-pricing-card')
    expect(cards.length).toBe(3) // pricingPlans has 3 default cards
    expect(cards[0].text()).toBe('3months')
    expect(cards[1].text()).toBe('6months')
    expect(cards[2].text()).toBe('14months')
  })
})
