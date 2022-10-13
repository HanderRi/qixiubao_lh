import {createRouter, createWebHistory} from 'vue-router'
import Layout from '../layout/Layout.vue'


const routes = [
    {
        path: '/',
        name: 'Home',
        redirect: '/login', //重定向
    },
    {
        path: '/login',
        name: 'Login',
        component: ()=> import("@/views/Login")
    },
    {
        path: '/success',
        name: 'Layout',
        component: Layout,
        children: [
            {
                path: '/warningDevices',//告警设备界面路由
                name: 'WarningDevices',
                component: () => import("@/views/WarningDevices"),
            },
            {
                path: '/warningMessages',//告警信息界面路由
                name: 'WarningMessages',
                component: () => import("@/views/WarningMessages"),
            },
            {
                path: '/storage',//材料出入库界面
                name: 'Storage',
                component: () => import("@/views/Storage"),
            },
            {
                path: '/storageMessages',//材料出入库信息界面
                name: 'StorageMessages',
                component: () => import("@/views/StorageMessages"),
            },
            {
                path: '/rectify',//列表界面
                name: 'Rectify',
                component: () => import("@/views/Rectify"),
            },
        ]
    },
    {
        path: '/websocket',
        name: 'websocket',
        component: ()=> import("@/views/Messages")
    },
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
