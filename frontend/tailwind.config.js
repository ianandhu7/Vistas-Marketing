/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'vistas-bg': '#0D1B2A',
        'vistas-card': '#1E1B4B',
        'vistas-accent': '#FFB020',
        'vistas-text': '#FFFFFF',
        'vistas-muted': '#A0AEC0',
      },
      borderRadius: {
        'vistas': '12px',
      },
      fontSize: {
        'vistas-body': '12px',
        'vistas-h1': '24px',
        'vistas-h2': '20px',
        'vistas-small': '11px',
      },
      screens: {
        'xs': '400px',
      },
      spacing: {
        'vistas-gap': '8px',
        'vistas-padding': '12px',
      }
    },
  },
  plugins: [],
}
