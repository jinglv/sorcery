import request from '@/utils/request'

export function uploadFile(data) {
  return request({
    url: '/api/v1/static/file',
    method: 'post',
    headers: { 'Content-Type': 'multipart/form-data' },
    timeout: 20000,
    data
  })
}

export function updateImage(data) {
  return request({
    url: '/api/commons/upload',
    method: 'post',
    timeout: 20000,
    data
  })
}
