<template>
  <b-container>

    <b-row align-h="center">
      <b-col cols="6">
        <b-form @submit.prevent="onSubmit">
          <b-form-group label="Email" label-for="email">
            <b-form-input id="email" v-model="email" required placeholder="이메일을입력해주세요."></b-form-input>
          </b-form-group>
          <b-form-group label="Name" label-for="name">
            <b-form-input id="name" v-model="name" required placeholder="이름을입력해주세요."></b-form-input>
          </b-form-group>
          <b-form-group label="Password" label-for="password">
            <b-form-input type="password" id="password" v-model="password" required placeholder="5자 이상 15자 이하의 비밀번호를 입력해주세요."></b-form-input>
          </b-form-group>
          <b-button type="submit" :disabled="invalidForm" block variant="primary">Sign up</b-button>
        </b-form>
       </b-col>
    </b-row>

    <b-row align-h="center" v-if="message">
      <b-col cols="6">
        <b-alert show variant="warning" >{{message}}</b-alert>
      </b-col>
    </b-row>
    <b-row align-h="center" v-if="error">
      <b-col cols="6">
        <b-alert show variant="danger" >{{error}}</b-alert>
      </b-col>
    </b-row>

  </b-container>
</template>

<script>
import {auth} from '../api'
export default {
  data() {
    return {
      email: '',
      password: '',
      name: '',
      error: '',
      message: '',
      rPath: ''
    }
  },
  computed: {
    invalidForm() {
      return !this.email || !this.name || !this.password
    }
  },
  created() {
    this.rPath = '/login'
  },
  methods: {
    onSubmit() {
      auth.signup(this.email, this.name, this.password)
        .then(data => {
          this.$router.push(this.rPath)
        })
        .catch(err => {
          this.message = err.data.message
          this.error = err.data.error
        })
    }
  }
}
</script>