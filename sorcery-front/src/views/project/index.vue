<template>
  <div class="project">
    <div class="filter-container">
      <el-input v-model="projectForm.projectName" placeholder="项目名称" style="width: 30%;margin-right: 5px;" class="filter-item" />
      <el-button class="filter-item" icon="el-icon-delete" @click="clearSearch()">重置</el-button>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="initProjectList()">搜索</el-button>
    </div>
    <div class="project-list" style="margin-top: 10px;">
      <el-button class="filter-item" type="primary" icon="el-icon-circle-plus-outline" @click="showDialog()">创建</el-button>
    </div>
    <div
      v-for="(item, index) in projectData"
      :key="index"
      class="text item"
    >
      <el-col :span="7" class="project-card">
        <el-card style="width: 85%; height: 55%; margin-top:10px">
          <div slot="header" class="clearfix">
            <span>{{ item.projectName }}</span>
            <div style="float: right">
              <el-dropdown>
                <span class="el-dropdown-link">
                  <i class="el-icon-setting" />
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item>
                    <el-button
                      type="text"
                      @click="showDetail(item.id)"
                    >详情</el-button>
                  </el-dropdown-item>
                  <el-dropdown-item>
                    <el-button
                      type="text"
                      @click="showEdit(item.id)"
                    >编辑</el-button>
                  </el-dropdown-item>
                  <el-dropdown-item>
                    <el-button
                      type="text"
                      @click="deleteProject(item.id)"
                    >删除</el-button>
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>
          </div>
          {{ item.address }}
          <img
            :src="item.image"
            class="image"
            style="height: 250px; width: 80%; display: block; margin: 0 auto;"
          >
        </el-card>
      </el-col>
    </div>
    <!--分页-->
    <div class="pagination-container">
      <el-pagination
        background
        layout="prev, pager, next"
        :page-size="req.pageSize"
        :total="total"
        @current-change="handleCurrentChange"
      />
    </div>
    <!--引入子组件-->
    <project-dialog
      v-if="dialogFlag"
      :title="dialogTitle"
      :pid="currentPorjectId"
      @cancel="closeDialog"
    />
  </div>
</template>

<script>
import ProjectDialog from '@/components/Projects/projectDialog.vue'
import { projectList, deleteProject } from '@/api/projects'

export default {
  name: 'Porject',
  components: {
    ProjectDialog
  },
  data() {
    return {
      dialogFlag: false,
      dialogTitle: 'create',
      currentPorjectId: '',
      projectForm: {
        projectName: ''
      },
      projectData: [],
      req: {
        pageNum: 1,
        pageSize: 6
      },
      // 分页页数
      total: 6
    }
  },
  mounted() {
    // 组件加载的时候 进行调用
    this.initProjectList()
  },
  methods: {
    // 分页查询项目列表
    async initProjectList() {
      const resp = await projectList(this.req, JSON.stringify(this.projectForm))
      console.info('请求参数', JSON.stringify(this.projectForm))
      if (resp.code === '00000') {
        this.projectData = resp.data.list
        this.total = resp.data.total
        this.$message.success('查询成功！')
      } else {
        this.$message.error('查询失败！')
      }
    },
    // 清除搜索
    clearSearch() {
      this.projectForm.projectName = ''
      this.initProjectList()
    },
    // 展示子组件
    showDialog() {
      this.currentPorjectId = 0
      this.dialogTitle = 'create'
      this.dialogFlag = true
    },
    // 关闭子组件，子组件的closeDialog回调父组件
    closeDialog() {
      this.dialogFlag = false
      this.initProjectList()
    },
    // 跳转到第几页
    handleCurrentChange(val) {
      // console.log(`当前页: ${val}`)
      this.req.pageNum = val
      this.initProjectList()
    },
    // 展示项目详情
    showDetail(id) {
      this.currentPorjectId = id
      this.dialogTitle = 'detail'
      this.dialogFlag = true
    },
    // 编辑项目
    showEdit(id) {
      this.currentPorjectId = id
      this.dialogTitle = 'edit'
      this.dialogFlag = true
    },
    // 删除项目
    async deleteProject(id) {
      await deleteProject(id).then((resp) => {
        this.$confirm('删除任务, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(() => {
          if (resp.code === '00000') {
            this.req.pageNum = 1
            this.initProjectList()
          } else {
            this.$message.error(resp.msg)
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
      })
    }
  }
}
</script>
<style scoped>
.pagination-container{
  width: 100px;
  height: 100px;
  position: absolute;
  right: 10%;
  bottom: -3%;
}
</style>
