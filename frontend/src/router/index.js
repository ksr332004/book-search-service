import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/Login.vue'
import Home from '../components/Home.vue'
import NotFound from '../components/NotFound.vue'

Vue.use(VueRouter)

const requireAuth = (to, from, next) => {
  const isAuth = localStorage.getItem('token')
  const loginPath = `/login?rPath=${encodeURIComponent(to.path)}`
  isAuth ? next() : next(loginPath)
}

const router = new VueRouter({
  mode: 'history',
  routes: [
    { 
      path: '/login', 
      component: Login 
    },
    { 
      path: '/', 
      component: Home,
      beforeEnter: requireAuth
    },
    { 
      path: '*', 
      component: NotFound 
    }
  ]
})

export default router