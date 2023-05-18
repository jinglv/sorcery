<template>
  <el-dialog
    :title="showTitle"
    :visible.sync="dialogVisible"
    width="800px"
    :before-close="closeDialog"
  >
    <el-form
      v-if="title == 'detail'"
      :model="jenkinsTaskForm"
      label-width="auto"
      class="demo-ruleForm"
    >
      <el-form-item label="Jenkins名称:" prop="jenkinsId">{{ jenkinsTaskForm.jenkinsId }}</el-form-item>
      <el-form-item label="Jenkins任务名称:" prop="jenkinsTaskName">{{ jenkinsTaskForm.jenkinsTaskName }}</el-form-item>
      <el-form-item label="Jenkins Job名称:" prop="jenkinsJobName">{{ jenkinsTaskForm.jenkinsJobName }}</el-form-item>
      <el-form-item label="command:" prop="command">{{ jenkinsTaskForm.command }}</el-form-item>
      <el-form-item type="textarea" label="备注:">{{ jenkinsTaskForm.remark }}</el-form-item>
      <el-form-item style="text-align: right">
        <el-button @click="closeDialog">返回</el-button>
      </el-form-item>
    </el-form>
    <el-form
      v-if="title != 'detail'"
      ref="ruleForm"
      :model="jenkinsTaskForm"
      :rules="rules"
      label-width="auto"
      class="demo-ruleForm"
    >
      <el-form-item label="Jenkins名称" prop="jenkinsId">
        <el-select
          v-model="jenkinsTaskForm.jenkinsId"
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
      </el-form-item>
      <el-form-item label="Jenkins任务名称:" prop="jenkinsTaskName">
        <el-input v-model="jenkinsTaskForm.jenkinsTaskName" />
      </el-form-item>
      <el-form-item label="Jenkins Job名称:" prop="jenkinsJobName">
        <el-input v-model="jenkinsTaskForm.jenkinsJobName" />
      </el-form-item>
      <el-form-item label="command:" prop="command">
        <el-input v-model="jenkinsTaskForm.command" />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="jenkinsTaskForm.remark" type="textarea" />
      </el-form-item>
      <el-form-item style="text-align: right">
        <el-button @click="closeDialog">取消</el-button>
        <el-button
          type="primary"
          @click="submitJenkinsTask('ruleForm')"
        >确定</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
import { getJenkinsInfoAll, getJenkinsTaskInfoById, createJenkinsTaskInfo, updateJenkinsTaskInfo } from '@/api/jenkins'

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
      jenkinsInfoValue: '',
      jenkinsInfoLabel: '',
      jenkinsInfoOption: [],
      jenkinsTaskForm: {
        jenkinsId: '',
        jenkinsJobName: '',
        jenkinsTaskName: '',
        command: '',
        remark: ''
      },
      rules: {
        jenkinsId: [
          { required: true, message: '请选择Jenkins任务', trigger: 'blur' }
        ],
        jenkinsTaskName: [
          { required: true, message: '请输入Jenkins名称', trigger: 'blur' }
        ],
        jenkinsJobName: [
          { required: true, message: '请输入Jenkins基础地址', trigger: 'blur' }
        ],
        command: [
          { required: true, message: '请输入Jenkins Job任务执行命令', trigger: 'blur' }
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
      this.showTitle = '创建Jenkins任务信息'
      this.initJenkinsInfoAllList()
    } else if (this.title === 'detail') {
      this.showTitle = 'Jenkins任务详情'
      this.initJenkinsTaskInfoDetail()
    } else if (this.title === 'edit') {
      this.showTitle = '编辑Jenkins任务信息'
      this.initJenkinsTaskInfoDetail()
      this.initJenkinsInfoAllList()
    }
  },
  methods: {
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
    // 修改选中Jenkins任务
    changeJenkins(value) {
      this.jenkinsInfoValue = value
      this.jenkinsInfoLabel = this.jenkinsInfoOption.find(
        (item) => item.value === value
      ).label
      this.jenkinsTaskForm.jenkinsId = value
    },
    // 创建Jenkins任务
    submitJenkinsTask(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.title === 'create') {
            createJenkinsTaskInfo(this.jenkinsTaskForm).then((resp) => {
              // code：'00000'表示成功
              if (resp.code === '00000') {
                // 创建成功，关闭弹窗
                this.closeDialog()
                this.$message.success('Jenkins任务信息创建成功!')
              } else {
                this.$message.error(resp.msg)
              }
            })
          } else if (this.title === 'edit') {
            updateJenkinsTaskInfo(this.pid, this.jenkinsTaskForm).then((resp) => {
              if (resp.code === '00000') {
                this.closeDialog()
                this.$message.success('Jenkins任务信息编辑成功!')
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
    async initJenkinsTaskInfoDetail() {
      const resp = await getJenkinsTaskInfoById(this.pid)
      if (resp.code === '00000') {
        this.jenkinsTaskForm = resp.data
        this.$message.success('Jenkins任务信息详情成功!')
      } else {
        this.$message.error('Jenkins任务信息详情失败!')
      }
    },
    // 关闭组件
    closeDialog() {
      this.$emit('cancel', {})
    }
  }
})
</script>
