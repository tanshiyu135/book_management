<template>
    <div class="login-container">
      <el-card class="box-card">
        <h2 style="text-align: center">用户登录</h2>
        <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="form.username" autocomplete="off" />
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="form.password" type="password" autocomplete="off" />
          </el-form-item>
          <el-form-item label="角色" prop="role">
            <el-select v-model="form.role" placeholder="请选择角色">
              <el-option label="读者" value="reader" />
              <el-option label="管理员" value="admin" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleLogin">登录</el-button>
            <el-button type="text" @click="goToRegister">去注册</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import axios from 'axios'
  import { useRouter } from 'vue-router'
  import { ElMessage } from 'element-plus'
  
  const router = useRouter()
  
  const formRef = ref()
  const form = ref({
    username: '',
    password: '',
    role: 'reader' // 默认设置为 'reader'
  })
  
  const rules = {
    username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
    password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
    role: [{ required: true, message: '请选择角色', trigger: 'change' }]
  }
  
  const handleLogin = async () => {
    formRef.value.validate(async (valid) => {
      if (!valid) return
      try {
        const res = await axios.post('http://localhost:8080/api/users/login', {
          username: form.value.username,
          password: form.value.password
          // 注意：这里不传 role 给后端
        })
        const user = res.data
        if (!user) {
          ElMessage.error('用户名或密码错误')
          return
        }
  
        if (user.role !== form.value.role) {
          ElMessage.error('角色不匹配，请确认角色选择')
          return
        }
  
        ElMessage.success('登录成功')
        // 可保存用户信息用于后续使用
        localStorage.setItem('user', JSON.stringify(user))
        if (user.role === 'admin') {
          router.push('/admin/dashboard')
        } else {
          router.push('/reader/home')
        }
      } catch (err) {
        ElMessage.error('服务器错误')
      }
    })
  }
  
  const goToRegister = () => {
    router.push('/register')
  }
  </script>
  
  <style scoped>
  .login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
  }
  .box-card {
    width: 400px;
    padding: 20px;
  }
  </style>
  