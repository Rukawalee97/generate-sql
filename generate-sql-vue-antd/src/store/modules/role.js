import { getRoles, updateRole } from '@/api/role'

const role = {
    state: {
        roles: []
    },
    mutations: {
        SET_ROLES: (state, roles) => {
            state.roles = roles
        }
    },
    actions: {
        GetRoles ({ commit }) {
            return new Promise((resolve, reject) => {
                getRoles()
                .then(response => {
                    commit('SET_ROLES', response.result)
                    resolve(response)
                }).catch(error => {
                    reject(error)
                })
            })
        },
        UpdateRole ({ commit }, role) {
            return new Promise((resolve, reject) => {
                updateRole(role)
                .then(response => {
                    resolve(response)
                }).catch(error => {
                    reject(error)
                })
            })
        }
    }
}

export default role
