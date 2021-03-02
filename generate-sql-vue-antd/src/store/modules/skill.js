import { updateSkill, getSkills, deleteSkill } from '@/api/skill'

const skill = {
    state: {
        skills: []
    },
    mutations: {
        SET_SKILLS: (state, skills) => {
            state.skills = skills
        }
    },
    actions: {
        UpdateSkill ({ commit }, skill) {
            return new Promise((resolve, reject) => {
                updateSkill(skill)
                .then(response => resolve(response))
                .catch(error => reject(error))
            })
        },
        GetSkills ({ commit }, skillType) {
            return new Promise((resolve, reject) => {
                getSkills(skillType)
                .then(response => {
                    commit('SET_SKILLS', response.result)
                    resolve(response)
                })
                .catch(error => reject(error))
            })
        },
        DeleteSkill ({ commit }, skillId) {
            return new Promise((resolve, reject) => {
                deleteSkill(skillId)
                .then(response => {
                    resolve(response)
                })
                .catch(error => reject(error))
            })
        }
    }
}

export default skill
