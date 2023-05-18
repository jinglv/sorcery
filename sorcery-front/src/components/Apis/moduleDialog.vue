<template>
  <el-dialog
    :title="showTitle"
    :visible.sync="dialogVisible"
    width="500px"
    :before-close="closeDialog"
  >
    <el-form
      ref="ruleForm"
      :model="moduleForm"
      :rules="rules"
      label-width="100px"
      class="demo-ruleForm"
    >
      <el-form-item label="项目名称" prop="name">
        <el-input v-model="plabel" disabled />
      </el-form-item>
      <el-form-item v-if="parentObj.label !== undefined" label="父节点">
        <el-input v-model="parentObj.label" disabled />
      </el-form-item>
      <el-form-item label="模块名称" prop="moduleName">
        <el-input v-model="moduleForm.moduleName" />
      </el-form-item>
      <el-form-item style="text-align: right">
        <el-button @click="closeDialog">取消</el-button>
        <el-button
          type="primary"
          @click="submitForm('ruleForm')"
        >确定</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
  <!-- <el-button type="danger" @click="closeDialog">关闭</el-button> -->
</template>

<script>
import { createModule } from '@/api/modules'

export default {
  name: 'ModuleDialog',
  components: {},
  props: {
    pid: {
      type: Number,
      default: 1
    },
    plabel: {
      type: String,
      default: null
    },
    rootId: {
      type: Boolean,
      default: true
    },
    parentObj: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      showTitle: '',
      updateURL: '',
      dialogVisible: true,
      moduleForm: {
        moduleName: '',
        projectId: 0,
        moduleParentId: 0
      },
      rules: {
        moduleName: [
          { required: true, message: '请输入模块的名称', trigger: 'blur' }
        ]
      },
      fileList: [],
      imageUrl: '',
      imageVisible: false,
      disabled: false
    }
  },
  mounted() {
    this.moduleForm.projectId = this.pid
    if (this.rootId === true) {
      this.showTitle = '创建根节点'
    } else {
      this.showTitle = '创建子节点'
      this.moduleForm.moduleParentId = this.parentObj.id
    }
  },
  methods: {
    closeDialog() {
      console.log('closeDialog')
      this.$emit('cancel', {})
    },
    // 创建模块
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          createModule(this.moduleForm).then((resp) => {
            console.info('响应结果：', resp)
            if (resp.code === '00000') {
              this.closeDialog()
              this.$message.success('创建成功！')
            } else {
              this.$message.error(resp.msg)
            }
          })
        }
      })
    }
  }
}
</script>
<style scoped>
#image {
text-align: left;
}
</style>
