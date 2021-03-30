import request from '@/utils/request'

const commentApi = {
  comment: '/api/comment',
  comments: '/api/comments'
}

export function uploadComment (comment) {
  return request(commentApi.comment, {
    method: 'POST',
    data: comment
  })
}

export function getComments (comment) {
  return request(commentApi.comments, {
    method: 'POST',
    data: comment
  })
}
