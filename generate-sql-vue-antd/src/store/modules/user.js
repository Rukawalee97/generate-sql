import storage from 'store'
import { login, register, getInfo, logout, updatePassword, uploadFile, getManages, deleteUser } from '@/api/login'
import { ACCESS_TOKEN } from '@/store/mutation-types'
import { welcome } from '@/utils/util'

const user = {
  state: {
    token: '',
    name: '',
    role: '',
    welcome: '',
    userIntroduction: '',
    avatar: '',
    info: {},
    roleManages: []
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, { name, welcome }) => {
      state.name = name
      state.welcome = welcome
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_INTRODUCTION: (state, introduction) => {
      state.userIntroduction = introduction
    },
    SET_ROLE: (state, role) => {
      state.role = role
    },
    SET_INFO: (state, info) => {
      state.info = info
    },
    SET_ROLE_MANAGES: (state, roleManages) => {
      state.roleManages = roleManages
    }
  },

  actions: {
    // 登录
    Login ({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        login(userInfo)
        .then(response => {
          const result = response.result
          storage.set(ACCESS_TOKEN, result.accessToken, 30 * 60 * 1000)
          commit('SET_TOKEN', result.accessToken)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    Register ({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        register(userInfo)
        .then(response => {
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo ({ commit }) {
      return new Promise((resolve, reject) => {
        getInfo()
        .then(response => {
          const result = response.result
          if (result) {
            commit('SET_NAME', { name: result.username, welcome: welcome() })
            commit('SET_AVATAR', result.userHead)
            commit('SET_INTRODUCTION', result.userIntroduction)
            commit('SET_ROLE', result.roleId)
          } else {
            commit('SET_TOKEN', '')
            storage.remove(ACCESS_TOKEN)
          }
          resolve(result)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    Logout ({ commit, state }) {
      return new Promise((resolve) => {
        logout(state.token)
        .then(() => {
          commit('SET_TOKEN', '')
          storage.remove(ACCESS_TOKEN)
          resolve()
        }).catch(() => {
          resolve()
        }).finally(() => {
        })
      })
    },

    // 更新用户信息
    UpdateUserInfo ({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        updatePassword(userInfo)
        .then(response => {
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    UploadFile ({ commit }, files) {
      return new Promise((resolve, reject) => {
        uploadFile(files)
        .then(response => {
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    GetManages ({ commit }) {
      return new Promise((resolve, reject) => {
        getManages()
        .then(response => {
          commit('SET_ROLE_MANAGES', response.result)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    DeleteUser ({ commit }, userId) {
      return new Promise((resolve, reject) => {
        deleteUser(userId)
        .then(response => {
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    }
  }
}

export default user
