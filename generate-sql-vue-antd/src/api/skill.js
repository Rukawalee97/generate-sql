import request from '@/utils/request'

const skillApi = {
    UpdateSkill: '/api/skill',
    QuerySkills: '/api/skills',
    QuerySkillTopics: '/api/skill/topic'
}

export function getSkills (param) {
    return request(skillApi.QuerySkills.concat('/').concat(param))
}

export function updateSkill (params) {
    return request(skillApi.UpdateSkill, {
        method: params.skillId ? 'PUT' : 'POST',
        data: params
    })
}

export function deleteSkill (param) {
    return request(skillApi.UpdateSkill.concat('/').concat(param), {
        method: 'DELETE'
    })
}

export function getSkillTopics (param) {
    return request(skillApi.QuerySkillTopics.concat('/').concat(param))
}

export function getSkill (param) {
    return request(skillApi.UpdateSkill.concat('/').concat(param))
}
