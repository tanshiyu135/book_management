<template>
    <div class="register-container">
      <el-card class="box-card">
        <h2 style="text-align: center">用户注册</h2>
        <el-form :model="form" :rules="rules" ref="formRef" label-width="90px">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="form.username" />
          </el-form-item>
  
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="form.password" />
          </el-form-item>
  
          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input type="password" v-model="form.confirmPassword" />
          </el-form-item>
  
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="form.phone" />
          </el-form-item>
  
          <el-form-item label="昵称" prop="nickname">
            <el-input v-model="form.nickname" />
          </el-form-item>
  
          <el-form-item label="角色" prop="role">
            <el-select v-model="form.role" placeholder="请选择角色">
              <el-option label="读者" value="reader" />
              <el-option label="管理员" value="admin" />
            </el-select>
          </el-form-item>
  
          <el-form-item v-if="form.role === 'admin'" label="邀请码" prop="invitationCode">
            <el-input v-model="form.invitationCode" placeholder="请输入管理员邀请码" />
          </el-form-item>
  
          <el-form-item>
            <el-button type="primary" @click="handleRegister">注册</el-button>
            <el-button type="text" @click="goToLogin">去登录</el-button>
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
    confirmPassword: '',
    phone: '',
    nickname: '',
    role: 'reader',  // 默认设置为 'reader'
    invitationCode: ''
  })
  
  // 假设的邀请码
  const ADMIN_INVITATION = '666'
  
  const rules = {
    username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
    password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
    confirmPassword: [
      { required: true, message: '请确认密码', trigger: 'blur' },
      {
        validator: (rule, value, callback) => {
          if (value !== form.value.password) {
            callback(new Error('两次密码不一致'))
          } else {
            callback()
          }
        },
        trigger: 'blur'
      }
    ],
    phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
    nickname: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
    role: [{ required: true, message: '请选择角色', trigger: 'change' }],
    invitationCode: [
      {
        required: true,
        message: '请输入邀请码',
        trigger: 'blur',
        // 只有管理员才验证
        validator: (rule, value, callback) => {
          if (form.value.role === 'admin' && value !== ADMIN_INVITATION) {
            callback(new Error('邀请码错误'))
          } else {
            callback()
          }
        }
      }
    ]
  }
  
  const handleRegister = () => {
    formRef.value.validate(async (valid) => {
      if (!valid) return
      try {
        const res = await axios.post('http://localhost:8080/api/users/register', {
          username: form.value.username,
          password: form.value.password,
          nickname: form.value.nickname,
          phone: form.value.phone,
          role: form.value.role
        })
        ElMessage.success('注册成功，请登录')
        router.push('/login')
      } catch (err) {
        ElMessage.error(err.response?.data?.message || '注册失败')
      }
    })
  }
  
  const goToLogin = () => {
    router.push('/login')
  }
  </script>
  
  <style scoped>
  .register-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
  }
  .box-card {
    width: 450px;
    padding: 20px;
  }
  </style>
  