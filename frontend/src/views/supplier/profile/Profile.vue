
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
                  <a-input v-decorator="['corp_name', { rules: [{ required: true, message: '请输入企业名称' }] }]" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="公司法人">
                  <a-input v-decorator="['legal_person']" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="联系方式">
                  <a-input v-decorator="['contact_info']" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="注册资本 (万元)">
                  <a-input-number v-decorator="['reg_capital']" style="width: 100%" />
                </a-form-item>
              </a-col>
            </a-row>

            <a-row :gutter="16">
              <a-col :span="6">
                <a-form-item label="营业执照号码">
                  <a-input v-decorator="['license_num']" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="行业类别">
                  <a-input v-decorator="['industry_type']" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="业务性质">
                  <a-select v-decorator="['business_nature']">
                    <a-select-option value="生产企业">生产企业</a-select-option>
                    <a-select-option value="销售公司">销售公司</a-select-option>
                    <a-select-option value="服务机构">服务机构</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="资本类型">
                  <a-select v-decorator="['capital_type']">
                    <a-select-option value="有限责任公司">有限责任公司</a-select-option>
                    <a-select-option value="股份公司">股份公司</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
            </a-row>

            <a-form-item label="股权结构情况">
              <a-textarea v-decorator="['equity_structure']" :rows="3" />
            </a-form-item>

            <a-form-item label="主要经营范围">
              <a-textarea v-decorator="['business_scope']" :rows="3" />
            </a-form-item>

            <a-row :gutter="16">
              <a-col :span="6">
                <a-form-item label="是否有进出口业务">
                  <a-switch v-decorator="['has_import_export']" checked-children="是" un-checked-children="否" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="进出口模式">
                  <a-input v-decorator="['ie_mode']" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="企业注册地址">
                  <a-input v-decorator="['reg_address']" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="邮政编码">
                  <a-input v-decorator="['post_code']" />
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
                  <a-date-picker v-decorator="['establish_date']" style="width: 100%" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="企业规模">
                  <a-input v-decorator="['scale']" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="参保人数">
                  <a-input-number v-decorator="['insured_num']" style="width: 100%" />
                </a-form-item>
              </a-col>
            </a-row>

            <a-row :gutter="16">
              <a-col :span="6">
                <a-form-item label="年产值 (万人民币)">
                  <a-input-number v-decorator="['annual_output']" style="width: 100%" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="年销售额 (万人民币)">
                  <a-input-number v-decorator="['annual_sales']" style="width: 100%" />
                </a-form-item>
              </a-col>
            </a-row>

            <a-form-item label="公司简介">
              <a-textarea v-decorator="['corp_profile']" :rows="4" />
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
                <a-input v-decorator="['contact_num']" />
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
                <a-year-picker v-decorator="['year', { rules: [{ required: true, message: '请选择年份' }] }]" style="width: 100%" />
              </a-form-item>

              <a-form-item label="收入总额 (万人民币)">
                <a-input-number v-decorator="['total_revenue']" style="width: 100%" />
              </a-form-item>

              <a-form-item label="净利润总额 (万人民币)">
                <a-input-number v-decorator="['net_profit']" style="width: 100%" />
              </a-form-item>

              <a-form-item label="其他财务数据">
                <a-textarea v-decorator="['other_data']" :rows="3" />
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
                    <a-input-number v-decorator="['sales_amount']" style="width: 100%" />
                  </a-form-item>
                </a-col>
                <a-col :span="12">
                  <a-form-item label="占总销售额百分比">
                    <a-input-number v-decorator="['sales_percentage']" style="width: 100%" />
                  </a-form-item>
                </a-col>
              </a-row>

              <a-form-item label="优势概述/产品说明">
                <a-textarea v-decorator="['advantage_overview']" :rows="3" />
              </a-form-item>

              <a-form-item label="客户名称（仅业绩字段）">
                <a-input v-decorator="['customer']" />
              </a-form-item>

              <a-row :gutter="16">
                <a-col :span="12">
                  <a-form-item label="项目周期（仅业绩字段）">
                    <a-input v-decorator="['project_cycle']" />
                  </a-form-item>
                </a-col>
                <a-col :span="12">
                  <a-form-item label="验收状况（仅业绩字段）">
                    <a-input v-decorator="['acceptance_status']" />
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
                <a-input v-decorator="['cert_name', { rules: [{ required: true, message: '请输入证书名称' }] }]" />
              </a-form-item>

              <a-form-item label="认证/授予机构">
                <a-input v-decorator="['agency']" />
              </a-form-item>

              <a-form-item label="适用范围">
                <a-input v-decorator="['apply_to']" />
              </a-form-item>

              <a-row :gutter="16">
                <a-col :span="12">
                  <a-form-item label="签发日期">
                    <a-date-picker v-decorator="['issue_date']" style="width: 100%" />
                  </a-form-item>
                </a-col>
                <a-col :span="12">
                  <a-form-item label="有效期至">
                    <a-date-picker v-decorator="['expiry_date']" style="width: 100%" />
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
                <a-textarea v-decorator="['features_value']" :rows="3" />
              </a-form-item>
            </a-form>
          </a-modal>
        </a-tab-pane>
      </a-tabs>
    </div>
  </a-card>
