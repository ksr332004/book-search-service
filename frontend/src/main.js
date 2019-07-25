import Vue from 'vue'
import router from './router'
import BootstrapVue from 'bootstrap-vue'
import { ModalPlugin } from 'bootstrap-vue'
import { AlertPlugin } from 'bootstrap-vue'
import App from './App.vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(BootstrapVue)
Vue.use(ModalPlugin)
Vue.use(AlertPlugin)

new Vue({
  el: '#app',
  router,
  render: h => h(App)
})