<template>
  <div class="home">
    <a-layout
      class="basic-layout"
    >
      <a-layout-content
        class="basic-layout-content"
      >
        <a-row
          type="flex"
          justify="center"
        >
          <a-col
            :span="10"
            :offset="2"
          >
            <a-card
              class="content-center-card"
            >
              <a-carousel
                arrows
                autoplay
              >
                <div
                  :key="i"
                  v-for="(c, i) in carouselList"
                >
                  <img
                    :src="c.imgSrc"
                    class="carousel-img"
                    :title="c.tip"
                  />
                </div>
              </a-carousel>
            </a-card>
          </a-col>
          <a-col
            :span="9"
            :offset="2"
          >
            <a-card
              class="content-center-card content-center-card-text"
            >
              <a-timeline
                class="content-timeline"
              >
                <a-timeline-item
                  color="blue"
                >
                  <h2>欢迎使用 Generate SQL 在线学习平台</h2>
                </a-timeline-item>
                <a-timeline-item
                  color="green"
                >
                  <h2>在这里学习 SQL 相关的知识点</h2>
                </a-timeline-item>
                <a-timeline-item
                  color="green"
                >
                  <h2>在这里快速生成 SQL 加深学习印象</h2>
                </a-timeline-item>
                <a-timeline-item
                  color="gray"
                >
                  <h2>加入我们，深入学习，成就自己</h2>
                </a-timeline-item>
              </a-timeline>
            </a-card>
          </a-col>
        </a-row>
      </a-layout-content>
      <a-layout-footer
        class="basic-layout-footer"
      >
        <a-button
          v-for="item in footerList"
          type="link"
          :key="item.foot"
          :title="item.foot"
          :href="item.imgHref"
          ghost
        >
          <img
            :src="item.imgSrc"
            class="footer-img"
          />
          <br />
          {{ item.foot }}
        </a-button>
        <hr />
      </a-layout-footer>
    </a-layout>
  </div>
</template>

<script>
import java from '@/assets/java.png'
import github from '@/assets/github.png'
import springboot from '@/assets/springboot.png'
import antdPro from '@/assets/antd_pro.png'
import mysql from '@/assets/mysql.svg'
import main from '@/assets/main.png'
import home from '@/assets/home.png'

export default {
  data () {
    return {
      footerList: [
        {
          imgSrc: java,
          foot: 'Java',
          imgHref: 'https://www.runoob.com/java/java-tutorial.html'
        },
        {
          imgSrc: springboot,
          foot: 'SpringBoot',
          imgHref: 'https://spring.io/projects/spring-boot/'
        },
        {
          imgSrc: antdPro,
          foot: 'Antd Vue Pro',
          imgHref: 'https://pro.antdv.com/'
        },
        {
          imgSrc: mysql,
          foot: 'MySQL',
          imgHref: 'https://www.runoob.com/mysql/mysql-tutorial.html'
        },
        {
          imgSrc: github,
          foot: 'GitHub',
          imgHref: 'https://www.runoob.com/git/git-tutorial.html'
        }
      ],
      carouselList: [
        {
          imgSrc: main,
          tip: 'Generate SQL'
        },
        {
          imgSrc: home,
          tip: 'Home Page'
        }
      ]
    }
  }
}
</script>

<style scoped>
.home {
  height: calc(100vh - 64px);
  background-image: linear-gradient(-225deg, #69EACB 0%, #EACCF8 48%, #6654F1 100%);
}
.basic-layout {
  text-align: center;
  background: transparent;
}
.basic-layout-content {
  height: calc(100vh - 64px);
}
.basic-layout-footer {
  position: absolute;
  background: transparent;
  bottom: 48px;
  width: 100%;
}
.content-center-card {
  top: 20px;
  border: none;
  background: transparent;
  height: calc(100vh - 240px);
}
.content-center-card-text {
  text-align: left;
}
.content-timeline {
  margin-top: 100px;
}
.footer-span {
  margin: 3px;
  border-radius: 8px;
}
.footer-img {
  width: 48px;
  padding: 4px;
}
.footer-img:hover {
  cursor: pointer;
  background-image: linear-gradient(to top, #b3ffab 0%, #12fff7 100%);
}
.carousel-img {
  width: calc(100% - 30px);
}
</style>
