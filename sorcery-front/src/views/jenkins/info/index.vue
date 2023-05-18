<template>
  <div class="jenkins-info">
    <div class="filter-container">
      <el-row :gutter="21">
        <el-col :span="7">
          <div class="demo-input-suffix">
            Jenkins名称:
            <el-input v-model="jenkinsForm.jenkinsName" placeholder="请输入Jenkins名称" style="width: 60%;margin-right: 5px;" class="filter-item" />
          </div>
        </el-col>
        <el-col :span="7">
          <div class="demo-input-suffix">
            Jenkins基础地址:
            <el-input v-model="jenkinsForm.jenkinsUrl" placeholder="请输入Jenkins基础地址" style="width: 60%;margin-right: 5px;" class="filter-item" />
          </div>
        </el-col>
      </el-row>
      <div style="text-align: right">
        <el-button class="filter-item" icon="el-icon-delete" @click="clearSearch()">重置</el-button>
        <el-button class="filter-item" type="primary" icon="el-icon-search" @click="initJenkinsList()">搜索</el-button>
      </div>
    </div>
    <div class="jenkins-info-create" style="margin-top: 10px;">
      <el-button class="filter-item" type="primary" icon="el-icon-circle-plus-outline" @click="showDialog()">创建</el-button>
    </div>
    <div class="jenkins-info-list" style="margin-top: 10px;">
      <el-table
        :data="jenkinsData"
        border
        style="width: 100%"
      >
        <el-table-column prop="id" label="ID" width="100" />
        <el-table-column prop="jenkinsName" label="Jenkins名称" width="250" />
        <el-table-column prop="jenkinsUrl" label="Jenkins基础地址" width="300" />
        <el-table-column prop="remark" label="备注" width="300" />
        <el-table-column prop="createTime" label="创建时间" width="200" />
        <el-table-column fixed="right" label="操作">
          <template slot-scope="scope">
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
    <jenkins-dialog
      v-if="dialogFlag"
      :title="dialogTitle"
      :pid="currentJenkinsInfo"
      @cancel="closeDialog"
    />
  </div>
</template>

<script>
import JenkinsDialog from '@/components/Jenkins/jenkinsDialog'
import { jenkinsList, deleteJenkinsInfo } from '@/api/jenkins'

export default {
  name: 'JeninsInfo',
  components: {
    JenkinsDialog
  },
  data() {
    return {
      dialogFlag: false,
      dialogTitle: 'create',
      currentJenkinsInfo: 0, // 当前选中的信息
      jenkinsForm: {
        jenkinsName: '',
        jenkinsUsername: '',
        jenkinsPassword: '',
        jenkinsUrl: '',
        remark: ''
      },
      jenkinsData: [],
      req: {
        pageNum: 1,
        pageSize: 10
      },
      // 分页页数
      total: 10
    }
  },
  mounted() {
    // 组件加载的时候 进行调用
    this.initJenkinsList()
  },
  methods: {
    // 分页查询项目列表
    async initJenkinsList() {
      const resp = await jenkinsList(this.req, JSON.stringify(this.jenkinsForm))
      if (resp.code === '00000') {
        this.jenkinsData = resp.data.list
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
            this.initJenkinsList()
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
      this.jenkinsForm.jenkinsName = ''
      this.jenkinsForm.jenkinsUrl = ''
      this.initJenkinsList()
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
      this.initJenkinsList()
    },
    // 跳转到第几页
    handleCurrentChange(val) {
      // console.log(`当前页: ${val}`)
      this.req.pageNum = val
      this.initJenkinsList()
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
