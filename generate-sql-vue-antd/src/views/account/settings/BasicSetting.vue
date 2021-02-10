<template>
  <div class="account-settings-info-view">
    <a-form
      ref="formUpdateUser"
      id="formUpdateUser"
      :form="userForm"
      layout="vertical"
      @submit="updateUser"
    >
      <a-row :gutter="16">
        <a-col :md="24" :lg="16">
          <a-form-item
            :label="$t('account.settings.basic.nickname')"
          >
            <a-input
              type="text"
              :placeholder="$t('account.settings.basic.nickname-message')"
              v-decorator="[
                'username'
              ]"
            />
          </a-form-item>
          <a-form-item
            :label="$t('account.settings.basic.profile')"
          >
            <a-textarea
              rows="6"
              :placeholder="$t('account.settings.basic.profile-message')"
              v-decorator="[
                'userIntroduction'
              ]"
            />
          </a-form-item>
          <a-form-item>
            <a-input
              hidden
              v-decorator="[
                'userHead'
              ]"
            />
          </a-form-item>
        </a-col>
        <a-col :md="24" :lg="8" :style="{ minHeight: '180px' }">
          <div class="ant-upload-preview" @click="$refs.modal.edit(1)" >
            <a-icon type="cloud-upload-o" class="upload-icon"/>
            <div class="mask">
              <a-icon type="plus" />
            </div>
            <img :src="option.img || avatar"/>
          </div>
        </a-col>
      </a-row>
      <a-row type="flex" justify="center">
        <a-col :span="6" style="margin-top:24px">
          <a-form-item>
            <a-button
              type="primary"
              htmlType="submit"
              :loading="updateLoding"
            >
              {{ $t('account.settings.basic.update') }}
            </a-button>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>

    <avatar-modal ref="modal" @ok="setavatar"/>

  </div>
</template>

<script>
import AvatarModal from './AvatarModal'
import { mapGetters, mapActions } from 'vuex'
import { message } from 'ant-design-vue'
import { isEmpty } from '@/utils/util'

export default {
  components: {
    AvatarModal
  },
  computed: {
    ...mapGetters(['nickname', 'avatar', 'token', 'userIntroduction']),
    changeUserInfo () {
      return {
        username: this.nickname,
        userIntroduction: this.userIntroduction,
        userHead: this.option.img || this.avatar
      }
    }
  },
  watch: {
    changeUserInfo (values) {
      this.userForm.setFieldsValue({ ...values })
    }
  },
  data () {
    return {
      // cropper
      preview: {},
      option: {
        img: undefined
      },
      userForm: this.$form.createForm(this),
      updateLoding: false
    }
  },
  mounted () {
    this.isLoged()
  },
  methods: {
    ...mapActions(['UpdateUserInfo', 'GetInfo']),
    setavatar (url) {
      this.option.img = url
    },
    isLoged () {
      if (isEmpty(this.token)) {
        message.warning('您还没有登录！')
        this.$router.push({ path: '/' })
        return
      }
      this.userForm.setFieldsValue({
        username: this.nickname,
        userIntroduction: this.userIntroduction,
        userHead: this.avatar
      })
    },
    updateUser (e) {
      e.preventDefault()
      this.updateLoding = true
      const {
        userForm: { validateFields },
        UpdateUserInfo,
        GetInfo
      } = this
      const validateFieldsKey = ['username', 'userIntroduction', 'userHead']
      validateFields(validateFieldsKey, { force: true }, (err, values) => {
        if (!err) {
          UpdateUserInfo(values)
          .then(response => {
            console.log(response)
            if (response.result) {
              message.success(response.message)
              GetInfo()
              this.updateLoding = false
            }
          })
          .catch(err => this.requestFailed(err))
          .finally(() => {
            setTimeout(() => {
              this.updateLoding = false
            }, 600)
          })
        }
      })
    },
    requestFailed (err) {
      this.$notification['error']({
        message: '发生错误',
        description: err.message || ((err.response || {}).data || {}).message || '请求出现错误，请稍后再试',
        duration: 4
      })
      this.registerBtn = false
    }
  }
}
</script>

<style lang="less" scoped>

  .avatar-upload-wrapper {
    height: 200px;
    width: 100%;
  }

  .ant-upload-preview {
    position: relative;
    margin: 0 auto;
    width: 100%;
    max-width: 180px;
    border-radius: 50%;
    box-shadow: 0 0 4px #ccc;

    .upload-icon {
      position: absolute;
      top: 0;
      right: 10px;
      font-size: 1.4rem;
      padding: 0.5rem;
      background: rgba(222, 221, 221, 0.7);
      border-radius: 50%;
      border: 1px solid rgba(0, 0, 0, 0.2);
    }
    .mask {
      opacity: 0;
      position: absolute;
      background: rgba(0,0,0,0.4);
      cursor: pointer;
      transition: opacity 0.4s;

      &:hover {
        opacity: 1;
      }

      i {
        font-size: 2rem;
        position: absolute;
        top: 50%;
        left: 50%;
        margin-left: -1rem;
        margin-top: -1rem;
        color: #d6d6d6;
      }
    }

    img, .mask {
      width: 100%;
      max-width: 180px;
      height: 100%;
      border-radius: 50%;
      overflow: hidden;
    }
  }
</style>
