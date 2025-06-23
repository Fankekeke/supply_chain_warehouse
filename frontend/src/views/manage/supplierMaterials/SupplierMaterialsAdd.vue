<template>
  <a-drawer
    title="新增供应物料"
    :maskClosable="false"
    width=850
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="moduleAddVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
    <a-form :form="form" layout="vertical">
      <a-row :gutter="10">
        <a-col :span="12">
          <a-form-item label='供应物料'>
            <a-select v-decorator="[
              'code',
              { rules: [{ required: true, message: '请选择物料!' }] }
              ]">
              <a-select-option :value="item.code" v-for="(item, index) in materialsList" :key="index">{{ item.name }}
              </a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='批次记录'>
            <a-input v-decorator="[
            'batchRecord',
            { rules: [{ required: true, message: '请输入批次记录!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='封装'>
            <a-input v-decorator="[
            'packageSet',
            { rules: [{ required: true, message: '请输入封装!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='品牌'>
            <a-input v-decorator="[
            'brand',
            { rules: [{ required: true, message: '请输入品牌!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='可供状态'>
            <a-select v-decorator="[
              'status',
              { rules: [{ required: true, message: '请输入可供状态!' }] }
              ]">
              <a-select-option value="0">不可供</a-select-option>
              <a-select-option value="1">可供</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>

    <div class="drawer-bootom-button">
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
      previewVisible: false,
      previewImage: '',
      materialsList: []
    }
  },
  mounted () {
    this.queryMaterialsList()
  },
  methods: {
    queryMaterialsList () {
      this.$get('/business/materials-info/list').then((r) => {
        this.materialsList = r.data.data
      })
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
    picHandleChange ({fileList}) {
      this.fileList = fileList
    },
    reset () {
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      // 获取图片List
      let images = []
      this.fileList.forEach(image => {
        images.push(image.response)
      })
      this.form.validateFields((err, values) => {
        values.images = images.length > 0 ? images.join(',') : null
        if (!err) {
          this.loading = true
          this.$post('/business/supplier-materials-info', {
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
