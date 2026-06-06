import { defineStore } from 'pinia'
import { ref, onMounted } from 'vue'

export const useThemeStore = defineStore('theme', () => {
  const theme = ref('dark') // Default to dark for consistency

  const toggleTheme = () => {
    theme.value = theme.value === 'dark' ? 'light' : 'dark'
    document.documentElement.setAttribute('data-theme', theme.value)
    localStorage.setItem('theme', theme.value)
  }

  const initTheme = () => {
    const savedTheme = localStorage.getItem('theme') || 'dark'
    theme.value = savedTheme
    document.documentElement.setAttribute('data-theme', savedTheme)
  }

  return {
    theme,
    toggleTheme,
    initTheme
  }
})
