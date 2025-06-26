<template>
  <a-modal v-model="show" title="采购需求详情" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="moduleData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span
          class="view-title">采购需求信息</span></a-col>
        <a-col :span="8"><b>订单编号：</b>
          {{ moduleData.orderCode }}
        </a-col>
        <a-col :span="8"><b>采购金额：</b>
          {{ moduleData.totalPrice }} 元
        </a-col>
        <a-col :span="8"><b>详细地址：</b>
          {{ moduleData.address }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span
          class="view-title">采购需求内容</span></a-col>
        <a-col :span="24">
          <a-table :columns="columns" :data-source="planList" :pagination="false">
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
        title: '物料名称',
        dataIndex: 'materialsName',
        ellipsis: true,
      }, {
        title: '需求数量',
        dataIndex: 'purchaseNum',
        ellipsis: true,
        customRender: (text, row, index) => {
          if (text !== null) {
            return text + ' '+ row.measurementUnit
          } else {
            return '- -'
          }
        }
      }, {
        title: '物料图片',
        dataIndex: 'images',
        customRender: (text, record, index) => {
          if (!record.images) return <a-avatar shape="square" icon="user"/>
          return <a-popover>
            <template slot="content">
              <a-avatar shape="square" size={132} icon="user"
                        src={'http://127.0.0.1:9527/imagesWeb/' + record.images.split(',')[0]}/>
            </template>
            <a-avatar shape="square" icon="user"
                      src={'http://127.0.0.1:9527/imagesWeb/' + record.images.split(',')[0]}/>
          </a-popover>
        }
      }, {
        title: '状态',
        dataIndex: 'status',
        ellipsis: true,
        customRender: (text, row, index) => {
          switch (text) {
            case '0':
              return <a-tag>待下发</a-tag>
            case '1':
              return <a-tag>计划下发</a-tag>
            case '2':
              return <a-tag>采购询价</a-tag>
            case '3':
              return <a-tag>生成订单</a-tag>
            case '4':
              return <a-tag>配送中</a-tag>
            case '5':
              return <a-tag>已完成</a-tag>
            default:
              return '- -'
          }
        }
      }, {
        title: '总金额',
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
        title: '采购供应商',
        dataIndex: 'supplierName',
        ellipsis: true,
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }]
    }
  },
  data () {
    return {
      loading: false,
      fileList: [],
      planList: [],
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
    queryPlanByPurchaseCode(purchaseCode) {
      this.$get('/business/purchase-plan-info/queryPlanByPurchaseCode', {
        purchaseCode: purchaseCode
      }).then(res => {
        this.planList = res.data.data
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

</style>
