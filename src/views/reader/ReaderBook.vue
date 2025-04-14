<template>
    <div class="book-page">
      <!-- 搜索框 -->
      <div class="search-bar">
        <el-input
          v-model="keyword"
          placeholder="输入书名或作者关键词"
          prefix-icon="Search"
          clearable
        />
      </div>
  
      <!-- 图书列表 -->
      <div class="book-list">
        <el-card
          v-for="book in paginatedBooks"
          :key="book.id"
          class="book-card"
          shadow="hover"
          @click="goToDetail(book.id)"
        >
          <div class="book-info">
            <h3>{{ book.title }}</h3>
            <p>作者：{{ book.author }}</p>
            <p>简介：{{ book.publisher }}</p>
            <p>库存：{{ book.availableQty }} 本</p>
          </div>
        </el-card>
      </div>
  
      <!-- 分页组件 -->
      <div class="pagination">
        <el-pagination
          background
          layout="prev, pager, next"
          :total="filteredBooks.length"
          :page-size="pageSize"
          :current-page="currentPage"
          @current-change="handlePageChange"
        />
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted, computed } from 'vue'
  import axios from 'axios'
  import { useRouter } from 'vue-router'
  
  const router = useRouter()
  const books = ref([])
  const keyword = ref('')
  
  // 分页相关
  const currentPage = ref(1)
  const pageSize = 9
  
  onMounted(async () => {
    const res = await axios.get('http://localhost:8080/api/books')
    books.value = res.data
  })
  
  const filteredBooks = computed(() => {
    if (!keyword.value) return books.value
    return books.value.filter(
      book =>
        book.title.includes(keyword.value) ||
        book.author.includes(keyword.value)
    )
  })
  
  const paginatedBooks = computed(() => {
    const start = (currentPage.value - 1) * pageSize
    return filteredBooks.value.slice(start, start + pageSize)
  })
  
  const handlePageChange = (page) => {
    currentPage.value = page
  }
  
  const goToDetail = (bookId) => {
    router.push(`/reader/book/${bookId}`)
  }
  </script>
  
  <style scoped>
  .book-page {
    padding: 20px;
    padding-bottom: 80px; /* 给内容区域底部增加空间，避免分页覆盖内容 */
  }
  
  .search-bar {
    margin-bottom: 20px;
    max-width: 400px;
  }
  
  .book-list {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: 20px;
  }
  
  .book-card {
    cursor: pointer;
  }
  
  .book-info h3 {
    margin: 0 0 10px;
    color: #409EFF;
  }
  
  /* 固定分页组件到左下角 */
  .pagination {
    position: fixed;
    bottom: 20px; /* 距离页面底部一定距离 */
    left: 20; /* 距离左侧一定距离 */
    width: auto;
    background-color: #fff;
    padding: 10px;
    box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.05);
    z-index: 1000;
    text-align: left;

  }
  </style>
  