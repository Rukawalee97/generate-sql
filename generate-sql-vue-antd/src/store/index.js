import Vue from 'vue'
import Vuex from 'vuex'

import app from './modules/app'
import user from './modules/user'
import role from './modules/role'
import skill from './modules/skill'
import collect from './modules/collect'
import comment from './modules/comment'

// default router permission control
import permission from './modules/permission'

// dynamic router permission control (Experimental)
// import permission from './modules/async-router'
import getters from './getters'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    app,
    user,
    role,
    collect,
    skill,
    comment,
    permission
  },
  state: {

  },
  mutations: {

  },
  actions: {

  },
  getters
})
