import request from '@/utils/request'

const roleApi = {
    roles: '/api/roles',
    role: '/api/role'
}

export function getRoles () {
    return request(roleApi.roles)
}

export function updateRole (params) {
    return request(roleApi.role, {
        method: 'post',
        data: params
    })
}
