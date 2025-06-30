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
                label="审核编号"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}">
                <a-input v-model="queryParams.code"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="完成状态"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}">
                <a-select v-model="queryParams.status" allowClear>
                  <a-select-option value="0">未审核</a-select-option>
                  <a-select-option value="1">通过</a-select-option>
                  <a-select-option value="2">驳回</a-select-option>
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
          <a-icon v-if="record.status != 0" type="cloud" @click="handleModuleViewOpen(record)" title="详 情"></a-icon>
          <a-icon v-else type="setting" theme="twoTone" twoToneColor="#4a9ff5" @click="handleModuleViewOpen(record)" title="修 改"
                  style="margin-left: 15px"></a-icon>
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
      @success="handleModuleViewSuccess"
      :moduleShow="moduleView.visiable"
      :moduleData="moduleView.data">
    </module-view>
  </a-card>
</template>

<script>
import RangeDate from '@/components/datetime/RangeDate'
import moduleAdd from './AuditAdd.vue'
import moduleEdit from './AuditEdit.vue'
import moduleView from './AuditView.vue'
import {mapState} from 'vuex'
import moment from 'moment'

moment.locale('zh-cn')

export default {
  name: 'module',
  components: {moduleAdd, moduleEdit, moduleView, RangeDate},
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
        title: '审核编号',
        dataIndex: 'code',
        ellipsis: true
      }, {
        title: '审核备注',
        dataIndex: 'content',
        ellipsis: true,
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '审核状态',
        dataIndex: 'status',
        ellipsis: true,
        customRender: (text, row, index) => {
          switch (text) {
            case '0':
              return <a-tag>未审核</a-tag>
            case '1':
              return <a-tag color="green">通过</a-tag>
            case '2':
              return <a-tag color="red">驳回</a-tag>
            default:
              return '- -'
          }
        }
      }, {
        title: '供应商名称',
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
        title: '审核时间',
        dataIndex: 'auditDate',
        ellipsis: true,
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
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
    handleModuleViewOpen (row) {
      this.moduleView.data = row
      this.moduleView.visiable = true
    },
    handleModuleViewClose () {
      this.moduleView.visiable = false
    },
    handleModuleViewSuccess () {
      this.moduleView.visiable = false
      this.$message.success('审核成功')
      this.search()
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
      this.$message.success('新增审核成功')
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
      this.$message.success('修改审核成功')
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
          that.$delete('/business/supplier-audit-record/' + ids).then(() => {
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
      this.$get('/business/supplier-audit-record/page', {
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
