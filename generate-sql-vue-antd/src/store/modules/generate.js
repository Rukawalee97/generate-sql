import { getSql } from '@/api/generate'

const generate = {
  state: {
    sqlMap: {}
  },
  mutations: {
    SET_SQL_MAP: (state, sqlMap) => {
      state.sqlMap = sqlMap
    }
  },
  actions: {
    GenerateSql ({ commit }, param) {
      return new Promise((resolve, reject) => {
        getSql(param)
        .then(response => {
          commit('SET_SQL_MAP', response.result)
          resolve(response)
        })
        .catch(error => reject(error))
      })
    }
  }
}

export default generate
