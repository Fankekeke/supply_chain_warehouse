<template>
  <a-modal v-model="show" title="采购订单详情" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="moduleData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span
          style="font-size: 15px;font-weight: 650;color: #000c17">采购订单信息</span></a-col>
        <a-col :span="8"><b>订单编号：</b>
          {{ moduleData.orderCode }}
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
        <a-col :span="8"><b>型号：</b>
          {{ moduleData.model }}
        </a-col>
        <a-col :span="8"><b>采购数量：</b>
          {{ moduleData.purchaseNum }} {{ moduleData.measurementUnit }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span
          style="font-size: 15px;font-weight: 650;color: #000c17">供应商信息</span></a-col>
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
          style="font-size: 15px;font-weight: 650;color: #000c17">采购订单内容</span></a-col>
        <a-col :span="24">{{ moduleData.remark }}</a-col>
      </a-row>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span
          style="font-size: 15px;font-weight: 650;color: #000c17">物料图片</span></a-col>
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
    }
  },
  data () {
    return {
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: ''
    }
  },
  watch: {
    moduleShow: function (value) {
      if (value) {
        if (this.moduleData.materialsImages !== null && this.moduleData.materialsImages !== '') {
          this.imagesInit(this.moduleData.materialsImages)
        }
      }
    }
  },
  methods: {
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

</style>
