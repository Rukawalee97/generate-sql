<template>
  <a-form
    ref="formPassword"
    id="formPassword"
    :form="form"
    @submit="updatePassword"
  >
    <a-form-item :label="$t('account.settings.security.old.password')">
      <a-input-password
        type="text"
        :placeholder="$t('account.settings.security.old.password.placeholder')"
        v-decorator="[
          'oldPassword',
          {
            rules: [
              {
                required: true,
                message: $t('account.settings.security.password.required')
              },
              {
                min: 6,
                message: $t('account.settings.security.password.length')
              }
            ],
            validateTrigger: [
              'change',
              'blur'
            ]
          }
        ]"
      >
        <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
      </a-input-password>
    </a-form-item>
    <a-form-item :label="$t('account.settings.security.new.password')">
      <a-input-password
        type="text"
        :placeholder="$t('account.settings.security.new.password.placeholder')"
        v-decorator="[
          'password',
          {
            rules: [
              {
                required: true,
                message: $t('account.settings.security.password.required')
              },
              {
                min: 6,
                message: $t('account.settings.security.password.length')
              }
            ],
            validateTrigger: [
              'change',
              'blur'
            ]
          }
        ]"
      >
        <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
      </a-input-password>
    </a-form-item>
    <a-form-item :label="$t('account.settings.security.password.confirm')">
      <!-- <a-input
        :placeholder="$t('account.settings.security.password.confirm.placeholder')"
      /> -->
      <a-input-password
        type="text"
        :placeholder="$t('account.settings.security.password.confirm.placeholder')"
        v-decorator="[
          'confirmPassword',
          {
            rules: [
              {
                required: true,
                message: $t('account.settings.security.password.required')
              },
              {
                min: 6,
                message: $t('account.settings.security.password.length')
              },
              {
                validator: handlePasswordCheck
              }
            ],
            validateTrigger: [
              'change',
              'blur'
            ]
          }
        ]"
      >
        <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
      </a-input-password>
    </a-form-item>
    <a-row type="flex" justify="center">
      <a-form-item>
        <a-button
          type="primary"
          htmlType="submit"
          :loading="state.updateBtn"
          :disabled="state.updateBtn"
        >
          {{ $t('account.settings.security.update.btn') }}
        </a-button>
      </a-form-item>
    </a-row>
  </a-form>
</template>

<script>
import { message } from 'ant-design-vue'
import { mapActions, mapGetters } from 'vuex'
import { isEmpty } from '@/utils/util'

export default {
  name: 'Password',
  data () {
    return {
      form: this.$form.createForm(this),
      state: {
        updateBtn: false
      }
    }
  },
  mounted () {
    this.isLoged()
  },
  computed: {
    ...mapGetters(['token'])
  },
  methods: {
    ...mapActions(['UpdateUserInfo']),
    updatePassword (e) {
      e.preventDefault()
      const {
        form: { validateFields, resetFields },
        state,
        UpdateUserInfo
      } = this
      state.updateBtn = true
      const validateFieldsKey = ['oldPassword', 'password', 'confirmPassword']
      validateFields(validateFieldsKey, { force: true }, (err, values) => {
        if (!err) {
          const isSame = this.confirmPassword(values.password, values.confirmPassword)
          console.log(isSame)
          if (isSame) {
            const updatePassword = { ...values }
            delete updatePassword.confirmPassword
            UpdateUserInfo(updatePassword)
            .then(res => {
              if (res.result) {
                message.success(res.message)
                resetFields()
              } else {
                this.requestFailed(res)
              }
            })
            .catch(err => this.requestFailed(err))
            .finally(() => {
              state.updateBtn = false
            })
          }
        } else {
          setTimeout(() => {
            state.updateBtn = false
          }, 600)
        }
      })
    },
    confirmPassword (password, password2) {
      return password === password2
    },
    requestFailed (err) {
      this.$notification['error']({
        message: '发生错误',
        description: err.message || ((err.response || {}).data || {}).message || '请求出现错误，请稍后再试',
        duration: 4
      })
      this.registerBtn = false
    },
    handlePasswordCheck (rule, value, callback) {
      const password = this.form.getFieldValue('password')
      if (value && password && value.trim() !== password.trim()) {
        callback(new Error(this.$t('account.settings.security.password.twice')))
      }
      callback()
    },
    isLoged () {
      if (isEmpty(this.token)) {
        message.warning('您还没有登录！')
        this.$router.push({ path: '/' })
      }
    }
  }
}
</script>

<style scoped>

</style>
