import { updateSkill, getSkills, deleteSkill, getSkillTopics, getSkill } from '@/api/skill'

const skill = {
    state: {
        skills: [],
        skill: undefined
    },
    mutations: {
        SET_SKILLS: (state, skills) => {
            state.skills = skills
        },
        SET_SKILL: (state, skill) => {
            state.skill = skill
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
        GetSkillTopics ({ commit }, skillType) {
            return new Promise((resolve, reject) => {
                getSkillTopics(skillType)
                .then(response => {
                    const skills = response.result
                    commit('SET_SKILLS', skills)
                    if (skills.length > 0) {
                        commit('SET_SKILL', skills[0])
                    }
                    resolve(response)
                })
                .catch(error => reject(error))
            })
        },
        GetSkill ({ commit }, skillId) {
            return new Promise((resolve, reject) => {
                getSkill(skillId)
                .then(response => {
                    commit('SET_SKILL', response.result)
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
