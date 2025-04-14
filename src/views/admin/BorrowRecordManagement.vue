<template>
    <div class="p-4">
      <el-card>
        <template #header>
          <div class="flex justify-between items-center">
            <span class="text-lg font-bold">借阅记录管理</span>
          </div>
        </template>
  
        <el-table :data="records" style="width: 100%" stripe>
          <el-table-column prop="id" label="记录ID" width="80" />
          <el-table-column prop="nickname" label="用户名" width="100" />
          <el-table-column prop="bookTitle" label="书名" width="100" />
          <el-table-column prop="borrowDate" label="借阅时间" :formatter="formatDate" />
          <el-table-column prop="dueDate" label="应还时间" :formatter="formatDate" />
          <el-table-column prop="returnDate" label="归还时间" :formatter="formatDate" />
          <el-table-column prop="status" label="状态" width="100">
            <template #default="scope">
              <el-tag :type="scope.row.status === 'returned' ? 'success' : 'warning'">
                {{ scope.row.status === 'returned' ? '已归还' : '未归还' }}
              </el-tag>
            </template>
          </el-table-column>
  
          <el-table-column label="操作" width="240">
            <template #default="scope">
              <div class="flex items-center">
                <template v-if="scope.row.status === 'borrowed'">
                  <el-button
                    class="mr-2"
                    size="small"
                    type="success"
                    @click="renew(scope.row.id)"
                    :disabled="scope.row.renewCount >= 1"
                  >
                    {{ scope.row.renewCount >= 1 ? '已续借' : '续借' }}
                  </el-button>
                  <el-button
                    size="small"
                    type="primary"
                    @click="returnBook(scope.row.id)"
                  >
                    还书
                  </el-button>
                </template>
                <template v-else>
                  <el-button size="small" disabled>已归还</el-button>
                </template>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import axios from 'axios'
  import { ElMessage, ElMessageBox } from 'element-plus'
  import dayjs from 'dayjs'
  
  const records = ref([])
  
  const fetchRecords = async () => {
    const res = await axios.get('http://localhost:8080/api/borrow')
    records.value = res.data
  }
  
  const returnBook = async (id) => {
    try {
      await ElMessageBox.confirm('确认归还此图书？', '提示', { type: 'warning' })
      const res = await axios.put(`http://localhost:8080/api/borrow/return/${id}`)
      if (res.data) {
        ElMessage.success('归还成功')
        fetchRecords()
      }
    } catch {
      ElMessage.info('已取消操作')
    }
  }
  
  const renew = async (id) => {
    try {
      await ElMessageBox.confirm('确认续借此图书？', '提示', { type: 'info' })
      const res = await axios.put(`http://localhost:8080/api/borrow/renew/${id}`)
      if (res.data) {
        ElMessage.success('续借成功')
        fetchRecords()
      }
    } catch {
      ElMessage.info('已取消操作')
    }
  }
  
  const formatDate = (row, column, cellValue) => {
    return cellValue ? dayjs(cellValue).format('YYYY-MM-DD HH:mm:ss') : ''
  }
  
  onMounted(fetchRecords)
  </script>
  