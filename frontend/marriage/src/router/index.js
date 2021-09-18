import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

export const constantRoutes = [
  {
    path: '/',
    component: () => import('@/views/index/index'),
    hidden: true
  },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  // {
  //   path: '/setting',
  //   component: () => import('@/views/regist/index')
  // },
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/list',
    component: Layout,
    redirect: '/list/table',
    children: [
      {
        path: 'table',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/index'),
        meta: { title: '用户信息列表', icon: 'user' }
      },
      {
        path: 'create',
        name: 'Create',
        component: () => import('@/views/create/index'),
        meta: { title: '新建', icon: 'user' },
        hidden: true
      }
    ]
  },
  {
    path: '/marriageInfo',
    component: Layout,
    redirect: '/join/list',
    children: [
      {
        path: 'list',
        name: 'jonList',
        component: () => import('@/views/join/index'),
        meta: { title: '证书列表', icon: 'example' }
      },
      {
        path: 'createCer',
        name: 'CreateCer',
        component: () => import('@/views/create-cer/index'),
        meta: { title: '新建证书', icon: 'Create' },
        hidden: true
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () =>
  new Router({
    // mode: 'history', // require service support
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRoutes
  })

const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
