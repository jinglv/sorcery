import request from '@/utils/request'

export function projectList(params, data) {
  return request({
    url: '/api/v1/projects',
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    params,
    data
  })
}

export function createProject(data) {
  return request({
    url: '/api/v1/project',
    method: 'post',
    data
  })
}

export function getProject(id) {
  return request({
    url: '/api/v1/project/' + id,
    method: 'get'
  })
}

export function updateProject(id, data) {
  return request({
    url: '/api/v1/project/' + id,
    method: 'put',
    data
  })
}

export function deleteProject(id) {
  return request({
    url: '/api/v1/project/' + id,
    method: 'delete'
  })
}
