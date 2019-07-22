<template>
  <b-container>

    <b-row align-h="center">
      <b-col cols="6">
        <b-form @submit="onSubmit">
          <b-form-group label="Email" label-for="email">
            <b-form-input id="email" v-model="email" required placeholder="이메일"></b-form-input>
          </b-form-group>
          <b-form-group label="Name" label-for="name">
            <b-form-input id="name" v-model="name" required placeholder="이름"></b-form-input>
          </b-form-group>
          <b-form-group label="Password" label-for="password">
            <b-form-input id="password" v-model="password" required placeholder="비밀번호"></b-form-input>
          </b-form-group>
          <b-button type="submit" :disabled="invalidForm" block variant="primary">Sign up</b-button>
        </b-form>
       </b-col>
    </b-row>

    <b-row align-h="center">
      <b-col cols="6">
          <p class="error" v-if="error">{{error}}</p>
      </b-col>
    </b-row>

  </b-container>
</template>

<script>

</script>