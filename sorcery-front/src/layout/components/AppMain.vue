<template>
  <section class="app-main">
    <transition name="fade-transform" mode="out-in">
      <el-card class="el-card-define">
        <router-view :key="key" />
      </el-card>
    </transition>
  </section>
</template>

<script>
export default {
  name: 'AppMain',
  computed: {
    key() {
      return this.$route.path
    }
  }
}
</script>

<style scoped>
.app-main {
  /*50 = navbar  */
  min-height: calc(100vh - 50px);
  width: 100%;
  position: relative;
  overflow: hidden;
}
.fixed-header+.app-main {
  padding-top: 50px;
}
</style>

<style lang="scss">
// fix css style bug in open el-dialog
.el-popup-parent--hidden {
  .fixed-header {
    padding-right: 15px;
  }
}
.el-card-define {
  width: 100%;
  height: 100%;
  aspect-ratio: 16/9; /**根据屏幕比例设置自适应*/
}
</style>
