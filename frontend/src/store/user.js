import { defineStore } from 'pinia'
import { getCurrentUser } from '../api/auth'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    userInfo: JSON.parse(localStorage.getItem('user') || 'null')
  }),
  
  getters: {
    isLoggedIn: (state) => !!state.token,
    userId: (state) => state.userInfo?.userId
  },
  
  actions: {
    // 登录
    setLogin(data) {
      this.token = data.token
      this.userInfo = {
        userId: data.userId,
        username: data.username,
        nickname: data.nickname,
        avatar: data.avatar
      }
      localStorage.setItem('token', data.token)
      localStorage.setItem('user', JSON.stringify(this.userInfo))
    },
    
    // 退出登录
    logout() {
      this.token = ''
      this.userInfo = null
      localStorage.removeItem('token')
      localStorage.removeItem('user')
    },
    
    // 刷新用户信息
    async refreshUserInfo() {
      try {
        const res = await getCurrentUser()
        this.userInfo = {
          userId: res.data.id,
          username: res.data.username,
          nickname: res.data.nickname,
          avatar: res.data.avatar
        }
        localStorage.setItem('user', JSON.stringify(this.userInfo))
      } catch (e) {
        console.error('刷新用户信息失败', e)
      }
    }
  }
})
