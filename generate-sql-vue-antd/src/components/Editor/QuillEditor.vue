<template>
  <div :class="prefixCls">
    <quill-editor
      v-model="content"
      ref="myQuillEditor"
      :options="editorOption"
      @blur="onEditorBlur($event)"
      @focus="onEditorFocus($event)"
      @ready="onEditorReady($event)"
      @change="onEditorChange($event)"
    >
    </quill-editor>

  </div>
</template>

<script>
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'
import 'highlight.js/styles/xcode.css'
import hljs from 'highlight.js'

import { quillEditor } from 'vue-quill-editor'

export default {
  name: 'QuillEditor',
  components: {
    quillEditor
  },
  props: {
    prefixCls: {
      type: String,
      default: 'ant-editor-quill'
    },
    // 表单校验用字段
    // eslint-disable-next-line
    value: {
      type: String
    },
    editorPlaceholder: {
      type: String,
      default: 'Input text hear...'
    },
    toolbars: undefined,
    readOnly: {
      type: Boolean,
      default: true
    },
    theme: {
      type: String,
      default: 'snow'
    }
  },
  data () {
    return {
      content: this.value,
      editorOption: {
        // some quill options
        modules: {
          toolbar: this.toolbars || [
            [{ 'size': [] }],
            ['bold', 'italic', 'underline', 'strike'],
            ['blockquote', 'code-block', { 'script': 'sub' }, { 'script': 'super' }],
            [{ 'list': 'ordered' }, { 'list': 'bullet' }, { 'align': [] }],
            [{ 'color': [] }, { 'background': [] }],
            ['link', 'image']
          ],
          syntax: {
            highlight: text => {
              return hljs.highlightAuto(text).value
            }
          }
        },
        readOnly: this.readOnly,
        placeholder: this.editorPlaceholder,
        theme: this.theme
      }
    }
  },
  methods: {
    onEditorBlur (quill) {
      // console.log('editor blur!', quill)
    },
    onEditorFocus (quill) {
      // console.log('editor focus!', quill)
    },
    onEditorReady (quill) {
      // console.log('editor ready!', quill)
    },
    onEditorChange ({ quill, html, text }) {
      // console.log('editor change!', quill, html, text)
      const {
        $emit
      } = this
      $emit('change', html)
    }
  },
  watch: {
    value (val) {
      this.content = val
    }
  }
}
</script>

<style lang="less" scoped>
@import url('../index.less');

/* 覆盖 quill 默认边框圆角为 ant 默认圆角，用于统一 ant 组件风格 */
.ant-editor-quill {
  line-height: initial;
  /deep/ .ql-toolbar.ql-snow {
    border-radius: @border-radius-base @border-radius-base 0 0;
  }
  /deep/ .ql-container.ql-snow {
    border-radius: 0 0 @border-radius-base @border-radius-base;
  }
  /deep/ .ql-editor {
    height: 360px;
  }
}
</style>
