<template>
  <div>
    <div class="navbar">
      <el-button @click="$router.back()" text>
        <el-icon><ArrowLeft /></el-icon> 返回
      </el-button>
      <div class="logo">个人中心</div>
      <div></div>
    </div>
    
    <div class="app-container">
      <!-- 个人信息 -->
      <div class="card">
        <div class="profile-header">
          <img :src="userStore.userInfo?.avatar || '/uploads/avatar/default.png'" 
               class="avatar avatar-large" />
          <div class="nickname">{{ userStore.userInfo?.nickname }}</div>
          <div class="bio">{{ userStore.userInfo?.bio || '这个人很懒，什么都没写~' }}</div>
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
          <div class="stat">
            <div class="stat-value">{{ stats.postCount }}</div>
            <div class="stat-label">动态</div>
          </div>
        </div>
      </div>
      
      <!-- 我的动态 -->
      <div class="card">
        <h3 style="margin-bottom: 16px;">我的动态</h3>
        <PostCard 
          v-for="post in myPosts" 
          :key="post.id" 
          :post="post"
        />
        <el-empty v-if="myPosts.length === 0" description="还没有发布动态" />
      </div>
      
      <!-- 设置 -->
      <div class="card">
        <h3 style="margin-bottom: 16px;">设置</h3>
        <el-button @click="showEditDialog = true" style="width: 100%;">
          编辑个人资料
        </el-button>
      </div>
    </div>
    
    <!-- 编辑资料对话框 -->
    <el-dialog v-model="showEditDialog" title="编辑个人资料" width="400px">
      <el-form :model="editForm" label-width="80px">
        <el-form-item label="昵称">
          <el-input v-model="editForm.nickname" />
        </el-form-item>
        <el-form-item label="简介">
          <el-input v-model="editForm.bio" type="textarea" :rows="3" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showEditDialog = false">取消</el-button>
        <el-button type="primary" @click="saveProfile">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useUserStore } from '../store/user'
import { updateUser } from '../api/auth'
import { getPostList } from '../api/post'
import { getFollowStats } from '../api/follow'
import PostCard from '../components/PostCard.vue'

const userStore = useUserStore()

const myPosts = ref([])
const stats = reactive({
  followingCount: 0,
  followerCount: 0,
  postCount: 0
})
const showEditDialog = ref(false)
const editForm = reactive({
  nickname: '',
  bio: ''
})

const loadData = async () => {
  try {
    // 加载我的动态
    const postRes = await getPostList(1, 20)
    myPosts.value = (postRes.data.records || []).filter(
      p => p.userId === userStore.userId
    )
    stats.postCount = myPosts.value.length
    
    // 加载关注统计
    const statsRes = await getFollowStats(userStore.userId)
    stats.followingCount = statsRes.data.followingCount
    stats.followerCount = statsRes.data.followerCount
    
    // 初始化编辑表单
    editForm.nickname = userStore.userInfo?.nickname || ''
    editForm.bio = userStore.userInfo?.bio || ''
  } catch (e) {
    console.error(e)
  }
}

const saveProfile = async () => {
  await updateUser({
    nickname: editForm.nickname,
    bio: editForm.bio
  })
  await userStore.refreshUserInfo()
  ElMessage.success('保存成功')
  showEditDialog.value = false
}

onMounted(loadData)
</script>
