import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/Login.vue'
import Signup from '../components/Signup.vue'
import Home from '../components/Home.vue'
import History from '../components/History.vue'
import Keyword from '../components/Keyword.vue'
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
      path: '/signup',
      component: Signup
    },
    { 
      path: '/', 
      component: Home,
      beforeEnter: requireAuth
    },
    {
      path: '/history',
      component: History,
      beforeEnter: requireAuth
    },
    {
      path: '/keyword',
      component: Keyword,
      beforeEnter: requireAuth
    },
    { 
      path: '*', 
      component: NotFound 
    }
  ]
})

export default router