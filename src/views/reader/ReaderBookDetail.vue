<template>
    <div class="book-detail">
      <!-- 返回按钮 -->
      <el-button type="text" icon="el-icon-arrow-left" @click="goBack" class="back-button">
        返回图书浏览
      </el-button>
  
      <el-card>
        <h2>{{ book.title }}</h2>
        <p>作者：{{ book.author }}</p>
        <p>分类：{{ book.category }}</p>
        <p>简介：{{ book.summary }}</p>
        <p>剩余库存：{{ book.availableQty }} 本</p>
  
        <el-button type="primary" @click="borrowBook" :disabled="book.availableQty === 0">借阅</el-button>
      </el-card>
  
      <el-divider>用户评价</el-divider>
  
      <div v-if="reviews.length === 0">暂无评论</div>
      <el-card v-for="r in reviews" :key="r.id" class="review">
        <p><strong>{{ r.username }}</strong> 评分：{{ r.rating }} ⭐</p>
        <p>{{ r.content }}</p>
        <p class="time">{{ r.createTime }}</p>
      </el-card>
  
      <el-divider>发表评论</el-divider>
  
      <el-form @submit.prevent="submitReview" class="review-form">
        <el-form-item label="评分">
          <el-rate v-model="newReview.rating" />
        </el-form-item>
        <el-form-item label="内容">
          <el-input
            type="textarea"
            v-model="newReview.content"
            placeholder="请输入你的评论"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitReview">提交</el-button>
        </el-form-item>
      </el-form>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import axios from 'axios'
  import { ElMessage } from 'element-plus'
  
  const route = useRoute()
  const router = useRouter()
  const bookId = route.params.id
  
  const book = ref({})
  const reviews = ref([])
  const newReview = ref({ rating: 0, content: '' })
  
  const user = JSON.parse(localStorage.getItem('user'))
  
  onMounted(async () => {
    const bookRes = await axios.get(`http://localhost:8080/api/books/${bookId}`)
    book.value = bookRes.data
  
    const reviewRes = await axios.get(`http://localhost:8080/api/review/books/${bookId}`)
    reviews.value = reviewRes.data
  })
  
  // 提交评论
  const submitReview = async () => {
    if (!newReview.value.content || newReview.value.rating === 0) {
      return ElMessage.warning('请填写评分和评论内容')
    }
  
    const review = {
      ...newReview.value,
      userId: user.id,
      bookId: parseInt(bookId),
      username: user.nickname
    }
  
    const res = await axios.post('http://localhost:8080/api/review', review)
    if (res.data) {
      ElMessage.success('评论成功')
      // 重新加载评论
      const r = await axios.get(`http://localhost:8080/api/review/book/${bookId}`)
      reviews.value = r.data
      newReview.value = { rating: 0, content: '' }
    } else {
      ElMessage.error('评论失败')
    }
  }
  
  // 借阅图书
  const borrowBook = async () => {
    const record = {
      userId: user.id,
      bookId: parseInt(bookId)
    }
  
    const res = await axios.post('http://localhost:8080/api/borrow/borrow', record)
    if (res.data) {
      ElMessage.success('借阅成功')
      book.value.availableQty -= 1
    } else {
      ElMessage.error('借阅失败')
    }
  }
  
  // 返回图书浏览页面
  const goBack = () => {
    router.push('/reader/book') 
  }
  </script>
  
  <style scoped>
  .book-detail {
    padding: 20px;
  }
  
  .review {
    margin-bottom: 15px;
  }
  
  .review-form {
    max-width: 500px;
  }
  
  .time {
    font-size: 12px;
    color: #888;
  }
  
  .back-button {
    margin-bottom: 20px;
  }
  </style>
  