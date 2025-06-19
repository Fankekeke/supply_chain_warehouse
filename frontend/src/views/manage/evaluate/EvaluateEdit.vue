<template>
  <a-drawer
    title="修改供应商评价"
    :maskClosable="false"
    width=850
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="moduleEditVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
    <a-form :form="form" layout="vertical">
      <a-row :gutter="10">
        <a-col :span="12">
          <a-form-item label='评价供应商'>
            <a-select v-decorator="[
              'supplierId',
              { rules: [{ required: true, message: '请选择评价供应商!' }] }
              ]">
              <a-select-option :value="item.id" v-for="(item, index) in supplierList" :key="index">{{ item.name }}
              </a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='评价年份' v-bind="formItemLayout">
            <a-select disabled v-decorator="[
            'year',
            { rules: [{ required: true, message: '请输入评价年份!' }] }
            ]">
              <a-select-option value="2022">2022</a-select-option>
              <a-select-option value="2023">2023</a-select-option>
              <a-select-option value="2024">2024</a-select-option>
              <a-select-option value="2025">2025</a-select-option>
              <a-select-option value="2026">2026</a-select-option>
              <a-select-option value="2027">2027</a-select-option>
              <a-select-option value="2028">2028</a-select-option>
              <a-select-option value="2029">2029</a-select-option>
              <a-select-option value="2030">2030</a-select-option>
              <a-select-option value="2031">2031</a-select-option>
              <a-select-option value="2032">2032</a-select-option>
              <a-select-option value="2033">2033</a-select-option>
              <a-select-option value="2034">2034</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='质量评分' v-bind="formItemLayout">
            <a-input-number style="width: 100%"
                            v-decorator="[
            'qualityScore',
            { rules: [{ required: true, message: '请输入质量评分!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='成品评分' v-bind="formItemLayout">
            <a-input-number style="width: 100%"
                            v-decorator="[
            'costSocre',
            { rules: [{ required: true, message: '请输入成品评分!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='交付评分' v-bind="formItemLayout">
            <a-input-number style="width: 100%"
                            v-decorator="[
            'deliveryScore',
            { rules: [{ required: true, message: '请输入交付评分!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='服务评分' v-bind="formItemLayout">
            <a-input-number style="width: 100%"
                            v-decorator="[
            'serviceScore',
            { rules: [{ required: true, message: '请输入服务评分!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='创新与合作评分' v-bind="formItemLayout">
            <a-input-number style="width: 100%"
                            v-decorator="[
            'innovationScore',
            { rules: [{ required: true, message: '请输入创新与合作评分!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='财务评分' v-bind="formItemLayout">
            <a-input-number style="width: 100%"
                            v-decorator="[
            'financialScore',
            { rules: [{ required: true, message: '请输入财务评分!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='备注内容' v-bind="formItemLayout">
            <a-textarea :rows="6" v-decorator="[
            'content'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='供应商评价图片' v-bind="formItemLayout">
            <a-upload
              name="avatar"
              action="http://127.0.0.1:9527/file/fileUpload/"
              list-type="picture-card"
              :file-list="fileList"
              @preview="handlePreview"
              @change="picHandleChange"
            >
              <div v-if="fileList.length < 8">
                <a-icon type="plus"/>
                <div class="ant-upload-text">
                  Upload
                </div>
              </div>
            </a-upload>
            <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
              <img alt="example" style="width: 100%" :src="previewImage"/>
            </a-modal>
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

function getBase64(file) {
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
  name: 'moduleEdit',
  props: {
    moduleEditVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.moduleEditVisiable
      },
      set: function () {
      }
    }
  },
  data() {
    return {
      rowId: null,
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      supplierList: []
    }
  },
  mounted() {
    this.querySupplier()
  },
  methods: {
    querySupplier() {
      this.$get('/business/supplier-info/list').then((r) => {
        this.supplierList = r.data
      })
    },
    handleCancel() {
      this.previewVisible = false
    },
    async handlePreview(file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    picHandleChange({fileList}) {
      this.fileList = fileList
    },
    imagesInit(images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    setFormValues({...module}) {
      this.rowId = module.id
      let fields = ['supplierId', 'year', 'qualityScore', 'costSocre', 'deliveryScore', 'serviceScore', 'innovationScore', 'financialScore', 'score', 'content']
      let obj = {}
      Object.keys(module).forEach((key) => {
        if (key === 'images') {
          this.fileList = []
          this.imagesInit(module['images'])
        }
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          obj[key] = module[key]
        }
      })
      this.form.setFieldsValue(obj)
    },
    reset() {
      this.loading = false
      this.form.resetFields()
    },
    onClose() {
      this.reset()
      this.$emit('close')
    },
    handleSubmit() {
      // 获取图片List
      let images = []
      this.fileList.forEach(image => {
        if (image.response !== undefined) {
          images.push(image.response)
        } else {
          images.push(image.name)
        }
      })
      this.form.validateFields((err, values) => {
        values.id = this.rowId
        values.images = images.length > 0 ? images.join(',') : null
        if (!err) {
          this.loading = true
          this.$put('/business/supplier-evaluate', {
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
