<template>
    <div>
      <h2>借阅记录</h2>
      <el-table :data="records" style="width: 100%" v-loading="loading">
        <el-table-column prop="bookTitle" label="书名" />
        <el-table-column prop="borrowDate" label="借书日期" />
        <el-table-column prop="returnDate" label="应还日期" />
        <el-table-column prop="returned" label="状态">
          <template #default="{ row }">
            <el-tag :type="row.returned ? 'success' : 'danger'">
              {{ row.returned ? '已归还' : '未归还' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template #default="{ row }">
            <el-button size="small" @click="renewBook(row.id)" :disabled="row.returned">续借</el-button>
            <el-button size="small" type="success" @click="returnBook(row.id)" :disabled="row.returned">还书</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import axios from 'axios'
  import { ElMessage, ElMessageBox } from 'element-plus'
  
  const records = ref([])
  const loading = ref(false)
  
  const fetchRecords = async () => {
    loading.value = true
    const user = JSON.parse(localStorage.getItem('user'))
    try {
      const res = await axios.get(`http://localhost:8080/api/borrow/user/${user.id}`)
      records.value = res.data
    } catch (err) {
      ElMessage.error('获取借阅记录失败')
    } finally {
      loading.value = false
    }
  }
  
  onMounted(() => {
    fetchRecords()
  })
  
  const returnBook = async (id) => {
    ElMessageBox.confirm('确定要归还此书吗？', '提示', {
      type: 'warning',
    }).then(async () => {
      const res = await axios.put(`http://localhost:8080/api/borrow/return/${id}`)
      if (res.data) {
        ElMessage.success('归还成功')
        fetchRecords()
      } else {
        ElMessage.error('归还失败')
      }
    }).catch(() => {})
  }
  
  const renewBook = async (id) => {
    const res = await axios.put(`http://localhost:8080/api/borrow/renew/${id}`)
    if (res.data) {
      ElMessage.success('续借成功')
      fetchRecords()
    } else {
      ElMessage.error('续借失败')
    }
  }
  </script>
  