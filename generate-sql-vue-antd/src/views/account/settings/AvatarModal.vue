<template>

  <a-modal
    :title="$t('account.settings.basic.change-avatar')"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    :width="800"
    :footer="null"
    destroyOnClose
    @cancel="cancelHandel"
  >
    <a-row>
      <a-col :xs="24" :md="12" :style="{height: '350px'}">
        <vue-cropper
          ref="cropper"
          :img="options.img"
          :info="true"
          :autoCrop="options.autoCrop"
          :autoCropWidth="options.autoCropWidth"
          :autoCropHeight="options.autoCropHeight"
          :fixedBox="options.fixedBox"
          @realTime="realTime"
        >
        </vue-cropper>
      </a-col>
      <a-col :xs="24" :md="12" :style="{height: '350px'}">
        <div class="avatar-upload-preview">
          <img :src="options.img" :style="previews.img"/>
        </div>
      </a-col>
    </a-row>
    <br>
    <a-row>
      <a-col :lg="2" :md="2">
        <a-upload name="file" :beforeUpload="beforeUpload" :showUploadList="false">
          <a-button icon="upload">{{ $t('account.settings.basic.select-avatar') }}</a-button>
        </a-upload>
      </a-col>
      <a-col :lg="{span: 1, offset: 2}" :md="2">
        <a-button icon="plus" @click="changeScale(1)"/>
      </a-col>
      <a-col :lg="{span: 1, offset: 1}" :md="2">
        <a-button icon="minus" @click="changeScale(-1)"/>
      </a-col>
      <a-col :lg="{span: 1, offset: 1}" :md="2">
        <a-button icon="undo" @click="rotateLeft"/>
      </a-col>
      <a-col :lg="{span: 1, offset: 1}" :md="2">
        <a-button icon="redo" @click="rotateRight"/>
      </a-col>
      <a-col :lg="{span: 2, offset: 6}" :md="2">
        <a-button
          type="primary"
          @click="finish('blob')"
          :loading="uploading"
        >
          {{ $t('account.settings.basic.save-btn') }}
        </a-button>
      </a-col>
    </a-row>
  </a-modal>

</template>
<script>
import { mapActions } from 'vuex'

export default {
  data () {
    return {
      visible: false,
      id: null,
      confirmLoading: false,
      file: undefined,
      uploading: false,
      options: {
        img: '',
        autoCrop: true,
        autoCropWidth: 200,
        autoCropHeight: 200,
        fixedBox: true
      },
      previews: {}
    }
  },
  methods: {
    ...mapActions(['UploadFile']),
    edit (id) {
      this.visible = true
      this.id = id
    },
    close () {
      this.id = null
      this.visible = false
    },
    cancelHandel () {
      this.close()
    },
    changeScale (num) {
      num = num || 1
      this.$refs.cropper.changeScale(num)
    },
    rotateLeft () {
      this.$refs.cropper.rotateLeft()
    },
    rotateRight () {
      this.$refs.cropper.rotateRight()
    },
    beforeUpload (file) {
      const reader = new FileReader()
      // ???Array Buffer?????????blob ?????????base64?????????
      // ?????????base64
      reader.readAsDataURL(file)
      reader.onload = () => {
        this.options.img = reader.result
      }
      this.file = file
      // ?????????blob
      // reader.readAsArrayBuffer(file)
      return false
    },

    // ????????????????????????????????????
    finish (type) {
      this.uploading = true
      const formData = new FormData()
      const _this = this
      // ??????
      if (type === 'blob') {
        const {
          UploadFile
        } = this
        formData.append('files', this.file, this.fileName)
        UploadFile(formData)
        .then(response => {
          _this.model = true
          _this.modelSrc = response.result
          _this.uploading = false
          _this.$message.success('????????????')
          _this.$emit('ok', response.result)
          _this.visible = false
          _this.options.img = ''
        })
      }
    },
    okHandel () {
      const vm = this

      vm.confirmLoading = true
      setTimeout(() => {
        vm.confirmLoading = false
        vm.close()
        vm.$message.success('??????????????????')
      }, 2000)
    },

    realTime (data) {
      this.previews = data
    }
  }
}
</script>

<style lang="less" scoped>

  .avatar-upload-preview {
    position: absolute;
    top: 50%;
    transform: translate(50%, -50%);
    width: 180px;
    height: 180px;
    border-radius: 50%;
    box-shadow: 0 0 4px #ccc;
    overflow: hidden;

    img {
      width: 100%;
      height: 100%;
    }
  }
</style>
