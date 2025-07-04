<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <!-- 搜索区域 -->
      <a-form layout="horizontal">
        <a-row :gutter="15">
          <div :class="advanced ? null: 'fold'">
            <a-col :md="6" :sm="24">
              <a-form-item
                label="供应商名称"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}">
                <a-input v-model="queryParams.supplierName"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="订单编号"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}">
                <a-input v-model="queryParams.orderCode"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="物料名称"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}">
                <a-input v-model="queryParams.materialsName"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="状态"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}">
                <a-select v-model="queryParams.status" allowClear>
                  <a-select-option value="0">未付款</a-select-option>
                  <a-select-option value="1">已付款</a-select-option>
                  <a-select-option value="2">已发货</a-select-option>
                  <a-select-option value="3">检验中</a-select-option>
                  <a-select-option value="4">已退货</a-select-option>
                  <a-select-option value="5">已入库</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </div>
          <span style="float: right; margin-top: 3px;">
            <a-button type="primary" @click="search">查询</a-button>
            <a-button style="margin-left: 8px" @click="reset">重置</a-button>
          </span>
        </a-row>
      </a-form>
    </div>
    <div>
      <div class="operator">
<!--        <a-button type="primary" ghost @click="add">新增</a-button>-->
        <a-button @click="batchDelete">删除</a-button>
      </div>
      <!-- 表格区域 -->
      <a-table ref="TableInfo"
               :columns="columns"
               :rowKey="record => record.id"
               :dataSource="dataSource"
               :pagination="pagination"
               :loading="loading"
               :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
               :scroll="{ x: 900 }"
               @change="handleTableChange">
        <template slot="operation" slot-scope="text, record">
          <a-icon type="cloud" @click="handleModuleViewOpen(record)" title="详 情"></a-icon>
          <a-icon v-if="record.status == 0" type="alipay" @click="orderPay(record)" title="支 付" style="margin-left: 15px"></a-icon>
          <a-icon v-if="record.status == 3" type="carry-out" theme="twoTone" @click="orderStockOpen(record)" title="提 交" style="margin-left: 15px"></a-icon>
          <a-popconfirm
            title="是否确认收货?"
            ok-text="是"
            cancel-text="否"
            @confirm="orderGet(record)"
          >
            <a-icon v-if="record.status == 2" type="shopping" theme="twoTone" title="收 货" style="margin-left: 15px"></a-icon>
          </a-popconfirm>
<!--          <a-icon type="setting" theme="twoTone" twoToneColor="#4a9ff5" @click="edit(record)" title="修 改"-->
<!--                  style="margin-left: 15px"></a-icon>-->
        </template>
      </a-table>
    </div>
    <module-add
      @close="handleModuleAddClose"
      @success="handleModuleAddSuccess"
      :moduleAddVisiable="moduleAdd.visiable">
    </module-add>
    <module-edit
      ref="moduleEdit"
      @close="handleModuleEditClose"
      @success="handleModuleEditSuccess"
      :moduleEditVisiable="moduleEdit.visiable">
    </module-edit>
    <module-view
      @close="handleModuleViewClose"
      :moduleShow="moduleView.visiable"
      :moduleData="moduleView.data">
    </module-view>
    <module-stock
      @close="handleModuleStockClose"
      @success="handleModuleStockSuccess"
      :moduleShow="moduleStock.visiable"
      :moduleData="moduleStock.data">
    </module-stock>
  </a-card>
</template>

<script>
import RangeDate from '@/components/datetime/RangeDate'
import moduleAdd from './OrderAdd.vue'
import moduleEdit from './OrderEdit.vue'
import moduleView from './OrderView.vue'
import moduleStock from './OrderStock.vue'
import {mapState} from 'vuex'
import moment from 'moment'

moment.locale('zh-cn')

