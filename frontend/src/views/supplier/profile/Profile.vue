
<template>
  <a-card :bordered="false" hoverable style="margin-top: 5px;width: 100%">
    <div class="supplier-management">
      <a-tabs defaultActiveKey="main" :animated="false">
        <!-- 供应商基本信息 Tab -->
        <a-tab-pane key="main" tab="基本信息">
          <a-form :form="mainForm" layout="vertical">
            <a-row :gutter="16">
              <a-col :span="6">
                <a-form-item label="企业名称">
                  <a-input v-decorator="['corpName', { rules: [{ required: true, message: '请输入企业名称' }] }]" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="公司法人">
                  <a-input v-decorator="['legalPerson']" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="联系方式">
                  <a-input v-decorator="['contactInfo']" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="注册资本 (万元)">
                  <a-input-number v-decorator="['regCapital']" style="width: 100%" />
                </a-form-item>
              </a-col>
            </a-row>

            <a-row :gutter="16">
              <a-col :span="6">
                <a-form-item label="营业执照号码">
                  <a-input v-decorator="['licenseNum']" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="行业类别">
                  <a-input v-decorator="['industryType']" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="业务性质">
                  <a-select v-decorator="['businessNature']">
                    <a-select-option value="生产企业">生产企业</a-select-option>
                    <a-select-option value="销售公司">销售公司</a-select-option>
                    <a-select-option value="服务机构">服务机构</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="资本类型">
                  <a-select v-decorator="['capitalType']">
                    <a-select-option value="有限责任公司">有限责任公司</a-select-option>
                    <a-select-option value="股份公司">股份公司</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
            </a-row>

            <a-form-item label="股权结构情况">
              <a-textarea v-decorator="['equityStructure']" :rows="3" />
            </a-form-item>

            <a-form-item label="主要经营范围">
              <a-textarea v-decorator="['businessScope']" :rows="3" />
            </a-form-item>

            <a-row :gutter="16">
              <a-col :span="6">
                <a-form-item label="是否有进出口业务">
                  <a-switch v-decorator="['hasImportExport']" checked-children="是" un-checked-children="否" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="进出口模式">
                  <a-input v-decorator="['ieMode']" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="企业注册地址">
                  <a-input v-decorator="['regAddress']" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="邮政编码">
                  <a-input v-decorator="['postCode']" />
                </a-form-item>
              </a-col>
            </a-row>

            <a-row :gutter="16">
              <a-col :span="6">
                <a-form-item label="企业网址">
                  <a-input v-decorator="['website']" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="企业成立时间">
                  <a-date-picker v-decorator="['establishDate']" style="width: 100%" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="企业规模">
                  <a-input v-decorator="['scale']" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="参保人数">
                  <a-input-number v-decorator="['insuredNum']" style="width: 100%" />
                </a-form-item>
              </a-col>
            </a-row>

            <a-row :gutter="16">
              <a-col :span="6">
                <a-form-item label="年产值 (万人民币)">
                  <a-input-number v-decorator="['annualOutput']" style="width: 100%" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="年销售额 (万人民币)">
                  <a-input-number v-decorator="['annualSales']" style="width: 100%" />
                </a-form-item>
              </a-col>
            </a-row>

            <a-form-item label="公司简介">
              <a-textarea v-decorator="['corpProfile']" :rows="4" />
            </a-form-item>

            <a-form-item>
              <a-button type="primary" @click="saveMainInfo">保存基本信息</a-button>
            </a-form-item>
          </a-form>
        </a-tab-pane>

        <!-- 其他 Tab 保持不变 -->
        <!-- 联系人信息 Tab -->
        <a-tab-pane key="contact" tab="联系人信息" forceRender>
          <a-table :columns="contactColumns" :data-source="contactData" :pagination="false" style="margin-bottom: 20px">
            <span slot="action" slot-scope="text, record">
              <a @click="editContact(record)">编辑</a>
              <a-divider type="vertical" />
              <a @click="deleteContact(record)">删除</a>
            </span>
          </a-table>

          <a-button type="dashed" style="width: 100%" @click="addContact">
            <a-icon type="plus" /> 添加联系人
          </a-button>

          <!-- 联系人表单弹窗 -->
          <a-modal title="联系人信息" :visible="contactModalVisible" @ok="saveContact" @cancel="closeContactModal">
            <a-form :form="contactForm">
              <a-form-item label="姓名">
                <a-input v-decorator="['name', { rules: [{ required: true, message: '请输入姓名' }] }]" />
              </a-form-item>

              <a-form-item label="性别">
                <a-radio-group v-decorator="['gender']">
                  <a-radio value="男">男</a-radio>
                  <a-radio value="女">女</a-radio>
                </a-radio-group>
              </a-form-item>

              <a-form-item label="职务">
                <a-input v-decorator="['title']" />
              </a-form-item>

              <a-form-item label="部门">
                <a-input v-decorator="['department']" />
              </a-form-item>

              <a-form-item label="联系方式">
                <a-input v-decorator="['contactNum']" />
              </a-form-item>

              <a-form-item label="电子邮箱">
                <a-input v-decorator="['email']" />
              </a-form-item>

              <a-form-item label="联系人类型">
                <a-select v-decorator="['contact_type']">
                  <a-select-option value="商务">商务</a-select-option>
                  <a-select-option value="技术">技术</a-select-option>
                </a-select>
              </a-form-item>
            </a-form>
          </a-modal>
        </a-tab-pane>

        <!-- 财务信息 Tab -->
        <a-tab-pane key="finance" tab="财务信息" forceRender>
          <a-table :columns="financeColumns" :data-source="financeData" :pagination="false" style="margin-bottom: 20px">
            <span slot="action" slot-scope="text, record">
              <a @click="editFinance(record)">编辑</a>
              <a-divider type="vertical" />
              <a @click="deleteFinance(record)">删除</a>
            </span>
          </a-table>

          <a-button type="dashed" style="width: 100%" @click="addFinance">
            <a-icon type="plus" /> 添加财务记录
          </a-button>

          <!-- 财务信息表单弹窗 -->
          <a-modal title="财务信息" :visible="financeModalVisible" @ok="saveFinance" @cancel="closeFinanceModal">
            <a-form :form="financeForm">
              <a-form-item label="年份">
                <a-input-number v-decorator="['year', { rules: [{ required: true, message: '请选择年份' }] }]" style="width: 100%" />
              </a-form-item>

              <a-form-item label="收入总额 (万人民币)">
                <a-input-number v-decorator="['totalRevenue']" style="width: 100%" />
              </a-form-item>

              <a-form-item label="净利润总额 (万人民币)">
                <a-input-number v-decorator="['netProfit']" style="width: 100%" />
              </a-form-item>

              <a-form-item label="其他财务数据">
                <a-textarea v-decorator="['otherData']" :rows="3" />
              </a-form-item>
            </a-form>
          </a-modal>
        </a-tab-pane>

        <!-- 产品与业绩 Tab -->
        <a-tab-pane key="performance" tab="产品与业绩" forceRender>
          <a-table :columns="performanceColumns" :data-source="performanceData" :pagination="false" style="margin-bottom: 20px">
            <span slot="action" slot-scope="text, record">
              <a @click="editPerformance(record)">编辑</a>
              <a-divider type="vertical" />
              <a @click="deletePerformance(record)">删除</a>
            </span>
          </a-table>

          <a-button type="dashed" style="width: 100%" @click="addPerformance">
            <a-icon type="plus" /> 添加产品/业绩
          </a-button>

          <!-- 产品与业绩表单弹窗 -->
          <a-modal title="产品与业绩信息" :visible="performanceModalVisible" @ok="savePerformance" @cancel="closePerformanceModal" width="600px">
            <a-form :form="performanceForm">
              <a-form-item label="记录类型">
                <a-select v-decorator="['type', { rules: [{ required: true, message: '请选择记录类型' }] }]">
                  <a-select-option value="产品">产品</a-select-option>
                  <a-select-option value="业绩">业绩</a-select-option>
                </a-select>
              </a-form-item>

              <a-form-item label="产品/服务名称 或 项目名称">
                <a-input v-decorator="['name', { rules: [{ required: true, message: '请输入名称' }] }]" />
              </a-form-item>

              <a-row :gutter="16">
                <a-col :span="12">
                  <a-form-item label="销售额 (万人民币)">
                    <a-input-number v-decorator="['salesAmount']" style="width: 100%" />
                  </a-form-item>
                </a-col>
                <a-col :span="12">
                  <a-form-item label="占总销售额百分比">
                    <a-input-number v-decorator="['salesPercentage']" style="width: 100%" />
                  </a-form-item>
                </a-col>
              </a-row>

              <a-form-item label="优势概述/产品说明">
                <a-textarea v-decorator="['advantageOverview']" :rows="3" />
              </a-form-item>

              <a-form-item label="客户名称（仅业绩字段）">
                <a-input v-decorator="['customer']" />
              </a-form-item>

              <a-row :gutter="16">
                <a-col :span="12">
                  <a-form-item label="项目周期（仅业绩字段）">
                    <a-input v-decorator="['projectCycle']" />
                  </a-form-item>
                </a-col>
                <a-col :span="12">
                  <a-form-item label="验收状况（仅业绩字段）">
                    <a-input v-decorator="['acceptanceStatus']" />
                  </a-form-item>
                </a-col>
              </a-row>

              <a-form-item label="备注">
                <a-textarea v-decorator="['note']" :rows="2" />
              </a-form-item>
            </a-form>
          </a-modal>
        </a-tab-pane>

        <!-- 资质信息 Tab -->
        <a-tab-pane key="qualification" tab="资质信息" forceRender>
          <a-table :columns="qualificationColumns" :data-source="qualificationData" :pagination="false" style="margin-bottom: 20px">
            <span slot="action" slot-scope="text, record">
              <a @click="editQualification(record)">编辑</a>
              <a-divider type="vertical" />
              <a @click="deleteQualification(record)">删除</a>
            </span>
          </a-table>

          <a-button type="dashed" style="width: 100%" @click="addQualification">
            <a-icon type="plus" /> 添加资质信息
          </a-button>

          <!-- 资质信息表单弹窗 -->
          <a-modal title="资质信息" :visible="qualificationModalVisible" @ok="saveQualification" @cancel="closeQualificationModal" width="600px">
            <a-form :form="qualificationForm">
              <a-form-item label="证书名称/类型">
                <a-input v-decorator="['certName', { rules: [{ required: true, message: '请输入证书名称' }] }]" />
              </a-form-item>

              <a-form-item label="认证/授予机构">
                <a-input v-decorator="['agency']" />
              </a-form-item>

              <a-form-item label="适用范围">
                <a-input v-decorator="['applyTo']" />
              </a-form-item>

              <a-row :gutter="16">
                <a-col :span="12">
                  <a-form-item label="签发日期">
                    <a-date-picker v-decorator="['issueDate']" style="width: 100%" />
                  </a-form-item>
                </a-col>
                <a-col :span="12">
                  <a-form-item label="有效期至">
                    <a-date-picker v-decorator="['expiryDate']" style="width: 100%" />
                  </a-form-item>
                </a-col>
              </a-row>

              <a-form-item label="类型">
                <a-select v-decorator="['type']">
                  <a-select-option value="认证">认证</a-select-option>
                  <a-select-option value="专利">专利</a-select-option>
                  <a-select-option value="授权代理">授权代理</a-select-option>
                </a-select>
              </a-form-item>

              <a-form-item label="特点与价值（仅专利字段）">
                <a-textarea v-decorator="['featuresValue']" :rows="3" />
              </a-form-item>
            </a-form>
          </a-modal>
        </a-tab-pane>
      </a-tabs>
    </div>
  </a-card>
