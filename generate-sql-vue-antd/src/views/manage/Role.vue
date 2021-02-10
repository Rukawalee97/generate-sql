<template>
  <a-card :bordered="false">
    <a-table
      rowKey="userId"
      :data-source="roleManages"
      :columns="roleManagesColumns"
    >
      <span slot="no" slot-scope="_, __, index">
        {{ index + 1 }}
      </span>
      <span slot="roleName" slot-scope="roleName,record">
        <a-select
          :defaultValue="roleName"
          style="width: 100%"
          @select="(roleName, option) => selectRole(roleName, option, record)"
        >
          <a-select-option
            :key="role.roleId"
            :value="role.roleName"
            v-for="role in roles"
          >
            {{ role.roleName }}
          </a-select-option>
        </a-select>
      </span>
      <span slot="password" slot-scope="_,record">
        <a-input-password
          placeholder="请输入用户密码"
          @change="e => changePassword(e, record)"
        />
      </span>
      <span slot="operation" slot-scope="text, record">
        <a-button
          shape="round"
          type="primary"
          size="small"
          @click="() => updateRoleManage(record)"
        >
          更新
        </a-button>
        <a-divider type="vertical" />
        <a-button
          shape="round"
          type="danger"
          size="small"
          @click="() => deleteRoleManage(record)"
          ghost
        >
          删除
        </a-button>
      </span>
    </a-table>
  </a-card>
</template>>

<script>
  import { mapActions, mapGetters } from 'vuex'

  export default {
    computed: {
      ...mapGetters([
        'roleManages',
        'roles'
      ])
    },
    methods: {
      ...mapActions([
        'GetManages',
        'GetRoles',
        'UpdateUserInfo'
      ]),
      updateRoleManage (roleManage) {
        this.UpdateUserInfo(roleManage)
          .then(res => {
            if (!res.result) {
              this.requestFailed(res)
            }
          })
          .catch(err => this.requestFailed(err))
          // .finally(() => {
          //   state.loginBtn = false
          // })
      },
      deleteRoleManage (roleManage) {
        console.log(roleManage)
      },
      selectRole (roleName, option, roleManage) {
        const relRole = this.roleManages.find(role => role.userId === roleManage.userId)
        relRole.roleId = option.key
        relRole.roleName = roleName
      },
      changePassword (e, roleManage) {
        const relRole = this.roleManages.find(role => role.userId === roleManage.userId)
        relRole.password = relRole.password ? relRole.password.concat(e.data) : e.data
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
    mounted () {
      this.GetManages()
      this.GetRoles()
    },
    data () {
      return {
        roleManagesColumns: [
          {
            title: '编号',
            dataIndex: 'no',
            scopedSlots: {
              customRender: 'no'
            }
          },
          {
            title: '用户名',
            dataIndex: 'username',
            key: 'username'
          },
          {
            title: '用户角色',
            dataIndex: 'roleName',
            key: 'roleName',
            width: '160px',
            scopedSlots: {
              customRender: 'roleName'
            }
          },
          {
            title: '用户密码',
            dataIndex: 'password',
            key: 'password',
            scopedSlots: {
              customRender: 'password'
            }
          },
          {
            title: '操作',
            key: 'operation',
            width: '180px',
            scopedSlots: {
              customRender: 'operation'
            }
          }
        ]
      }
    }
  }
</script>

<style scoped>
</style>
