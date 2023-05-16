import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/api/v1/user-tokens',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: '/api/v1/users',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/api/v1/logout',
    method: 'delete'
  })
}
