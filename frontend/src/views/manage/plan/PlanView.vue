<template>
  <a-modal v-model="show" title="采购计划详情" @cancel="onClose" :width="1200">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="moduleData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span
          class="view-title">采购需求单号</span></a-col>
        <a-col :span="8"><b>订单编号：</b>
          {{ moduleData.purchaseCode }}
        </a-col>
        <a-col :span="8"><b>采购金额：</b>
          <span v-if="moduleData.status == 0">待下发</span>
          <span v-if="moduleData.status == 1">计划下发</span>
          <span v-if="moduleData.status == 2">采购询价</span>
          <span v-if="moduleData.status == 3">生成订单</span>
          <span v-if="moduleData.status == 4">配送中</span>
          <span v-if="moduleData.status == 5">已完成</span>
        </a-col>
        <a-col :span="8"><b>采购数量：</b>
          {{ moduleData.purchaseNum }}{{ moduleData.measurementUnit }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>物料名称：</b>
          {{ moduleData.materialsName }}
        </a-col>
        <a-col :span="8"><b>物料编号：</b>
          {{ moduleData.code }}
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
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="moduleData.status == 0">
        <a-col style="margin-bottom: 15px"><span
          class="view-title">匹配供应商下发</span></a-col>
        <a-col :span="24">
          <a-result title="一键下发采购计划">
            <template #extra>
              <a-date-picker show-time @change="handleDateChange"/>
              <a-button key="console" type="primary" @click="handleSubmit">
                Go Console
              </a-button>
            </template>
          </a-result>
          <a-row :gutter="10">
            <a-col :span="6" style="margin-bottom: 10px" v-for="(item, index) in supplierList" :key="index">
              <a-card hoverable style="width: 100%">
                <a-card-meta :title="item.name">
                  <div slot="description">
                    <p style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">{{ item.supplyType }} {{ item.phone }}</p>
                  </div>
                  <a-avatar
                    slot="avatar"
                    shape="square"
                    :style="{ verticalAlign: 'middle' }"
                  >
                    {{ item.name ? item.name.charAt(0) : '' }}
                  </a-avatar>
                </a-card-meta>
              </a-card>
            </a-col>
          </a-row>
        </a-col>
      </a-row>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="moduleData.status != 0">
        <a-col style="margin-bottom: 15px"><span
          class="view-title">采购计划内容</span></a-col>
        <a-col :span="24">
          <a-table :columns="columns" :data-source="planList" :pagination="false">
            <template slot="operation" slot-scope="text, record" v-if="moduleData.status == 1">
              <a-icon type="container" theme="twoTone" twoToneColor="#4a9ff5" @click="checkOrder(record)" title="下发订单" v-if="record.totalPrice != null"></a-icon>
            </template>
          </a-table>
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
    columns () {
      return [{
        endDate: null,
        title: '下发供应商',
        dataIndex: 'supplierName',
        ellipsis: true,
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '供应商图片',
        dataIndex: 'supplierImages',
        customRender: (text, record, index) => {
          if (!record.supplierImages) return <a-avatar shape="square" icon="user"/>
          return <a-popover>
            <template slot="content">
              <a-avatar shape="square" size={132} icon="user"
                src={'http://127.0.0.1:9527/imagesWeb/' + record.supplierImages.split(',')[0]}/>
            </template>
            <a-avatar shape="square" icon="user"
              src={'http://127.0.0.1:9527/imagesWeb/' + record.supplierImages.split(',')[0]}/>
          </a-popover>
        }
      }, {
        title: '负责人',
        dataIndex: 'chargePerson',
        ellipsis: true,
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '可供类型',
        dataIndex: 'supplyType',
        ellipsis: true,
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '联系方式',
        dataIndex: 'phone',
        ellipsis: true,
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '状态',
        dataIndex: 'status',
        ellipsis: true,
        customRender: (text, row, index) => {
          switch (text) {
            case '0':
              return <a-tag>未接收</a-tag>
            case '1':
              return <a-tag color="green">已接收</a-tag>
            case '2':
              return <a-tag color="blue">已报价</a-tag>
            default:
              return '- -'
          }
        }
      }, {
        title: '报价金额',
        dataIndex: 'totalPrice',
        ellipsis: true,
        customRender: (text, row, index) => {
          if (text !== null) {
            return text + '元'
          } else {
            return '- -'
          }
        }
      }, {
        title: '操作',
        dataIndex: 'operation',
        scopedSlots: {customRender: 'operation'}
      }]
    }
  },
  data () {
    return {
      loading: false,
      fileList: [],
      planList: [],
      supplierList: [],
      previewVisible: false,
      previewImage: ''
    }
  },
  watch: {
    moduleShow: function (value) {
      if (value) {
        this.queryPlanByPurchaseCode(this.moduleData.id)
        this.querySupplierByMatch(this.moduleData.code)
      }
    }
  },
  methods: {
    handleDateChange (date, dateString) {
      this.endDate = dateString
    },
    handleSubmit () {
      if (!this.endDate) {
        this.$message.error('请选择截止时间')
        return false
      }
      let param = {
        planId: this.moduleData.id,
        materialsCode: this.moduleData.code,
        endDate: this.endDate
      }
      let purchaseQuotationInfoList = []
      this.supplierList.forEach(item => {
        purchaseQuotationInfoList.push({
          supplierId: item.id
        })
      })
      param.purchaseQuotationInfoList = JSON.stringify(purchaseQuotationInfoList)
      this.$post('/business/purchase-quotation-info', param).then(res => {
        this.endDate = null
        this.$emit('success')
      })
    },
    checkOrder (row) {
      this.$post('/business/order-info', {
        planId: this.moduleData.id,
        materialsCode: this.moduleData.code,
        totalPrice: row.totalPrice,
        purchaseNum: this.moduleData.purchaseNum,
        supplierId: row.supplierId
      }).then(res => {
        this.$emit('orderSuccess')
      })
    },
    queryPlanByPurchaseCode (id) {
      this.$get('/business/purchase-quotation-info/queryQuotationByPlanId', {
        planId: id
      }).then(res => {
        this.planList = res.data.data
      })
    },
    querySupplierByMatch (code) {
      this.$get('/business/supplier-info/querySupplierByMatch', {
        materialsCode: code
      }).then(res => {
        this.supplierList = res.data.data
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
  >>> .ant-card-meta-title {
    font-size: 14px;
  }
  >>> .ant-card-meta-description {
    font-size: 13px;
    overflow: hidden;
  }
</style>
