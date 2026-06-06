<script setup>
import { ref } from 'vue'
import { useThemeStore } from '../../stores/theme'
import { useSubscriptionStore } from '../../stores/subscription'

const themeStore = useThemeStore()
const subscriptionStore = useSubscriptionStore()

// Track which card is currently flipped on mobile (tap-to-flip)
const flippedCard = ref(null)

const toggleFlip = (title) => {
  flippedCard.value = flippedCard.value === title ? null : title
}

const handleCardClick = (f) => {
  // On touch devices, toggle flip. On desktop, go to verification.
  if (window.matchMedia('(hover: none)').matches) {
    toggleFlip(f.title)
  } else {
    subscriptionStore.handlePlanSelect('14months')
  }
}

const features = [
  {
    title: "ACADEMICS",
    category: "Board Support",
    desc: "Maths, Science, Social\nEnglish, Kannada, Hindi & more",
    description: "Expert guidance for K-10 students in all core subjects.",
    icon: "school",
    bg: "#3B82F6", // Blue
  },
  {
    title: "ANIMATED VIDEOS",
    category: "Visual Learning",
    desc: "Concepts in easy\n& engaging way",
    description: "Engaging animated lessons that make concepts crystal clear.",
    icon: "play_circle",
    bg: "#A855F7", // Purple
  },
  {
    title: "RECORDED CLASSES",
    category: "Flexibility",
    desc: "Learn anytime,\nanywhere",
    description: "Access high-quality recorded sessions whenever you need.",
    icon: "videocam",
    bg: "#F97316", // Orange
  },
  {
    title: "STUDY MATERIALS",
    category: "Resources",
    desc: "Chapter-wise notes\n& PDFs",
    description: "Comprehensive notes and practice PDFs for quick revision.",
    icon: "description",
    bg: "#10B981", // Green
  },
  {
    title: "TESTS & ASSESSMENTS",
    category: "Practice",
    desc: "Practice, Analyze\n& Improve",
    description: "Smart tests with detailed analytics to track your progress.",
    icon: "check_box", 
    bg: "#EF4444", // Red
  },
  {
    title: "SPOKEN ENGLISH",
    category: "Language",
    desc: "Basic, Advanced &\nBusiness English",
    description: "Build fluency and confidence in English communication.",
    icon: "mic",
    bg: "#8B5CF6", // Indigo/Purple
  },
  {
    title: "CODING",
    category: "Future Skills",
    desc: "From basics to\nreal-world skills",
    description: "Learn logical thinking and software development through projects.",
    icon: "code",
    bg: "#0EA5E9", // Light Blue
  },
  {
    title: "COMMUNICATION",
    category: "Soft Skills",
    desc: "Grammar, Vocabulary\n& Confidence",
    description: "Master the art of expression and improve your body language.",
    icon: "chat",
    bg: "#14B8A6", // Teal
  },
  {
    title: "CREATIVE LEARNING",
    category: "Arts & Hobbies",
    desc: "Music, Guitar,\nDrawing & more",
    description: "Explore your creative side with Music, Drawing and more.",
    icon: "palette",
    bg: "#EAB308", // Yellow
  },
]
</script>