export default {
  name: 'module',
  components: {moduleAdd, moduleEdit, moduleView, moduleStock, RangeDate},
  data () {
    return {
      advanced: false,
      moduleAdd: {
        visiable: false
      },
      moduleEdit: {
        visiable: false
      },
      moduleView: {
        visiable: false,
        data: null
      },
      moduleStock: {
        visiable: false,
        data: null
      },
      queryParams: {},
      filteredInfo: null,
      sortedInfo: null,
      paginationInfo: null,
      dataSource: [],
      selectedRowKeys: [],
      loading: false,
      pagination: {
        pageSizeOptions: ['10', '20', '30', '40', '100'],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) => `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`
      },
      userList: []
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    columns () {
      return [{
        title: '订单编号',
        dataIndex: 'code',
        ellipsis: true,
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '总价格',
        dataIndex: 'totalPrice',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text + '元'
          } else {
            return '- -'
          }
        },
        ellipsis: true
      }, {
        title: '供应商',
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
        title: '状态',
        dataIndex: 'status',
        ellipsis: true,
        customRender: (text, row, index) => {
          switch (text) {
            case '0':
              return <a-tag>未付款</a-tag>
            case '1':
              return <a-tag>已付款</a-tag>
            case '2':
              return <a-tag>已发货</a-tag>
            case '3':
              return <a-tag>检验中</a-tag>
            case '4':
              return <a-tag>已退货</a-tag>
            case '5':
              return <a-tag>已入库</a-tag>
            default:
              return '- -'
          }
        }
      }, {
        title: '采购物料',
        dataIndex: 'materialsName',
        ellipsis: true,
        customRender: (text, row, index) => {
          if (text !== null) {
            return text + ' ' + row.purchaseNum + '' + row.measurementUnit
          } else {
            return '- -'
          }
        }
      }, {
        title: '物料型号',
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
        title: '物料图片',
        dataIndex: 'materialsImages',
        customRender: (text, record, index) => {
          if (!record.materialsImages) return <a-avatar shape="square" icon="user"/>
          return <a-popover>
            <template slot="content">
              <a-avatar shape="square" size={132} icon="user"
                src={'http://127.0.0.1:9527/imagesWeb/' + record.materialsImages.split(',')[0]}/>
            </template>
            <a-avatar shape="square" icon="user"
              src={'http://127.0.0.1:9527/imagesWeb/' + record.materialsImages.split(',')[0]}/>
          </a-popover>
        }
      }, {
        title: '采购数量',
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
        title: '创建时间',
        dataIndex: 'createDate',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
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
  mounted () {
    this.fetch()
  },
  methods: {
    orderGet (record) {
      this.$get('/business/order-info/setOrderStatus', { id: record.id, status: '3' }).then((r) => {
        this.$message.success('收货成功')
        this.fetch()
      })
    },
    orderStockOpen (record) {
      this.moduleStock.data = record
      this.moduleStock.visiable = true
    },
    orderPay (record) {
      let data = { outTradeNo: record.code, subject: `${record.createDate}缴费信息`, totalAmount: record.totalPrice, body: '' }
      this.$post('/business/pay/alipay', data).then((r) => {
        // console.log(r.data.msg)
        // 添加之前先删除一下，如果单页面，页面不刷新，添加进去的内容会一直保留在页面中，二次调用form表单会出错
        const divForm = document.getElementsByTagName('div')
        if (divForm.length) {
          document.body.removeChild(divForm[0])
        }
        const div = document.createElement('div')
        div.innerHTML = r.data.msg // data就是接口返回的form 表单字符串
        // console.log(div.innerHTML)
        document.body.appendChild(div)
        document.forms[0].setAttribute('target', '_self') // 新开窗口跳转
        document.forms[0].submit()
      })
    },
    handleModuleStockOpen (row) {
      this.moduleStock.data = row
      this.moduleStock.visiable = true
    },
    handleModuleStockSuccess () {
      this.moduleStock.visiable = false
      this.$message.success('操作成功')
      this.fetch()
    },
    handleModuleStockClose () {
      this.moduleStock.visiable = false
    },
    handleModuleViewOpen (row) {
      this.moduleView.data = row
      this.moduleView.visiable = true
    },
    handleModuleViewClose () {
      this.moduleView.visiable = false
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    add () {
      this.moduleAdd.visiable = true
    },
    handleModuleAddClose () {
      this.moduleAdd.visiable = false
    },
    handleModuleAddSuccess () {
      this.moduleAdd.visiable = false
      this.$message.success('新增采购订单成功')
      this.search()
    },
    edit (record) {
      this.$refs.moduleEdit.setFormValues(record)
      this.moduleEdit.visiable = true
    },
    handleModuleEditClose () {
      this.moduleEdit.visiable = false
    },
    handleModuleEditSuccess () {
      this.moduleEdit.visiable = false
      this.$message.success('修改采购订单成功')
      this.search()
    },
    batchDelete () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择需要删除的记录')
        return
      }
      let that = this
      this.$confirm({
        title: '确定删除所选中的记录?',
        content: '当您点击确定按钮后，这些记录将会被彻底删除',
        centered: true,
        onOk () {
          let ids = that.selectedRowKeys.join(',')
          that.$delete('/business/order-info/' + ids).then(() => {
            that.$message.success('删除成功')
            that.selectedRowKeys = []
            that.search()
          })
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })
    },
    search () {
      let {sortedInfo, filteredInfo} = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.fetch({
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams,
        ...filteredInfo
      })
    },
    reset () {
      // 取消选中
      this.selectedRowKeys = []
      // 重置分页
      this.$refs.TableInfo.pagination.current = this.pagination.defaultCurrent
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent
        this.paginationInfo.pageSize = this.pagination.defaultPageSize
      }
      // 重置列过滤器规则
      this.filteredInfo = null
      // 重置列排序规则
      this.sortedInfo = null
      // 重置查询参数
      this.queryParams = {}
      this.fetch()
    },
    handleTableChange (pagination, filters, sorter) {
      // 将这三个参数赋值给Vue data，用于后续使用
      this.paginationInfo = pagination
      this.filteredInfo = filters
      this.sortedInfo = sorter

      this.fetch({
        sortField: sorter.field,
        sortOrder: sorter.order,
        ...this.queryParams,
        ...filters
      })
    },
    fetch (params = {}) {
      // 显示loading
      this.loading = true
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize
        params.size = this.paginationInfo.pageSize
        params.current = this.paginationInfo.current
      } else {
        // 如果分页信息为空，则设置为默认值
        params.size = this.pagination.defaultPageSize
        params.current = this.pagination.defaultCurrent
      }
      if (params.status === undefined) {
        delete params.status
      }
      this.$get('/business/order-info/page', {
        ...params
      }).then((r) => {
        let data = r.data.data
        const pagination = {...this.pagination}
        pagination.total = data.total
        this.dataSource = data.records
        this.pagination = pagination
        // 数据加载完毕，关闭loading
        this.loading = false
      })
    }
  },
  watch: {}
}
</script>
<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
