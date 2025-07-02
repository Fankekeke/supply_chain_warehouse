<template>
  <a-modal v-model="show" title="采购订单详情" @cancel="onClose" :width="1000">
    <template slot="footer">
      <a-button key="back" @click="onSubmit(4)" type="danger">
        驳回
      </a-button>
      <a-button key="back1" @click="onSubmit(5)" type="primary">
        入库
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="moduleData !== null">
      <div style="padding-left: 24px;padding-right: 24px;margin-bottom: 50px;margin-top: 50px">
        <a-steps :current="current" progress-dot size="small">
          <a-step title="未付款" />
          <a-step title="已付款" />
          <a-step title="已发货" />
          <a-step title="检验中" />
          <a-step title="已退货" />
          <a-step title="已入库" />
        </a-steps>
      </div>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span
          class="view-title">采购订单信息</span></a-col>
        <a-col :span="8"><b>订单编号：</b>
          {{ moduleData.code }}
        </a-col>
        <a-col :span="8"><b>采购金额：</b>
          {{ moduleData.totalPrice }} 元
        </a-col>
        <a-col :span="8"><b>订单状态：</b>
          <span v-if="moduleData.status == 0">未付款</span>
          <span v-if="moduleData.status == 1">已付款</span>
          <span v-if="moduleData.status == 2">已发货</span>
          <span v-if="moduleData.status == 3">检验中</span>
          <span v-if="moduleData.status == 4">已退货</span>
          <span v-if="moduleData.status == 5">已入库</span>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>物料名称：</b>
          {{ moduleData.materialsName }}
        </a-col>
        <a-col :span="8"><b>物料编号：</b>
          {{ moduleData.materialsCode }}
        </a-col>
        <a-col :span="8"><b>类型：</b>
          {{ moduleData.type }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="16"><b>型号：</b>
          {{ moduleData.model }}
        </a-col>
        <a-col :span="8"><b>采购数量：</b>
          {{ moduleData.purchaseNum }} {{ moduleData.measurementUnit }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span
          class="view-title">供应商信息</span></a-col>
        <a-col :span="8"><b>供应商编号：</b>
          {{ moduleData.supplierCode }}
        </a-col>
        <a-col :span="8"><b>供应商名称：</b>
          {{ moduleData.supplierName }}
        </a-col>
        <a-col :span="8"><b>负责人：</b>
          {{ moduleData.chargePerson }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>联系方式：</b>
          {{ moduleData.phone }}
        </a-col>
        <a-col :span="8"><b>反馈时间：</b>
          {{ moduleData.createDate }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span
          class="view-title">采购订单内容</span></a-col>
        <a-col :span="24">{{ moduleData.remark }}</a-col>
      </a-row>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span
          class="view-title">物料图片</span></a-col>
        <a-col :span="24">
          <a-upload
            name="avatar"
            action="http://127.0.0.1:9527/file/fileUpload/"
            list-type="picture-card"
            :file-list="fileList"
            @preview="handlePreview"
            @change="picHandleChange"
          >
          </a-upload>
          <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
            <img alt="example" style="width: 100%" :src="previewImage"/>
          </a-modal>
        </a-col>
      </a-row>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span
          class="view-title">采购检测</span></a-col>
        <a-col :span="24">
          <a-form :form="form" layout="vertical">
            <a-row :gutter="10">
              <a-col :span="8">
                <a-form-item label='监察人'>
                  <a-input v-decorator="[
            'auditUser',
            { rules: [{ required: true, message: '请输入监察人!' }] }
            ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="8">
                <a-form-item label='合格率'>
                  <a-input-number style="width: 100%" v-decorator="[
            'passRate',
            { rules: [{ required: true, message: '请输入监察人!' }] }
            ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="24">
                <a-form-item label='备注'>
                  <a-textarea :rows="4" v-decorator="[
            'content'
            ]"/>
                </a-form-item>
              </a-col>
            </a-row>
          </a-form>
        </a-col>
      </a-row>
      <br/>
    </div>
  </a-modal>
</template>

<script>
import moment from 'moment'

moment.locale('zh-cn')
const formItemLayout = {
  labelCol: {span: 24},
  wrapperCol: {span: 24}
}
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}

export default {
  name: 'moduleView',
  props: {
    moduleShow: {
      type: Boolean,
      default: false
    },
    moduleData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.moduleShow
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      formItemLayout,
      form: this.$form.createForm(this),
      current: 0,
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: ''
    }
  },
  watch: {
    moduleShow: function (value) {
      if (value) {
        this.current = this.moduleData.status
        if (this.moduleData.materialsImages !== null && this.moduleData.materialsImages !== '') {
          this.imagesInit(this.moduleData.materialsImages)
        }
      }
    }
  },
  methods: {
    onSubmit (status) {
      this.form.validateFields((err, values) => {
        values.id = this.moduleData.id
        values.status = status
        if (!err) {
          this.loading = true
          this.$put('/business/order-info', {
            ...values
          }).then((r) => {
            if (status != 4) {
              this.$get('/business/order-info/orderPutStock', {
                orderId: this.moduleData.id
              }).then((r) => {
                this.$emit('success')
              })
            } else {
              this.$emit('success')
            }
          }).catch(() => {
            this.loading = false
          })
        }
      })
    },
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
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
    onClose () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>
/* 修改步骤条的未完成状态颜色 */
.ant-steps-item-wait .ant-steps-item-icon {
  background-color: #1890ff; /* 蓝色 */
}

/* 修改步骤条的已完成状态颜色 */
.ant-steps-item-finish .ant-steps-item-icon {
  background-color: #52c41a; /* 绿色 */
}

/* 修改步骤条的错误状态颜色 */
.ant-steps-item-error .ant-steps-item-icon {
  background-color: #ff4d4f; /* 红色 */
}
.ant-steps .ant-steps-item .ant-steps-item-content {
  font-size: 16px; /* 调整字体大小 */
  line-height: 24px; /* 调整行高 */
}
.ant-steps {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 16px;
  border-radius: 8px;
}
.ant-steps {
  background-color: #f9f9f9; /* 浅灰色背景 */
  border-radius: 8px;
}
</style>
