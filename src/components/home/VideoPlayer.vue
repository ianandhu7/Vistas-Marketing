<script setup>
import { ref, watch, onMounted, onUnmounted } from 'vue'

const currentVideoIndex = ref(0)
const videoElement = ref(null)
const progress = ref(0)
const isPlaying = ref(true)
const isLoading = ref(false)
const deferredSrc = ref('')
const isVideoPlaying = ref(false)
const userInteracted = ref(false)

const handleInteraction = () => {
  if (!userInteracted.value) {
    userInteracted.value = true
    deferredSrc.value = videos[currentVideoIndex.value].src
    removeListeners()
  }
}

const removeListeners = () => {
  window.removeEventListener('scroll', handleInteraction)
  window.removeEventListener('click', handleInteraction)
  window.removeEventListener('touchstart', handleInteraction)
}

const videos = [
  { 
    headline: "Interactive Concepts.", 
    subheadline: "Learn with\nEngaging &\nAnimated Videos.",
    src: "/7552660-hd_1920_1080_25fps.mp4"
  },
  { 
    headline: "Expert Educators.", 
    subheadline: "Get Guidance\nFrom the\nBest Teachers.",
    src: "/8170437-uhd_3840_2160_25fps.mp4"
  },
  { 
    headline: "Future Skills.", 
    subheadline: "Coding, Arts &\nGlobal Readiness\nFor All.",
    src: "/8519333-uhd_3840_2160_30fps.mp4"
  }
]

const updateProgress = () => {
  if (videoElement.value) {
    const val = (videoElement.value.currentTime / videoElement.value.duration) * 100
    progress.value = val || 0
  }
}

const togglePlay = () => {
  if (videoElement.value) {
    if (videoElement.value.paused) {
      videoElement.value.play()
      isPlaying.value = true
    } else {
      videoElement.value.pause()
      isPlaying.value = false
    }
  }
}

// Reload video when index changes
watch(currentVideoIndex, () => {
  if (videoElement.value) {
    isVideoPlaying.value = false
    deferredSrc.value = videos[currentVideoIndex.value].src
    videoElement.value.load()
    videoElement.value.play()
    isPlaying.value = true
  } else {
    userInteracted.value = true
    deferredSrc.value = videos[currentVideoIndex.value].src
    removeListeners()
  }
})

const handleVideoEnded = () => {
  currentVideoIndex.value = (currentVideoIndex.value + 1) % videos.length
}

const stats = [
  { line1: "15,000+", line2: "Videos", icon: "play_circle" },
  { line1: "Expert", line2: "Teachers", icon: "school" },
  { line1: "Concept Based", line2: "Learning", icon: "lightbulb" },
  { line1: "Affordable", line2: "For Everyone", icon: "payments" },
  { line1: "Learn Anytime,", line2: "Anywhere", icon: "devices" }
]

let autoSlideTimer = null

const startAutoSlide = () => {
  autoSlideTimer = setInterval(() => {
    // Only slide if video is paused (user is not watching)
    if (videoElement.value && videoElement.value.paused) {
      currentVideoIndex.value = (currentVideoIndex.value + 1) % videos.length
    }
  }, 6000)
}

onMounted(() => {
  startAutoSlide()
  window.addEventListener('scroll', handleInteraction, { passive: true })
  window.addEventListener('click', handleInteraction, { passive: true })
  window.addEventListener('touchstart', handleInteraction, { passive: true })
  setTimeout(() => {
    isLoading.value = false
  }, 800)
})

onUnmounted(() => {
  if (autoSlideTimer) clearInterval(autoSlideTimer)
  removeListeners()
})
</script>

