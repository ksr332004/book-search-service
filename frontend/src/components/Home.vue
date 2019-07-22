<template>
  <b-container>

    <b-row align-h="center">
      <b-col cols="2">
        <b-form-select v-model="selected" :options="options"></b-form-select>
      </b-col>
      <b-col cols="8">
        <b-form-input v-model="query" placeholder="검색"></b-form-input>
      </b-col>
      <b-col cols="1">
        <b-button variant="info">Info</b-button>
      </b-col>
    </b-row>

    <b-row align-h="center">
      <b-col cols="11">
        <b-list-group>
          <b-list-group-item to="#" class="flex-column align-items-start">
            <div class="d-flex w-100 justify-content-between">
              <h5 class="mb-1">List group item heading</h5>
              <small class="text-muted">3 days ago</small>
            </div>
            <p class="mb-1">
              Donec id elit non mi porta gravida at eget metus. Maecenas sed diam eget risus varius blandit.
            </p>
            <small class="text-muted">Donec id elit non mi porta.</small>
          </b-list-group-item>
        </b-list-group>
      </b-col>
    </b-row>

    <b-row align-h="center">
      <b-col cols="1">
        <b-button variant="outline-info">Info</b-button>
      </b-col>
      <b-col cols="9">
      </b-col>
      <b-col cols="1">
        <b-button variant="outline-info">Info</b-button>
      </b-col>
    </b-row>

  </b-container>
</template>

<script>
export default {
  data() {
    return {
      selected: null,
      options: [
        { value: null, text: '검색조건' },
        { value: 'title', text: '제목검색' },
        { value: 'isbn', text: 'ISBN검색' },
        { value: 'publisher', text: '출판사검색'},
        { value: 'person', text: '인명검색'}
      ]
    }
  }
}
</script>