<template>
  <main>
      <div id="login">
          <a href="/oauth2/authorization/kakao">
            <img class="kakao" src="../assets/kakao_login_large_wide.png" alt="">
          </a>
          <a href="/oauth2/authorization/google">
            <img class="google" src="../assets/btn_google_signin_light_normal_web@2x.png" alt="">
          </a>
      </div>
  </main>
</template>

<script>
export default {
    
}
</script>

<style scoped>
main {
  width: 100%;
  height: 100vh;
}

#login {
  width: 50%;
  height: 50%;
  margin: 0 auto;
  border: 2px solid #000000;
  border-radius: 10px;
  display: grid;
  grid-template-rows: repeat(2, 1fr);
}

#login img {
  width: 60%;
  height: 40%;
  margin: 0 auto;
}
</style>