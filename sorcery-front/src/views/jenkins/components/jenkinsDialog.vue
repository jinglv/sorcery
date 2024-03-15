<template>
  <el-dialog
    :title="showTitle"
    :visible.sync="dialogVisible"
    width="800px"
    :before-close="closeDialog"
  >
    <el-form
      v-if="title == 'detail'"
      :model="jenkinsForm"
      label-width="auto"
      class="demo-ruleForm"
    >
      <el-form-item label="Jenkins名称:" prop="jenkinsName">{{ jenkinsForm.jenkinsName }}</el-form-item>
      <el-form-item label="Jenkins基础地址:" prop="jenkinsUrl">{{ jenkinsForm.jenkinsUrl }}</el-form-item>
      <el-form-item label="Jenkins认证登录用户名:" prop="jenkinsUsername">{{ jenkinsForm.jenkinsUsername }}</el-form-item>
      <el-form-item label="Jenkins认证登录密码:" prop="jenkinsPassword">{{ jenkinsForm.jenkinsPassword }}</el-form-item>
      <el-form-item type="textarea" label="备注:">{{ jenkinsForm.remark }}</el-form-item>
      <el-form-item style="text-align: right">
        <el-button @click="closeDialog">返回</el-button>
      </el-form-item>
    </el-form>
    <el-form
      v-if="title != 'detail'"
      ref="ruleForm"
      :model="jenkinsForm"
      :rules="rules"
      label-width="auto"
      class="demo-ruleForm"
    >
      <el-form-item label="Jenkins名称" prop="jenkinsName">
        <el-input v-model="jenkinsForm.jenkinsName" />
      </el-form-item>
      <el-form-item label="Jenkins基础地址" prop="jenkinsUrl">
        <el-input v-model="jenkinsForm.jenkinsUrl" />
      </el-form-item>
      <el-form-item label="Jenkins认证登录用户名" prop="jenkinsUsername">
        <el-input v-model="jenkinsForm.jenkinsUsername" />
      </el-form-item>
      <el-form-item label="Jenkins认证登录密码" prop="jenkinsPassword">
        <el-input v-model="jenkinsForm.jenkinsPassword" />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="jenkinsForm.remark" type="textarea" />
      </el-form-item>
      <el-form-item style="text-align: right">
        <el-button @click="closeDialog">取消</el-button>
        <el-button
          type="primary"
          @click="submitJenkins('ruleForm')"
        >确定</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
import { getJenkinsInfoById, createJenkinsInfo, updateJenkinsInfo } from '@/api/jenkins'

export default ({
  name: 'PorjectDialog',
  components: {},
  props: {
    title: {
      type: String,
      default: null
    },
    pid: {
      type: Number,
      default: 1
    }
  },
  data() {
    return {
      dialogVisible: true,
      showTitle: '',
      jenkinsForm: {
        jenkinsName: '',
        jenkinsUsername: '',
        jenkinsPassword: '',
        jenkinsUrl: '',
        remark: ''
      },
      rules: {
        jenkinsName: [
          { required: true, message: '请输入Jenkins名称', trigger: 'blur' }
        ],
        jenkinsUrl: [
          { required: true, message: '请输入Jenkins基础地址', trigger: 'blur' }
        ],
        jenkinsUsername: [
          { required: true, message: '请输入Jenkins认证登录用户名', trigger: 'blur' }
        ],
        jenkinsPassword: [
          { required: true, message: '请输入Jenkins认证登录密码', trigger: 'blur' }
        ]
      },
      fileList: [], // 现有图片列表
      imageUrl: '', // 图片路径
      imageVisible: false,
      disabled: false
    }
  },
  mounted() {
    if (this.title === 'create') {
      this.showTitle = '创建Jenkins信息'
    } else if (this.title === 'detail') {
      this.showTitle = 'Jenkins详情详情'
      this.initJenkinsInfoDetail()
    } else if (this.title === 'edit') {
      this.showTitle = '编辑Jenkins信息'
      this.initJenkinsInfoDetail()
    }
  },
  methods: {
    // 创建项目
    submitJenkins(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.title === 'create') {
            createJenkinsInfo(this.jenkinsForm).then((resp) => {
              // code：'00000'表示成功
              if (resp.code === '00000') {
                // 创建成功，关闭弹窗
                this.closeDialog()
                this.$message.success('Jenkins信息创建成功！')
              } else {
                this.$message.error(resp.msg)
              }
            })
          } else if (this.title === 'edit') {
            updateJenkinsInfo(this.pid, this.jenkinsForm).then((resp) => {
              if (resp.code === '00000') {
                this.closeDialog()
                this.$message.success('Jenkins信息编辑成功！')
              } else {
                this.$message.error(resp.error.data)
              }
            })
          }
        } else {
          return false
        }
      })
    },
    // 根据项目Id查询项目详情
    async initJenkinsInfoDetail() {
      const resp = await getJenkinsInfoById(this.pid)
      if (resp.code === '00000') {
        this.jenkinsForm = resp.data
        this.$message.success('Jenkins信息详情成功！')
      } else {
        this.$message.error('Jenkins信息详情失败！')
      }
    },
    // 关闭组件
    closeDialog() {
      this.$emit('cancel', {})
    }
  }
})
</script>
