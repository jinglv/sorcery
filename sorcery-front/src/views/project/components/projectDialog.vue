<template>
  <el-dialog
    :title="showTitle"
    :visible.sync="dialogVisible"
    width="500px"
    :before-close="closeDialog"
  >
    <el-form
      v-if="title == 'detail'"
      ref="ruleForm"
      :model="projectForm"
      :rules="rules"
      label-width="100px"
      class="demo-ruleForm"
    >
      <el-form-item label="项目名称" prop="projectName">{{ projectForm.projectName }}</el-form-item>
      <el-form-item label="项目描述" prop="description">{{ projectForm.description }}</el-form-item>
      <el-form-item label="图片" prop="image">
        <div id="image">
          <el-image
            style="width: 100px; height: 100px"
            :src="imageUrl"
          />
        </div>
      </el-form-item>
      <el-form-item style="text-align: right">
        <el-button @click="closeDialog">返回</el-button>
      </el-form-item>
    </el-form>
    <el-form
      v-if="title != 'detail'"
      ref="ruleForm"
      :model="projectForm"
      :rules="rules"
      label-width="100px"
      class="demo-ruleForm"
    >
      <el-form-item label="项目名称" prop="projectName">
        <el-input v-model="projectForm.projectName" />
      </el-form-item>
      <el-form-item label="项目描述" prop="description">
        <el-input v-model="projectForm.description" type="textarea" />
      </el-form-item>
      <el-form-item label="图片" prop="desc">
        <div id="image">
          <el-upload
            action="#"
            :before-upload="beforeUpload"
            :http-request="httpRequest"
            list-type="picture-card"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :file-list="fileList"
          >
            <i class="el-icon-plus" />
          </el-upload>
          <el-dialog :visible.sync="imageVisible">
            <img width="100%" :src="imageUrl" alt="">
          </el-dialog>
        </div>
      </el-form-item>
      <el-form-item style="text-align: right">
        <el-button @click="closeDialog">取消</el-button>
        <el-button
          type="primary"
          @click="submitProject('ruleForm')"
        >确定</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
import { getProject, createProject, updateProject } from '@/api/projects'
import { uploadFile } from '@/api/uploads'

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
      projectForm: {
        // 项目名称
        projectName: '',
        // 项目描述
        description: '',
        // 上传图片
        image: ''
      },
      rules: {
        projectName: [
          { required: true, message: '请输入项目的名称', trigger: 'blur' }
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
      this.showTitle = '创建项目'
    } else if (this.title === 'detail') {
      this.showTitle = '项目详情'
      this.initProjectDetail()
    } else if (this.title === 'edit') {
      this.showTitle = '编辑项目'
      this.initProjectDetail()
    }
  },
  methods: {
    // 创建项目
    submitProject(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.title === 'create') {
            createProject(this.projectForm).then((resp) => {
              // code：'00000'表示成功
              if (resp.code === '00000') {
                // 创建成功，关闭弹窗
                this.closeDialog()
                this.$message.success('项目创建成功！')
              } else {
                this.$message.error(resp.msg)
              }
            })
          } else if (this.title === 'edit') {
            updateProject(this.pid, this.projectForm).then((resp) => {
              if (resp.code === '00000') {
                this.closeDialog()
                this.$message.success('项目编辑成功！')
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
    async initProjectDetail() {
      const resp = await getProject(this.pid)
      if (resp.code === '00000') {
        this.projectForm = resp.data
        this.fileList.push({
          name: resp.data.image,
          url: resp.data.image
        })
        this.imageUrl = resp.data.image
        this.$message.success('项目详情成功！')
      } else {
        this.$message.error('项目详情失败！')
      }
    },
    // 删除图片
    handleRemove(file) {
      console.log('删除', file)
    },
    // 预览图片
    handlePreview(file, fileList) {
      console.log('上传成功', file, fileList)
      this.imageUrl = file.url
      this.imageVisible = true
    },
    // 上传文件
    async beforeUpload(file) {
      console.log('上传文件对象', file)
      const fb = new FormData()
      fb.append('file', file)
      const resp = await uploadFile(fb)
      console.log('resp-->', resp)
      if (resp.code === '00000') {
        // 获取文件名
        this.projectForm.image = resp.data
        // 拼接静态文件路径，用于图片展示
        const imagePath = resp.data
        const imageInfo = {
          name: file.name,
          url: imagePath
        }
        this.fileList.push(imageInfo)
        this.$message.success('上传成功！')
      } else {
        console.log('上传失败', resp)
        this.$message.error(resp.error.message)
      }
    },
    // 关闭组件
    closeDialog() {
      this.$emit('cancel', {})
    },
    // 覆盖默认的上传行为，可以自定义上传的实现，避免使用框架自动上传功能
    httpRequest() {}
  }
})
</script>
