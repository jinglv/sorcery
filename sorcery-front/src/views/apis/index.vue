<template>
  <div class="api-manage">
    <div style="text-align: left; margin-top: 10px;">
      <el-form :inline="true">
        <el-form-item label="项目:">
          <el-select
            v-model="projectValue"
            placeholder="请选择项目"
            @change="changeProject"
          >
            <el-option
              v-for="item in projectOption"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item style="float: right">
          <el-button type="primary" size="medium" @click="createCase()">创建接口</el-button>
        </el-form-item>
        <el-form-item style="float: right">
          <el-button type="primary" size="medium" style="margin-left: 35px" @click="createEnv()">配置环境变量</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div style="margin-top: 10px">
      <el-card style="width: 25%; float: left" class="box-card card-height" :style="conheight">
        <el-button
          class="label-title"
          type="text"
          icon="el-icon-circle-plus-outline"
          @click="createRootModule"
        >{{ projectLabel }}-模块</el-button>
        <el-tree
          :data="moduleData"
          show-checkbox
          node-key="id"
          default-expand-all
          :expand-on-click-node="false"
          @node-click="nodeClick"
        >
          <span slot-scope="{ node, data }" class="custom-tree-node">
            <span class="label-text">{{ node.label }}</span>
            <span style="float: right">
              <el-button type="text" @click="() => append(data)">
                <i class="el-icon-circle-plus-outline" />
              </el-button>
              <el-button
                type="text"
                @click="() => remove(node, data)"
              >
                <i class="el-icon-delete" />
              </el-button>
            </span>
          </span>
        </el-tree>
      </el-card>
      <div style="width: 74%; float: right">
        <el-table
          :data="apiData"
          border
          style="width: 100%"
        >
          <el-table-column prop="id" label="用例ID" width="100" />
          <el-table-column prop="apiName" label="用例名称" width="180" />
          <el-table-column prop="method" label="请求方法" width="100" />
          <el-table-column prop="apiPath" label="API Path" width="180" />
          <el-table-column prop="createTime" label="创建时间" width="180" />
          <el-table-column fixed="right" label="操作">
            <template slot-scope="scope">
              <el-button
                type="text"
                @click="caseRowApiInfo(scope.row)"
              >查看</el-button>
              <el-button
                type="text"
                @click="editRowApiInfo(scope.row)"
              >编辑</el-button>
              <el-button
                type="text"
                @click="deleteRowApiInfo(scope.row)"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!--分页-->
        <div style="width: 100%; text-align: right">
          <el-pagination
            background
            :total="total"
            :page-size="req.pageSize"
            layout="total, prev, pager, next"
            @current-change="handleCurrentChange"
          />
        </div>
        <!-- 创建模块 -->
        <module-dialog
          v-if="dialogFlag"
          :pid="projectValue"
          :plabel="projectLabel"
          :root-id="rootFlag"
          :parent-obj="parentObj"
          @cancel="closeDialog"
        />
      </div>
    </div>
  </div>
</template>
<script>
import ModuleDialog from '@/components/Apis/moduleDialog.vue'
import { projectAllList } from '@/api/projects'
import { apiListByModuleId, deleteApiInfo } from '@/api/apis'
import { getModuleTree, deleteModule } from '@/api/modules'