</template>

<script>
import {mapState} from 'vuex'
import moment from 'moment'
moment.locale('zh-cn')
export default {
  name: 'SupplierManagement',
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    })
  },
  data () {
    return {
      // 主要表单
      mainForm: this.$form.createForm(this),

      // 联系人相关
      contactForm: this.$form.createForm(this),
      contactModalVisible: false,
      contactData: [],
      editingContact: null,

      // 财务信息相关
      financeForm: this.$form.createForm(this),
      financeModalVisible: false,
      financeData: [],
      editingFinance: null,

      // 产品与业绩相关
      performanceForm: this.$form.createForm(this),
      performanceModalVisible: false,
      performanceData: [],
      editingPerformance: null,

      // 资质信息相关
      qualificationForm: this.$form.createForm(this),
      qualificationModalVisible: false,
      qualificationData: [],
      editingQualification: null,

      // 表格列定义
      contactColumns: [
        { title: '姓名', dataIndex: 'name', key: 'name' },
        { title: '性别', dataIndex: 'gender', key: 'gender' },
        { title: '职务', dataIndex: 'title', key: 'title' },
        { title: '部门', dataIndex: 'department', key: 'department' },
        { title: '联系方式', dataIndex: 'contact_num', key: 'contact_num' },
        { title: '电子邮箱', dataIndex: 'email', key: 'email' },
        { title: '类型', dataIndex: 'contactType', key: 'contactType' },
        { title: '操作', key: 'action', scopedSlots: { customRender: 'action' } }
      ],

      financeColumns: [
        { title: '年份', dataIndex: 'year', key: 'year' },
        { title: '收入总额(万元)', dataIndex: 'totalRevenue', key: 'totalRevenue' },
        { title: '净利润(万元)', dataIndex: 'netProfit', key: 'netProfit' },
        { title: '操作', key: 'action', scopedSlots: { customRender: 'action' } }
      ],

      performanceColumns: [
        { title: '类型', dataIndex: 'type', key: 'type' },
        { title: '名称', dataIndex: 'name', key: 'name' },
        { title: '销售额(万元)', dataIndex: 'salesAmount', key: 'salesAmount' },
        { title: '占比(%)', dataIndex: 'salesPercentage', key: 'salesPercentage' },
        { title: '客户', dataIndex: 'customer', key: 'customer' },
        { title: '操作', key: 'action', scopedSlots: { customRender: 'action' } }
      ],

      qualificationColumns: [
        { title: '证书名称', dataIndex: 'certName', key: 'certName' },
        { title: '机构', dataIndex: 'agency', key: 'agency' },
        { title: '签发日期', dataIndex: 'issueDate', key: 'issueDate' },
        { title: '有效期至', dataIndex: 'expiryDate', key: 'expiryDate' },
        { title: '类型', dataIndex: 'type', key: 'type' },
        { title: '操作', key: 'action', scopedSlots: { customRender: 'action' } }
      ],
      supplierMain: null,
      supplierQualificationList: [],
      supplierPerformanceList: [],
      supplierFinanceList: [],
      supplierContactList: []
    }
  },
  mounted () {
    this.querySupplierMainById()
    this.querySupplierQualification()
    this.querySupplierPerformance()
    this.querySupplierFinance()
    this.querySupplierContact()
  },
  methods: {
    querySupplierMainById () {
      this.$get('/business/supplier-info/querySupplierMainById', {supplierUserId: this.currentUser.userId}).then((r) => {
        this.supplierMain = r.data.data
        if (this.supplierMain) {
          console.log(this.supplierMain)
          this.setFormValues(this.supplierMain)
        }
      })
    },
    setFormValues ({...supplier}) {
      this.rowId = supplier.id
      let fields = ['corpName', 'legalPerson', 'contactInfo', 'regCapital', 'licenseNum', 'industryType', 'businessNature', 'capitalType', 'equityStructure', 'businessScope', 'hasImportExport', 'ieMode', 'regAddress', 'postCode', 'website', 'establishDate', 'scale', 'insuredNum', 'annualOutput', 'annualSales', 'corpProfile', 'requiredCerts', '', '', '', '', '']
      let obj = {}
      Object.keys(supplier).forEach((key) => {
        if (key === 'establishDate' && supplier[key] != null) {
          supplier[key] = moment(supplier[key])
        }
        if (fields.indexOf(key) !== -1) {
          this.mainForm.getFieldDecorator(key)
          obj[key] = supplier[key]
        }
      })
      this.mainForm.setFieldsValue(obj)
    },
    querySupplierQualification () {
      this.$get('/business/supplier-info/querySupplierQualification', {supplierUserId: this.currentUser.userId}).then((r) => {
        this.qualificationData = r.data.data
      })
    },
    querySupplierPerformance () {
      this.$get('/business/supplier-info/querySupplierPerformance', {supplierUserId: this.currentUser.userId}).then((r) => {
        this.performanceData = r.data.data
      })
    },
    querySupplierFinance () {
      this.$get('/business/supplier-info/querySupplierFinance', {supplierUserId: this.currentUser.userId}).then((r) => {
        this.financeData = r.data.data
      })
    },
    querySupplierContact () {
      this.$get('/business/supplier-info/querySupplierContact', {supplierUserId: this.currentUser.userId}).then((r) => {
        this.contactData = r.data.data
      })
    },
    // 保存基本信息
    saveMainInfo () {
      this.mainForm.validateFields((err, values) => {
        if (!err) {
          if (values.hasImportExport !== undefined && values.establishDate) {
            values.establishDate = moment(values.establishDate).format('YYYY-MM-DD')
          }
          if (values.hasImportExport !== undefined) {
            values.hasImportExport = values.hasImportExport ? 1 : 0
          }
          values.id = this.supplierMain.id
          console.log('基本信息:', values)
          this.$put('/business/supplier-main', {
            ...values
          }).then((r) => {
            this.$message.success('基本信息保存成功')
            this.querySupplierMainById()
          })
        }
      })
    },

    // 联系人操作
    addContact () {
      this.editingContact = null
      this.contactForm.resetFields()
      this.contactModalVisible = true
    },

    editContact (record) {
      this.editingContact = record
      this.contactModalVisible = true
      this.$nextTick(() => {
        this.contactForm.setFieldsValue(record)
      })
    },

    deleteContact (record) {
      // this.contactData = this.contactData.filter(item => item.contact_id !== record.contact_id)
      this.$delete('/business/supplier-contact/' + record.id).then(() => {
        this.$message.success('删除成功')
        this.querySupplierContact()
      })
    },

    saveContact () {
      this.contactForm.validateFields((err, values) => {
        if (!err) {
          if (this.editingContact) {
            // 编辑
            values.id = this.editingContact.id
            this.$put('/business/supplier-contact', {
              ...values
            }).then(() => {
              this.querySupplierContact()
            })
            // const index = this.contactData.findIndex(item => item.contact_id === this.editingContact.contact_id)
            // this.contactData.splice(index, 1, { ...values, contact_id: this.editingContact.contact_id })
          } else {
            // 新增
            values.supplierId = this.supplierMain.supplierId
            this.$post('/business/supplier-contact', {
              ...values
            }).then(() => {
              this.querySupplierContact()
            })
            // const newContact = { ...values, contact_id: Date.now() }
            // this.contactData.push(newContact)
          }
          this.closeContactModal()
          this.$message.success('保存成功')
        }
      })
    },

    closeContactModal () {
      this.contactModalVisible = false
      this.editingContact = null
    },

    // 财务信息操作
    addFinance () {
      this.editingFinance = null
      this.financeForm.resetFields()
      this.financeModalVisible = true
    },

    editFinance (record) {
      this.editingFinance = record
      this.financeModalVisible = true
      this.$nextTick(() => {
        this.financeForm.setFieldsValue(record)
      })
    },

    deleteFinance (record) {
      // this.financeData = this.financeData.filter(item => item.finance_id !== record.finance_id)
      this.$delete('/business/supplier-finance/' + record.id).then(() => {
        this.$message.success('删除成功')
        this.querySupplierFinance()
      })
    },

    saveFinance () {
      this.financeForm.validateFields((err, values) => {
        if (!err) {
          if (this.editingFinance) {
            // 编辑
            // const index = this.financeData.findIndex(item => item.finance_id === this.editingFinance.finance_id)
            // this.financeData.splice(index, 1, { ...values, finance_id: this.editingFinance.finance_id })
            values.id = this.editingFinance.id
            this.$put('/business/supplier-finance', {
              ...values
            }).then(() => {
              this.querySupplierFinance()
            })
          } else {
            // 新增
            // const newFinance = { ...values, finance_id: Date.now() }
            // this.financeData.push(newFinance)
            values.supplierId = this.supplierMain.supplierId
            this.$post('/business/supplier-finance', {
              ...values
            }).then(() => {
              this.querySupplierFinance()
            })
          }
          this.closeFinanceModal()
          this.$message.success('保存成功')
        }
      })
    },

    closeFinanceModal () {
      this.financeModalVisible = false
      this.editingFinance = null
    },

    // 产品与业绩操作
    addPerformance () {
      this.editingPerformance = null
      this.performanceForm.resetFields()
      this.performanceModalVisible = true
    },

    editPerformance (record) {
      this.editingPerformance = record
      this.performanceModalVisible = true
      this.$nextTick(() => {
        this.performanceForm.setFieldsValue(record)
      })
    },

    deletePerformance (record) {
      // this.performanceData = this.performanceData.filter(item => item.performance_id !== record.performance_id)
      this.$delete('/business/supplier-performance/' + record.id).then(() => {
        this.$message.success('删除成功')
        this.querySupplierPerformance()
      })
    },

    savePerformance () {
      this.performanceForm.validateFields((err, values) => {
        if (!err) {
          if (this.editingPerformance) {
            // 编辑
            // const index = this.performanceData.findIndex(item => item.performance_id === this.editingPerformance.performance_id)
            // this.performanceData.splice(index, 1, { ...values, performance_id: this.editingPerformance.performance_id })
            values.id = this.editingPerformance.id
            this.$put('/business/supplier-performance', {
              ...values
            }).then(() => {
              this.querySupplierPerformance()
            })
          } else {
            // 新增
            // const newPerformance = { ...values, performance_id: Date.now() }
            // this.performanceData.push(newPerformance)
            values.supplierId = this.supplierMain.supplierId
            this.$post('/business/supplier-performance', {
              ...values
            }).then(() => {
              this.querySupplierPerformance()
            })
          }
          this.closePerformanceModal()
          this.$message.success('保存成功')
        }
      })
    },

    closePerformanceModal () {
      this.performanceModalVisible = false
      this.editingPerformance = null
    },

    // 资质信息操作
    addQualification () {
      this.editingQualification = null
      this.qualificationForm.resetFields()
      this.qualificationModalVisible = true
    },

    editQualification (record) {
      this.editingQualification = record
      this.qualificationModalVisible = true
      this.$nextTick(() => {
        this.qualificationForm.setFieldsValue(record)
      })
    },

    deleteQualification (record) {
      this.$delete('/business/supplier-qualification/' + record.id).then(() => {
        this.$message.success('删除成功')
        this.querySupplierQualification()
      })
    },

    saveQualification () {
      this.qualificationForm.validateFields((err, values) => {
        if (!err) {
          if (this.editingQualification) {
            // 编辑
            // const index = this.qualificationData.findIndex(item => item.qual_id === this.editingQualification.qual_id)
            // this.qualificationData.splice(index, 1, { ...values, qual_id: this.editingQualification.qual_id })
            values.id = this.editingQualification.id
            if (values.issueDate) {
              values.issueDate = moment(values.issueDate).format('YYYY-MM-DD')
            }
            if (values.expiryDate) {
              values.expiryDate = moment(values.expiryDate).format('YYYY-MM-DD')
            }
            this.$put('/business/supplier-qualification', {
              ...values
            }).then(() => {
              this.querySupplierQualification()
            })
          } else {
            // 新增
            // const newQualification = { ...values, qual_id: Date.now() }
            // this.qualificationData.push(newQualification)
            values.supplierId = this.supplierMain.supplierId
            if (values.issueDate) {
              values.issueDate = moment(values.issueDate).format('YYYY-MM-DD')
            }
            if (values.expiryDate) {
              values.expiryDate = moment(values.expiryDate).format('YYYY-MM-DD')
            }
            this.$post('/business/supplier-qualification', {
              ...values
            }).then(() => {
              this.querySupplierQualification()
            })
          }
          this.closeQualificationModal()
          this.$message.success('保存成功')
        }
      })
    },

    closeQualificationModal () {
      this.qualificationModalVisible = false
      this.editingQualification = null
    }
  }
}
</script>

<style lang="less" scoped>.supplier-management {
  :deep(.ant-tabs-nav) {
    margin-bottom: 20px;
  }

  :deep(.ant-form-item) {
    margin-bottom: 12px;
  }
}
</style>
