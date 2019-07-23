<template>
  <div>

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

      <b-row align-h="center">
        <b-col cols="12">
          <b-list-group v-if="bookList.totalElements > 0">
            <b-list-group-item class="flex-column align-items-start" v-for="(book, i) in bookList.books" :key="i" @click="showModal(book)">
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
          <b-list-group v-if="bookList && bookList.totalElements < 1">
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
          <b-button block variant="outline-info" v-if="bookList && bookList.first" @click="onSearch(page--)">이전</b-button>
        </b-col>
        <b-col cols="8">
        </b-col>
        <b-col cols="2">
          <b-button block variant="outline-info" v-if="bookList && bookList.last" @click="onSearch(page++)">다음</b-button>
        </b-col>
      </b-row>
    </b-container>

              
    <b-modal id="my-modal" hide-footer>
      <template slot="modal-title" v-if="bookList.apiName == 'NAVER'">
        <h5 v-html="clickedBook.title"></h5>
      </template>
      <template slot="modal-title" v-else>{{ clickedBook.title }}</template>
      <div class="d-block text-center">
        <b-img v-bind:src="clickedBook.thumbnail"
               alt="책이미지"
               width="250px">
        </b-img>
        <p class="mb-1" v-if="bookList.apiName == 'NAVER'" v-html="clickedBook.contents"></p>
        <p class="mb-1" v-else>{{ clickedBook.contents }}</p>
        <p>지은이 : {{ clickedBook.authors }}</p>
        <p>출판사 : {{ clickedBook.publisher }}</p>
        <p>원가 : {{ clickedBook.price }}원</p>
        <p>판매가 : {{ clickedBook.sale_price }}원</p>
        <p>출판일 : {{ clickedBook.publish_date }}</p>
        <p>ISBN : {{ clickedBook.isbn }}</p>
      </div>
      <b-button class="mt-3" block @click="hideModal">닫기</b-button>
    </b-modal>

  </div>
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
      bookList: '',
      clickedBook: ''
    }
  },
  computed: {
    invalidCheck() {
      return !this.query
    }
  },
  methods: {
    onClickSearch() {
      book.search(this.target, this.query, this.page, true)
        .then(data => {
          this.bookList = data
        })
        .catch(err => {
          this.error = err.data.error
        })
    },
    onSearch() {
      book.search(this.target, this.query, this.page, false)
        .then(data => {
          this.bookList = data
        })
        .catch(err => {
          this.error = err.data.error
        })
    },
    showModal(book) {
      this.clickedBook = book;
      this.$bvModal.show('my-modal')
    },
    hideModal() {
      this.$bvModal.hide('my-modal')
    }
  }
}
</script>