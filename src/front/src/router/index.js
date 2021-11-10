import { createRouter, createWebHistory } from 'vue-router';
import mainComponent from './../components/MainComponent.vue';
import loginComponent from './../components/LoginComponent.vue';
import userInfoComponent from './../components/UserInfoComponent.vue';

const routes = [
    {
        path: '/',
        component: mainComponent,
    },
    {
        path: '/login',
        component: loginComponent,
    },
    {
        path: '/auth_info',
        component: userInfoComponent,
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;