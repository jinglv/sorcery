<template>
  <div class="apiInfo" :style="{'max-height': timeLineHeight +'px'}" style="margin-left: 10px; margin-right: 10px; overflow-y:scroll;">
    <el-container>
      <el-header height="40px" class="iconfont">&#xe603;接口请求</el-header>
      <el-main class="api-request">
        <div class="div-line" style="height: 50px">
          <span style="float: left;" class="iconfont">&#xe635;请求：</span>
          <el-select
            placeholder="请求方法"
            style="width: 10%; float: left"
          >
            <el-option
              v-for="item in methodOption"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
          <el-select
            v-model="evnValue"
            placeholder="请选执行择环境"
            style="width: 15%; float: left"
            @change="changeEnvs"
          />
          <el-input
            placeholder="请输入接口路径"
            style="width: 65%; float: left"
          />
          <el-button
            type="primary"
          >发送</el-button>
        </div>
      </el-main>
    </el-container>
    <el-container>
      <el-header height="40px" class="iconfont">&#xe603;请求参数</el-header>
      <el-main class="request-params">具体内容</el-main>
    </el-container>
    <el-container>
      <el-header height="40px" class="iconfont">&#xe603;响应内容</el-header>
      <el-main class="response-content">具体内容</el-main>
    </el-container>
    <el-container>
      <el-header height="40px" class="iconfont">&#xe603;断言/提取器</el-header>
      <el-main class="extract-content">具体内容</el-main>
    </el-container>
    <div class="api-name" style="height: 50px;">
      <span style="float: left;" class="iconfont">&#xe635;接口名称：</span>
      <el-input placeholder="请输入接口名称" size="small" style="width: 60%; float: left;" />
      <el-button type="primary" size="small" style="float: left;">保存</el-button>
    </div>
  </div>
</template>

<script>

export default {
  name: 'ApiInfoDialog',
  props: {
    title: {
      type: String,
      default: null
    },
    mid: {
      type: Number,
      default: 1
    }
  },
  data() {
    return {
      timeLineHeight: '',
      methodOption: [
        {
          value: 'get',
          label: 'GET'
        },
        {
          value: 'post',
          label: 'POST'
        },
        {
          value: 'put',
          label: 'PUT'
        },
        {
          value: 'delete',
          label: 'DELETE'
        }
      ]
    }
  },
  mounted() {
    // 窗口滑动
    this.timeLineHeight = document.documentElement.clientHeight - 150
    window.onresize = () => { this.timeLineHeight = document.documentElement.clientHeight - 150 }
  }
}
</script>

<style scoped>
 .el-header {
    color: #333;
    text-align: left;
    line-height: 40px;
    font-family: "Liberation Mono", monospace, serif, sans-serif;
    font-size: 16px;
 }
 .api-request {
    text-align: center;
    line-height: 40px;
 }
 .request-params {
    text-align: center;
    line-height: 160px;
 }
 .response-content {
    text-align: center;
    line-height: 160px;
 }
 .extract-content {
    text-align: center;
    line-height: 160px;
 }
 .api-name {
    display: flex;
    align-items: center; /* 垂直居中 */
 }
</style>
