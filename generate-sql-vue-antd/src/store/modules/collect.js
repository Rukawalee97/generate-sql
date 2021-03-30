import { getCollects, updateCollect, deleteCollect } from '@/api/collect'

const collect = {
    state: {
        collects: []
    },
    mutations: {
        SET_COLLECTS: (state, collects) => {
            state.collects = collects
        }
    },
    actions: {
        GetCollects ({ commit }) {
            return new Promise((resolve, reject) => {
                getCollects()
                .then(response => {
                    commit('SET_COLLECTS', response.result)
                    resolve(response)
                })
                .catch(error => reject(error))
            })
        },
        UpdateCollect ({ commit }, collect) {
            return new Promise((resolve, reject) => {
                updateCollect(collect)
                .then(response => resolve(response))
                .catch(error => reject(error))
            })
        },
        DeleteCollect ({ commit }, collectId) {
            return new Promise((resolve, reject) => {
                deleteCollect(collectId)
                .then(response => {
                    resolve(response)
                })
                .catch(error => reject(error))
            })
        }
    }
}

export default collect
