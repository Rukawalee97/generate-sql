<template>
  <div>
    <a-table
      :columns="columns"
      :data-source="collects"
    >
      <span slot="no" slot-scope="_, __, index">
        {{ index + 1 }}
      </span>
      <span
        slot="operation"
        slot-scope="text, record"
      >
        <a-button
          shape="round"
          type="primary"
          size="small"
          @click="() => getSkill(record)"
        >
          查看
        </a-button>
        <a-divider
          type="vertical"
        />
        <a-button
          shape="round"
          size="small"
          gost
          @click="() => updateCollect(record.collectId)"
        >
          取消
        </a-button>
      </span>
    </a-table>
    <a-modal
      :title="skill.skillTopic"
      :visible="skillModalVisible"
      :maskClosable="false"
      :width="skillModalWidth"
      @cancel="() => showSkillModal(false)"
      @ok="() => showSkillModal(false)"
    >
      <span
        v-html="skill.skillContent"
      />
    </a-modal>
  </div>
</template>

<script>
import { message } from 'ant-design-vue'
import { mapActions, mapGetters } from 'vuex'

const columns = [
  {
    title: '序号',
    dataIndex: 'no',
    width: '60px',
    key: 'no',
    scopedSlots: {
      customRender: 'no'
    }
  },
  {
    title: '知识点',
    dataIndex: 'skillTopic',
    key: 'skillTopic'
  },
  {
    title: '收藏时间',
    dataIndex: 'collectTime',
    key: 'collectTime'
  },
  {
    title: '操作',
    dataIndex: 'operation',
    key: 'operation',
    width: '180px',
    scopedSlots: {
      customRender: 'operation'
    }
  }
]

export default {
  name: 'Article',
  data () {
    return {
      columns,
      skillModalVisible: false,
      skillModalWidth: window.innerWidth * 0.6
    }
  },
  computed: {
    ...mapGetters([
      'collects',
      'skill'
    ])
  },
  mounted () {
    this.GetCollects()
  },
  methods: {
    ...mapActions(['GetCollects', 'DeleteCollect', 'GetSkill']),
    updateCollect (collectId) {
      this.DeleteCollect(collectId)
      .then(response => {
        if (!response.result) {
          this.requestFailed(response)
        } else {
          message.success('取消收藏成功！')
          this.GetCollects()
        }
      })
      .catch(error => this.requestFailed(error))
    },
    getSkill (skill) {
      if (skill) {
        this.GetSkill(skill.skillId)
        this.showSkillModal(true)
      }
    },
    showSkillModal (visible) {
      this.skillModalVisible = visible
    },
    requestFailed (err) {
      this.isLoginError = true
      this.$notification['error']({
        message: '发生错误',
        description: err.message || ((err.response || {}).data || {}).message || '请求出现错误，请稍后再试',
        duration: 4
      })
    }
  }
}
</script>

<style scoped>

</style>
