<template>
  <a-drawer
    title="出库"
    :maskClosable="false"
    placement="right"
    :closable="false"
    :visible="show"
    :width="1200"
    @close="onClose"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;"
  >
    <a-form :form="form" layout="vertical">
      <a-row :gutter="20">
        <a-col :span="12">
          <a-form-item label='出库单名称' v-bind="formItemLayout">
            <a-input v-decorator="[
            'name',
            { rules: [{ required: true, message: '请输入出库单名称!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='备注消息' v-bind="formItemLayout">
            <a-textarea :rows="4" v-decorator="[
            'remark',
             { rules: [{ required: true, message: '请输入名称!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-table :columns="columns" :data-source="dataList">
            <template slot="amountShow" slot-scope="text, record">
              <a-input-number v-model="record.quantity" :min="1" :max="record.max !== undefined ? record.max : 999999" :step="1"/>
            </template>
          </a-table>
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
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'StockOut',
  props: {
    stockoutVisiable: {
      default: false
    },
    stockoutData: {
      type: Array
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.stockoutVisiable
      },
      set: function () {
      }
    },
    columns () {
      return [{
        title: '物料名称',
        dataIndex: 'materialsName'
      }, {
        title: '型号',
        dataIndex: 'model'
      }, {
        title: '数量',
        dataIndex: 'amount',
        scopedSlots: {customRender: 'amountShow'}
      }, {
        title: '物料类型',
        dataIndex: 'type'
      }, {
        title: '单位',
        dataIndex: 'measurementUnit',
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
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      dataList: [],
      staffList: [],
      handler: null,
      custodian: null,
      materialList: []
    }
  },
  watch: {
    stockoutVisiable: function (value) {
      if (value) {
        this.dataList = this.stockoutData
      }
    }
  },
  mounted () {
  },
  methods: {
    reset () {
      this.loading = false
      this.dataList = []
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      if (this.dataList.length === 0) {
        this.$message.error('请添加出库记录！')
      }
      this.form.validateFields((err, values) => {
        if (!err) {
          values.warehouseInfoList = JSON.stringify(this.dataList)
          this.loading = true
          this.$post('/business/warehouse-info/warehouseOut', {
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