<template>
  <div class="features-wrapper flex flex-col h-full gap-3 relative">
    
    <!-- Header -->
    <div class="flex items-center justify-center gap-2 shrink-0 px-2 mt-1">
      <div class="h-px flex-1 max-w-[40px]" style="background: linear-gradient(90deg, transparent, rgba(168, 85, 247, 0.7))"></div>
      <div class="w-1.5 h-1.5 rotate-45 bg-[#A855F7]"></div>
      <h2 class="text-white text-[13px] font-black uppercase tracking-widest mx-1 drop-shadow-md">
        Everything <span class="text-[#A855F7]">Your Child</span> Gets
      </h2>
      <div class="w-1.5 h-1.5 rotate-45 bg-[#A855F7]"></div>
      <div class="h-px flex-1 max-w-[40px]" style="background: linear-gradient(90deg, rgba(168, 85, 247, 0.7), transparent)"></div>
    </div>

    <!-- 3x3 Grid with Flip effect -->
    <div class="grid grid-cols-3 md:grid-rows-3 gap-2 flex-grow min-h-0 perspective-container">
      <div 
        v-for="f in features" 
        :key="f.title"
        class="flip-card group cursor-pointer"
        :class="{ 'is-flipped': flippedCard === f.title }"
        @click="handleCardClick(f)"
      >
        <div class="flip-inner">
          <!-- FRONT FACE: Horizontal Layout matching reference image -->
          <div 
            class="flip-front feat-card backdrop-blur-md relative rounded-[14px] p-2.5 flex items-center gap-2 overflow-hidden text-left transition-all duration-300"
            :style="{ 
              background: themeStore.theme === 'dark' 
                ? `linear-gradient(135deg, rgba(30, 20, 50, 0.45) 0%, rgba(15, 10, 30, 0.8) 100%)` 
                : `linear-gradient(135deg, rgba(255, 255, 255, 0.8) 0%, rgba(240, 245, 255, 0.9) 100%)`,
              border: themeStore.theme === 'dark' ? `1.5px solid ${f.bg}50` : `1px solid rgba(0,0,0,0.08)`,
              boxShadow: themeStore.theme === 'dark' ? `inset 0 0 15px ${f.bg}10` : `0 4px 15px rgba(0,0,0,0.05)`,
              '--card-glow-color': f.bg,
              '--card-glow-shadow': `${f.bg}60`
            }"
          >
            <!-- Soft background radial glow from top-left -->
            <div class="absolute inset-0 opacity-[0.15] pointer-events-none" :style="{ background: `radial-gradient(circle at 0% 0%, ${f.bg}, transparent 70%)` }"></div>

            <!-- Dot Pattern Top Left -->
            <div class="dot-pattern absolute top-0 left-0 w-24 h-24 pointer-events-none z-0" :class="themeStore.theme === 'dark' ? 'opacity-40' : 'opacity-[0.15] invert'"></div>

            <!-- Left: Glowing Icon -->
            <div class="relative z-10 shrink-0 flex items-center justify-center w-11 h-11">
              <!-- Outer intense glow -->
              <div class="absolute inset-0 rounded-full blur-[12px]" :class="themeStore.theme === 'dark' ? 'opacity-[0.85]' : 'opacity-40'" :style="{ background: f.bg }"></div>
              
              <!-- Inner circle with glass effect -->
              <div class="relative w-9 h-9 rounded-full flex items-center justify-center overflow-hidden border backdrop-blur-md shadow-inner" 
                   :class="themeStore.theme === 'dark' ? 'border-white/30' : 'border-white/60'"
                   :style="{ background: themeStore.theme === 'dark' ? `linear-gradient(135deg, ${f.bg}e6 0%, ${f.bg}60 100%)` : `linear-gradient(135deg, ${f.bg} 0%, ${f.bg}cc 100%)` }">
                 <!-- Glossy highlight top edge -->
                 <div class="absolute top-0 left-0 w-full h-[45%] bg-white/30 rounded-t-full"></div>
                 
                 <!-- Icon -->
                 <span class="material-symbols-outlined text-white text-[20px] z-10 drop-shadow-[0_2px_4px_rgba(0,0,0,0.5)]" style="font-variation-settings: 'FILL' 1">{{ f.icon }}</span>
              </div>
            </div>

            <!-- Right: Text Content -->
            <div class="relative z-10 flex flex-col justify-center pt-0.5 pb-1 flex-1 pr-1.5">
              <h4 class="text-[8px] font-black uppercase leading-tight tracking-wide mb-1"
                  :class="themeStore.theme === 'dark' ? 'text-white' : 'text-gray-900'">{{ f.title }}</h4>
              <p class="text-[6.5px] leading-[1.3] font-semibold whitespace-pre-line"
                 :class="themeStore.theme === 'dark' ? 'text-[#9CA3AF] opacity-90' : 'text-gray-600'">{{ f.desc }}</p>
            </div>

            <!-- Bottom Right Arrow Button -->
            <div class="absolute bottom-1.5 right-1.5 w-[16px] h-[16px] rounded-full flex items-center justify-center z-10 border"
                 :style="{ background: `${f.bg}20`, borderColor: `${f.bg}60`, boxShadow: `0 0 8px ${f.bg}30` }">
               <span class="material-symbols-outlined text-[10px]" :style="{ color: f.bg }">chevron_right</span>
            </div>
          </div>

          <!-- BACK FACE: Details on Hover -->
          <div 
            class="flip-back backdrop-blur-md rounded-[14px] p-4 flex flex-col items-center text-center justify-center gap-1.5 h-full w-full overflow-hidden transition-all duration-300"
            :style="{ 
              background: themeStore.theme === 'dark'
                ? `linear-gradient(135deg, rgba(30, 20, 50, 0.75) 0%, rgba(15, 10, 30, 0.95) 100%)`
                : `linear-gradient(135deg, rgba(255, 255, 255, 0.8) 0%, rgba(240, 245, 255, 0.95) 100%)`,
              border: themeStore.theme === 'dark' ? `1.5px solid ${f.bg}40` : `1px solid rgba(0,0,0,0.08)`,
              boxShadow: themeStore.theme === 'dark' ? 'none' : `0 4px 15px rgba(0,0,0,0.05)`
            }"
          >
            <div class="flex flex-col items-center">
              <h4 class="text-[11px] font-black uppercase tracking-tight"
                  :class="themeStore.theme === 'dark' ? 'text-white' : 'text-gray-900'">{{ f.title }}</h4>
              <span class="text-[9px] font-black uppercase tracking-widest mt-1" :style="{ color: f.bg }">{{ f.category }}</span>
            </div>
            <div class="w-10 h-px my-2" :class="themeStore.theme === 'dark' ? 'bg-white/20' : 'bg-gray-200'"></div>
            <p class="text-[8px] leading-[1.4] font-bold px-2"
               :class="themeStore.theme === 'dark' ? 'text-white/80' : 'text-gray-700'">{{ f.description }}</p>
          </div>
          
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.features-wrapper {
  background-color: transparent;
}

