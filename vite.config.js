import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { fileURLToPath, URL } from 'node:url'
import compression from 'vite-plugin-compression'

// https://vitejs.dev/config/
export default defineConfig(({ mode }) => ({
  plugins: [
    vue(),
    // Brotli (preferred by modern browsers — ~20% smaller than gzip)
    compression({
      algorithm: 'brotliCompress',
      ext: '.br',
      threshold: 1024,
    }),
    // Gzip fallback for older clients / CDNs
    compression({
      algorithm: 'gzip',
      ext: '.gz',
      threshold: 1024,
    }),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    host: true,
    proxy: {
      '/api': {
        target: 'http://localhost:9090',
        changeOrigin: true,
        secure: false,
      }
    }
  },
  build: {
    target: 'es2015',
    cssCodeSplit: true,
    // Fix #1 — full JS minification via esbuild (faster than terser, same output)
    minify: 'esbuild',
    // Fix #7 — minify CSS in production
    cssMinify: true,
    // Fix #1 — never bundle source maps into production output
    sourcemap: process.env.VITE_SOURCEMAP === 'true' ? 'inline' : false,
    chunkSizeWarningLimit: 600,
    rollupOptions: {
      output: {
        manualChunks: {
          vendor: ['vue', 'pinia', 'vue-router']
        }
      }
    }
  },
  // Fix #1 — explicit esbuild minify options
  esbuild: {
    minifyIdentifiers: true,
    minifySyntax: true,
    minifyWhitespace: true,
    // Drop console.* and debugger in production
    drop: mode === 'production' ? ['console', 'debugger'] : [],
  },
  test: {
    globals: true,
    environment: 'jsdom'
  }
}))
