<template>
  <global-footer
    class="footer custom-render"
    style="padding: 0px;"
  >
    Copyright © 2021 Rukawa Lee
  </global-footer>
</template>

<script>
import { GlobalFooter } from '@ant-design-vue/pro-layout'

export default {
  name: 'ProGlobalFooter',
  components: {
    GlobalFooter
  }
}
</script>

<style lang="less">
.ant-layout-footer {
    padding: 0px;
}
</style>
