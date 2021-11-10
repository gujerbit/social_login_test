<template>
  <main>
    <p v-for="value in user" :key="value">{{value}}</p>
    <a href="/logout">logout</a>
  </main>
</template>

<script>
import { ref, onMounted } from 'vue';
import axios from 'axios';

export default {
  setup() {
    const user = ref({
      info: [],
    });

    onMounted(async () => {
      let { data } = await axios.get('/final_factor_user_info');
      user.value.info = data;
    });

    return { user };
  }
}
</script>

<style scoped>

</style>