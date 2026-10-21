import request from '../utils/request'

// 关注/取消关注
export const toggleFollow = (userId) => 
  request.post(`/follow/${userId}`)

// 我的关注列表
export const getFollowingList = () => 
  request.get('/follow/following')

// 我的粉丝列表
export const getFollowerList = () => 
  request.get('/follow/followers')

// 关注统计
export const getFollowStats = (userId) => 
  request.get(`/follow/stats/${userId}`)
