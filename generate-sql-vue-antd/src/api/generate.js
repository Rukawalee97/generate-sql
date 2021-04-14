import request from '@/utils/request'

const GenerateApi = {
  generate: '/api/generate'
}

export function getSql (param) {
  return request(GenerateApi.generate, {
    method: 'POST',
    data: param
  })
}
