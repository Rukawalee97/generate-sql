<template>
  <a-card
    class="generate-card"
  >
    <a-row
      :gutter="[6, 6]"
    >
      <a-col
        span="12"
      >
        <div>
          <a-tooltip
            placement="rightTop"
          >
            <template
              slot="title"
              style="width: 500px"
            >
              <pre class="ql-syntax" spellcheck="false">{
	tableName: "test",
	entity: {
		fieldA: 1,
		fieldB: "b",
		fieldC: "c"
	},
	param: {
		conditionFields: [
			"fieldB"
		],
		executionFields: [
			"fieldA",
			"fieldB"
		],
		similarMap: {
			fieldC: "c"
		},
		rangeParam: [
			{
				fieldName: "fieldA",
				lower: 0,
				upper: 5,
				rangeSymbol: "BETWEEN_AND"
			}
		],
		orderParam: [
			{
				fieldName: "fieldA",
				orderSymbol: "ASC"
			}
		],
		pageParam: {
			current: 1,
			pageSize: 10
		}
	}
}
</pre>
            </template>
            <b>请输入JSON对象</b>
          </a-tooltip>
          <div
            class="submit-btn"
          >
            <a-button
              type="primary"
              @click="submitJson"
            >
              生成
            </a-button>
          </div>
        </div>
        <quill-editor
          ref="json"
          :toolbars="[]"
          theme="bubble"
          class="quill-new-height"
          value="<pre class='ql-syntax' spellcheck='false'>{
	tableName: 'test',
	entity: {
		fieldA: 1,
		fieldB: 'b',
		fieldC: 'c'
	},
	param: {
		conditionFields: [
			'fieldB'
		],
		executionFields: [
			'fieldA',
			'fieldB'
		],
		similarMap: {
			fieldC: 'c'
		},
		rangeParam: [
			{
				fieldName: 'fieldA',
				lower: 0,
				upper: 5,
				rangeSymbol: 'BETWEEN_AND'
			}
		],
		orderParam: [
			{
				fieldName: 'fieldA',
				orderSymbol: 'ASC'
			}
		],
		pageParam: {
			current: 1,
			pageSize: 10
		}
	}
}
</pre>"
        />
      </a-col>
      <a-col
        span="12"
      >
        <a-row
          :gutter="[6, 6]"
        >
          <a-col
            span="24"
          >
            <b>JDBC SQL</b>
            <quill-editor
              :toolbars="[]"
              editorPlaceholder="JDBC SQL"
              theme="bubble"
              :value="sqlMap.jdbc"
            />
          </a-col>
        </a-row>
        <a-row
          :gutter="[6, 6]"
        >
          <a-col
            span="24"
          >
            <b>JDBCTemplate SQL</b>
            <quill-editor
              :toolbars="[]"
              editorPlaceholder="JDBCTemplate SQL"
              theme="bubble"
              :value="sqlMap.jdbcTemplate"
            />
          </a-col>
        </a-row>
      </a-col>
    </a-row>
  </a-card>
</template>

<script>
import QuillEditor from '@/components/Editor/QuillEditor.vue'
import { mapActions, mapGetters } from 'vuex'
import { isEmpty } from '@/utils/util'
import { message } from 'ant-design-vue'

  export default {
    components: { QuillEditor },
    computed: {
      ...mapGetters(['sqlMap', 'token'])
    },
    methods: {
      ...mapActions(['GenerateSql']),
      submitJson () {
        const {
          $refs,
          GenerateSql
        } = this
        const json = $refs.json.$refs.myQuillEditor.quill.getText()
        GenerateSql({ json })
        .catch(error => this.requestFailed(error))
      },
      requestFailed (err) {
        this.isLoginError = true
        this.$notification['error']({
          message: '发生错误',
          description: err.message || ((err.response || {}).data || {}).message || '请求出现错误，请稍后再试',
          duration: 4
        })
      },
      isLoged () {
        if (isEmpty(this.token)) {
          message.warning('您还没有登录！')
          this.$router.push({ path: '/' })
          return false
        }
        return true
      }
    },
    mounted () {
      this.isLoged()
    }
  }
</script>

<style lang="less" scoped>
.generate-card {
  height: calc(100vh - 64px);
}
.submit-btn {
  float: right;
}
.quill-new-height {
  /deep/ .ql-editor {
    height: calc(100vh - 148px);
  }
}
</style>