<template>
  <!-- Skeleton Loader for Mobile / Premium Feel -->
  <div v-if="isLoading" class="flex flex-col h-full animate-pulse space-y-4">
    <div class="video-player-container-skeleton relative flex-grow bg-[#1a0a3d]/60 border border-[var(--border-light)] rounded-[14px] min-h-[220px] md:min-h-0 flex items-center justify-center">
      <span class="material-symbols-outlined text-purple-600/40 text-[48px] animate-spin">progress_activity</span>
    </div>
    <div class="flex justify-center gap-[8px] py-[12px]">
      <div v-for="i in 3" :key="i" class="w-2.5 h-2.5 rounded-full bg-purple-900/40"></div>
    </div>
    <div class="h-12 bg-[#1a0a3d]/60 border border-[var(--border-light)] rounded-[12px] w-full"></div>
  </div>

  <div v-else class="flex flex-col h-full">
    <!-- Video Player Section -->
    <div class="video-player-container relative group flex-grow bg-black rounded-[14px] overflow-hidden shadow-2xl border border-[var(--border-light)] min-h-0">
      <!-- Poster Image (Permanently mounted LCP Element) -->
      <img 
        src="/hero-video-thumbnail.webp" 
        alt="Vistas Learning Video Poster" 
        class="absolute inset-0 w-full h-full object-cover transition-opacity duration-1000 z-10"
        :class="isVideoPlaying ? 'opacity-0 pointer-events-none' : 'opacity-100'"
        width="1280"
        height="720"
        style="aspect-ratio: 16/9"
        fetchpriority="high"
        loading="eager"
      />
      
      <!-- Video Element -->
      <video 
        v-if="userInteracted"
        ref="videoElement"
        class="w-full h-full object-cover opacity-100"
        autoplay 
        muted 
        playsinline
        preload="metadata"
        :src="deferredSrc"
        width="1280"
        height="720"
        style="aspect-ratio: 16/9"
        @playing="isVideoPlaying = true"
        @ended="handleVideoEnded"
        @timeupdate="updateProgress"
      >
        Your browser does not support the video tag.
      </video>
      
      <!-- Gradient Overlay (Flux Deep) -->
      <div class="absolute inset-0 bg-gradient-to-r from-[var(--bg-main)]/80 via-[var(--bg-main)]/20 to-transparent pointer-events-none"></div>
      
      <!-- Video Content Overlay -->
      <div class="absolute inset-0 p-6 flex flex-col justify-center pointer-events-none">
        <div class="video-text-overlay max-w-[55%] space-y-4">
          <!-- Logo -->
          <div class="flex items-center gap-2">
            <div class="w-7 h-7 bg-[var(--bg-secondary)]/50 backdrop-blur-md border border-[var(--border-light)] rounded-lg flex items-center justify-center shadow-sm">
              <span class="material-symbols-outlined text-[var(--accent-purple)] text-[16px]" style="font-variation-settings: 'FILL' 1">school</span>
            </div>
            <span class="text-[var(--text-primary)] text-[10px] font-black tracking-[0.15em] uppercase">Vistas Learning</span>
          </div>
          
          <!-- Headline (Dynamic) -->
          <div class="transition-all duration-500 transform">
            <p class="text-[var(--text-primary)] text-[26px] font-black leading-[1.15] tracking-tight italic drop-shadow-lg">
              {{ videos[currentVideoIndex].headline }}
            </p>
            <p class="text-[var(--accent-purple)] text-[24px] font-black leading-[1.15] tracking-tight whitespace-pre-line drop-shadow-lg">
              {{ videos[currentVideoIndex].subheadline }}
            </p>
          </div>
        </div>
      </div>

      <!-- Play/Pause Button (Center) -->
      <div 
        @click="togglePlay"
        class="absolute inset-0 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity duration-300 z-30"
      >
        <div class="w-16 h-16 bg-[var(--accent-purple)]/90 text-white rounded-full flex items-center justify-center shadow-2xl cursor-pointer hover:scale-110 transition-transform">
          <span class="material-symbols-outlined text-white text-[32px]" style="font-variation-settings: 'FILL' 1">
            {{ isPlaying ? 'pause' : 'play_arrow' }}
          </span>
        </div>
      </div>

      <!-- Video Progress Bar -->
      <div class="absolute bottom-0 left-0 right-0 px-4 pb-3 pt-8 bg-gradient-to-t from-black/80 to-transparent pointer-events-none">
        <div class="flex justify-end items-center mb-1.5 text-white">
          <div class="flex gap-3">
            <span class="material-symbols-outlined text-[16px] drop-shadow-md">volume_up</span>
          </div>
        </div>
        <div class="h-[3px] bg-white/20 rounded-full overflow-hidden">
          <div 
            class="h-full bg-[var(--accent-purple)] relative rounded-full shadow-lg transition-all duration-100 ease-linear"
            :style="{ width: `${progress}%` }"
          >
            <div class="absolute right-0 top-1/2 -translate-y-1/2 w-3 h-3 bg-white rounded-full shadow-lg border-2 border-[var(--accent-purple)]"></div>
          </div>
        </div>
      </div>
    </div>

    <!-- Dot Pagination Indicators -->
    <div class="flex justify-center items-center gap-[8px] py-[12px]">
      <button 
        v-for="(video, index) in videos" 
        :key="index"
        @click="currentVideoIndex = index"
        :aria-label="'Go to video slide ' + (index + 1)"
        class="w-6 h-6 flex items-center justify-center cursor-pointer z-20"
      >
        <span 
          class="transition-all duration-300 rounded-full"
          :style="{
            width: currentVideoIndex === index ? '8px' : '6px',
            height: currentVideoIndex === index ? '8px' : '6px',
            backgroundColor: currentVideoIndex === index ? '#7B2FBE' : 'transparent',
            border: currentVideoIndex === index ? 'none' : '1.5px solid #888'
          }"
        ></span>
      </button>
    </div>

    <!-- Stats Bar -->
    <div class="stats-bar flex items-center justify-between bg-[var(--bg-secondary)] border border-[var(--border-light)] rounded-[12px] px-5 py-2.5 shrink-0 shadow-lg">
      <div v-for="stat in stats" :key="stat.line1" class="flex items-center gap-2 group">
        <div class="w-7 h-7 rounded-full bg-[var(--bg-main)] border border-[var(--border-light)] flex items-center justify-center group-hover:scale-110 transition-transform">
          <span class="material-symbols-outlined text-[18px] text-[var(--accent-purple)]" style="font-variation-settings: 'FILL' 1">{{ stat.icon }}</span>
        </div>
        <div class="flex flex-col leading-none">
          <span class="text-[10px] font-black text-[var(--text-primary)]">{{ stat.line1 }}</span>
          <span class="text-[9px] font-bold text-[var(--text-secondary)]">{{ stat.line2 }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@media (max-width: 768px) {
  .video-player-container {
    height: auto !important;
    aspect-ratio: 16/9 !important;
  }
  
  .absolute.inset-0.p-6.flex.flex-col.justify-center.pointer-events-none {
    padding: 1rem !important;
  }
  
  .video-text-overlay {
    max-width: 85% !important;
    gap: 0.5rem !important;
  }
  
  .video-text-overlay h2:first-of-type {
    font-size: 18px !important;
    line-height: 1.2 !important;
  }
  
  .video-text-overlay h2:last-of-type {
    font-size: 16px !important;
    line-height: 1.2 !important;
  }
  
  .stats-bar {
    flex-wrap: nowrap !important;
    overflow-x: auto !important;
    scroll-snap-type: x mandatory !important;
    -webkit-overflow-scrolling: touch !important;
    gap: 12px !important;
    padding: 8px 12px !important;
    justify-content: flex-start !important;
    scrollbar-width: none;
  }

  .stats-bar::-webkit-scrollbar {
    display: none;
  }

  .stats-bar > div {
    flex: 0 0 auto !important;
    scroll-snap-align: center !important;
    width: 120px !important;
    justify-content: center !important;
    background: rgba(123, 47, 190, 0.12) !important;
    border: 1px solid rgba(123, 47, 190, 0.25) !important;
    border-radius: 10px !important;
    padding: 6px 8px !important;
    transition: transform 0.2s ease, box-shadow 0.2s ease;
  }

  .stats-bar > div:active {
    transform: scale(0.95);
  }

  .stats-bar .w-7 {
    width: 22px !important;
    height: 22px !important;
  }

  .stats-bar .material-symbols-outlined {
    font-size: 13px !important;
  }

  .stats-bar span.text-\[10px\] {
    font-size: 9px !important;
    line-height: 1.1 !important;
  }

  .stats-bar span.text-\[9px\] {
    font-size: 8px !important;
    line-height: 1.1 !important;
  }
}

@media (max-width: 480px) {
  .stats-bar {
    gap: 10px !important;
    padding: 6px 10px !important;
  }

  .stats-bar > div {
    width: 110px !important;
  }

  .stats-bar .w-7 {
    width: 18px !important;
    height: 18px !important;
  }

  .stats-bar .material-symbols-outlined {
    font-size: 11px !important;
  }

  .stats-bar span.text-\[10px\] {
    font-size: 8px !important;
  }

  .stats-bar span.text-\[9px\] {
    font-size: 7px !important;
  }
}
</style>
