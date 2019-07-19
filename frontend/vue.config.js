module.exports = {
  assetsDir: 'static',
  outputDir: 'target/dist',
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8098',
        ws: true,
        changeOrigin: true
      }
    }
  }
};