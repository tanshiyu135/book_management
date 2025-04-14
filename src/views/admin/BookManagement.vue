<template>
    <div>
      <el-card>
        <div class="header">
          <el-input v-model="searchKeyword" placeholder="输入书名或作者搜索" class="input" clearable />
          <el-input v-model="searchCategory" placeholder="输入类别搜索" class="input" clearable />
          <el-button type="primary" icon="el-icon-search" @click="fetchBooks">搜索</el-button>
          <el-button type="success" @click="openAddDialog">新增图书</el-button>
        </div>
  
        <el-table :data="books" stripe border style="margin-top: 20px">
          <el-table-column label="ISBN" prop="isbn" width="180" />
          <el-table-column label="书名" prop="title" />
          <el-table-column label="作者" prop="author" />
          <el-table-column label="出版社" prop="publisher" />
          <el-table-column label="类别" prop="category" />
          <el-table-column label="库存总量" prop="quantity" />
          <el-table-column label="可借数量" prop="availableQty" />
          <el-table-column label="操作" width="180">
            <template #default="scope">
              <el-button size="small" type="primary" @click="openEditDialog(scope.row)">编辑</el-button>
              <el-button size="small" type="danger" @click="deleteBook(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
  
      <!-- 添加/编辑图书对话框 -->
      <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑图书' : '新增图书'" width="500px">
        <el-form :model="form" label-width="100px">
          <el-form-item label="书名">
            <el-input v-model="form.title" />
          </el-form-item>
          <el-form-item label="作者">
            <el-input v-model="form.author" />
          </el-form-item>
          <el-form-item label="出版社">
            <el-input v-model="form.publisher" />
          </el-form-item>
          <el-form-item label="ISBN">
            <el-input v-model="form.isbn" />
          </el-form-item>
          <el-form-item label="类别">
            <el-input v-model="form.category" />
          </el-form-item>
          <el-form-item label="图书简介">
  <el-input v-model="form.summary" type="textarea" rows="3" />
</el-form-item>

<el-form-item label="封面地址">
  <el-input v-model="form.coverUrl" />
</el-form-item>

          <el-form-item label="库存总量">
            <el-input-number v-model="form.quantity" :min="0" />
          </el-form-item>
          <el-form-item label="可借数量">
            <el-input-number v-model="form.availableQty" :min="0" />
          </el-form-item>
        </el-form>
        <template #footer>
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm">确认</el-button>
        </template>
      </el-dialog>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import axios from 'axios'
  import { ElMessage, ElMessageBox } from 'element-plus'
  
  const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

  const books = ref([])
  const searchKeyword = ref('')
  const searchCategory = ref('')
  const dialogVisible = ref(false)
  const isEdit = ref(false)
  const form = ref({
    id: null,
    title: '',
    author: '',
    publisher: '',
    category: '',
    totalQty: 0,
    availableQty: 0
  })
  
  // 获取图书数据
  const fetchBooks = async () => {
    try {
      const res = await axios.get('http://localhost:8080/api/books/search', {
        params: {
          keyword: searchKeyword.value,
          category: searchCategory.value
          
        }
      })
      books.value = res.data
    } catch (err) {
      ElMessage.error('获取图书失败')
    }
  }
  
  onMounted(fetchBooks)
  
  const openAddDialog = () => {
    isEdit.value = false
    form.value = {
      title: '',
      author: '',
      publisher: '',
      category: '',
      totalQty: 0,
      availableQty: 0
    }
    dialogVisible.value = true
  }
  
  const openEditDialog = (book) => {
    isEdit.value = true
    form.value = { ...book }
    dialogVisible.value = true
  }
  
  const submitForm = async () => {
    try {
      if (isEdit.value) {
        await axios.put('http://localhost:8080/api/books', form.value)
        ElMessage.success('图书更新成功')
      } else {
        await axios.post('http://localhost:8080/api/books', form.value)
        ElMessage.success('图书添加成功')
      }
      dialogVisible.value = false
      fetchBooks()
    } catch (err) {
      ElMessage.error('操作失败')
    }
  }
  
  const deleteBook = (id) => {
    ElMessageBox.confirm('确认删除这本图书？', '提示', {
      type: 'warning'
    }).then(async () => {
      await axios.delete(`http://localhost:8080/api/books/${id}`)
      ElMessage.success('删除成功')
      fetchBooks()
    }).catch(() => {})
  }
  </script>
  
  <style scoped>
  .header {
    display: flex;
    gap: 10px;
    align-items: center;
    margin-bottom: 10px;
  }
  .input {
    width: 200px;
  }
  </style>
  