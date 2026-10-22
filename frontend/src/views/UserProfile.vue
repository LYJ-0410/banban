<template>
  <div>
    <div class="navbar">
      <el-button @click="$router.back()" text>
        <el-icon><ArrowLeft /></el-icon> 返回
      </el-button>
      <div class="logo">用户主页</div>
      <div></div>
    </div>
    
    <div class="app-container" v-loading="loading">
      <!-- 用户信息 -->
      <div class="card" v-if="user">
        <div class="profile-header">
          <img :src="user.avatar || '/avatar/default.svg'" 
               class="avatar avatar-large" />
          <div class="nickname">{{ user.nickname }}</div>
          <div class="bio">{{ user.bio || '这个人很懒，什么都没写~' }}</div>
        </div>
        
        <div class="profile-stats">
          <div class="stat">
            <div class="stat-value">{{ stats.followingCount }}</div>
            <div class="stat-label">关注</div>
          </div>
          <div class="stat">
            <div class="stat-value">{{ stats.followerCount }}</div>
            <div class="stat-label">粉丝</div>
          </div>
        </div>
        
        <div style="text-align: center; margin-top: 16px;" v-if="userStore.isLoggedIn && userStore.userId !== user.id">
          <el-button 
            :type="stats.isFollowing ? 'default' : 'primary'"
            @click="handleFollow"
          >
            {{ stats.isFollowing ? '已关注' : '关注' }}
          </el-button>
        </div>
      </div>
      
      <!-- 用户的动态 -->
      <div class="card">
        <h3 style="margin-bottom: 16px;">TA 的动态</h3>
        <PostCard 
          v-for="post in userPosts" 
          :key="post.id" 
          :post="post"
        />
        <el-empty v-if="userPosts.length === 0" description="还没有发布动态" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useUserStore } from '../store/user'
import { getPostList } from '../api/post'
import { getFollowStats, toggleFollow } from '../api/follow'
import PostCard from '../components/PostCard.vue'

const route = useRoute()
const userStore = useUserStore()

const user = ref(null)
const userPosts = ref([])
const loading = ref(false)
const stats = reactive({
  followingCount: 0,
  followerCount: 0,
  isFollowing: false
})

const loadData = async () => {
  loading.value = true
  try {
    const userId = route.params.id
    
    // 加载动态
    const postRes = await getPostList(1, 50)
    userPosts.value = (postRes.data.records || []).filter(p => p.userId == userId)
    
    // 取第一个动态的用户信息
    if (userPosts.value.length > 0) {
      user.value = {
        id: userPosts.value[0].userId,
        nickname: userPosts.value[0].nickname,
        avatar: userPosts.value[0].avatar,
        bio: ''
      }
    }
    
    // 加载关注统计
    try {
      const statsRes = await getFollowStats(userId)
      stats.followingCount = statsRes.data.followingCount
      stats.followerCount = statsRes.data.followerCount
      stats.isFollowing = statsRes.data.isFollowing
    } catch (e) {
      // 未登录时忽略
    }
  } finally {
    loading.value = false
  }
}

const handleFollow = async () => {
  await toggleFollow(user.value.id)
  ElMessage.success(stats.isFollowing ? '已取消关注' : '关注成功')
  loadData()
}

onMounted(loadData)
</script>
