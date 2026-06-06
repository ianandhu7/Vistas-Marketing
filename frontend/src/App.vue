<script setup>
import { onMounted } from 'vue'
import { RouterView } from 'vue-router'
import AppNavbar from './components/layout/AppNavbar.vue'
import { useThemeStore } from './stores/theme'

const themeStore = useThemeStore()

onMounted(() => {
  themeStore.initTheme()
})
</script>

<template>
  <div class="app-container font-sans antialiased">
    <AppNavbar />
    <main class="flex-grow min-h-0 relative overflow-hidden">
      <RouterView />
    </main>
  </div>
</template>

<style>
.app-container {
  height: 100vh;
  width: 100vw;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  position: relative;
  background-color: var(--bg-main);
  color: var(--text-primary);
  transition: all 0.3s ease;
}

/* Custom Premium Scrollbar */
::-webkit-scrollbar {
  width: 4px;
}

::-webkit-scrollbar-track {
  background: transparent;
}

::-webkit-scrollbar-thumb {
  background: rgba(168, 85, 247, 0.2);
  border-radius: 10px;
}

::-webkit-scrollbar-thumb:hover {
  background: rgba(168, 85, 247, 0.4);
}

[data-theme='dark'] .app-container {
  background-color: var(--bg-main);
  background-image: none;
  color: #FFFFFF;
}

[data-theme='light'] .app-container {
  background-image: radial-gradient(circle at 50% -20%, #F3EEFF 0%, #FFFFFF 80%);
  background-color: #F8FAFC;
  color: #111827;
}

main {
  flex: 1;
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

/* Smooth transitions for all elements */
* {
  transition: background-color 0.3s ease, border-color 0.3s ease, color 0.3s ease;
}

@media (max-width: 768px) {
  .app-container {
    height: 100vh !important;
    height: 100dvh !important; /* Dynamic viewport height for mobile browsers */
    overflow: hidden !important;
    width: 100% !important;
  }
  main {
    flex: 1 !important;
    min-height: 0 !important;
    overflow: hidden !important;
    display: flex !important;
    flex-direction: column !important;
  }
}
</style>
