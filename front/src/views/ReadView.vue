<script setup lang="ts">

import {defineProps, onMounted, ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

const router = useRouter();

const props = defineProps({
  postId: {
    type: [Number, String],
    require: true,
  }
});

const post = ref({
  id: 0,
  title: "",
  content: ""
});

const moveToEdit = () => {
  router.push({name: 'edit', params: {postId: props.postId}})
}

onMounted(() => {
  axios.get(`/my-backend-api/posts/${props.postId}`)
      .then(response => {
        post.value = response.data
      })
})
</script>

<template>

  <el-row>
    <el-col>
      <h2 class="title">{{ post.title }}</h2>

      <div class="sub d-flex">
        <div class="category">개발</div>
        <div class="regDate">2023-08-05 23:01:14</div>
      </div>
    </el-col>
  </el-row>

  <el-row class="mt-3">
    <el-col>
      <div class="content">{{ post.content }}</div>
    </el-col>
  </el-row>

  <el-row class="mt-3">
    <el-col>
      <div class="d-flex justify-content-end">
        <el-button type="warning" @click="moveToEdit()">수정</el-button>
      </div>
    </el-col>
  </el-row>
</template>

<style scoped lang="scss">
.title {
  color: #383838;
  font-size: 1.6rem;
  font-weight: 600;
  margin: 0;
}

.sub {
  margin-top: 10px;
  font-size: 0.78rem;

  .regDate {
    margin-left: 10px;
    color: #9b9b9b;
  }
}

.content {
  color: #9b9b9b;
  margin-top: 12px;
  font-size: 0.95rem;
  white-space: break-spaces;
  line-height: 1.5;
}

</style>