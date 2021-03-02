import request from '@/utils/request'

const userApi = {
  Login: '/api/user/login',
  Logout: '/api/user/logout',
  ForgetPassword: '/auth/forget-password',
  Register: '/api/user/register',
  // get my info
  UserInfo: '/api/user/info',
  Update: '/api/user/update',
  UploadFile: '/api/file/upload',
  Manages: '/api/user/manages',
  Delete: '/api/user'
}

/**
 * login func
 * parameter: {
 *     username: '',
 *     password: '',
 *     remember_me: true
 * }
 * @param parameter
 * @returns {*}
 */
export function login (parameter) {
  return request({
    url: userApi.Login,
    method: 'post',
    data: parameter
  })
}

export function register (parameter) {
  return request(userApi.Register, {
    method: 'post',
    data: parameter
  })
}

export function getInfo () {
  return request({
    url: userApi.UserInfo,
    method: 'get'
  })
}

export function logout () {
  return request({
    url: userApi.Logout,
    method: 'post'
  })
}

export function updatePassword (params) {
  return request(userApi.Update, {
    method: 'put',
    data: params
  })
}

export function uploadFile (params) {
  return request(userApi.UploadFile, {
    method: 'POST',
    data: params
  })
}

export function getManages () {
  return request(userApi.Manages)
}

export function deleteUser (param) {
  return request(userApi.Delete.concat('/').concat(param), {
    method: 'DELETE'
  })
}
