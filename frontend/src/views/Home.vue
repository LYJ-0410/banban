<template>
  <div>
    <!-- 顶部导航 -->
    <div class="navbar">
      <div class="logo">🌟 伴伴</div>
      <div class="nav-links">
        <span class="nav-link active" @click="$router.push('/')">广场</span>
        <span class="nav-link" @click="$router.push('/publish')">发布</span>
        <span class="nav-link" @click="$router.push('/profile')">我的</span>
        <span class="nav-link" @click="handleLogout">退出</span>
      </div>
    </div>
    
    <!-- 内容区 -->
    <div class="app-container">
      <!-- 发布入口 -->
      <div class="card" style="display: flex; align-items: center; gap: 12px;" 
           v-if="userStore.isLoggedIn">
        <img :src="userStore.userInfo?.avatar || '/avatar/default.svg'" 
             class="avatar" />
        <el-input 
          placeholder="分享你的想法..." 
          @click="$router.push('/publish')"
          readonly
          style="cursor: pointer"
        />
      </div>
      
      <!-- 动态列表 -->
      <div v-loading="loading">
        <PostCard 
          v-for="post in posts" 
          :key="post.id" 
          :post="post"
          @refresh="loadPosts"
          @delete="handleDelete"
        />
        
        <el-empty v-if="!loading && posts.length === 0" description="暂无动态" />
        
        <div style="text-align: center; padding: 20px;" v-if="hasMore">
          <el-button @click="loadMore" :loading="loadingMore">加载更多</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getPostList } from '../api/post'
import { useUserStore } from '../store/user'
import PostCard from '../components/PostCard.vue'

const router = useRouter()
const userStore = useUserStore()

const posts = ref([])
const loading = ref(false)
const loadingMore = ref(false)
const page = ref(1)
const hasMore = ref(true)

const loadPosts = async () => {
  loading.value = true
  try {
    const res = await getPostList(1, 10)
    posts.value = res.data.records || []
    hasMore.value = posts.value.length >= 10
    page.value = 1
  } finally {
    loading.value = false
  }
}

const loadMore = async () => {
  loadingMore.value = true
  try {
    page.value++
    const res = await getPostList(page.value, 10)
    const newPosts = res.data.records || []
    posts.value.push(...newPosts)
    hasMore.value = newPosts.length >= 10
  } finally {
    loadingMore.value = false
  }
}

const handleDelete = (postId) => {
  posts.value = posts.value.filter(p => p.id !== postId)
}

const handleLogout = () => {
  userStore.logout()
  ElMessage.success('已退出')
  router.push('/login')
}

onMounted(() => {
  loadPosts()
})
</script>
