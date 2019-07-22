<template>
  <div id="app">
    <Navbar />    
    <router-view />
  </div>
</template>

<script>
import Navbar from './components/Navbar.vue'
export default {
  name: 'app',
  components: { Navbar },
  data () {
    return {
      
    }
  }
}
</script>

<style>
.row {
  margin-top: 20px
}
</style>
