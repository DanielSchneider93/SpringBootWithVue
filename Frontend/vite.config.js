import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    proxy: {
      '/persons': 'http://localhost:8080',  // Proxy für den Endpunkt /persons
    }
  }
})
