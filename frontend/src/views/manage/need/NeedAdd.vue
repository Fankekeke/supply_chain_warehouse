<template>
  <a-drawer
    title="新增采购需求"
    :maskClosable="false"
    width=650
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="moduleAddVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
    <a-form :form="form" layout="vertical">
      <a-row :gutter="10">
        <a-col :span="24">
          <a-form-item label='需求发起人'>
            <a-input v-decorator="[
            'createBy',
            { rules: [{ required: true, message: '请输入需求发起人!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='采购内容' v-bind="formItemLayout">
            <a-textarea :rows="6" v-decorator="[
            'content',
            { rules: [{ required: true, message: '请输入采购内容!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='采购文件上传' v-bind="formItemLayout">
            <a-upload-dragger
              name="file"
              :multiple="true"
              accept=".xls, .xlsx"
              action="http://127.0.0.1:9527/business/warehouse-info/import"
              @change="handleChange"
            >
              <p class="ant-upload-drag-icon">
                <a-icon type="inbox" />
              </p>
              <p class="ant-upload-text">
                Click or drag file to this area to upload
              </p>
              <p class="ant-upload-hint">
                Support for a single or bulk upload
              </p>
            </a-upload-dragger>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>

    <div class="drawer-bootom-button">
      <a-button @click="download" type="primary" style="margin-right: .8rem">下载</a-button>
      <a-popconfirm title="确定放弃编辑？" @confirm="onClose" okText="确定" cancelText="取消">
        <a-button style="margin-right: .8rem">取消</a-button>
      </a-popconfirm>
      <a-button @click="handleSubmit" type="primary" :loading="loading">提交</a-button>
    </div>
  </a-drawer>
</template>

<script>
import {mapState} from 'vuex'

function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}

const formItemLayout = {
  labelCol: {span: 24},
  wrapperCol: {span: 24}
}
export default {
  name: 'moduleAdd',
  props: {
    moduleAddVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.moduleAddVisiable
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      fileList: [],
      goodsList: [],
      previewVisible: false,
      previewImage: ''
    }
  },
  methods: {
    handleChange ({file}) {
      if (file.response !== undefined) {
        console.log(file.response.code)
        console.log(file.status)
        if (file.status === 'error') {
          this.$message.error(file.response.message)
          return false
        }
        if (file.response.code === 500 && file.status === 'done') {
          this.$message.error(file.response.msg)
        } else if (file.response.code === 0 && file.status === 'done') {
          console.log(file.response.data)
          this.goodsList = file.response.data
        }
      }
    },
    download () {
      window.location.href = 'http://127.0.0.1:9527/imagesWeb/导入模板.xlsx'
    },
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    picHandleChange ({ fileList }) {
      this.fileList = fileList
    },
    reset () {
      this.goodsList = []
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      if (this.goodsList.length === 0) {
        this.$message.error('采购物料不能为空')
        return false
      }
      this.form.validateFields((err, values) => {
        if (!err) {
          this.loading = true
          let materialsInfoList = []
          this.goodsList.forEach(item => {
            materialsInfoList.push({
              code: item.code,
              minValue: item.minValue
            })
          })
          values.materialsInfoList = JSON.stringify(materialsInfoList)
          this.$post('/business/purchase-need-info', {
            ...values
          }).then((r) => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
