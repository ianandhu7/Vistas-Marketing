import { setActivePinia, createPinia } from 'pinia'
import { describe, it, expect, beforeEach, vi } from 'vitest'
import { useThemeStore } from '../theme'

describe('Theme Store', () => {
  beforeEach(() => {
    setActivePinia(createPinia())
    localStorage.clear()
    document.documentElement.removeAttribute('data-theme')
  })

  it('initializes with default dark theme', () => {
    const store = useThemeStore()
    expect(store.theme).toBe('dark')
  })

  it('toggles theme correctly from dark to light and back', () => {
    const store = useThemeStore()
    expect(store.theme).toBe('dark')
    
    store.toggleTheme()
    expect(store.theme).toBe('light')
    expect(document.documentElement.getAttribute('data-theme')).toBe('light')
    expect(localStorage.getItem('theme')).toBe('light')

    store.toggleTheme()
    expect(store.theme).toBe('dark')
    expect(document.documentElement.getAttribute('data-theme')).toBe('dark')
    expect(localStorage.getItem('theme')).toBe('dark')
  })

  it('initializes from saved localStorage theme', () => {
    localStorage.setItem('theme', 'light')
    const store = useThemeStore()
    
    store.initTheme()
    expect(store.theme).toBe('light')
    expect(document.documentElement.getAttribute('data-theme')).toBe('light')
  })
})
