<template>
  <div>
    <a-dropdown v-if="isShowLogedMenu" placement="bottomRight">
      <span class="ant-pro-account-avatar">
        <a-avatar size="small" :src="state.avatar" class="antd-pro-global-header-index-avatar" />
        <span>{{ state.name }}</span>
      </span>
      <template v-slot:overlay>
        <a-menu class="ant-pro-drop-down menu" :selected-keys="[]">
          <a-menu-item key="center" @click="handleToCenter">
            <a-icon type="user" />
            {{ $t('menu.account.center') }}
          </a-menu-item>
          <a-menu-item key="settings" @click="handleToSettings">
            <a-icon type="setting" />
            {{ $t('menu.account.settings') }}
          </a-menu-item>
          <a-menu-divider />
          <a-menu-item key="logout" @click="handleLogout">
            <a-icon type="logout" />
            {{ $t('menu.account.logout') }}
          </a-menu-item>
        </a-menu>
      </template>
    </a-dropdown>
    <a-button v-if="isShowRegister" value="small" shape="round" @click="redirectTo('login')" key="login">
      <a-icon type="user" />
      {{ $t('user.signIn') }}
    </a-button>
    <a-button v-if="isShowRegister" value="small" shape="round" @click="redirectTo('register')" key="register">
      <a-icon type="user-add" />
      {{ $t('user.signUp') }}
    </a-button>
  </div>
</template>

<script>
import { Modal } from 'ant-design-vue'
import { mapActions } from 'vuex'
import storage from 'store'
import { ACCESS_TOKEN } from '@/store/mutation-types'

export default {
  name: 'AvatarDropdown',
  data () {
    return {
      isShowLogedMenu: false,
      isShowRegister: true,
      state: {
        name: undefined,
        avatar: '/images/user.png'
      }
    }
  },
  mounted () {
    this.getUserInfo()
  },
  methods: {
    ...mapActions(['GetInfo']),
    handleToCenter () {
      this.$router.push({ path: '/account/center' })
    },
    handleToSettings () {
      this.$router.push({ path: '/account/settings' })
    },
    handleLogout (e) {
      Modal.confirm({
        title: this.$t('layouts.usermenu.dialog.title'),
        content: this.$t('layouts.usermenu.dialog.content'),
        onOk: () => {
          return this.$store.dispatch('Logout').then(() => {
            this.$router.push({ name: 'login' })
          })
        },
        onCancel () {}
      })
    },
    getUserInfo () {
      const token = storage.get(ACCESS_TOKEN)
      if (token) {
        const {
          GetInfo
        } = this
        GetInfo()
        .then(res => {
          const {
            state
          } = this
          if (res) {
            state.name = res.username
            state.avatar = res.userHead
            this.isShowLogedMenu = true
            this.isShowRegister = false
          }
        })
      }
    },
    redirectTo (url) {
      this.$router.push({ name: url })
    }
  }
}
</script>

<style lang="less" scoped>
.ant-pro-drop-down {
  /deep/ .action {
    margin-right: 8px;
  }
  /deep/ .ant-dropdown-menu-item {
    min-width: 160px;
  }
}
</style>
