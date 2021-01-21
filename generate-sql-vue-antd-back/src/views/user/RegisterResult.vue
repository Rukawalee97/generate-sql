<template>
  <a-result
    :isSuccess="true"
    :content="false"
    :title="$t('user.register-result.msg') + username + $t('user.register-result.success')"
    :sub-title="$t('user.register-result.activation')">

    <template #extra>
      <a-button size="large" style="margin-left: 8px" @click="goHomeHandle">
        {{ $t('user.backToLogin') }}
      </a-button>
    </template>

  </a-result>
</template>

<script>
export default {
  name: 'RegisterResult',
  data () {
    return {
      form: {}
    }
  },
  computed: {
    username () {
      const v = this.form && this.form.username || 'xxx'
      return v
    }
  },
  created () {
    this.form = this.$route.params
  },
  methods: {
    goHomeHandle () {
      this.$router.push({ name: 'login' })
    }
  }
}
</script>

<style scoped>

</style>
