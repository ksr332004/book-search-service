<template>
  <b-container>

    <b-row align-h="center">
      <b-col cols="12">
        <b-list-group v-if="historyList && historyList.totalElements > 0">
          <b-list-group-item class="flex-column align-items-start" v-for="(history, i) in historyList.content" :key="i">
            <div class="d-flex w-100 justify-content-between">
              <h5 class="mb-1">{{ history.keyword }}</h5>
              <small class="text-muted">{{ history.registrationDate }}</small>
            </div>
          </b-list-group-item>
        </b-list-group>
        <b-list-group v-else>
          <b-list-group-item class="flex-column align-items-start">
            <div class="d-flex w-100 justify-content-between">
              <h5 class="mb-1">결과가 없습니다.</h5>
            </div>
          </b-list-group-item>
        </b-list-group>
      </b-col>
    </b-row>

    <b-row align-h="center">
      <b-col cols="2">
        <b-button block variant="outline-info" v-if="historyList && !historyList.first" @click="onSearch(page-1)">이전</b-button>
      </b-col>
      <b-col cols="8">
      </b-col>
      <b-col cols="2">
        <b-button block variant="outline-info" v-if="historyList && !historyList.last" @click="onSearch(page+1)">다음</b-button>
      </b-col>
    </b-row>

  </b-container>
</template>

<script>
import {history} from '../api'
export default {
  data() {
    return {
      page: 0,
      historyList: ''
    }
  },
  created() {
    this.onSearch(this.page)
  },
  methods: {
    onSearch(page) {
      history.search({ 'page':page })
        .then(data => {
          this.historyList = data
        })
        .catch(err => {
          this.error = err.data.error
        })
    }
  }
}
</script>