<template>
  <div>
    <a-dropdown v-if="isShowLogedMenu" placement="bottomRight">
      <span class="ant-pro-account-avatar">
        <a-avatar size="small" :src="avatar" class="antd-pro-global-header-index-avatar" />
        <span>{{ nickname }}</span>
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
    <a-button v-if="!isShowLogedMenu" value="small" shape="round" @click="redirectTo('login')" key="login">
      <a-icon type="user" />
      {{ $t('user.signIn') }}
    </a-button>
    <a-button v-if="!isShowLogedMenu" value="small" shape="round" @click="redirectTo('register')" key="register">
      <a-icon type="user-add" />
      {{ $t('user.signUp') }}
    </a-button>
  </div>
</template>

<script>
import { Modal } from 'ant-design-vue'
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'AvatarDropdown',
  data () {
    return {
      isShowLogedMenu: false
    }
  },
  computed: {
    ...mapGetters(['nickname', 'avatar', 'token', 'role', 'roles'])
  },
  mounted () {
    if (this.token) {
      this.GetInfo()
      .then(() => this.GetRoles()
      .then(() => this.changeMenuBar()))
      this.changeMenu()
    }
  },
  methods: {
    ...mapActions(['GetRoles', 'GetInfo']),
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
            this.$store.dispatch('ToggleLayoutMode', 'topmenu')
            this.redirectTo('login')
          })
        },
        onCancel () {}
      })
    },
    redirectTo (url) {
      this.$router.push({ name: url })
    },
    changeMenu () {
      this.isShowLogedMenu = this.token !== undefined && this.token !== ''
    },
    changeMenuBar () {
      if (this.isAdmin()) {
        console.log('管理员登陆')
        this.$store.dispatch('ToggleLayoutMode', 'sidemenu')
      }
    },
    isAdmin () {
      const {
        role,
        roles
      } = this
      const relRole = roles.find(r => r.roleId === role)
      return relRole.roleName === '管理员'
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
