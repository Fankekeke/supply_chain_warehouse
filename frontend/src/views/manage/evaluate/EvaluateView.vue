<template>
  <a-modal v-model="show" title="供应商评价详情" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="moduleData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span
          style="font-size: 15px;font-weight: 650;color: #000c17">评价信息</span></a-col>
        <a-col :span="8"><b>评价年份：</b>
          {{ moduleData.year }} 年
        </a-col>
        <a-col :span="8"><b>综合评分：</b>
          {{ moduleData.score }} 分
        </a-col>
        <a-col :span="8"><b>评价时间：</b>
          {{ moduleData.createDate }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>质量评分：</b>
          {{ moduleData.qualityScore }} 分
        </a-col>
        <a-col :span="8"><b>成品评分：</b>
          {{ moduleData.costSocre }} 分
        </a-col>
        <a-col :span="8"><b>交付评分：</b>
          {{ moduleData.deliveryScore }} 分
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>服务评分：</b>
          {{ moduleData.serviceScore }} 分
        </a-col>
        <a-col :span="8"><b>创新与合作评分：</b>
          {{ moduleData.innovationScore }} 分
        </a-col>
        <a-col :span="8"><b>财务评分：</b>
          {{ moduleData.financialScore }} 分
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span
          style="font-size: 15px;font-weight: 650;color: #000c17">评价公式</span></a-col>
        <a-col :span="24">{{ moduleData.formula }}</a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span
          style="font-size: 15px;font-weight: 650;color: #000c17">详细图表</span></a-col>
        <a-col :span="24">{{ moduleData.formula }}</a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span
          style="font-size: 15px;font-weight: 650;color: #000c17">供应商评价内容</span></a-col>
        <a-col :span="24">{{ moduleData.content }}</a-col>
      </a-row>
      <br/>
    </div>
    <div style="font-size: 13px;font-family: SimHei" v-if="supplierInfo !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span
          style="font-size: 15px;font-weight: 650;color: #000c17">供应商信息</span></a-col>
        <a-col :span="8"><b>供应商名称：</b>
          {{ supplierInfo.name }}
        </a-col>
        <a-col :span="8"><b>供应商编号：</b>
          {{ supplierInfo.code }}
        </a-col>
        <a-col :span="8"><b>详细地址：</b>
          {{ supplierInfo.address }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>负责人：</b>
          {{ supplierInfo.legalPerson }}
        </a-col>
        <a-col :span="8"><b>联系电话：</b>
          {{ supplierInfo.phone }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>信用代码：</b>
          {{ supplierInfo.creditCode }}
        </a-col>
        <a-col :span="8"><b>可供类型：</b>
          {{ supplierInfo.supplyType }}
        </a-col>
        <a-col :span="8"><b>营业执照号：</b>
          {{ supplierInfo.businessLicense }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>开户银行：</b>
          {{ supplierInfo.bankName }}
        </a-col>
        <a-col :span="8"><b>银行账号：</b>
          {{ supplierInfo.bankAccount }}
        </a-col>
        <a-col :span="8"><b>创建时间：</b>
          {{ supplierInfo.createDate }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span
          style="font-size: 15px;font-weight: 650;color: #000c17">备注内容</span></a-col>
        <a-col :span="24">{{ supplierInfo.content }}</a-col>
      </a-row>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span
          style="font-size: 15px;font-weight: 650;color: #000c17">供应商图片</span></a-col>
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
      <br/>
    </div>
  </a-modal>
</template>

<script>
import moment from 'moment'
import baiduMap from '@/utils/map/baiduMap'

moment.locale('zh-cn')

function getBase64(file) {
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
  data() {
    return {
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      supplierInfo: null
    }
  },
  watch: {
    moduleShow: function (value) {
      if (value) {
        this.querySupplierDetail(this.moduleData.supplierId)
      }
    }
  },
  methods: {
    querySupplierDetail(supplierId) {
      this.$get(`/business/supplier-info/${supplierId}`).then((r) => {
        this.supplierInfo = r.data.data
        if (this.supplierInfo.images !== null && this.supplierInfo.images !== '') {
          this.imagesInit(this.supplierInfo.images)
        }
      })
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
    onClose() {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>