/* Flip Card 3D Setup */
.perspective-container {
  perspective: 1200px;
}

.flip-card {
  background-color: transparent;
  width: 100%;
  height: 100%;
  perspective: 1200px;
  position: relative;
}

.flip-inner {
  position: relative;
  width: 100%;
  height: 100%;
  transition: transform 0.65s cubic-bezier(0.4, 0.2, 0.2, 1);
  transform-style: preserve-3d;
}

.flip-card:hover .flip-inner {
  transform: rotateY(180deg);
}

.flip-front, .flip-back {
  position: absolute;
  width: 100%;
  height: 100%;
  -webkit-backface-visibility: hidden;
  backface-visibility: hidden;
}

.flip-back {
  transform: rotateY(180deg);
}

/* Dotted Pattern Overlay */
.dot-pattern {
  background-image: radial-gradient(rgba(255, 255, 255, 0.45) 1px, transparent 1px);
  background-size: 8px 8px;
  mask-image: radial-gradient(circle at top left, black, transparent 70%);
  -webkit-mask-image: radial-gradient(circle at top left, black, transparent 70%);
}

/* Base card container styles so the theme doesn't ruin it */
[data-theme='light'] .flip-front, [data-theme='light'] .flip-back {
  color: white !important;
}

/* Tap-to-flip support for mobile (touch devices) */
.flip-card.is-flipped .flip-inner {
  transform: rotateY(180deg);
}

@media (max-width: 768px) {
  /* Active card glow on tap for mobile */
  .flip-card:active .feat-card {
    transform: translateY(-2px) scale(1.02) !important;
    border-color: var(--card-glow-color) !important;
    box-shadow: 0 0 15px var(--card-glow-shadow), inset 0 0 10px rgba(255,255,255,0.05) !important;
  }

  .grid-cols-3 {
    grid-template-columns: repeat(3, minmax(0, 1fr)) !important;
    gap: 6px !important;
  }
  
  /* Keep flip working — scale down the cards */
  .flip-card {
    min-height: 70px;
  }
  
  .feat-card h4 {
    font-size: 7.5px !important;
    line-height: 1.1 !important;
    margin-bottom: 2px !important;
  }
  
  .feat-card p {
    font-size: 6px !important;
    line-height: 1.2 !important;
  }
  
  .feat-card .w-11 {
    width: 24px !important;
    height: 24px !important;
  }
  
  .feat-card .w-9 {
    width: 20px !important;
    height: 20px !important;
  }
  
  .feat-card .blur-\[12px\] {
    display: none !important;
  }
  
  .feat-card .material-symbols-outlined {
    font-size: 11px !important;
  }

  /* Scale down back face text for mobile */
  .flip-back h4 {
    font-size: 8px !important;
  }
  
  .flip-back span {
    font-size: 7px !important;
  }
  
  .flip-back p {
    font-size: 6.5px !important;
    padding: 0 4px !important;
  }
  
  .flip-back .w-10 {
    width: 20px !important;
  }
}

@media (max-width: 480px) {
  .grid-cols-3 {
    grid-template-columns: repeat(3, minmax(0, 1fr)) !important;
    gap: 4px !important;
  }
  
  .flip-card {
    min-height: 62px;
  }
  
  .flip-front {
    padding: 4px 2px !important;
  }
  
  .feat-card h4 {
    font-size: 7px !important;
  }
  
  .feat-card p {
    font-size: 5.5px !important;
  }
  
  .feat-card .w-11 {
    width: 20px !important;
    height: 20px !important;
  }
  
  .feat-card .w-9 {
    width: 16px !important;
    height: 16px !important;
  }
  
  .feat-card .material-symbols-outlined {
    font-size: 9px !important;
  }
}
</style>
