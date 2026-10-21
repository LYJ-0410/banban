<template>
  <div>
    <!-- 顶部导航 -->
    <div class="navbar">
      <el-button @click="$router.back()" text>
        <el-icon><ArrowLeft /></el-icon> 返回
      </el-button>
      <div class="logo">动态详情</div>
      <div></div>
    </div>
    
    <div class="app-container" v-loading="loading">
      <!-- 动态内容 -->
      <div class="card" v-if="post">
        <div class="post-header">
          <img :src="post.avatar || '/uploads/avatar/default.png'" class="avatar" />
          <div class="user-info">
            <div class="nickname">{{ post.nickname }}</div>
            <div class="time">{{ post.createTime }}</div>
          </div>
        </div>
        
        <div class="post-content" style="font-size: 16px;">{{ post.content }}</div>
        
        <div class="post-images" v-if="imageList.length > 0">
          <img v-for="(img, i) in imageList" :key="i" :src="img" />
        </div>
        
        <div class="post-actions">
          <span class="post-action" :class="{ liked: post.liked }" @click="handleLike">
            <el-icon><StarFilled v-if="post.liked" /><Star v-else /></el-icon>
            {{ post.likeCount }}
          </span>
          <span class="post-action">
            <el-icon><ChatDotRound /></el-icon>
            {{ post.commentCount }}
          </span>
        </div>
      </div>
      
      <!-- 评论区 -->
      <div class="card">
        <h3 style="margin-bottom: 16px;">评论 ({{ comments.length }})</h3>
        
        <!-- 发表评论 -->
        <div v-if="userStore.isLoggedIn" style="margin-bottom: 20px;">
          <el-input 
            v-model="commentContent"
            type="textarea"
            :rows="2"
            placeholder="写下你的评论..."
          />
          <el-button 
            type="primary" 
            style="margin-top: 8px;"
            @click="submitComment"
            :disabled="!commentContent.trim()"
          >
            发表评论
          </el-button>
        </div>
        
        <!-- 评论列表 -->
        <div v-for="comment in comments" :key="comment.id" 
             style="padding: 12px 0; border-bottom: 1px solid #f0f0f0;">
          <div style="display: flex; gap: 8px;">
            <div style="flex: 1;">
              <div style="font-weight: 600; font-size: 14px;">
                {{ comment.nickname || '匿名用户' }}
              </div>
              <div style="color: #333; margin: 4px 0;">{{ comment.content }}</div>
              <div style="color: #999; font-size: 12px;">{{ comment.createTime }}</div>
            </div>
          </div>
        </div>
        
        <el-empty v-if="comments.length === 0" description="暂无评论，快来抢沙发~" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getPostDetail, getComments, addComment, toggleLike } from '../api/post'
import { useUserStore } from '../store/user'

const route = useRoute()
const userStore = useUserStore()

const post = ref(null)
const comments = ref([])
const loading = ref(false)
const commentContent = ref('')

const imageList = computed(() => {
  try {
    return post.value?.images ? JSON.parse(post.value.images) : []
  } catch {
    return []
  }
})

const loadData = async () => {
  loading.value = true
  try {
    const postId = route.params.id
    const [postRes, commentRes] = await Promise.all([
      getPostDetail(postId),
      getComments(postId)
    ])
    post.value = postRes.data
    comments.value = commentRes.data.records || []
  } finally {
    loading.value = false
  }
}

const handleLike = async () => {
  await toggleLike(post.value.id)
  loadData()
}

const submitComment = async () => {
  if (!commentContent.value.trim()) return
  await addComment({
    postId: post.value.id,
    content: commentContent.value
  })
  ElMessage.success('评论成功')
  commentContent.value = ''
  loadData()
}

onMounted(loadData)
</script>
