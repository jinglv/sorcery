<template>
  <div class="jenkins-task">
    <div class="filter-container">
      <el-row :gutter="20">
        <el-col :span="6">
          <div class="demo-input-suffix">
            Jenkins名称:
            <el-select
              v-model="jenkinsInfoValue"
              placeholder="请选择Jenkins"
              @change="changeJenkins"
            >
              <el-option
                v-for="item in jenkinsInfoOption"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="demo-input-suffix">
            Jenkins任务名称:
            <el-input v-model="jenkinsTaskForm.jenkinsTaskName" placeholder="请输入Jenkins任务名称" style="width: 60%;margin-right: 5px;" class="filter-item" />
          </div>
        </el-col>
        <el-col :span="8">
          <div class="demo-input-suffix">
            Jenkins Job名称:
            <el-input v-model="jenkinsTaskForm.jenkinsJobName" placeholder="请输入Jenkins Job名称" style="width: 60%;margin-right: 5px;" class="filter-item" />
          </div>
        </el-col>
      </el-row>
      <div style="text-align: right">
        <el-button class="filter-item" icon="el-icon-delete" @click="clearSearch()">重置</el-button>
        <el-button class="filter-item" type="primary" icon="el-icon-search" @click="initJenkinsTaskList()">搜索</el-button>
      </div>
    </div>
    <div class="jenkins-info-create" style="margin-top: 10px;">
      <el-button class="filter-item" type="primary" icon="el-icon-circle-plus-outline" @click="showDialog()">创建</el-button>
    </div>
    <div class="jenkins-info-list" style="margin-top: 10px;">
      <el-table
        :data="jenkinsTaskData"
        border
        style="width: 100%"
      >
        <el-table-column prop="id" label="ID" width="100" />
        <el-table-column prop="jenkinsTaskName" label="Jenkins任务名称" width="auto" />
        <el-table-column prop="jenkinsName" label="Jenkins名称" width="auto" />
        <el-table-column prop="jenkinsJobName" label="Jenkins Job名称" width="auto" />
        <el-table-column prop="remark" label="备注" width="auto" />
        <el-table-column prop="createTime" label="创建时间" width="auto" />
        <el-table-column fixed="right" label="操作">
          <template slot-scope="scope">
            <el-button
              type="text"
              @click="viewRowJenkinsInfo(scope.row)"
            >执行任务</el-button>
            <el-button
              type="text"
              @click="viewRowJenkinsInfo(scope.row)"
            >查看</el-button>
            <el-button
              type="text"
              @click="editRowJenkinsInfo(scope.row)"
            >编辑</el-button>
            <el-button
              type="text"
              @click="deleteRowJenkinsInfo(scope.row)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!--分页-->
    <div style="width: 100%; text-align: right">
      <el-pagination
        background
        layout="prev, pager, next"
        :page-size="req.pageSize"
        :total="total"
        @current-change="handleCurrentChange"
      />
    </div>
    <!--引入子组件-->
    <jenkins-task-dialog
      v-if="dialogFlag"
      :title="dialogTitle"
      :pid="currentJenkinsInfo"
      @cancel="closeDialog"
    />
  </div>
</template>

<script>
import { getJenkinsInfoAll, jenkinsTaskList, deleteJenkinsInfo } from '@/api/jenkins'
import JenkinsTaskDialog from '@/views/jenkins/components/jenkinsTaskDialog.vue'

export default {
  name: 'JeninsInfo',
  components: {
    JenkinsTaskDialog
  },
  data() {
    return {
      jenkinsInfoValue: '',
      jenkinsInfoLabel: '',
      jenkinsInfoOption: [],
      dialogFlag: false,
      dialogTitle: 'create',
      currentJenkinsInfo: 0, // 当前选中的信息
      jenkinsTaskForm: {
        jenkinsId: '',
        jenkinsJobName: '',
        jenkinsTaskName: '',
        command: '',
        remark: ''
      },
      jenkinsTaskData: [],
      req: {
        pageNum: 1,
        pageSize: 10
      },
      // 分页页数
      total: 10
    }
  },
  mounted() {
    this.initJenkinsInfoAllList()
    // 组件加载的时候 进行调用
    this.initJenkinsTaskList()
  },
  methods: {
    // 初始化项目列表
    async initJenkinsInfoAllList() {
      const resp = await getJenkinsInfoAll()
      if (resp.code === '00000') {
        for (let i = 0; i < resp.data.length; i++) {
          this.jenkinsInfoOption.push({
            value: resp.data[i].id,
            label: resp.data[i].jenkinsName
          })
        }
        // this.$message.success("查询成功！")
      } else {
        this.$message.error('查询失败！')
      }
    },
    // 修改选中项目
    changeJenkins(value) {
      this.jenkinsInfoValue = value
      this.jenkinsInfoLabel = this.jenkinsInfoOption.find(
        (item) => item.value === value
      ).label
      this.jenkinsTaskForm.jenkinsId = value
    },
    // 分页查询项目列表
    async initJenkinsTaskList() {
      const resp = await jenkinsTaskList(this.req, JSON.stringify(this.jenkinsTaskForm))
      if (resp.code === '00000') {
        this.jenkinsTaskData = resp.data.list
        this.total = resp.data.total
        this.$message.success('查询成功！')
      } else {
        this.$message.error('查询失败！')
      }
    },
    // 查看Jenkins信息详情
    viewRowJenkinsInfo(row) {
      // 点击用例，获取用例id
      this.currentJenkinsInfo = row.id
      this.dialogFlag = true
      this.dialogTitle = 'detail'
    },
    // 编辑Jenkins信息
    editRowJenkinsInfo(row) {
      // 点击用例，获取用例id
      this.currentJenkinsInfo = row.id
      this.dialogFlag = true
      this.dialogTitle = 'edit'
    },
    // 删除Jenkins信息
    async deleteRowJenkinsInfo(row) {
      await deleteJenkinsInfo(row.id).then((resp) => {
        this.$confirm('删除任务, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(() => {
          if (resp.code === '00000') {
            this.req.pageNum = 1
            this.initJenkinsTaskList()
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
    },
    // 清除搜索
    clearSearch() {
      this.jenkinsInfoValue = ''
      this.jenkinsTaskForm.jenkinsId = ''
      this.jenkinsTaskForm.jenkinsJobName = ''
      this.jenkinsTaskForm.jenkinsTaskName = ''
      this.initJenkinsTaskList()
    },
    // 展示子组件
    showDialog() {
      this.currentJenkinsInfo = 0
      this.dialogTitle = 'create'
      this.dialogFlag = true
    },
    // 关闭子组件，子组件的closeDialog回调父组件
    closeDialog() {
      this.dialogFlag = false
      this.initJenkinsTaskList()
    },
    // 跳转到第几页
    handleCurrentChange(val) {
      // console.log(`当前页: ${val}`)
      this.req.pageNum = val
      this.initJenkinsTaskList()
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
    }
  }
}
</script>
