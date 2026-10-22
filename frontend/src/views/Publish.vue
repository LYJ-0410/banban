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
        
        <!-- 图片上传区域 -->
        <div class="upload-area" style="margin-top: 16px;">
          <el-upload
            action="/api/upload/image"
            :headers="uploadHeaders"
            list-type="picture-card"
            v-model:file-list="imageList"
            :on-success="handleUploadSuccess"
            :on-remove="handleUploadRemove"
            :before-upload="beforeUpload"
            accept="image/*"
            :limit="9"
          >
            <el-icon><Plus /></el-icon>
          </el-upload>
          <div style="color: #999; font-size: 12px; margin-top: 8px;">
            最多上传 9 张图片，支持 JPG、PNG 格式
          </div>
        </div>
        
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
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { createPost } from '../api/post'

const router = useRouter()
const content = ref('')
const loading = ref(false)
const imageList = ref([])

// 上传请求头（带 token）
const uploadHeaders = computed(() => {
  const token = localStorage.getItem('token')
  return {
    Authorization: token ? `Bearer ${token}` : ''
  }
})

// 上传前验证
const beforeUpload = (file) => {
  const isImage = file.type.startsWith('image/')
  const isLt5M = file.size / 1024 / 1024 < 5

  if (!isImage) {
    ElMessage.error('只能上传图片文件')
    return false
  }
  if (!isLt5M) {
    ElMessage.error('图片大小不能超过 5MB')
    return false
  }
  return true
}

// 上传成功回调
const handleUploadSuccess = (response, uploadFile) => {
  if (response.code === 200) {
    uploadFile.url = response.data
  } else {
    ElMessage.error(response.msg || '上传失败')
    // 移除失败的文件
    const idx = imageList.value.findIndex(f => f.uid === uploadFile.uid)
    if (idx !== -1) imageList.value.splice(idx, 1)
  }
}

// 移除图片回调
const handleUploadRemove = (uploadFile) => {
  const idx = imageList.value.findIndex(f => f.uid === uploadFile.uid)
  if (idx !== -1) imageList.value.splice(idx, 1)
}

// 发布动态
const handlePublish = async () => {
  if (!content.value.trim()) {
    ElMessage.warning('请输入内容')
    return
  }
  
  loading.value = true
  try {
    // 提取所有已上传成功的图片 URL
    const images = imageList.value
      .filter(img => img.status === 'success' && img.url)
      .map(img => img.url)
    
    await createPost({ 
      content: content.value,
      images: images
    })
    ElMessage.success('发布成功')
    router.push('/')
  } catch (error) {
    ElMessage.error('发布失败')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.upload-area :deep(.el-upload--picture-card) {
  width: 100px;
  height: 100px;
}

.upload-area :deep(.el-upload-list--picture-card .el-upload-list__item) {
  width: 100px;
  height: 100px;
}
</style>
