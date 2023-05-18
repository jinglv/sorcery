import request from '@/utils/request'

export function apiListByModuleId(moduleId, params, data) {
  return request({
    url: '/api/v1/apis/' + moduleId,
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    params,
    data
  })
}

export function deleteApiInfo(id) {
  return request({
    url: '/api/v1/apis/' + id,
    method: 'delete'
  })
}
