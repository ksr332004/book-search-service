import axios from 'axios'
import router from '../router'

const DOMAIN = 'http://localhost:8080'
const UNAUTHORIZED = 401
const FORBIDDEN = 403
const onUnauthorized = () => {
  router.push(`/login?rPath=${encodeURIComponent(location.pathname)}`)
}

const getRequest = (url, data) => {
  return axios.get(DOMAIN + url, { params: data })
    .then(result => result.data)
    .catch(result => {
      const { status } = result.response
      if (status === UNAUTHORIZED || status ===  FORBIDDEN) onUnauthorized()
      throw result.response
    })
}

const postRequest = (url, data) => {
  return axios.post(DOMAIN + url, data)
    .then(result => result.data)
    .catch(result => {
      const { status } = result.response
      if (status === UNAUTHORIZED || status === FORBIDDEN) onUnauthorized()
      throw result.response
    })
}

export const setAuthInHeader = token => {
  axios.defaults.headers.common['Authorization'] = token ? `Bearer ${token}` : null;
}

const {token} = localStorage
if (token) setAuthInHeader(token)

export const auth = {
  login(email, password) {
    return postRequest('/api/auth', { email, password })
  },
  signup(email, name, password) {
    return postRequest('/api/user', { email, name, password })
  }
}

export const book = {
  search(target, query, page, isButtonEvent) {
    return postRequest('/api/search/book', { target, query, page, isButtonEvent })
  }
}

export const history = {
  search(page) {
    return getRequest('/api/search/keyword/user', page)
    
  }
}

export const keyword = {
  search() {
    return getRequest('/api/search/keyword', {})
  }
}