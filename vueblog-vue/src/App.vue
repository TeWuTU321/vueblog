<template>
<router-view v-if="!$route.meta.keepAlive" class="container"></router-view>
</template>

<style>

#app {
 
  max-width: 960px;
  margin: 0 auto;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
