import request from '../utils/request'

// 获取动态列表
export const getPostList = (page = 1, size = 10) => 
  request.get('/post/list', { params: { page, size } })

// 获取动态详情
export const getPostDetail = (postId) => 
  request.get(`/post/${postId}`)

// 发布动态
export const createPost = (data) => 
  request.post('/post', data)

// 删除动态
export const deletePost = (postId) => 
  request.delete(`/post/${postId}`)

// 点赞/取消点赞
export const toggleLike = (postId) => 
  request.post(`/post/${postId}/like`)

// 获取评论列表
export const getComments = (postId, page = 1, size = 20) => 
  request.get(`/comment/post/${postId}`, { params: { page, size } })

// 发表评论
export const addComment = (data) => 
  request.post('/comment', data)

// 删除评论
export const deleteComment = (commentId) => 
  request.delete(`/comment/${commentId}`)
