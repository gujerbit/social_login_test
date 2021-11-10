<template>
  <main></main>
</template>

<script>
import { onMounted } from 'vue';
import axios from 'axios';

export default {
    setup() {
        axios.defaults.baseURL = 'https://localhost';

        onMounted(async () => {
          // let { data } = await axios.get('/user_info');
          // store.commit('setName', data[0]);
          // store.commit('setEmail', data[1]);
          
          // if(data !== '') location.href = '/info';

          await axios.get()
        });
    }
}
</script>

<style>

</style>