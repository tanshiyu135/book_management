// src/main.js
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

// ✅ 这部分是关键！正确导入 vue-echarts
import VueECharts from 'vue-echarts'
import 'echarts'

// ✅ 创建 app
const app = createApp(App)

app.use(router)
app.use(ElementPlus)

// ✅ 注册组件：注意这里是默认导入，不是 { VueECharts }
app.component('v-chart', VueECharts)

app.mount('#app')
