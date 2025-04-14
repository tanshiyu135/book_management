<template>
    <div>
      <!-- 顶部导航栏 -->
      <header class="navbar">
        <div class="nav-container">
          <!-- 左侧 Logo -->
          <div class="logo">高校图书系统</div>
  
          <!-- 中间导航项 -->
          <ul class="nav-list">
            <li><router-link to="/reader/home">首页</router-link></li>
            <li><router-link to="/reader/book">图书</router-link></li>
            <li><router-link to="/reader/record">借阅记录</router-link></li>
            <li><router-link to="/reader/profile">个人中心</router-link></li>
          </ul>
  
          <!-- 右侧 用户昵称 + 退出按钮 -->
          <div class="right-box">
            <span class="username">{{ username }}</span>
            <el-button size="small" type="danger" @click="logout" plain>退出</el-button>
          </div>
        </div>
      </header>
  
      <!-- 页面内容 -->
      <main class="content">
        <router-view />
      </main>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import { useRouter } from 'vue-router'
  import { ElMessage } from 'element-plus'
  
  const router = useRouter()
  const username = ref('')
  
  onMounted(() => {
    const user = JSON.parse(localStorage.getItem('user'))
    if (user) {
      username.value = user.nickname || '未命名用户'
    } else {
      ElMessage.error('请先登录')
      router.push('/login')
    }
  })
  
  const logout = () => {
    localStorage.removeItem('user')
    ElMessage.success('已退出登录')
    router.push('/login')
  }
  </script>
  
  <style scoped>
  /* 固定顶部导航栏 */
  .navbar {
    background-color: #409EFF;
    padding: 12px 30px;
    position: fixed;  /* 固定导航栏 */
    top: 0;
    left: 0;
    right: 0;
    z-index: 10;  /* 确保导航栏在最上层 */
  }
  
  /* 容器最大宽度设置，保持居中 */
  .nav-container {
    display: flex;
    align-items: center;
    justify-content: space-between;
    max-width: 1200px;
    margin: 0 auto;
    position: relative;
  }
  
  /* 左侧 Logo */
  .logo {
    color: #fff;
    font-size: 20px;
    font-weight: bold;
    white-space: nowrap;
  }
  
  /* 中间导航项 */
  .nav-list {
    position: absolute;
    left: 50%;
    transform: translateX(-50%);
    display: flex;
    list-style: none;
    gap: 30px;
    padding: 0;
    margin: 0;
  }
  
  .nav-list li a {
    color: #fff;
    font-weight: 500;
    text-decoration: none;
  }
  
  .nav-list li a:hover {
    text-decoration: underline;
  }
  
  /* 右侧昵称 + 退出按钮 */
  .right-box {
    display: flex;
    align-items: center;
    gap: 10px;
  }
  
  .username {
    color: #fff;
  }
  
  /* 页面内容容器 */
  .content {
    max-width: 1000px;
    margin: 90px auto 30px;  /* 由于导航栏固定，给页面内容设置顶部外边距 */
    padding: 20px;
    background: #fff;
    border-radius: 8px;
  }
  </style>
  