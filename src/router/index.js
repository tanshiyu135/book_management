import { createRouter, createWebHistory } from 'vue-router'

// 懒加载方式引入组件
const Login = () => import('@/views/Login.vue')
const Register = () => import('@/views/Register.vue')

// 管理员界面
const AdminLayout = () => import('@/layouts/AdminLayout.vue')
const Dashboard = () => import('@/views/admin/Dashboard.vue')
const BookManagement = () => import('@/views/admin/BookManagement.vue')
const UserManagement = () => import('@/views/admin/UserManagement.vue')
const BorrowRecordManagement = () => import('@/views/admin/BorrowRecordManagement.vue')
const ReviewManagement = () => import('@/views/admin/ReviewManagement.vue')



// 读者界面
const UserLayout = () => import('@/layouts/UserLayout.vue')
const ReaderHome = () => import('@/views/reader/ReaderHome.vue')
const ReaderBook = () => import('@/views/reader/ReaderBook.vue')
const ReaderRecord = () => import('@/views/reader/ReaderRecord.vue')
const Profile = () => import('@/views/reader/Profile.vue')
const ReaderBookDetail = () => import('@/views/reader/ReaderBookDetail.vue')



const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', name: 'Login', component: Login },
  { path: '/register', name: 'Register', component: Register },

  {
    path: '/admin',
    component: AdminLayout,
    children: [
      { path: '', redirect: 'dashboard' },
      { path: 'dashboard', name: 'Dashboard', component: Dashboard },
      { path: 'books', name: 'BookManagement', component: BookManagement},
      { path: 'users', name: 'UserManagement', component: UserManagement},
      { path: 'records', name: 'BorrowRecordManagement', component:BorrowRecordManagement},
      { path: 'reviews', name: 'ReviewManagement', component: ReviewManagement},
      // 后续添加图书管理、用户管理等子路由
    ]
  },

  {
    path: '/reader',
    component: UserLayout,
    children: [
      { path: '', redirect: 'home' },
      { path: 'home', name: 'ReaderHome', component: ReaderHome },
      { path: 'book', name: 'ReaderBook', component: ReaderBook },
      { path: 'record', name: 'ReaderRecord', component: ReaderRecord },
      { path: 'profile', name: 'Profile', component: Profile },
      { path: 'book/:id',name :'ReaderBookDetail',component:ReaderBookDetail},
      
      // 后续添加图书、借阅记录、个人中心等子路由
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
