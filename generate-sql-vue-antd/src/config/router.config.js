// eslint-disable-next-line
import { UserLayout, BasicLayout } from '@/layouts'
// import { bxAnaalyse } from '@/core/icons'

const RouteView = {
  name: 'RouteView',
  render: h => h('router-view')
}

export const asyncRouterMap = [
  {
    path: '/',
    name: 'index',
    component: BasicLayout,
    meta: { title: 'menu.home' },
    redirect: '/home',
    children: [
      {
        path: '/home',
        name: 'home',
        hidden: true,
        component: () => import('@/views/home/Home'),
        meta: {
          title: 'Home',
          keepAlive: false
        }
      },
      {
        path: '/study',
        name: 'study',
        component: RouteView,
        meta: {
          title: 'menu.study',
          keepAlive: true,
          icon: 'pie-chart'
        },
        children: [
          {
            path: '/study/mysql',
            name: 'mysql',
            component: () => import('@/views/study/Article'),
            meta: {
              title: 'MySQL',
              keepAlive: false
            }
          },
          {
            path: '/study/mybatis',
            name: 'mybatis',
            component: () => import('@/views/study/Article'),
            meta: {
              title: 'MyBatis',
              keepAlive: false
            }
          }
        ]
      },
      {
        path: '/generate',
        name: 'generate',
        component: RouteView,
        meta: {
          title: 'menu.generate',
          keepAlive: true,
          icon: 'desktop'
        }
      },
      {
        path: '/manage',
        name: 'manage',
        component: RouteView,
        meta: {
          title: 'menu.manage',
          keepAlive: true,
          icon: 'windows'
        },
        children: [
          {
            path: '/manage/role',
            name: 'role',
            component: () => import('@/views/manage/Role'),
            meta: {
              title: 'menu.manage.role',
              keepAlive: false
            }
          },
          {
            path: '/manage/mysql',
            name: 'manage-mysql',
            component: () => import('@/views/manage/Manage'),
            meta: {
              title: 'menu.manage.mysql',
              keepAlive: false
            }
          },
          {
            path: '/manage/mybatis',
            name: 'manage-mybatis',
            component: () => import('@/views/manage/Manage'),
            meta: {
              title: 'menu.manage.mybatis',
              keepAlive: false
            }
          }
        ]
      },
      // Exception
      {
        path: '/exception',
        name: 'exception',
        hidden: true,
        component: RouteView,
        redirect: '/exception/403',
        meta: { title: 'menu.exception', icon: 'warning', permission: ['exception'] },
        children: [
          {
            path: '/exception/403',
            name: 'Exception403',
            component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/403'),
            meta: { title: 'menu.exception.not-permission', permission: ['exception'] }
          },
          {
            path: '/exception/404',
            name: 'Exception404',
            component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/404'),
            meta: { title: 'menu.exception.not-find', permission: ['exception'] }
          },
          {
            path: '/exception/500',
            name: 'Exception500',
            component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/500'),
            meta: { title: 'menu.exception.server-error', permission: ['exception'] }
          }
        ]
      },

      // account
      {
        path: '/account',
        component: RouteView,
        redirect: '/account/center',
        name: 'account',
        meta: { title: 'menu.account', icon: 'user', keepAlive: true, permission: ['user'] },
        children: [
          {
            path: '/account/center',
            name: 'center',
            component: () => import('@/views/account/center'),
            meta: { title: 'menu.account.center', keepAlive: true, permission: ['user'] }
          },
          {
            path: '/account/settings',
            name: 'settings',
            component: () => import('@/views/account/settings/Index'),
            meta: { title: 'menu.account.settings', hideHeader: true, permission: ['user'] },
            redirect: '/account/settings/basic',
            hideChildrenInMenu: true,
            children: [
              {
                path: '/account/settings/basic',
                name: 'BasicSettings',
                component: () => import('@/views/account/settings/BasicSetting'),
                meta: { title: 'account.settings.menuMap.basic', hidden: true, permission: ['user'] }
              },
              {
                path: '/account/settings/security',
                name: 'SecuritySettings',
                component: () => import('@/views/account/settings/Security'),
                meta: {
                  title: 'account.settings.menuMap.security',
                  hidden: true,
                  keepAlive: true
                }
              }
            ]
          }
        ]
      },
      {
        path: 'http://localhost:8080/druid',
        name: '数据监控',
        meta: { title: 'menu.druid.monitor', icon: 'heat-map', target: '_blank' }
      }
    ]
  },
  {
    path: '*',
    redirect: '/404',
    hidden: true
  }
]

/**
 * 基础路由
 * @type { *[] }
 */
export const constantRouterMap = [
  {
    path: '/user',
    component: UserLayout,
    redirect: '/user/login',
    hidden: true,
    children: [
      {
        path: 'login',
        name: 'login',
        component: () => import(/* webpackChunkName: "user" */ '@/views/user/Login')
      },
      {
        path: 'register',
        name: 'register',
        component: () => import(/* webpackChunkName: "user" */ '@/views/user/Register')
      },
      {
        path: 'register-result',
        name: 'registerResult',
        component: () => import(/* webpackChunkName: "user" */ '@/views/user/RegisterResult')
      },
      {
        path: 'recover',
        name: 'recover',
        component: undefined
      }
    ]
  },

  {
    path: '/404',
    component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/404')
  }
]
