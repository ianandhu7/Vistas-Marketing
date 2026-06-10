import { mount } from '@vue/test-utils'
import { describe, it, expect, beforeEach, vi } from 'vitest'
import { createPinia, setActivePinia } from 'pinia'
import PricingCard from '../home/PricingCard.vue'
import { useSubscriptionStore } from '../../stores/subscription'

describe('PricingCard Component', () => {
  const mockPlan = {
    id: '3months',
    name: '3 Months',
    durationName: '3 Months',
    price: '₹999',
    features: [
      { text: 'Feature A', included: true },
      { text: 'Feature B', included: false }
    ]
  }

  beforeEach(() => {
    setActivePinia(createPinia())
  })

  it('renders plan details name and price correctly', () => {
    const wrapper = mount(PricingCard, {
      props: {
        plan: mockPlan
      }
    })

    expect(wrapper.text()).toContain('3 Months Plan')
    expect(wrapper.text()).toContain('₹999')
  })

  it('shows subscribe button and triggers handlePlanSelect on click when not subscribed', async () => {
    const store = useSubscriptionStore()
    const selectSpy = vi.spyOn(store, 'handlePlanSelect').mockImplementation(() => {})

    const wrapper = mount(PricingCard, {
      props: {
        plan: mockPlan
      }
    })

    const button = wrapper.find('.cta-button')
    expect(button.exists()).toBe(true)
    expect(button.text()).toBe('SUBSCRIBE NOW')

    await button.trigger('click')
    expect(selectSpy).toHaveBeenCalledWith('3months')
  })

  it('does not show subscribe button but shows active subscription label and GO TO PORTAL button when subscribed', () => {
    const store = useSubscriptionStore()
    store.subscriptionCheckDone = true
    store.existingSubscription = true

    const wrapper = mount(PricingCard, {
      props: {
        plan: mockPlan
      }
    })

    expect(wrapper.text()).toContain('You already have an active subscription!')
    expect(wrapper.find('button.cta-button').exists()).toBe(false)

    const portalLink = wrapper.find('a.cta-button')
    expect(portalLink.exists()).toBe(true)
    expect(portalLink.text()).toBe('GO TO PORTAL')
    expect(portalLink.attributes('href')).toBe('https://www.student.vistaslearning.com/guest/')
  })
})