export default {
  name: 'ApisModule',
  components: {
    ModuleDialog
  },
  data() {
    return {
      projectValue: 1,
      projectLabel: '',
      rootFlag: true,
      projectOption: [],
      moduleData: [],
      dialogFlag: false,
      parentObj: {},
      apiData: [],
      apiSearchFrom: {
        apiName: ''
      },
      drawer: false,
      caseTitle: '',
      currentModule: 0, // 当前选中的模块
      currentCase: 0, // 当前选中的用例
      dialogEnvsFlag: false,
      req: {
        pageNum: 1,
        pageSize: 10
      },
      // 分页页数
      total: 10,
      conheight: {
        height: ''
      }
    }
  },
  mounted() {
    this.initProjectAllList()
  },
  created() {
    window.addEventListener('resize', this.getHeight)
    this.getHeight()
  },
  methods: {
    // 设置卡片高度自适应el-card
    getHeight() {
      this.conheight.height = window.innerHeight - 170 + 'px'
    },
    // 初始化项目列表
    async initProjectAllList() {
      const resp = await projectAllList()
      if (resp.code === '00000') {
        this.projectValue = resp.data[0].id
        this.projectLabel = resp.data[0].projectName
        // 在初始化项目信息，同时初始化项目下的模块信息
        this.initModuleList(this.projectValue)
        for (let i = 0; i < resp.data.length; i++) {
          this.projectOption.push({
            value: resp.data[i].id,
            label: resp.data[i].projectName
          })
        }
        // this.$message.success("查询成功！")
      } else {
        this.$message.error('查询失败！')
      }
    },
    // 修改选中项目
    changeProject(value) {
      this.projectValue = value
      this.projectLabel = this.projectOption.find(
        (item) => item.value === value
      ).label
      this.initModuleList(value)
    },
    // 查询模块列表
    async initModuleList(pid) {
      const resp = await getModuleTree(pid)
      if (resp.code === '00000') {
        this.moduleData = resp.data
        // this.$message.success("查询成功！")
      } else {
        this.$message.error('查询失败！')
      }
    },
    // 创建模块子节点
    append(data) {
      console.log('创建子节点', data)
      this.dialogFlag = true
      this.rootFlag = false
      this.parentObj = data
    },
    // 删除模块
    remove(node, data) {
      deleteModule(data.id).then((resp) => {
        if (resp.code === '00000') {
          this.$message.success('删除成功！')
          this.initModuleList(this.projectValue)
        } else {
          this.$message.error(resp.msg)
        }
      })
    },
    // 创建模块
    createRootModule() {
      this.dialogFlag = true
      this.rootFlag = true
    },
    // 创建模块关闭
    closeDialog() {
      this.dialogFlag = false
      this.initModuleList(this.projectValue)
    },
    nodeClick(data) {
      this.currentModule = data.id
      this.getApiList(data.id)
    },
    // 获取模块下的测试用例列表
    async getApiList(mid) {
      const resp = await apiListByModuleId(mid, this.req, JSON.stringify(this.apiSearchFrom))
      if (resp.code === '00000') {
        this.apiData = resp.data.list
        this.$message.success('查询成功！')
      } else {
        this.$message.error('查询失败！')
      }
    },
    // 创建测试用例
    createCase() {
      if (this.currentModule === 0) {
        this.$message.warning('请选择模块!')
      } else {
        this.currentCase = 0
        this.drawer = true
        this.caseTitle = '创建接口'
      }
    },
    // 查看用例详情
    caseRowApiInfo(row) {
      // 点击用例，获取用例id
      this.currentCase = row.id
      this.drawer = true
      this.caseTitle = '接口详情'
    },
    // 编辑测试用例
    editRowApiInfo(row) {
      // 点击用例，获取用例id
      this.currentCase = row.id
      this.drawer = true
      this.caseTitle = '编辑接口'
    },
    // 删除测试用例
    deleteRowApiInfo(row) {
      // 点击用例，获取用例id
      this.currentCase = row.id
      this.$confirm('删除接口, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        const resp = deleteApiInfo(this.currentCase)
        if (resp.success === true) {
          this.getCaseList(this.currentModule)
        } else {
          this.$message.error(resp.error.msg)
        }
        this.$message({
          type: 'success',
          message: '删除成功!'
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    // 跳转到第几页
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`)
      this.req.pageNum = val
      this.getCaseList(this.currentModule)
    },
    // 传递子组件，关闭抽屉
    closeDrawer() {
      this.drawer = false
    },
    // 传递子组件，关闭抽屉，刷新列表
    refreshCaseList() {
      console.info('currentModule', this.currentModule)
      this.getCaseList(this.currentModule)
    },
    // 创建模块
    createEnv() {
      this.dialogEnvsFlag = true
    },
    // 创建模块关闭
    closeEnvDialog() {
      this.dialogEnvsFlag = false
      this.initModuleList(this.projectValue)
    }
  }
}
</script>
<style scoped>
.custom-tree-node {
  width: 100%;
}
.label-title {
  font-family: "Liberation Mono", monospace, serif, sans-serif;
  font-size: 20px;
}
.label-text {
  font-family: "Lucida Calligraphy", cursive, serif, sans-serif;
  font-size: 18px;
  font-weight: bolder;
  float: left;
  margin-top: 5px;
}
</style>
