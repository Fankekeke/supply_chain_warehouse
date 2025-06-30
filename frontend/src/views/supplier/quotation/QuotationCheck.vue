<template>
  <a-modal v-model="show" title="采购报价" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="moduleData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span
          class="view-title">采购信息</span></a-col>
        <a-col :span="8"><b>采购编号：</b>
          {{ moduleData.purchaseCode }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>物料名称：</b>
          {{ moduleData.materialsName }}
        </a-col>
        <a-col :span="8"><b>采购数量：</b>
          {{ moduleData.purchaseNum }}{{ moduleData.measurementUnit }}
        </a-col>
        <a-col :span="8"><b>类型：</b>
          {{ moduleData.type }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="24"><b>型号：</b>
          {{ moduleData.model }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="moduleData.status == 0">
        <a-col style="margin-bottom: 15px"><span
          class="view-title">报价接收</span></a-col>
        <a-col :span="24">
          <a-result :title="'报价截至日期 ' + moduleData.endDate">
            <template #icon>
              <a-icon type="smile" theme="twoTone" />
            </template>
            <template #extra>
              <a-button
                type="primary"
                :disabled="isExpired"
                @click="acceptQuotation"
              >
                {{ isExpired ? '已过期' : '接收' }}
              </a-button>
            </template>
          </a-result>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="moduleData.status == 1">
        <a-col style="margin-bottom: 15px"><span
          class="view-title">报价提交</span></a-col>
        <a-col :span="24">
          <a-result title="填写报价">
            <template #icon>
              <a-icon type="insurance" theme="twoTone" />
            </template>
            <template #extra>
              <a-input-number style="width: 150px;margin-bottom: 30px" v-model="totalPrice"/>
              <a-button
                type="primary"
                :disabled="isExpired"
                @click="setQuotationPrice"
              >
                提交
              </a-button>
            </template>
          </a-result>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="moduleData.status == 2">
        <a-col style="margin-bottom: 15px"><span
          class="view-title">报价内容</span></a-col>
        <a-col :span="24">
          <a-result :title="'报价已提交  ￥' + moduleData.totalPrice">
            <template #icon>
              <a-icon type="calendar" theme="twoTone" />
            </template>
          </a-result>
        </a-col>
      </a-row>
      <br/>
    </div>
  </a-modal>
</template>

<script>
import moment from 'moment'

moment.locale('zh-cn')

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
    },
    isExpired () {
      if (!this.moduleData || !this.moduleData.endDate) return false
      return moment().isAfter(moment(this.moduleData.endDate, 'YYYY-MM-DD HH:mm:ss'))
    }
  },
  data () {
    return {
      totalPrice: null,
      content: '',
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: ''
    }
  },
  watch: {
    moduleShow: function (value) {
      if (value) {
      }
    }
  },
  methods: {
    setQuotationPrice () {
      if (!this.totalPrice) {
        this.$message.warning('请填写报价金额')
        return false
      }
      this.moduleData.totalPrice = this.totalPrice
      this.moduleData.num = this.moduleData.purchaseNum
      this.$put('/business/purchase-quotation-info/setQuotation', this.moduleData).then(res => {
        this.$emit('success')
      })
    },
    acceptQuotation () {
      if (this.isExpired) {
        this.$message.warning('该报价已过期，无法接收')
        return
      }
      // 执行接收报价逻辑
      this.$get('/business/purchase-quotation-info/setQuotationStatus', {
        id: this.moduleData.id,
        status: 1
      }).then(res => {
        this.$emit('success')
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
 >>> .ant-list-item {
   padding: 0;
 }
</style>
