<template>
  <b-navbar toggleable="lg" type="dark" variant="info">

    <b-navbar-brand>Book Search Service</b-navbar-brand>

    <b-collapse id="nav-collapse" is-nav>

      <b-navbar-nav>
        <b-nav-item to="/">Home</b-nav-item>
        <b-nav-item href="/">History</b-nav-item>
        <b-nav-item href="/">Keyword Rank</b-nav-item>
      </b-navbar-nav>

      <!-- Right aligned nav items -->
      <b-navbar-nav class="ml-auto">
        <b-nav-item href="" v-if="isAuth" @click.prevent="logout">Logout</b-nav-item>
        <b-nav-item v-else to="/login">Login</b-nav-item>
      </b-navbar-nav>

    </b-collapse>

  </b-navbar>
</template>

<script>
import {setAuthInHeader} from '../api'
export default {
  computed: {
    isAuth() {
      return !!localStorage.getItem('token')
    }
  },
  methods: {
    logout() {
      delete localStorage.token
      setAuthInHeader(null)
      this.$router.push('/login')
    }
  }
}
</script>