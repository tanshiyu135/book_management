<template>
    <div>
      <h2>个人中心</h2>
      <el-form :model="form" label-width="80px" style="max-width: 400px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" disabled />
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateProfile">保存</el-button>
        </el-form-item>
      </el-form>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import axios from 'axios'
  import { ElMessage } from 'element-plus'
  
  const form = ref({
    id: null,
    username: '',
    nickname: '',
    phone: '',
    password: '',
    role: ''
  })
  
  onMounted(() => {
    const user = JSON.parse(localStorage.getItem('user'))
    if (user) {
      form.value = { ...user }
    } else {
      ElMessage.error('请先登录')
    }
  })
  
  const updateProfile = async () => {
    try {
      const res = await axios.put('http://localhost:8080/api/users', form.value)
      if (res.data) {
        ElMessage.success('更新成功')
        // 更新本地缓存
        localStorage.setItem('user', JSON.stringify(form.value))
      } else {
        ElMessage.error('更新失败')
      }
    } catch (err) {
      ElMessage.error('服务器错误')
    }
  }
  </script>
  