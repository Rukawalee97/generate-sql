<template>
  <div>
    <a-card :bordered="false">
      <div class="table-operator">
        <a-button
          icon="plus"
          type="primary"
          @click="() => showModal(true)"
        >
          新增
        </a-button>
      </div>
      <a-table
        rowKey="skillId"
        :columns="columns"
        :data-source="skills"
      >
        <span slot="no" slot-scope="_, __, index">
          {{ index + 1 }}
        </span>
        <span slot="skillContent" slot-scope="skillContent, record">
          <a-tooltip>
            <template #title>
              <span v-html="skillContent" />
            </template>
            {{ record.simpleSkillContent }}
          </a-tooltip>
        </span>
        <span slot="operation" slot-scope="text, record">
          <a-button
            shape="round"
            type="primary"
            size="small"
            @click="() => modifySkill(record)"
          >
            修改
          </a-button>
          <a-divider type="vertical" />
          <a-popconfirm
            cancelText="取消"
            okText="确认"
            :title="'确认删除 ['.concat(record.skillTopic).concat(']?')"
            @confirm="() => deleteSkill(record)"
          >
            <a-button
              shape="round"
              type="danger"
              size="small"
              ghost
            >
              删除
            </a-button>
          </a-popconfirm>
        </span>
      </a-table>
    </a-card>
    <a-modal
      title="新增知识点"
      :visible="modalVisible"
      width="64%"
      @cancel="() => showModal(false)"
      :maskClosable="false"
      :footer="false"
    >
      <a-form
        layout="vertical"
        :form="manageForm"
        @submit="submitManage"
      >
        <a-form-item>
          <a-input
            placeholder="请输入知识点"
            v-decorator="[
              'skillId'
            ]"
            hidden
          />
        </a-form-item>
        <a-form-item
          label="知识点"
        >
          <a-input
            size="large"
            type="text"
            placeholder="请输入知识点"
            v-decorator="[
              'skillTopic',
              {rules: [{ required: true, message: '请输入知识点' }], validateTrigger: 'change'}
            ]"
          />
        </a-form-item>
        <a-form-item
          label="内容"
        >
          <a-input
            :value="skillContent"
            v-decorator="[
              'skillContent',
              {rules: [{ required: true, message: '请输入知识点内容' }], validateTrigger: 'change'}
            ]"
            hidden
          />
          <quill-editor
            ref="quillEditorRef"
            :value="editorValue"
            editorPlaceholder="请输入内容..."
          />
        </a-form-item>
        <a-form-item>
          <a-input
            :value="simpleSkillContent"
            v-decorator="[
              'simpleSkillContent'
            ]"
            hidden
          />
        </a-form-item>
        <a-form-item
          :wrapper-col="{ span: 24 }"
          style="text-align: center"
        >
          <a-button type="primary" html-type="submit">提交</a-button>
          <a-button style="margin-left: 10px">重置</a-button>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script>
import QuillEditor from '@/components/Editor/QuillEditor.vue'
import { mapActions, mapGetters } from 'vuex'
import { message } from 'ant-design-vue'
import { isEmpty } from '@/utils/util'

  export default {
    components: {
        QuillEditor
    },
    data () {
      return {
        modalVisible: false,
        manageForm: this.$form.createForm(this),
        editorValue: undefined,
        columns: [
          {
            title: '编号',
            dataIndex: 'no',
            width: '60px',
            scopedSlots: {
              customRender: 'no'
            }
          },
          {
            title: '知识点',
            dataIndex: 'skillTopic',
            width: '120px',
            ellipsis: true,
            key: 'skillTopic'
          },
          {
            title: '内容',
            dataIndex: 'skillContent',
            key: 'skillContent',
            ellipsis: true,
            scopedSlots: {
              customRender: 'skillContent'
            }
          },
          {
            title: '操作',
            dataIndex: 'operation',
            key: 'operation',
            width: '180px',
            scopedSlots: {
              customRender: 'operation'
            }
          }
        ]
      }
    },
    mounted () {
      if (this.isLoged() && this.isAdmin()) {
        this.GetSkills(this.getSkillType())
      }
    },
    watch: {
      $route () {
        if (this.isLoged() && this.isAdmin()) {
          this.GetSkills(this.getSkillType())
        }
      }
    },
    computed: {
      ...mapGetters([
        'skills',
        'token',
        'roles',
        'role'
      ])
    },
    methods: {
      ...mapActions([
        'UpdateSkill',
        'DeleteSkill',
        'GetSkills'
      ]),
      showModal (visible) {
        this.modalVisible = visible
        if (!visible) {
          const {
            manageForm: { setFieldsValue }
          } = this
          setFieldsValue({
            skillId: undefined,
            skillTopic: undefined,
            skillContent: undefined,
            simpleSkillContent: undefined
          })
          this.editorValue = undefined
        }
      },
      submitManage (e) {
        e.preventDefault()
        const {
          manageForm: { validateFields, setFieldsValue },
          $refs,
          UpdateSkill
        } = this
        const skillContent = $refs.quillEditorRef.$refs.myQuillEditor.value
        const simpleSkillContent = $refs.quillEditorRef.$refs.myQuillEditor.quill.getText()
        setFieldsValue({
          skillContent,
          simpleSkillContent
        })
        const validateFieldsKey = ['skillId', 'skillTopic', 'skillContent', 'simpleSkillContent']
        validateFields(validateFieldsKey, { force: true }, (err, values) => {
          if (!err) {
            const params = {
              ...values,
              skillType: this.getSkillType()
            }
            UpdateSkill(params)
            .then(res => {
              if (!res.result) {
                this.requestFailed(res)
              } else {
                message.success(`新增知识点[${values.skillTopic}]成功!`)
                this.GetSkills(this.getSkillType())
                this.showModal(false)
              }
            })
            .catch(err => this.requestFailed(err))
          }
        })
      },
      modifySkill (skill) {
        const {
          manageForm: { setFieldsValue }
        } = this
        setFieldsValue({
          skillId: skill.skillId,
          skillTopic: skill.skillTopic
        })
        this.editorValue = skill.skillContent
        this.showModal(true)
      },
      deleteSkill (skill) {
        if (skill) {
          this.DeleteSkill(skill.skillId)
          .then(res => {
            if (res.result) {
              this.GetSkills(this.getSkillType())
              message.success(`删除[${skill.skillTopic}]成功!`)
            } else {
              this.requestFailed(res)
            }
          })
          .catch(err => this.requestFailed(err))
        }
      },
      getSkillType () {
        let skillType = this.$route.fullPath
        skillType = skillType.substring(skillType.lastIndexOf('/') + 1)
        return skillType
      },
      requestFailed (err) {
        this.isLoginError = true
        this.$notification['error']({
          message: '发生错误',
          description: err.message || ((err.response || {}).data || {}).message || '请求出现错误，请稍后再试',
          duration: 4
        })
      },
      isLoged () {
        if (isEmpty(this.token)) {
          message.warning('您还没有登录！')
          this.$router.push({ path: '/' })
          return false
        }
        return true
      },
      isAdmin () {
        const {
          role,
          roles
        } = this
        const relRole = roles.find(r => r.roleId === role)
        if (relRole.roleName !== '管理员') {
          message.warning('您没有访问权限！')
          this.$router.push({ path: '/' })
          return false
        }
        return true
      }
    }
  }
</script>

<style lang="less" scoped>
</style>
