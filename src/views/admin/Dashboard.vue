<template>
    <div class="p-6">
      <el-row :gutter="20">
        <el-col :span="12">
          <!-- 柱状图 -->
          <v-chart :option="barChartOption" style="height: 400px;" />
        </el-col>
        <el-col :span="12">
          <!-- 饼图 -->
          <v-chart :option="pieChartOption" style="height: 400px;" />
        </el-col>
      </el-row>
    </div>
  </template>
  
  <script setup>
  import { ref, watchEffect, onMounted } from 'vue'
  import axios from 'axios'
  
  // 统计数据
  const stats = ref({
    userCount: 0,
    bookCount: 0,
    availableBookCount: 0,
    borrowRecordCount: 0,
  })
  
  // 图表配置项
  const barChartOption = ref({})
  const pieChartOption = ref({})
  
  // 获取数据
  const fetchStats = async () => {
    const res = await axios.get('http://localhost:8080/api/stats/overview')
    stats.value = res.data
  }
  
  // 自动更新图表配置
  watchEffect(() => {
    const unavailableCount = stats.value.bookCount - stats.value.availableBookCount
  
    barChartOption.value = {
      title: { text: '系统总体统计' },
      tooltip: {},
      xAxis: {
        type: 'category',
        data: ['用户总数', '图书总数', '借阅记录数'],
      },
      yAxis: {
        type: 'value',
      },
      series: [
        {
          name: '数量',
          type: 'bar',
          data: [
            stats.value.userCount,
            stats.value.bookCount,
            stats.value.borrowRecordCount,
          ],
          itemStyle: {
            color: '#409EFF',
          },
        },
      ],
    }
  
    pieChartOption.value = {
      title: {
        text: '图书可借情况',
        left: 'center',
      },
      tooltip: {
        trigger: 'item',
      },
      legend: {
        bottom: '0%',
        left: 'center',
      },
      series: [
        {
          name: '图书状态',
          type: 'pie',
          radius: '50%',
          data: [
            { value: stats.value.availableBookCount, name: '可借图书' },
            { value: unavailableCount, name: '不可借图书' },
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)',
            },
          },
        },
      ],
    }
  })
  
  onMounted(() => {
    fetchStats()
  })
  </script>
  