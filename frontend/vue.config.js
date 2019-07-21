module.exports = {
  assetsDir: 'static',
  outputDir: 'target/dist',
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true
      }
    }
  }
};