</template>

<script>export default {
  name: 'SupplierManagement',
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
        { title: '类型', dataIndex: 'contact_type', key: 'contact_type' },
        { title: '操作', key: 'action', scopedSlots: { customRender: 'action' } }
      ],

      financeColumns: [
        { title: '年份', dataIndex: 'year', key: 'year' },
        { title: '收入总额(万元)', dataIndex: 'total_revenue', key: 'total_revenue' },
        { title: '净利润(万元)', dataIndex: 'net_profit', key: 'net_profit' },
        { title: '操作', key: 'action', scopedSlots: { customRender: 'action' } }
      ],

      performanceColumns: [
        { title: '类型', dataIndex: 'type', key: 'type' },
        { title: '名称', dataIndex: 'name', key: 'name' },
        { title: '销售额(万元)', dataIndex: 'sales_amount', key: 'sales_amount' },
        { title: '占比(%)', dataIndex: 'sales_percentage', key: 'sales_percentage' },
        { title: '客户', dataIndex: 'customer', key: 'customer' },
        { title: '操作', key: 'action', scopedSlots: { customRender: 'action' } }
      ],

      qualificationColumns: [
        { title: '证书名称', dataIndex: 'cert_name', key: 'cert_name' },
        { title: '机构', dataIndex: 'agency', key: 'agency' },
        { title: '签发日期', dataIndex: 'issue_date', key: 'issue_date' },
        { title: '有效期至', dataIndex: 'expiry_date', key: 'expiry_date' },
        { title: '类型', dataIndex: 'type', key: 'type' },
        { title: '操作', key: 'action', scopedSlots: { customRender: 'action' } }
      ]
    }
  },
  methods: {
    querySupplierMainById () {
      this.$get('/business/supplier-info/list').then((r) => {
        this.staffList = r.data.data
      })
    },
    // 保存基本信息
    saveMainInfo () {
      this.mainForm.validateFields((err, values) => {
        if (!err) {
          console.log('基本信息:', values)
          this.$message.success('基本信息保存成功')
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
      this.contactForm.setFieldsValue(record)
      this.contactModalVisible = true
    },

    deleteContact (record) {
      this.contactData = this.contactData.filter(item => item.contact_id !== record.contact_id)
      this.$message.success('删除成功')
    },

    saveContact () {
      this.contactForm.validateFields((err, values) => {
        if (!err) {
          if (this.editingContact) {
            // 编辑
            const index = this.contactData.findIndex(item => item.contact_id === this.editingContact.contact_id)
            this.contactData.splice(index, 1, { ...values, contact_id: this.editingContact.contact_id })
          } else {
            // 新增
            const newContact = { ...values, contact_id: Date.now() }
            this.contactData.push(newContact)
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
      this.financeForm.setFieldsValue(record)
      this.financeModalVisible = true
    },

    deleteFinance (record) {
      this.financeData = this.financeData.filter(item => item.finance_id !== record.finance_id)
      this.$message.success('删除成功')
    },

    saveFinance () {
      this.financeForm.validateFields((err, values) => {
        if (!err) {
          if (this.editingFinance) {
            // 编辑
            const index = this.financeData.findIndex(item => item.finance_id === this.editingFinance.finance_id)
            this.financeData.splice(index, 1, { ...values, finance_id: this.editingFinance.finance_id })
          } else {
            // 新增
            const newFinance = { ...values, finance_id: Date.now() }
            this.financeData.push(newFinance)
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
      this.performanceForm.setFieldsValue(record)
      this.performanceModalVisible = true
    },

    deletePerformance (record) {
      this.performanceData = this.performanceData.filter(item => item.performance_id !== record.performance_id)
      this.$message.success('删除成功')
    },

    savePerformance () {
      this.performanceForm.validateFields((err, values) => {
        if (!err) {
          if (this.editingPerformance) {
            // 编辑
            const index = this.performanceData.findIndex(item => item.performance_id === this.editingPerformance.performance_id)
            this.performanceData.splice(index, 1, { ...values, performance_id: this.editingPerformance.performance_id })
          } else {
            // 新增
            const newPerformance = { ...values, performance_id: Date.now() }
            this.performanceData.push(newPerformance)
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
      this.qualificationForm.setFieldsValue(record)
      this.qualificationModalVisible = true
    },

    deleteQualification (record) {
      this.qualificationData = this.qualificationData.filter(item => item.qual_id !== record.qual_id)
      this.$message.success('删除成功')
    },

    saveQualification () {
      this.qualificationForm.validateFields((err, values) => {
        if (!err) {
          if (this.editingQualification) {
            // 编辑
            const index = this.qualificationData.findIndex(item => item.qual_id === this.editingQualification.qual_id)
            this.qualificationData.splice(index, 1, { ...values, qual_id: this.editingQualification.qual_id })
          } else {
            // 新增
            const newQualification = { ...values, qual_id: Date.now() }
            this.qualificationData.push(newQualification)
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
