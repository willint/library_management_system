import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/dashboard'
        },
        {
            path: '/',
            component: resolve => require(['../components/common/Home.vue'], resolve),
            meta: { title: '自述文件' },
            children: [
                {
                    path: '/dashboard',
                    component: resolve => require(['../components/page/Dashboard.vue'], resolve),
                    meta: { title: '系统首页'}
                },
                {
                    // 权限页面
                    path: '/permission',
                    component: resolve => require(['../components/page/Permission.vue'], resolve),
                    meta: { title: '权限测试', permission: true }
                },
                {
                    path: '/404',
                    component: resolve => require(['../components/page/404.vue'], resolve),
                    meta: { title: '404' }
                },
                {
                    path: '/403',
                    component: resolve => require(['../components/page/403.vue'], resolve),
                    meta: { title: '权限不足' }
                },
                {
                    path: '/user',
                    component: resolve => require(['../view/system/user/list.vue'], resolve),
                    meta: { title: '用户管理', permission: 'user:list'}
                },
                {
                    path: '/role',
                    component: resolve => require(['../view/system/role/list.vue'], resolve),
                    meta: { title: '角色管理', permission: 'role:list'}
                },
                {
                    path: '/menu',
                    component: resolve => require(['../view/system/menu/list.vue'], resolve),
                    meta: { title: '菜单权限管理', permission: 'menu:all'}
                },{
                    path: '/book',
                    component: resolve => require(['../view/mg/book/list.vue'], resolve),
                    meta: { title: '图书管理', permission: 'enterprise:list'}
                }
            ]
        },
        {
            path: '/login',
            component: resolve => require(['../components/page/Login.vue'], resolve)
        },
        {
            path: '*',
            redirect: '/404'
        }
    ]
})
