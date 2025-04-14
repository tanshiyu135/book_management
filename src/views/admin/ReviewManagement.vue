<template>
    <div class="p-4">
      <el-card>
        <template #header>
          <div class="flex justify-between items-center">
            <span class="text-lg font-bold">评论管理</span>
          </div>
        </template>
  
        <el-table :data="reviews" stripe style="width: 100%">
  <el-table-column prop="id" label="评论ID" width="80" />
  <el-table-column prop="nickname" label="用户昵称" width="120" />
  <el-table-column prop="bookTitle" label="图书名称" width="200" />
  <el-table-column prop="comment" label="评论内容" />
  <el-table-column prop="createdAt" label="时间" :formatter="formatDate" />
  <el-table-column label="操作" width="120">
    <template #default="scope">
      <el-button size="small" type="danger" @click="deleteReview(scope.row.id)">删除</el-button>
    </template>
  </el-table-column>
</el-table>


      </el-card>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import axios from 'axios'
  import { ElMessageBox, ElMessage } from 'element-plus'
  import dayjs from 'dayjs'
  
  const reviews = ref([])
  
  const fetchReviews = async () => {
    const res = await axios.get('http://localhost:8080/api/review/all')
    reviews.value = res.data
  }
  
  const deleteReview = async (id) => {
    try {
      await ElMessageBox.confirm('确定删除该评论吗？', '警告', { type: 'warning' })
      const res = await axios.delete(`http://localhost:8080/api/review/${id}`)
      if (res.data) {
        ElMessage.success('删除成功')
        fetchReviews()
      }
    } catch {
      ElMessage.info('操作取消')
    }
  }


const formatDate = (row, column, cellValue) => {
  return cellValue ? dayjs(cellValue).format('YYYY-MM-DD HH:mm:ss') : ''
}

  
  onMounted(() => {
    fetchReviews()
  })
  </script>
  