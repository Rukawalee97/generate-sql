<template>
  <div>
    <a-layout
      id="components-layout-demo-top-side-2"
      v-if="skills.length !== 0"
    >
      <a-layout-sider
        width="160"
        class="study-back"
      >
        <a-menu
          mode="inline"
          class="study-back"
          :selected-keys="[skill.skillId]"
        >
          <a-menu-item
            v-for="skill in skills"
            :key="skill.skillId"
            :title="skill.skillTopic"
            @click="() => getSkill(skill)"
          >
            {{ skill.skillTopic }}
          </a-menu-item>
        </a-menu>
      </a-layout-sider>
      <a-layout
        style="padding: 1px"
      >
        <a-layout>
          <a-layout-header
            class="topic-content-header"
          >
            {{ skill.skillTopic }}
            <img
              :src="collectImg"
              :title="'收藏' + skill.skillTopic"
              class="skill-collect"
              @click="() => collectSkill(skill)"
            />
          </a-layout-header>
          <a-layout-content
            class="study-content"
          >
            <span v-html="skill.skillContent" />
          </a-layout-content>
        </a-layout>
        <a-layout-sider
          class="skill-comment"
          width="300"
        >
          <a-layout>
            <a-layout-content
              class="study-layout-content"
            >
              <div v-if="comments && comments.length > 0">
                <a-comment
                  :key="comment.username + index"
                  v-for="(comment, index) in comments"
                >
                  <a slot="author">{{ comment.username }}</a>
                  <a-avatar
                    slot="avatar"
                    :src="comment.userHead"
                    :alt="comment.username"
                  />
                  <p slot="content" v-html="comment.commentContent"></p>
                  <a-tooltip slot="datetime" :title="moment(comment.commentTime).format('YYYY-MM-DD HH:mm:ss')">
                    <span>{{ moment(comment.commentTime).fromNow() }}</span>
                  </a-tooltip>
                  <hr
                    class="divider-hr"
                  />
                </a-comment>
              </div>
              <a-empty v-else />
            </a-layout-content>
            <a-layout-footer
              class="study-layout-bottom"
            >
              <div
                style="text-align: right"
              >
                <a-button
                  style="float: left"
                  type="dashed"
                >
                  @ Comment
                </a-button>
                <a-button
                  type="primary"
                  @click="submitCommentContent"
                >
                  提交
                </a-button>
              </div>
              <a-textarea
                ref="commentContent"
                class="study-content-textarea"
                placeholder="请输入评论..."
                rows="4"
              />
            </a-layout-footer>
          </a-layout>
        </a-layout-sider>
      </a-layout>
    </a-layout>
    <a-empty v-else />
  </div>
</template>

<script>
import moment from 'moment'
import { mapActions, mapGetters } from 'vuex'
import collect from '@/assets/collect.png'
import { message } from 'ant-design-vue'
import { isEmpty } from '@/utils/util'
import QuillEditor from '@/components/Editor/QuillEditor.vue'

  export default {
    components: {
        QuillEditor
    },
    data () {
      return {
        collectImg: collect,
        moment,
        toolbars: [
          [{ 'size': [] }],
          ['bold', 'italic', 'underline', 'strike'],
          ['blockquote', 'code-block'],
          [{ 'color': [] }, { 'background': [] }],
          ['link', 'image']
        ]
      }
    },
    computed: {
      ...mapGetters(['skill', 'skills', 'token', 'comments'])
    },
    methods: {
      ...mapActions(['GetSkillTopics', 'GetSkill', 'UpdateCollect', 'UploadComment', 'GetComments']),
      getSkillType () {
        let skillType = this.$route.fullPath
        skillType = skillType.substring(skillType.lastIndexOf('/') + 1)
        return skillType
      },
      getSkill (skill) {
        if (skill) {
          this.GetSkill(skill.skillId)
        }
      },
      submitCommentContent () {
        const {
          $refs,
          skill,
          UploadComment,
          GetComments
        } = this
        const commentContent = $refs.commentContent.$refs.resizableTextArea.value
        if (this.isLoged()) {
          if (commentContent) {
            const comment = {
              commentContent: commentContent,
              skillId: skill.skillId
            }
            UploadComment(comment)
            .then(response => {
              if (!response.result) {
                this.requestFailed(response)
              } else {
                message.success('评论成功！')
                GetComments(comment)
                $refs.commentContent.$refs.resizableTextArea.value = undefined
              }
            }).catch(error => this.requestFailed(error))
          } else {
            message.warning('你的评论为空！')
          }
        }
      },
      collectSkill (skill) {
        if (this.isLoged()) {
          this.UpdateCollect(skill)
          .then(response => {
            if (!response.result) {
              this.requestFailed(response)
            } else {
              message.success('收藏成功！')
            }
          })
          .catch(error => this.requestFailed(error))
        }
      },
      changeComment (e) {
        console.log(e.target.value)
      },
      isLoged () {
        if (isEmpty(this.token)) {
          message.warning('您还没有登录！')
          return false
        }
        return true
      },
      requestFailed (err) {
        this.isLoginError = true
        this.$notification['error']({
          message: '发生错误',
          description: err.message || ((err.response || {}).data || {}).message || '请求出现错误，请稍后再试',
          duration: 4
        })
      }
    },
    watch: {
      $route () {
        this.GetSkillTopics(this.getSkillType())
        .then(() => this.getSkill(this.skill))
      }
    },
    mounted () {
      this.GetSkillTopics(this.getSkillType())
      .then(() => {
        this.GetComments({
          skillId: this.skill.skillId
        })
        this.getSkill(this.skill)
      })
    }
  }
</script>

<style lang="less" scoped>
.study-back {
  height: calc(100vh - 64px);
  background: '#fff';
  overflow-y: 'auto';
}
.study-content {
  height: calc(100vh - 130px);
  background: #fff;
  padding: 24px;
  margin: 0;
  overflow-y: auto;
}
.topic-content-header {
  text-align: center;
  font-size: 24px;
  font-weight: bold;
  color: black;
  opacity: .8;
}
.skill-comment {
  background: #fff;
  box-shadow: inset 1px 2px 2px #888888;
}
.skill-collect {
  width: 32px;
  cursor: pointer;
}
.study-layout-bottom {
  bottom: 0px;
  position: absolute;
}
.study-content-textarea {
  width: 300px;
  resize: none;
}
.study-layout-content {
  padding: 0px 6px;
  height: calc(100vh - 191px);
  overflow-y: auto;
}
.divider-hr {
  margin: -20px 0px -10px;
  border: 1px solid gainsboro;
}
</style>
