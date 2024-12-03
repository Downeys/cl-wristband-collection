/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{ts,tsx,js,jsx,cljs}"],
  theme: {
    extend: {
      boxShadow: {
        pink: "0px 0px 6px 3px rgba(255, 0, 255, 0.25)",
        blue: "0px 0px 6px 3px rgba(6, 231, 236, 0.25)",
        green: "0px 0px 6px 3px rgba(58, 229, 0, 0.25)",
        footer: "0px -4px 4px rgba(255, 255, 255, 0.25)",
        header: "0px 4px 4px rgba(255, 255, 255, 0.25)"
      },
      colors: {
        wbGreen: "#3ae500",
        wbBlue: "#74efdb",
        wbPink: "#fb03bc"
      },
      fontFamily: {
        primary: ['Lacquer', 'sans-serif'],
        secondary: ['Life Savers']
      }
    },
  },
  plugins: [],
}

