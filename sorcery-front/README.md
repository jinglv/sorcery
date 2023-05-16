# vue-admin-template

## Build Setup

```bash
# clone the project（克隆项目）
git clone https://github.com/PanJiaChen/vue-admin-template.git

# enter the project directory（进行项目目录）
cd vue-admin-template

# install dependency（安装依赖） 建议：建议不要直接使用 cnpm 安装依赖，会有各种诡异的 bug。可以通过如下操作解决 npm 下载速度慢的问题
npm install / npm install --registry=https://registry.npm.taobao.org

# develop（启动服务）
npm run dev
```

This will automatically open（浏览器访问） http://localhost:9528

## Build(发布)

```bash
# build for test environment（构建测试环境）
npm run build:stage

# build for production environment（构建生产环境）
npm run build:prod
```

## Advanced(其他)

```bash
# preview the release environment effect（预览发布环境效果）
npm run preview

# preview the release environment effect + static resource analysis（预览发布环境效果 + 静态资源分析）
npm run preview -- --report

# code format check（代码格式检查）
npm run lint

# code format check and auto fix（代码格式检查并自动修复）
npm run lint -- --fix
```

# 使用icon

- https://www.iconfont.cn/

# 问题
1. npm run dev启动报错：`Error: error:0308010C:digital envelope routines::unsupported`，解决方法：执行`export NODE_OPTIONS=--openssl-legacy-provider`