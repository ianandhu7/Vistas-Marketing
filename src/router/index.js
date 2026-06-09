import { createRouter, createWebHistory } from 'vue-router'
import { defineAsyncComponent } from 'vue'
import HomeView from '../views/HomeView.vue'
import { isLoggedIn } from '../services/storage'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/courses',
      name: 'courses',
      component: HomeView
    },
    {
      path: '/blog',
      name: 'blog',
      component: HomeView
    },
    {
      path: '/pricing',
      name: 'pricing',
      component: HomeView
    },
    {
      path: '/payment-success',
      name: 'payment-success',
      component: defineAsyncComponent(() => import('../views/PaymentSuccessView.vue')),
      meta: { requiresAuth: true }
    },
    {
      path: '/:pathMatch(.*)*',
      name: 'not-found',
      redirect: '/'
    }
  ]
})

// Auth guard — uses localStorage
router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth) {
    if (!isLoggedIn()) {
      next({ name: 'home' })
    } else {
      next()
    }
  } else {
    next()
  }
})

export default router
