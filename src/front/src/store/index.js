import { createStore } from 'vuex';
import createPersistedState from 'vuex-persistedstate';

export default createStore({
    state: {
        name: '',
        email: '',
    },
    getters: {
        getName(state) {
            return state.name;
        },
        getEmail(state) {
            return state.email;
        }
    },
    mutations: {
        setName(state, value) {
            state.name = value;
        },
        setEmail(state, value) {
            state.email = value;
        }
    },
    plugins: [createPersistedState()],
})