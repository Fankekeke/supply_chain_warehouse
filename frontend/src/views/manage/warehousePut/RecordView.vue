<template>
  <a-modal v-model="show" title="入库详情" @cancel="onClose" :width="1000">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px" v-if="recordData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span class="view-title" style="font-size: 15px;font-weight: 650;color: #000c17">基础信息</span></a-col>
        <a-col :span="8"><b>入库单号：</b>
          {{ recordData.code !== null ? recordData.code : '- -' }}
        </a-col>
        <a-col :span="8"><b>价 格：</b>
          {{ recordData.totalPrice }} 元
        </a-col>
        <a-col :span="8"><b>入库名称：</b>
          {{ recordData.name }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>入库时间：</b>
          {{ recordData.createDate !== null ? recordData.createDate : '- -' }}
        </a-col>
        <a-col :span="8"><b>入库人：</b>
          {{ recordData.putUser }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span class="view-title" style="font-size: 15px;font-weight: 650;color: #000c17">备注</span></a-col>
        <a-col :span="24">
          {{ recordData.remark }}
        </a-col>
      </a-row>
      <br/>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" :gutter="15">
        <a-col style="margin-bottom: 15px"><span class="view-title" style="font-size: 15px;font-weight: 650;color: #000c17">物料详情</span></a-col>
        <a-col :span="24">
          <a-table :columns="columns" :data-source="goodsList">
          </a-table>
        </a-col>
      </a-row>
    </div>
  </a-modal>
</template>

<script>
import moment from 'moment'
moment.locale('zh-cn')
export default {
  name: 'RecordView',
  props: {
    recordShow: {
      type: Boolean,
      default: false
    },
    recordData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.recordShow
      },
      set: function () {
      }
    },
    columns () {
      return [{
        title: '物料名称',
        dataIndex: 'materialsName',
        ellipsis: true,
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '型号',
        dataIndex: 'model',
        ellipsis: true,
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '数量',
        dataIndex: 'quantity',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text + ' ' + row.measurementUnit
          } else {
            return '- -'
          }
        }
      }, {
        title: '物料类型',
        dataIndex: 'type',
        ellipsis: true,
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '单价',
        dataIndex: 'unitPrice',
        ellipsis: true,
        customRender: (text, row, index) => {
          if (text !== null) {
            return text + '元'
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
      goodsList: []
    }
  },
  watch: {
    recordShow: function (value) {
      if (value) {
        this.getGoodsByNum(this.recordData.code)
      }
    }
  },
  methods: {
    getGoodsByNum (num) {
      this.$get('/business/warehouse-info/queryPutRecordDetail', { code: num }).then((r) => {
        this.goodsList = r.data.data
      })
    },
    onClose () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>
