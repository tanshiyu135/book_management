<template>
    <el-container style="height: 100vh;">
      <!-- 侧边栏 -->
      <el-aside width="200px" class="sidebar">
        <div class="logo">高校图书系统</div>
        <el-menu
          default-active="$route.path"
          router
          class="menu"
          background-color="#2d3a4b"
          text-color="#fff"
          active-text-color="#ffd04b"
        >
          <el-menu-item index="/admin/dashboard">首页</el-menu-item>
          <el-menu-item index="/admin/books">图书管理</el-menu-item>
          <el-menu-item index="/admin/users">用户管理</el-menu-item>
          <el-menu-item index="/admin/records">借阅管理</el-menu-item>
          <el-menu-item index="/admin/reviews">评论管理</el-menu-item>
        </el-menu>
      </el-aside>
  
      <!-- 主体内容 -->
      <el-container>
        <el-header class="header">
          <div class="header-content">
            <span class="username">{{ username }}</span>
            <el-button size="small" type="danger" @click="logout" plain>退出</el-button>
          </div>
        </el-header>
        <el-main class="main">
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import { useRouter } from 'vue-router'
  import { ElMessage } from 'element-plus'
  
  const router = useRouter()
  const username = ref('')
  
  onMounted(() => {
    const user = JSON.parse(localStorage.getItem('user'))
    if (user && user.role === 'admin') {
      username.value = user.nickname
    } else {
      ElMessage.error('请使用管理员账号登录')
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
  /* 侧边栏样式 */
  .sidebar {
    background-color: #2d3a4b;
  }
  
  .logo {
    color: #fff;
    font-size: 22px;
    font-weight: bold;
    text-align: center;
    padding: 24px 0;
    border-bottom: 1px solid #1f2d3d;
    letter-spacing: 2px;
  }
  
  /* 菜单 hover 效果 */
  .menu >>> .el-menu-item:hover {
    background-color: #1f2d3d !important;
  }
  
  /* 顶部 header 样式 */
  .header {
    background-color: #409EFF;
    display: flex;
    align-items: center;
    padding: 0 20px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
  
  .header-content {
    margin-left: auto;
    display: flex;
    align-items: center;
    gap: 12px;
  }
  
  .username {
    color: #fff;
    font-weight: 500;
  }
  
  /* 主体内容区域 */
  .main {
    padding: 24px;
    background-color: #f5f7fa;
  }
  </style>
  