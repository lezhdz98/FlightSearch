import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    port: 8080, // Change this to your desired port number
  },
  build: {
    outDir: 'dist', // Ensure the build output directory is 'dist'
  },
});