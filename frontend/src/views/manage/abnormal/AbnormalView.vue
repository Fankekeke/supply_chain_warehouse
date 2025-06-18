<template>
  <a-modal v-model="show" title="异常反馈详情" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="moduleData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span
          style="font-size: 15px;font-weight: 650;color: #000c17">异常反馈信息</span></a-col>
        <a-col :span="8"><b>异常反馈名称：</b>
          {{ moduleData.name }}
        </a-col>
        <a-col :span="8"><b>异常反馈编号：</b>
          {{ moduleData.code }}
        </a-col>
        <a-col :span="8"><b>详细地址：</b>
          {{ moduleData.address }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>审核状态：</b>
          <span v-if="moduleData.status == 1" style="color: green">已通过</span>
          <span v-if="moduleData.status == 0" style="color: red">未审核</span>
        </a-col>
        <a-col :span="8"><b>负责人：</b>
          {{ moduleData.legalPerson }}
        </a-col>
        <a-col :span="8"><b>联系电话：</b>
          {{ moduleData.phone }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>信用代码：</b>
          {{ moduleData.creditCode }}
        </a-col>
        <a-col :span="8"><b>可供类型：</b>
          {{ moduleData.supplyType }}
        </a-col>
        <a-col :span="8"><b>营业执照号：</b>
          {{ moduleData.businessLicense }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>开户银行：</b>
          {{ moduleData.bankName }}
        </a-col>
        <a-col :span="8"><b>银行账号：</b>
          {{ moduleData.bankAccount }}
        </a-col>
        <a-col :span="8"><b>创建时间：</b>
          {{ moduleData.createDate }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span
          style="font-size: 15px;font-weight: 650;color: #000c17">备注内容</span></a-col>
        <a-col :span="24">{{ moduleData.content }}</a-col>
      </a-row>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span
          style="font-size: 15px;font-weight: 650;color: #000c17">异常反馈图片</span></a-col>
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
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 5px"><span
          style="font-size: 15px;font-weight: 650;color: #000c17">异常反馈位置</span></a-col>
      </a-row>
      <div>
        <a-card :bordered="false" style="height: 400px">
          <div id="areas"
               style="width: 100%;height: 350px;box-shadow: 3px 3px 3px rgba(0, 0, 0, .2);background:#ec9e3c;color:#fff"></div>
        </a-card>
      </div>
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
      previewImage: ''
    }
  },
  watch: {
    moduleShow: function (value) {
      if (value) {
        if (this.moduleData.images !== null && this.moduleData.images !== '') {
          this.imagesInit(this.moduleData.images)
        }
        setTimeout(() => {
          baiduMap.initMap('areas')
          setTimeout(() => {
            this.local(this.moduleData)
          }, 500)
        }, 200)
      }
    }
  },
  methods: {
    local(module) {
      baiduMap.clearOverlays()
      baiduMap.rMap().enableScrollWheelZoom(true)
      let point = new BMap.Point(module.longitude, module.latitude)
      baiduMap.pointAdd(point)
      baiduMap.findPoint(point, 16)
      // let driving = new BMap.DrivingRoute(baiduMap.rMap(), {renderOptions:{map: baiduMap.rMap(), autoViewport: true}});
      // driving.search(new BMap.Point(this.nowPoint.lng,this.nowPoint.lat), new BMap.Point(scenic.point.split(",")[0],scenic.point.split(",")[1]));
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
