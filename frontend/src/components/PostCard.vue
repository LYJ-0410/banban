<template>
  <div class="post-card">
    <!-- 头部：用户信息 -->
    <div class="post-header">
      <img :src="post.avatar || '/uploads/avatar/default.png'" class="avatar" 
           @click="$router.push(`/user/${post.userId}`)" />
      <div class="user-info">
        <div class="nickname" @click="$router.push(`/user/${post.userId}`)">
          {{ post.nickname || '匿名用户' }}
        </div>
        <div class="time">{{ formatTime(post.createTime) }}</div>
      </div>
    </div>
    
    <!-- 内容 -->
    <div class="post-content" @click="$router.push(`/post/${post.id}`)">
      {{ post.content }}
    </div>
    
    <!-- 图片 -->
    <div class="post-images" v-if="imageList.length > 0">
      <img v-for="(img, index) in imageList" :key="index" :src="img" />
    </div>
    
    <!-- 操作栏 -->
    <div class="post-actions">
      <span class="post-action" :class="{ liked: post.liked }" @click="handleLike">
        <el-icon><StarFilled v-if="post.liked" /><Star v-else /></el-icon>
        {{ post.likeCount || 0 }}
      </span>
      
      <span class="post-action" @click="$router.push(`/post/${post.id}`)">
        <el-icon><ChatDotRound /></el-icon>
        {{ post.commentCount || 0 }}
      </span>
      
      <span class="post-action" @click="handleShare">
        <el-icon><Share /></el-icon>
        分享
      </span>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import { toggleLike } from '../api/post'

const props = defineProps({
  post: { type: Object, required: true }
})

const emit = defineEmits(['refresh'])

const imageList = computed(() => {
  try {
    return props.post.images ? JSON.parse(props.post.images) : []
  } catch {
    return []
  }
})

const formatTime = (time) => {
  if (!time) return ''
  const date = new Date(time)
  const now = new Date()
  const diff = (now - date) / 1000
  
  if (diff < 60) return '刚刚'
  if (diff < 3600) return `${Math.floor(diff / 60)}分钟前`
  if (diff < 86400) return `${Math.floor(diff / 3600)}小时前`
  if (diff < 86400 * 7) return `${Math.floor(diff / 86400)}天前`
  
  return `${date.getMonth() + 1}月${date.getDate()}日`
}

const handleLike = async () => {
  try {
    await toggleLike(props.post.id)
    emit('refresh')
  } catch (e) {
    // 未登录时跳转到登录页
    if (e.response?.status === 401) {
      ElMessage.warning('请先登录')
    }
  }
}

const handleShare = () => {
  navigator.clipboard?.writeText(window.location.origin + `/post/${props.post.id}`)
  ElMessage.success('链接已复制')
}
</script>
