import request from '@/utils/request'

const collectApi = {
    Collect: '/api/collect',
    QueryCollects: '/api/collects'
}

export function getCollects () {
    return request(collectApi.QueryCollects)
}

export function updateCollect (param) {
    return request(collectApi.Collect, {
        method: 'POST',
        data: param
    })
}

export function deleteCollect (param) {
    return request(`${collectApi.Collect}/${param}`, {
        method: 'DELETE'
    })
}
