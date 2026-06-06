<template>
  <!-- Force Theme Update Trigger -->
  <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-2 sm:gap-3 w-full">
    <!-- Academics -->
    <div class="bg-[var(--bg-card)] border border-[var(--border-light)] rounded-[14px] p-3 flex items-start gap-3 hover:bg-[var(--bg-secondary)] transition-all cursor-pointer shadow-sm group">
      <div class="w-11 h-11 bg-[#4C6FFF] rounded-xl flex items-center justify-center shrink-0 shadow-lg shadow-blue-500/10">
        <span class="material-symbols-outlined text-white text-[24px]">book</span>
      </div>
      <div class="flex flex-col">
        <h4 class="text-[var(--text-primary)] text-[12px] font-black uppercase tracking-tight mb-0.5">Academics</h4>
        <p class="text-[var(--text-secondary)] text-[9px] font-bold leading-tight opacity-80">Maths, Science, Social, English, Kannada, Hindi</p>
      </div>
    </div>

    <!-- Animated Videos -->
    <div class="bg-[var(--bg-card)] border border-[var(--border-light)] rounded-[14px] p-3 flex items-start gap-3 hover:bg-[var(--bg-secondary)] transition-all cursor-pointer shadow-sm group">
      <div class="w-11 h-11 bg-[#4154FF] rounded-xl flex items-center justify-center shrink-0 shadow-lg shadow-blue-600/10">
        <span class="material-symbols-outlined text-white text-[24px]">play_circle</span>
      </div>
      <div class="flex flex-col">
        <h4 class="text-[var(--text-primary)] text-[12px] font-black uppercase tracking-tight mb-0.5">Animated Videos</h4>
        <p class="text-[var(--text-secondary)] text-[9px] font-bold leading-tight opacity-80">Concepts in easy & engaging way</p>
      </div>
    </div>

    <!-- Recorded Classes -->
    <div class="bg-[var(--bg-card)] border border-[var(--border-light)] rounded-[14px] p-3 flex items-start gap-3 hover:bg-[var(--bg-secondary)] transition-all cursor-pointer shadow-sm group">
      <div class="w-11 h-11 bg-[#FF8A00] rounded-xl flex items-center justify-center shrink-0 shadow-lg shadow-orange-500/10">
        <span class="material-symbols-outlined text-white text-[24px]">video_camera_front</span>
      </div>
      <div class="flex flex-col">
        <h4 class="text-[var(--text-primary)] text-[12px] font-black uppercase tracking-tight mb-0.5">Recorded Classes</h4>
        <p class="text-[var(--text-secondary)] text-[9px] font-bold leading-tight opacity-80">Learn anytime, anywhere</p>
      </div>
    </div>

    <!-- Study Materials -->
    <div class="bg-[var(--bg-card)] border border-[var(--border-light)] rounded-[14px] p-3 flex items-start gap-3 hover:bg-[var(--bg-secondary)] transition-all cursor-pointer shadow-sm group">
      <div class="w-11 h-11 bg-[#00D084] rounded-xl flex items-center justify-center shrink-0 shadow-lg shadow-emerald-500/10">
        <span class="material-symbols-outlined text-white text-[24px]">description</span>
      </div>
      <div class="flex flex-col">
        <h4 class="text-[var(--text-primary)] text-[12px] font-black uppercase tracking-tight mb-0.5">Study Materials</h4>
        <p class="text-[var(--text-secondary)] text-[9px] font-bold leading-tight opacity-80">Chapter-wise notes & PDFs</p>
      </div>
    </div>

    <!-- Tests & Assessments -->
    <div class="bg-[var(--bg-card)] border border-[var(--border-light)] rounded-[14px] p-3 flex items-start gap-3 hover:bg-[var(--bg-secondary)] transition-all cursor-pointer shadow-sm group">
      <div class="w-11 h-11 bg-[#FF4D4D] rounded-xl flex items-center justify-center shrink-0 shadow-lg shadow-red-500/10">
        <span class="material-symbols-outlined text-white text-[24px]">task_alt</span>
      </div>
      <div class="flex flex-col">
        <h4 class="text-[var(--text-primary)] text-[12px] font-black uppercase tracking-tight mb-0.5">Tests & Assessments</h4>
        <p class="text-[var(--text-secondary)] text-[9px] font-bold leading-tight opacity-80">Practice, Analyze & Improve</p>
      </div>
    </div>

    <!-- Spoken English -->
    <div class="bg-[var(--bg-card)] border border-[var(--border-light)] rounded-[14px] p-3 flex items-start gap-3 hover:bg-[var(--bg-secondary)] transition-all cursor-pointer shadow-sm group">
      <div class="w-11 h-11 bg-[#6C5CE7] rounded-xl flex items-center justify-center shrink-0 shadow-lg shadow-indigo-600/10">
        <span class="material-symbols-outlined text-white text-[24px]">mic</span>
      </div>
      <div class="flex flex-col">
        <h4 class="text-[var(--text-primary)] text-[12px] font-black uppercase tracking-tight mb-0.5">Spoken English</h4>
        <p class="text-[var(--text-secondary)] text-[9px] font-bold leading-tight opacity-80">Basic, Advanced & Business English</p>
      </div>
    </div>

    <!-- Coding -->
    <div class="bg-[var(--bg-card)] border border-[var(--border-light)] rounded-[14px] p-3 flex items-start gap-3 hover:bg-[var(--bg-secondary)] transition-all cursor-pointer shadow-sm group">
      <div class="w-11 h-11 bg-[#00A8FF] rounded-xl flex items-center justify-center shrink-0 shadow-lg shadow-sky-500/10">
        <span class="material-symbols-outlined text-white text-[24px]">code</span>
      </div>
      <div class="flex flex-col">
        <h4 class="text-[var(--text-primary)] text-[12px] font-black uppercase tracking-tight mb-0.5">Coding</h4>
        <p class="text-[var(--text-secondary)] text-[9px] font-bold leading-tight opacity-80">From basics to real-world skills</p>
      </div>
    </div>

    <!-- Communication -->
    <div class="bg-[var(--bg-card)] border border-[var(--border-light)] rounded-[14px] p-3 flex items-start gap-3 hover:bg-[var(--bg-secondary)] transition-all cursor-pointer shadow-sm group">
      <div class="w-11 h-11 bg-[#00B894] rounded-xl flex items-center justify-center shrink-0 shadow-lg shadow-teal-500/10">
        <span class="material-symbols-outlined text-white text-[24px]">chat_bubble</span>
      </div>
      <div class="flex flex-col">
        <h4 class="text-[var(--text-primary)] text-[12px] font-black uppercase tracking-tight mb-0.5">Communication</h4>
        <p class="text-[var(--text-secondary)] text-[9px] font-bold leading-tight opacity-80">Grammar, Vocabulary & Confidence</p>
      </div>
    </div>

    <!-- Creative Learning -->
    <div class="bg-[var(--bg-card)] border border-[var(--border-light)] rounded-[14px] p-3 flex items-start gap-3 hover:bg-[var(--bg-secondary)] transition-all cursor-pointer shadow-sm group">
      <div class="w-11 h-11 bg-[#F9CA24] rounded-xl flex items-center justify-center shrink-0 shadow-lg shadow-yellow-500/10">
        <span class="material-symbols-outlined text-white text-[24px]">palette</span>
      </div>
      <div class="flex flex-col">
        <h4 class="text-[var(--text-primary)] text-[12px] font-black uppercase tracking-tight mb-0.5">Creative Learning</h4>
        <p class="text-[var(--text-secondary)] text-[9px] font-bold leading-tight opacity-80">Music, Guitar, Drawing & more</p>
      </div>
    </div>
  </div>
</template>

<script setup>
</script>
