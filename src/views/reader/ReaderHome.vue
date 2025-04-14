<template>
    <div class="home">
      <h2>欢迎回来，{{ nickname }}！</h2>
  
      <div class="stats-box">
        <el-card class="stat-card">
          <div class="stat-title">系统图书总数</div>
          <div class="stat-value">{{ stats.bookCount }}</div>
        </el-card>
  
        <el-card class="stat-card">
          <div class="stat-title">你已借图书</div>
          <div class="stat-value">{{ borrowCount }}</div>
        </el-card>
  
        <el-card class="stat-card">
          <div class="stat-title">即将到期图书</div>
          <div class="stat-value">{{ remindList.length }}</div>
        </el-card>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import axios from 'axios'
  
  const nickname = ref('')
  const stats = ref({ bookCount: 0 })
  const borrowCount = ref(0)
  const remindList = ref([])
  
  onMounted(async () => {
    const user = JSON.parse(localStorage.getItem('user'))
    if (user) {
      nickname.value = user.nickname
  
      // 获取系统统计信息
      const res1 = await axios.get('http://localhost:8080/api/stats/overview')
      stats.value = res1.data
  
      // 获取用户借阅记录
      const res2 = await axios.get(`http://localhost:8080/api/borrow/user/${user.id}`)
      borrowCount.value = res2.data.length
  
      // 获取即将到期提醒
      const res3 = await axios.get(`http://localhost:8080/api/borrow/remind/${user.id}`)
      remindList.value = res3.data
    }
  })
  </script>
  
  <style scoped>
  .home {
    text-align: center;
  }
  
  h2 {
    margin-bottom: 30px;
  }
  
  .stats-box {
    display: flex;
    justify-content: space-around;
    flex-wrap: wrap;
    gap: 20px;
  }
  
  .stat-card {
    width: 250px;
    padding: 20px;
  }
  
  .stat-title {
    font-size: 16px;
    margin-bottom: 10px;
    color: #666;
  }
  
  .stat-value {
    font-size: 32px;
    font-weight: bold;
    color: #409EFF;
  }
  </style>
  