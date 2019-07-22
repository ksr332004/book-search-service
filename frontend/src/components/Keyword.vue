<template>
  <b-container>

    <b-row align-h="center">
      <b-col cols="11">
        <b-list-group v-if="keywordList > 0">
          <b-list-group-item class="d-flex justify-content-between align-items-center" v-for="(keyword, i) in keywordList" :key="i">
            {{ keyword.keyword }}
            <b-badge variant="primary" pill>{{ keyword.count }}</b-badge>
          </b-list-group-item>
        </b-list-group>
        <b-list-group v-else>
          <b-list-group-item class="d-flex justify-content-between align-items-center">
            결과가 없습니다.
          </b-list-group-item>
        </b-list-group>
      </b-col>
    </b-row>

  </b-container>
</template>

<script>
import {keyword} from '../api'
export default {
  data() {
    return {
      keywordList: ''
    }
  },
  created() {
    this.onSearch()
  },
  methods: {
    onSearch() {
      keyword.search()
        .then(data => {
          this.keywordList = data
          console.log(this.keywordList);
        })
        .catch(err => {
          this.error = err.data.error
        })
    }
  }
}
</script>