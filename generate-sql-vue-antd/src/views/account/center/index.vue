<template>
  <div class="page-header-index-wide page-header-wrapper-grid-content-main">
    <a-row :gutter="[2, 2]">
      <a-col :md="24" :lg="8" :xl="7">
        <a-card :bordered="false">
          <div class="account-center-avatarHolder">
            <div class="avatar">
              <img
                :src="avatar"
                @mouseup="() => clickImage(avatar)"
              >
            </div>
            <div class="username">{{ nickname }}</div>
            <div class="bio">{{ userIntroduction }}</div>
          </div>
          <a-tabs
            size="small"
          >
            <a-tab-pane
              key="tab_team"
              tab="技术团队"
            >
              <div class="account-center-detail">
                <p>
                  <i class="title"></i> 周艳明
                </p>
                <p>
                  <i class="address"></i> 重庆市 沙坪坝区
                </p>
                <p>
                  <i class="group"></i> 重庆师范大学 2017 计算机科学与技术
                </p>
              </div>
            </a-tab-pane>
          </a-tabs>
          <a-tabs
            size="small"
          >
            <a-tab-pane
              key="tab_tag"
              tab="技术标记"
            >
              <div class="account-center-tags">
                <template v-for="(tag, index) in tags">
                  <a-tooltip v-if="tag.length > 20" :key="tag" :title="tag">
                    <a-tag
                      :key="tag"
                    >
                      {{ `${tag.slice(0, 20)}...` }}
                    </a-tag>
                  </a-tooltip>
                  <a-tag
                    v-else
                    :key="tag"
                    :color="colors[index]"
                  >
                    {{ tag }}
                  </a-tag>
                </template>
              </div>
            </a-tab-pane>
          </a-tabs>
          <a-tabs>
            <a-tab-pane
              key="tab_technology"
              tab="技术支持"
            >
              <div class="account-center-team">
                <div class="members">
                  <a-row>
                    <a-col :span="12" v-for="(item, index) in teams" :key="index">
                      <a :href="item.link" target="_blank">
                        <a-avatar size="small" :src="item.avatar"/>
                        <span class="member">{{ item.name }}</span>
                      </a>
                    </a-col>
                  </a-row>
                </div>
              </div>
            </a-tab-pane>
          </a-tabs>
        </a-card>
      </a-col>
      <a-col :md="24" :lg="16" :xl="17">
        <a-card
          style="width:100%"
          :bordered="false"
          :tabList="tabListNoTitle"
          :activeTabKey="noTitleKey"
          @tabChange="key => handleTabChange(key, 'noTitleKey')"
        >
          <app-page v-if="noTitleKey === 'app'"></app-page>
          <article-page v-else-if="noTitleKey === 'article'"></article-page>
          <project-page v-else-if="noTitleKey === 'project'"></project-page>
        </a-card>
      </a-col>
    </a-row>
    <a-modal
      :visible="previewVisible"
      :footer="null"
      @cancel="handleCancel"
    >
      <img
        style="width: 100%"
        :src="previewImage"
      />
    </a-modal>
  </div>
</template>

<script>
import { PageView, RouteView } from '@/layouts'
import { AppPage, ArticlePage, ProjectPage } from './page'
import { mapGetters } from 'vuex'
import { isEmpty } from '@/utils/util'
import { message } from 'ant-design-vue'
import mybatis from '@/assets/mybatis.png'
import springboot from '@/assets/springboot.png'
import java from '@/assets/java.png'

export default {
  components: {
    RouteView,
    PageView,
    AppPage,
    ArticlePage,
    ProjectPage
  },
  data () {
    return {
      previewImage: '',
      previewVisible: false,
      tags: ['Java', 'MySQL', 'Vue', 'Antd'],
      colors: ['#e16512', '#005a85', '#3fbc88', '#40a9ff'],
      teams: [
        {
          link: 'https://github.com/Rukawalee/SQLBuilder',
          avatar: java,
          name: 'SQLBuilder'
        },
        {
          link: 'https://spring.io/projects/spring-boot',
          avatar: springboot,
          name: 'SpringBoot'
        },
        {
          link: 'https://mybatis.org/mybatis-3/',
          avatar: mybatis,
          name: 'MyBatis'
        }
      ],
      tabListNoTitle: [
        {
          key: 'app',
          tab: '引 用'
        },
        {
          key: 'article',
          tab: '收 藏'
        }
        // ,
        // {
        //   key: 'project',
        //   tab: '评 论'
        // }
      ],
      noTitleKey: 'app'
    }
  },
  computed: {
    ...mapGetters(['nickname', 'avatar', 'token', 'userIntroduction'])
  },
  mounted () {
    this.isLoged()
  },
  methods: {
    clickImage (avatar) {
      this.previewImage = avatar
      this.previewVisible = true
    },
    handleCancel () {
      this.previewVisible = false
    },
    handleTabChange (key, type) {
      this[type] = key
    },
    handleTagClose (removeTag) {
      const tags = this.tags.filter(tag => tag !== removeTag)
      this.tags = tags
    },
    isLoged () {
      if (isEmpty(this.token)) {
        message.warning('您还没有登录！')
        this.$router.push({ path: '/' })
      }
    }
  }
}
</script>

<style lang="less" scoped>
.page-header-wrapper-grid-content-main {
  width: 100%;
  height: 100%;
  min-height: 100%;
  transition: 0.3s;

  .account-center-avatarHolder {
    text-align: center;
    margin-bottom: 24px;

    & > .avatar {
      margin: 0 auto;
      width: 104px;
      height: 104px;
      margin-bottom: 20px;
      border-radius: 50%;
      overflow: hidden;
      img {
        height: 100%;
        width: 100%;
      }
    }

    .username {
      color: rgba(0, 0, 0, 0.85);
      font-size: 20px;
      line-height: 28px;
      font-weight: 500;
      margin-bottom: 4px;
    }
  }

  .account-center-detail {
    p {
      margin-bottom: 8px;
      padding-left: 26px;
      position: relative;
    }

    i {
      position: absolute;
      height: 14px;
      width: 14px;
      left: 0;
      top: 4px;
      background: url(https://gw.alipayobjects.com/zos/rmsportal/pBjWzVAHnOOtAUvZmZfy.svg);
    }

    .title {
      background-position: 0 0;
    }
    .group {
      background-position: 0 -22px;
    }
    .address {
      background-position: 0 -44px;
    }
  }

  .account-center-tags {
    .ant-tag {
      margin-bottom: 8px;
    }
  }

  .account-center-team {
    .members {
      a {
        display: block;
        margin: 12px 0;
        line-height: 24px;
        height: 24px;
        .member {
          font-size: 14px;
          color: rgba(0, 0, 0, 0.65);
          line-height: 24px;
          max-width: 100px;
          vertical-align: top;
          margin-left: 12px;
          transition: all 0.3s;
          display: inline-block;
        }
        &:hover {
          span {
            color: #1890ff;
          }
        }
      }
    }
  }

  .tagsTitle,
  .teamTitle {
    font-weight: 500;
    color: rgba(0, 0, 0, 0.85);
    margin-bottom: 12px;
  }
}
</style>
