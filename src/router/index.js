import { createRouter, createWebHistory } from 'vue-router'
import { isLoggedIn } from '../services/storage'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      // Fix #2 — lazy-load the home bundle; it's the largest single chunk
      component: () => import('../views/HomeView.vue')
    },
    {
      path: '/courses',
      name: 'courses',
      component: () => import('../views/HomeView.vue')
    },
    {
      path: '/blog',
      name: 'blog',
      component: () => import('../views/HomeView.vue')
    },
    {
      path: '/pricing',
      name: 'pricing',
      component: () => import('../views/HomeView.vue')
    },
    {
      path: '/payment-success',
      name: 'payment-success',
      component: () => import('../views/PaymentSuccessView.vue'),
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
