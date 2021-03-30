import { uploadComment, getComments } from '@/api/comment'

const comment = {
  state: {
    comment: {},
    comments: []
  },
  mutations: {
    SET_COMMENTS: (state, comments) => {
      state.comments = comments
    }
  },
  actions: {
    UploadComment ({ commit }, comment) {
      return new Promise((resolve, reject) => {
        uploadComment(comment)
        .then(response => {
          resolve(response)
        })
        .catch(error => reject(error))
      })
    },
    GetComments ({ commit }, comment) {
      return new Promise((resolve, reject) => {
        getComments(comment)
        .then(response => {
          commit('SET_COMMENTS', response.result)
          resolve(comment)
        })
        .catch(error => reject(error))
      })
    }
  }
}

export default comment
