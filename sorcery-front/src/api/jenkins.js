import request from '@/utils/request'

export function jenkinsList(params, data) {
  return request({
    url: '/api/v1/jenkins',
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    params,
    data
  })
}

export function deleteJenkinsInfo(id) {
  return request({
    url: '/api/v1/jenkins/' + id,
    method: 'delete'
  })
}

export function createJenkinsInfo(data) {
  return request({
    url: '/api/v1/jenkinsInfo',
    method: 'post',
    data
  })
}

export function getJenkinsInfoById(id) {
  return request({
    url: '/api/v1/jenkins/' + id,
    method: 'get'
  })
}

export function updateJenkinsInfo(id, data) {
  return request({
    url: '/api/v1/jenkins/' + id,
    method: 'put',
    data
  })
}

export function getJenkinsInfoAll() {
  return request({
    url: '/api/v1/jenkins/all',
    method: 'get'
  })
}

export function jenkinsTaskList(params, data) {
  return request({
    url: '/api/v1/jenkins/tasks',
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    params,
    data
  })
}

export function getJenkinsTaskInfoById(id) {
  return request({
    url: '/api/v1/jenkins/task/' + id,
    method: 'get'
  })
}

export function createJenkinsTaskInfo(data) {
  return request({
    url: '/api/v1/jenkins/task',
    method: 'post',
    data
  })
}

export function updateJenkinsTaskInfo(id, data) {
  return request({
    url: '/api/v1/jenkins/task/' + id,
    method: 'put',
    data
  })
}
