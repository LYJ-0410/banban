import request from '../utils/request'

// 登录
export const login = (data) => request.post('/auth/login', data)

// 注册
export const register = (data) => request.post('/auth/register', data)

// 获取当前用户信息
export const getCurrentUser = () => request.get('/auth/me')

// 更新用户信息
export const updateUser = (data) => request.put('/auth/me', data)
