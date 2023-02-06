/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {},
    screens: {
      sm: '0px',
      md: "500px",
      lg: "1000px",
      xl: "1500px",
      '2xl': "2000px"
      
    }
  },
  plugins: [],
}