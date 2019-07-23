<template>
  <b-container>

    <b-row align-h="center">
      <b-col cols="3">
        <b-form-select v-model="target" :options="options"></b-form-select>
      </b-col>
      <b-col cols="7">
        <b-form-input v-model="query" placeholder="책검색"></b-form-input>
      </b-col>
      <b-col cols="2">
        <b-button @click.prevent="onClickSearch" :disabled="invalidCheck" block variant="info">검색</b-button>
      </b-col>
    </b-row>

    <!-- TODO : Modal로 정보 보이기 -->
    <div>
      <b-button id="show-btn" @click="$bvModal.show('bv-modal-example')">Open Modal</b-button>

      <b-modal id="bv-modal-example" hide-footer>
        <template slot="modal-title">
          Using <code>$bvModal</code> Methods
        </template>
        <div class="d-block text-center">
          <h3>Hello From This Modal!</h3>
        </div>
        <b-button class="mt-3" block @click="$bvModal.hide('bv-modal-example')">Close Me</b-button>
      </b-modal>
    </div>

    <b-row align-h="center">
      <b-col cols="12">
        <b-list-group v-if="bookList.totalElements > 0">
          <b-list-group-item class="flex-column align-items-start" v-for="(book, i) in bookList.books" :key="i">
            <div class="d-flex w-100 justify-content-between" >
              <h5 class="mb-1" v-if="bookList.apiName == 'NAVER'" v-html="book.title"></h5>
              <h5 class="mb-1" v-else>{{ book.title }}</h5>
              <small class="text-muted">{{ book.publish_date }}</small>
            </div>
            <p class="mb-1" v-if="bookList.apiName == 'NAVER'" v-html="book.contents"></p>
            <p class="mb-1" v-else>
              {{ book.contents }}
            </p>
            <small class="text-muted">{{ book.authors }}</small>
          </b-list-group-item>
        </b-list-group>
        <b-list-group v-else>
          <b-list-group-item class="flex-column align-items-start">
            <div class="d-flex w-100 justify-content-between">
              <h5 class="mb-1">검색 결과가 없습니다.</h5>
            </div>
          </b-list-group-item>
        </b-list-group>
      </b-col>
    </b-row>

    <b-row align-h="center">
      <b-col cols="2">
        <b-button block variant="outline-info" v-if="bookList && bookList.hasPrevious">이전</b-button>
      </b-col>
      <b-col cols="8">
      </b-col>
      <b-col cols="2">
        <b-button block variant="outline-info" v-if="bookList && bookList.hasNext">다음</b-button>
      </b-col>
    </b-row>

  </b-container>
</template>

<script>
import {book} from '../api'
export default {
  data() {
    return {
      target: '',
      options: [
        { value: '', text: '검색조건' },
        { value: 'title', text: '제목검색' },
        { value: 'isbn', text: 'ISBN검색' },
        { value: 'publisher', text: '출판사검색'},
        { value: 'person', text: '인명검색'}
      ],
      query: '',
      page: 1,
      bookList: ''
    }
  },
  computed: {
    invalidCheck() {
      return !this.query
    }
  },
  created() {
  },
  methods: {
    onClickSearch() {
      book.search(this.target, this.query, this.page, true)
        .then(data => {
          this.bookList = data
          console.log(this.bookList);
        })
        .catch(err => {
          this.error = err.data.error
        })
    }
  }
}
</script>