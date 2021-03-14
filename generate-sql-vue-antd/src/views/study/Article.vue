<template>
  <div>
    <a-layout
      id="components-layout-demo-top-side-2"
      style="height: window.innerHeight"
      v-if="skills.length !== 0"
    >
      <a-layout-sider
        width="160"
        :style="{
          background: '#fff',
          height: contentHeight,
          'overflow-y': 'auto'
        }"
      >
        <a-menu
          mode="inline"
          :selected-keys="[skill.skillId]"
        >
          <a-menu-item
            v-for="skill in skills"
            :key="skill.skillId"
            :title="skill.skillTopic"
            @click="() => getSkill(skill)"
          >
            {{ skill.skillTopic }}
          </a-menu-item>
        </a-menu>
      </a-layout-sider>
      <a-layout
        style="padding: 1px"
      >
        <a-layout>
          <a-layout-header
            class="topic-content-header"
          >
            {{ skill.skillTopic }}
          </a-layout-header>
          <a-layout-content
            :style="{
              background: '#fff',
              padding: '24px',
              margin: 0,
              minHeight: '280px',
              'overflow-y': 'auto'
            }"
          >
            <span v-html="skill.skillContent" />
          </a-layout-content>
        </a-layout>
        <a-layout-sider
          class="skill-comment"
          width="300"
        >
          Sider
        </a-layout-sider>
      </a-layout>
    </a-layout>
    <a-empty v-else />
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'

  export default {
    data () {
      return {
        contentHeight: window.innerHeight - 64 + 'px'
      }
    },
    computed: {
      ...mapGetters(['skill', 'skills'])
    },
    methods: {
      ...mapActions(['GetSkillTopics', 'GetSkill']),
      getSkillType () {
        let skillType = this.$route.fullPath
        skillType = skillType.substring(skillType.lastIndexOf('/') + 1)
        return skillType
      },
      getSkill (skill) {
        if (skill) {
          this.GetSkill(skill.skillId)
        }
      },
      windowResize () {
        this.contentHeight = window.innerHeight - 64 + 'px'
      }
    },
    watch: {
      $route () {
        this.GetSkillTopics(this.getSkillType())
        .then(() => this.getSkill(this.skill))
      }
    },
    mounted () {
      this.GetSkillTopics(this.getSkillType())
      .then(() => this.getSkill(this.skill))
    }
  }
</script>

<style lang="less" scoped>
.topic-content-header {
  text-align: center;
  font-size: 16px;
  font-weight: bold;
  opacity: .8;
}
.skill-comment {
  background: #fff;
  box-shadow: inset 1px 2px 2px #888888;
}
</style>
