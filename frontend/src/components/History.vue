<template>
  <b-container>

    <b-row align-h="center">
      <b-col cols="11">
        <b-list-group>
          <b-list-group-item class="flex-column align-items-start" v-for="(history, i) in historyList.content" :key="i">
            <div class="d-flex w-100 justify-content-between">
              <h5 class="mb-1">{{ history.keyword }}</h5>
              <small class="text-muted">{{ history.registrationDate | date }}</small>
            </div>
          </b-list-group-item>
        </b-list-group>
      </b-col>
    </b-row>

    <b-row align-h="center">
      <b-col cols="1">
        <b-button variant="outline-info">이전</b-button>
      </b-col>
      <b-col cols="9">
      </b-col>
      <b-col cols="1">
        <b-button variant="outline-info">다음</b-button>
      </b-col>
    </b-row>

  </b-container>
</template>

<script>
import {history} from '../api'
export default {
  data() {
    return {
      page: 1,
      historyList: ''
    }
  },
  computed: {
  },
  created() {
    this.onSearch()
  },
  methods: {
    onSearch() {
      history.search()
        .then(data => {
          this.historyList = data
          console.log(this.historyList);
        })
        .catch(err => {
          this.error = err.data.error
        })
    }
  }
}
</script>