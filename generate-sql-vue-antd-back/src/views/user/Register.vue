<template>
  <div class="main user-layout-register">
    <h3><span>{{ $t('user.register.register') }}</span></h3>
    <a-form
      ref="formRegister"
      :form="form"
      id="formRegister"
      @submit="handleSubmit"
    >
      <a-form-item>
        <a-input
          size="large"
          type="text"
          :placeholder="$t('user.register.userName.placeholder')"
          v-decorator="[
            'username',
            {rules: [
              { required: true, message: $t('user.userName.required') },
              { min: 3, message: $t('user.userName.length')}
            ], validateTrigger: 'change'}
          ]"
        >
          <a-icon slot="prefix" type="user" :style="{ color: 'rgba(0,0,0,.25)' }"/>
        </a-input>
      </a-form-item>

      <a-popover
        placement="rightTop"
        :trigger="['focus']"
        :getPopupContainer="(trigger) => trigger.parentElement"
        v-model="state.passwordLevelChecked">
        <template slot="content">
          <div :style="{ width: '240px' }" >
            <div :class="['user-register', passwordLevelClass]">{{ $t(passwordLevelName) }}</div>
            <a-progress :percent="state.percent" :showInfo="false" :strokeColor=" passwordLevelColor " />
            <div style="margin-top: 10px;">
              <span>
                {{ $t('user.register.password.popover-message') }}
              </span>
            </div>
          </div>
        </template>
        <a-form-item>
          <a-input-password
            size="large"
            @focus="handlePasswordInputFocus"
            @blur="handlePasswordInputBlur"
            @change="handlePasswordLevel"
            :placeholder="$t('user.register.password.placeholder')"
            v-decorator="[
              'password',
              {rules: [
                { required: true, message: $t('user.password.required') },
                {min: 6, message: $t('user.password.length')}
              ], validateTrigger: ['change', 'blur']}]"
          >
            <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
          </a-input-password>
        </a-form-item>
      </a-popover>

      <a-form-item>
        <a-input-password
          size="large"
          :placeholder="$t('user.register.confirm-password.placeholder')"
          v-decorator="[
            'password2',
            {rules: [
              { required: true, message: $t('user.password.required') },
              {min: 6, message: $t('user.password.length')},
              {validator: handlePasswordCheck}
            ], validateTrigger: ['change', 'blur']}
          ]"
        >
          <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
        </a-input-password>
      </a-form-item>

      <a-form-item>
        <a-button
          size="large"
          type="primary"
          htmlType="submit"
          class="register-button"
          :loading="state.registerBtn"
          :disabled="state.registerBtn">
          {{ $t('user.register.register.btn') }}
        </a-button>
        <router-link class="login" :to="{ name: 'login' }">{{ $t('user.register.sign-in') }}</router-link>
      </a-form-item>

    </a-form>
  </div>
</template>

<script>
import { deviceMixin } from '@/store/device-mixin'
import { scorePassword } from '@/utils/util'
import { mapActions } from 'vuex'

const levelNames = {
  0: 'user.password.strength.short',
  1: 'user.password.strength.low',
  2: 'user.password.strength.medium',
  3: 'user.password.strength.strong'
}
const levelClass = {
  0: 'error',
  1: 'error',
  2: 'warning',
  3: 'success'
}
const levelColor = {
  0: '#ff0000',
  1: '#ff0000',
  2: '#ff7e05',
  3: '#52c41a'
}
export default {
  name: 'Register',
  components: {
  },
  mixins: [deviceMixin],
  data () {
    return {
      form: this.$form.createForm(this),
      state: {
        level: 0,
        passwordLevel: 0,
        passwordLevelChecked: false,
        percent: 10,
        registerBtn: false
      }
    }
  },
  computed: {
    passwordLevelClass () {
      return levelClass[this.state.passwordLevel]
    },
    passwordLevelName () {
      return levelNames[this.state.passwordLevel]
    },
    passwordLevelColor () {
      return levelColor[this.state.passwordLevel]
    }
  },
  methods: {
    ...mapActions(['Register']),
    handlePasswordLevel () {
      const value = this.form.getFieldValue('password')
      if (value.length >= 6) {
        if (scorePassword(value) > 30) {
          this.state.level = 1
        }
        if (scorePassword(value) > 60) {
        this.state.level = 2
        }
        if (scorePassword(value) > 80) {
        this.state.level = 3
        }
      } else {
        this.state.level = 0
      }
      this.state.passwordLevel = this.state.level
      this.state.percent = this.state.level * 33
    },

    handlePasswordCheck (rule, value, callback) {
      const password = this.form.getFieldValue('password')
      if (value === undefined) {
        callback(new Error(this.$t('user.password.required')))
      }
      if (value && password && value.trim() !== password.trim()) {
        callback(new Error(this.$t('user.password.twice.msg')))
      }
      callback()
    },

    handlePasswordInputFocus () {
      this.state.passwordLevelChecked = true
    },

    handlePasswordInputBlur () {
      this.state.passwordLevelChecked = false
    },

    handleSubmit (e) {
      e.preventDefault()

      const {
        form: { validateFields },
        state,
        $router,
        Register
      } = this

      state.registerBtn = true

      const validateFieldsKey = ['username', 'password', 'password2']

      validateFields(validateFieldsKey, { force: true }, (err, values) => {
        if (!err) {
          const isSame = this.confirmPassword(values.password, values.password2)
          if (isSame) {
            const registerParams = { ...values }
            Register(registerParams)
            .then(res => {
              if (res.result) {
                $router.push({ name: 'registerResult', params: { ...values } })
              } else {
                this.requestFailed(res)
              }
            })
            .catch(err => this.requestFailed(err))
            .finally(() => {
              state.registerBtn = false
            })
          }
        } else {
          setTimeout(() => {
            state.registerBtn = false
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
    }
  },
  watch: {
    'state.passwordLevel' (val) {
      console.log(val)
    }
  }
}
</script>
<style lang="less">
  .user-register {

    &.error {
      color: #ff0000;
    }

    &.warning {
      color: #ff7e05;
    }

    &.success {
      color: #52c41a;
    }

  }

  .user-layout-register {
    .ant-input-group-addon:first-child {
      background-color: #fff;
    }
  }
</style>
<style lang="less" scoped>
  .user-layout-register {

    & > h3 {
      font-size: 16px;
      margin-bottom: 20px;
    }

    .register-button {
      width: 50%;
    }

    .login {
      float: right;
      line-height: 40px;
    }
  }
</style>
