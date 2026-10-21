<template>
  <div>
    <div class="navbar">
      <el-button @click="$router.back()" text>
        <el-icon><ArrowLeft /></el-icon> 返回
      </el-button>
      <div class="logo">发布动态</div>
      <div></div>
    </div>
    
    <div class="app-container">
      <div class="card">
        <el-input 
          v-model="content"
          type="textarea"
          :rows="6"
          placeholder="分享你的想法..."
          maxlength="500"
          show-word-limit
        />
        
        <div style="margin-top: 16px;">
          <el-button type="primary" @click="handlePublish" :loading="loading">
            发布
          </el-button>
          <el-button @click="$router.back()">取消</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { createPost } from '../api/post'

const router = useRouter()
const content = ref('')
const loading = ref(false)

const handlePublish = async () => {
  if (!content.value.trim()) {
    ElMessage.warning('请输入内容')
    return
  }
  
  loading.value = true
  try {
    await createPost({ content: content.value })
    ElMessage.success('发布成功')
    router.push('/')
  } finally {
    loading.value = false
  }
}
</script>
