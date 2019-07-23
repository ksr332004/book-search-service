import axios from 'axios'
import router from '../router'

const DOMAIN = 'http://localhost:8080'
const UNAUTHORIZED = 401
const onUnauthorized = () => {
  router.push(`/login?rPath=${encodeURIComponent(location.pathname)}`)
}

const request = {
  get(path, data) {
    return axios.get(`${DOMAIN + path}`, { params: data })
      .catch(({ response }) => {
        const { status } = response
        if (status === UNAUTHORIZED) return onUnauthorized()
        throw Error(response)
      })
  },
  post(path, data) {
    return axios.post(`${DOMAIN + path}`, data)
  }
}

export const setAuthInHeader = token => {
  axios.defaults.headers.common['Authorization'] = token ? `Bearer ${token}` : null;
}

const {token} = localStorage
if (token) setAuthInHeader(token)

export const auth = {
  login(email, password) {
    return request.post('/api/auth', { email, password }).then(({ data }) => data)
  },
  signup(email, name, password) {
    return request.post('/api/user', { email, name, password }).then(({ data }) => data)
  }
}

export const book = {
  search(target, query, page, isButtonEvent) {
    return request.post('/api/search/book', { target, query, page, isButtonEvent }).then(({ data }) => data)
  }
}

export const history = {
  search(page) {
    return request.get('/api/search/keyword/user', page).then(({ data }) => data)
    
  }
}

export const keyword = {
  search() {
    return request.get('/api/search/keyword', {}).then(({ data }) => data)
  }
}