import axios from 'axios'
import router from '../router'

const DOMAIN = 'http://localhost:8080'
const UNAUTHORIZED = 401
const onUnauthorized = () => {
  router.push(`/login?rPath=${encodeURIComponent(location.pathname)}`)
}

const request = (method, url, data) => {
  console.log(axios.defaults.headers.common['Authorization']);

  return axios({
    method,
    url: DOMAIN + url,
    data
  }).then(result => result.data)
    .catch(result => {
      const {status} = result.response
      if (status === UNAUTHORIZED) onUnauthorized()
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
    return request('post', '/api/auth', { email, password }) 
  },
  signup(email, name, password) {
    return request('post', '/api/user', { email, name, password })
  }
}

export const book = {
  search(target, query, page, buttonEvent) {
    return request('post', '/api/search/book', { target, query, page, buttonEvent })
  }
}

export const history = {
  search() {
    return request('get', '/api/search/keyword/user', { })
  }
}

export const keyword = {
  search() {
    return request('get', '/api/search/keyword', { })
  }
}