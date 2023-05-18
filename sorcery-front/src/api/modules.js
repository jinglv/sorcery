import request from '@/utils/request'

export function getModuleTree(id) {
  return request({
    url: '/api/v1/module/tree/' + id,
    method: 'get'
  })
}

export function apiListByModuleId(moduleId, params, data) {
  return request({
    url: '/api/v1/apis/' + moduleId,
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    params,
    data
  })
}

export function createModule(data) {
  return request({
    url: '/api/v1/module',
    method: 'post',
    data
  })
}

export function deleteModule(id) {
  return request({
    url: '/api/v1/module/' + id,
    method: 'delete'
  })
}
