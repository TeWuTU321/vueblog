import { createRouter, createWebHashHistory } from 'vue-router'
import Login from '@/views/Login'
import BlogDetail from '@/views/BlogDetail'
import BlogEdit from '@/views/BlogEdit'
import Blogs from '@/views/Blogs'


const routes = [

  {
    path: '/',
    name: 'Index',
   redirect: '/blogs'
  },
  {
    path: '/blogs',
    name: 'Blogs',
    component: Blogs,
    
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
    // component: () => import( '@/views/')
  },
  {
    path: '/blogedit',
    name: 'BlogEdit',
    component: BlogEdit
  },
  {
    path: '/blogdetail',
    name: 'BlogDetail',
    component: BlogDetail
  },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
