import request from '@/utils/request'

const userApi = {
  Login: '/api/user/login',
  Logout: '/api/user/logout',
  ForgePassword: '/auth/forge-password',
  Register: '/api/user/register',
  // get my info
  UserInfo: '/api/user/info',
  UserMenu: '/api/user/nav'
}

/**
 * login func
 * parameter: {
 *     username: '',
 *     password: '',
 *     remember_me: true,
 *     captcha: '12345'
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

export function getCurrentUserNav () {
  return request({
    url: userApi.UserMenu,
    method: 'get'
  })
}

export function logout () {
  return request({
    url: userApi.Logout,
    method: 'post'
  })
}