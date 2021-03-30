<template>
  <div class="app-list">
    <a-list
      :grid="{ gutter: 24, lg: 3, md: 2, sm: 1, xs: 1 }"
      :dataSource="dataSource"
    >
      <a-list-item slot="renderItem" slot-scope="item">
        <a-card :hoverable="true">
          <a-card-meta>
            <div style="margin-bottom: 3px" slot="title">{{ item.title }}</div>
            <a-avatar class="card-avatar" slot="avatar" :src="item.avatar" size="small"/>
            <div class="meta-cardInfo" slot="description" :title="item.desc">
              {{ item.desc }}
            </div>
          </a-card-meta>
          <template class="ant-card-actions" slot="actions">
            <a v-if="item.codeSrc" :href="item.codeSrc" target="_blank" title="源代码">
              Source <a-icon type="link"/>
            </a>
            <a :href="item.binSrc" target="_blank" title="二进制文件">
              Binary <a-icon type="link" />
            </a>
          </template>
        </a-card>
      </a-list-item>
    </a-list>

  </div>
</template>

<script>
import java from '@/assets/java.png'
import github from '@/assets/github.png'
import springboot from '@/assets/springboot.png'
import antdPro from '@/assets/antd_pro.png'
import antd from '@/assets/antd.svg'
import mybatis from '@/assets/mybatis.png'
import quill from '@/assets/quill.svg'

const dataSource = [
  {
    title: 'SQLBuilder',
    avatar: github,
    desc: 'SQL快速生成器',
    codeSrc: 'https://github.com/Rukawalee/SQLBuilder',
    binSrc: 'https://gitee.com/rukawalee/mvn/tree/master/repo/com/rukawa/SQL'
  },
  {
    title: 'SpringBoot',
    avatar: springboot,
    desc: '一套企业级快速开发框架',
    binSrc: 'https://spring.io/projects/spring-boot'
  },
  {
    title: 'Antd Pro Vue',
    avatar: antdPro,
    desc: '开箱即用的中台前端/设计解决方案',
    binSrc: 'https://pro.antdv.com/'
  },
  {
    title: 'CommonUtil',
    avatar: github,
    desc: '公共工具类',
    codeSrc: 'https://github.com/Rukawalee/CommonUtil',
    binSrc: 'https://gitee.com/rukawalee/mvn/tree/master/repo/com/rukawa/CommonUtil'
  },
  {
    title: 'MyBatis',
    avatar: mybatis,
    desc: '一款优秀的持久层框架',
    binSrc: 'https://mybatis.org/mybatis-3/'
  },
  {
    title: 'Antd Vue',
    avatar: antd,
    desc: '开箱即用的高质量 Vue 组件',
    binSrc: 'https://www.antdv.com/docs/vue/introduce-cn/'
  },
  {
    title: 'Quill Editor',
    avatar: quill,
    desc: 'Your powerful rich text editor.',
    binSrc: 'https://quilljs.com/'
  },
  {
    title: 'Druid',
    avatar: java,
    desc: '为监控而生的数据库连接池',
    binSrc: 'https://github.com/alibaba/druid'
  }
]

export default {
  name: 'Article',
  components: {},
  data () {
    return {
      dataSource
    }
  }
}
</script>

<style lang="less" scoped>

  .app-list {

    .meta-cardInfo {
      zoom: 1;
      margin-top: 16px;

      > div {
        position: relative;
        text-align: left;
        float: left;
        width: 50%;

        p {
          line-height: 32px;
          font-size: 24px;
          margin: 0;

          &:first-child {
            color: rgba(0, 0, 0, .45);
            font-size: 12px;
            line-height: 20px;
            margin-bottom: 4px;
          }
        }

      }
    }
  }

</style>
