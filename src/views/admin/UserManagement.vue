<template>
    <div>
      <el-card>
        <template #header>
          <span>用户管理</span>
        </template>
  
        <el-table :data="userList" stripe style="width: 100%">
          <el-table-column prop="id" label="ID" width="180" />
          <el-table-column prop="username" label="用户名" />
          <el-table-column prop="nickname" label="昵称" />
          <el-table-column prop="phone" label="手机号" />
          <el-table-column prop="role" label="角色" />
          <el-table-column label="操作" width="180">
            <template #default="scope">
              <el-button type="primary" size="small" @click="editUser(scope.row)">编辑</el-button>
              <el-button type="danger" size="small" @click="deleteUser(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
  
      <!-- 编辑弹窗 -->
      <el-dialog v-model="dialogVisible" title="编辑用户">
        <el-form :model="form" label-width="80px">
          <el-form-item label="昵称">
            <el-input v-model="form.nickname" />
          </el-form-item>
          <el-form-item label="手机号">
            <el-input v-model="form.phone" />
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="form.password" show-password />
          </el-form-item>
        </el-form>
        <template #footer>
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitUpdate">保存</el-button>
        </template>
      </el-dialog>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import axios from 'axios'
  import { ElMessage, ElMessageBox } from 'element-plus'
  
  const userList = ref([])
  const dialogVisible = ref(false)
  const form = ref({})
  const currentId = ref(null)
  
  const loadUsers = async () => {
    const res = await axios.get('http://localhost:8080/api/users/all')
    userList.value = res.data
  }
  
  const editUser = (user) => {
    form.value = { ...user }
    currentId.value = user.id
    dialogVisible.value = true
  }
  
  const submitUpdate = async () => {
    const res = await axios.put(`http://localhost:8080/api/users/${currentId.value}/update`, form.value)
    if (res.data) {
      ElMessage.success('更新成功')
      dialogVisible.value = false
      loadUsers()
    } else {
      ElMessage.error('更新失败')
    }
  }
  
  const deleteUser = async (id) => {
    ElMessageBox.confirm('确定删除该用户？', '提示', { type: 'warning' }).then(async () => {
      const res = await axios.delete(`http://localhost:8080/api/users/${id}`)
      if (res.data) {
        ElMessage.success('删除成功')
        loadUsers()
      } else {
        ElMessage.error('删除失败')
      }
    })
  }
  
  onMounted(() => {
    loadUsers()
  })
  </script> 
  