<template>
  <a-modal v-model="show" title="供应商详情" @cancel="onClose" :width="1000">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div class="supplier-detail-content" v-if="supplierDetail !== null">
      <!-- 基本信息 -->
      <a-card title="基本信息" size="small" class="detail-card">
        <a-row :gutter="24">
          <a-col :span="12">
            <a-descriptions :column="1" size="small" bordered>
              <a-descriptions-item label="企业名称">{{ supplierDetail.supplierMain.corpName }}</a-descriptions-item>
              <a-descriptions-item label="法人代表">{{ supplierDetail.supplierMain.legalPerson }}</a-descriptions-item>
              <a-descriptions-item label="联系方式">{{ supplierDetail.supplierMain.contactInfo }}</a-descriptions-item>
              <a-descriptions-item label="注册资本(万元)">{{ supplierDetail.supplierMain.regCapital }}</a-descriptions-item>
              <a-descriptions-item label="行业类别">{{ supplierDetail.supplierMain.industryType }}</a-descriptions-item>
              <a-descriptions-item label="业务性质">{{ supplierDetail.supplierMain.businessNature }}</a-descriptions-item>
            </a-descriptions>
          </a-col>
          <a-col :span="12">
            <a-descriptions :column="1" size="small" bordered>
              <a-descriptions-item label="成立日期">{{ supplierDetail.supplierMain.establishDate }}</a-descriptions-item>
              <a-descriptions-item label="企业规模">{{ supplierDetail.supplierMain.scale }}</a-descriptions-item>
              <a-descriptions-item label="是否有进出口业务">
                <a-tag :color="supplierDetail.supplierMain.hasImportExport ? 'green' : 'red'">
                  {{ supplierDetail.supplierMain.hasImportExport ? '是' : '否' }}
                </a-tag>
              </a-descriptions-item>
              <a-descriptions-item label="企业网址">{{ supplierDetail.supplierMain.website }}</a-descriptions-item>
              <a-descriptions-item label="注册地址">{{ supplierDetail.supplierMain.regAddress }}</a-descriptions-item>
              <a-descriptions-item label="年销售额(万元)">{{ supplierDetail.supplierMain.annualSales }}</a-descriptions-item>
            </a-descriptions>
          </a-col>
        </a-row>
        <a-divider />
        <div class="profile-section">
          <h4>企业简介</h4>
          <p>{{ supplierDetail.supplierMain.corpProfile }}</p>
        </div>
      </a-card>

      <!-- 联系人信息 -->
      <a-card title="联系人信息" size="small" class="detail-card">
        <a-table
          :dataSource="supplierDetail.contactList"
          :columns="contactColumns"
          :pagination="false"
          size="middle"
          :scroll="{ x: 800 }"
        >
          <span slot="gender" slot-scope="gender">
            <a-tag :color="gender === '男' ? 'blue' : 'pink'">{{ gender }}</a-tag>
          </span>
        </a-table>
      </a-card>

      <!-- 财务信息 -->
      <a-card title="财务信息" size="small" class="detail-card">
        <a-table
          :dataSource="supplierDetail.financeList"
          :columns="financeColumns"
          :pagination="false"
          size="middle"
        />
      </a-card>

      <!-- 产品与业绩 -->
      <a-card title="产品与业绩" size="small" class="detail-card">
        <a-table
          :dataSource="supplierDetail.performanceList"
          :columns="performanceColumns"
          :pagination="false"
          size="middle"
        >
          <span slot="type" slot-scope="type">
            <a-tag :color="type === '产品' ? 'blue' : 'green'">{{ type }}</a-tag>
          </span>
        </a-table>
      </a-card>

      <!-- 资质信息 -->
      <a-card title="资质信息" size="small" class="detail-card">
        <a-table
          :dataSource="supplierDetail.qualificationList"
          :columns="qualificationColumns"
          :pagination="false"
          size="middle"
        >
          <span slot="type" slot-scope="type">
            <a-tag :color="getTypeColor(type)">{{ type }}</a-tag>
          </span>
        </a-table>
      </a-card>

      <!-- 订单信息 -->
      <a-card title="订单信息" size="small" class="detail-card">
        <a-table
          :dataSource="supplierDetail.orderList"
          :columns="orderColumns"
          :pagination="false"
          size="middle"
          :scroll="{ x: 1000 }"
        >
          <span slot="status" slot-scope="status">
            <a-tag :color="getStatusColor(status)">{{ getStatusText(status) }}</a-tag>
          </span>
        </a-table>
      </a-card>

      <!-- 评估信息 -->
      <a-card title="评估信息" size="small" class="detail-card">
        <a-table
          :dataSource="supplierDetail.evaluateList"
          :columns="evaluateColumns"
          :pagination="false"
          size="middle"
          v-if="supplierDetail.evaluateList && supplierDetail.evaluateList.length > 0"
        />
        <a-empty v-else description="暂无评估信息" />
      </a-card>
    </div>
  </a-modal>
</template>

<script>
import moment from 'moment'
import {mapState} from 'vuex'

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
    ...mapState({
      currentUser: state => state.account.user
    }),
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
      previewImage: '',
      supplierDetail: null,
      // 表格列定义
      contactColumns: [
        { title: '姓名', dataIndex: 'name', key: 'name', width: 100 },
        { title: '性别', dataIndex: 'gender', key: 'gender', width: 80 },
        { title: '职务', dataIndex: 'title', key: 'title', width: 120 },
        { title: '部门', dataIndex: 'department', key: 'department', width: 120 },
        { title: '联系方式', dataIndex: 'contactNum', key: 'contactNum', width: 120 },
        { title: '电子邮箱', dataIndex: 'email', key: 'email', width: 180 },
        { title: '类型', dataIndex: 'contactType', key: 'contactType', width: 100 }
      ],
      financeColumns: [
        { title: '年份', dataIndex: 'year', key: 'year', width: 100 },
        { title: '收入总额(万元)', dataIndex: 'totalRevenue', key: 'totalRevenue', width: 150 },
        { title: '净利润(万元)', dataIndex: 'netProfit', key: 'netProfit', width: 150 },
        { title: '其他财务数据', dataIndex: 'otherData', key: 'otherData' }
      ],
      orderColumns: [
        { title: '订单编号', dataIndex: 'code', key: 'code', width: 180 },
        { title: '物料名称', dataIndex: 'materialsName', key: 'materialsName', width: 150 },
        { title: '采购数量', dataIndex: 'purchaseNum', key: 'purchaseNum', width: 100 },
        { title: '总价', dataIndex: 'totalPrice', key: 'totalPrice', width: 100 },
        { title: '状态', dataIndex: 'status', key: 'status', width: 120 },
        { title: '创建日期', dataIndex: 'createDate', key: 'createDate', width: 160 }
      ],
      performanceColumns: [
        { title: '类型', dataIndex: 'type', key: 'type' },
        { title: '名称', dataIndex: 'name', key: 'name' },
        { title: '销售额(万元)', dataIndex: 'salesAmount', key: 'salesAmount' },
        { title: '占比(%)', dataIndex: 'salesPercentage', key: 'salesPercentage' },
        { title: '客户', dataIndex: 'customer', key: 'customer' }
      ],
      qualificationColumns: [
        { title: '证书名称', dataIndex: 'certName', key: 'certName' },
        { title: '机构', dataIndex: 'agency', key: 'agency' },
        { title: '签发日期', dataIndex: 'issueDate', key: 'issueDate' },
        { title: '有效期至', dataIndex: 'expiryDate', key: 'expiryDate' },
        { title: '类型', dataIndex: 'type', key: 'type' }
      ],
      evaluateColumns: [
        { title: '评估项目', dataIndex: 'item', key: 'item' },
        { title: '评分', dataIndex: 'score', key: 'score' },
        { title: '评估人', dataIndex: 'evaluator', key: 'evaluator' },
        { title: '评估日期', dataIndex: 'evaluateDate', key: 'evaluateDate' }
      ]
    }
  },
  watch: {
    moduleShow: function (value) {
      if (value) {
        this.querySupplierDetail()
      }
    }
  },
  methods: {
    getTypeColor (type) {
      const colorMap = {
        '认证': 'blue',
        '专利': 'green',
        '授权代理': 'purple'
      }
      return colorMap[type] || 'default'
    },

    getStatusText (status) {
      const statusMap = {
        '1': '待审核',
        '2': '审核通过',
        '3': '审核拒绝',
        '4': '执行中',
        '5': '已完成'
      }
      return statusMap[status] || '未知状态'
    },

    getStatusColor (status) {
      const colorMap = {
        '1': 'orange',
        '2': 'blue',
        '3': 'red',
        '4': 'purple',
        '5': 'green'
      }
      return colorMap[status] || 'default'
    },

    querySupplierDetail () {
      this.$get('/business/supplier-info/querySupplierDetail', {supplierUserId: this.moduleData.sysUserId}).then((r) => {
        this.supplierDetail = r.data
      }).catch(error => {
        this.$message.error('获取供应商详情失败')
        console.error(error)
      })
    },
    // local (module) {
    //   baiduMap.clearOverlays()
    //   baiduMap.rMap().enableScrollWheelZoom(true)
    //   let point = new BMap.Point(module.longitude, module.latitude)
    //   baiduMap.pointAdd(point)
    //   baiduMap.findPoint(point, 16)
    //   // let driving = new BMap.DrivingRoute(baiduMap.rMap(), {renderOptions:{map: baiduMap.rMap(), autoViewport: true}});
    //   // driving.search(new BMap.Point(this.nowPoint.lng,this.nowPoint.lat), new BMap.Point(scenic.point.split(",")[0],scenic.point.split(",")[1]));
    // },
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
<style lang="less" scoped>.supplier-detail-modal {
  .supplier-detail-content {
    max-height: 70vh;
    overflow-y: auto;

    .detail-card {
      margin-bottom: 16px;
      border-radius: 4px;

      &:last-child {
        margin-bottom: 0;
      }

      /deep/ .ant-card-head {
        background-color: #f7f9fc;
        border-bottom: 1px solid #e8e8e8;

        .ant-card-head-title {
          font-weight: 600;
          color: #333;
        }
      }

      /deep/ .ant-table-thead > tr > th {
        background-color: #fafafa;
        font-weight: 600;
      }
    }

    .profile-section {
      h4 {
        margin-bottom: 8px;
        color: #333;
      }

      p {
        color: #666;
        line-height: 1.6;
        text-align: justify;
      }
    }
  }
}
</style>
<style lang="less" scoped>
.supplier-detail-modal {
  .supplier-detail-content {
    max-height: 70vh;
    overflow-y: auto;
    padding: 16px;

    &::-webkit-scrollbar {
      width: 6px;
    }

    &::-webkit-scrollbar-track {
      background: #f1f1f1;
      border-radius: 10px;
    }

    &::-webkit-scrollbar-thumb {
      background: #c1c1c1;
      border-radius: 10px;
    }

    .detail-card {
      margin-bottom: 20px;
      border-radius: 8px;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.09);
      border: 1px solid #e8e8e8;
      transition: all 0.3s;

      &:hover {
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
      }

      &:last-child {
        margin-bottom: 0;
      }

      /deep/ .ant-card-head {
        background: linear-gradient(180deg, #fafafa 0%, #f5f5f5 100%);
        border-bottom: 1px solid #e8e8e8;
        border-radius: 8px 8px 0 0;
        padding: 0 24px;

        .ant-card-head-title {
          font-weight: 600;
          color: #1890ff;
          font-size: 16px;
          padding: 16px 0;
        }
      }

      /deep/ .ant-card-body {
        padding: 20px 24px;
      }

      /deep/ .ant-table-thead > tr > th {
        background-color: #f0f5ff;
        font-weight: 600;
        color: #595959;
        padding: 12px 16px;
      }

      /deep/ .ant-table-tbody > tr > td {
        padding: 12px 16px;
      }

      /deep/ .ant-descriptions-item-label {
        font-weight: 500;
        color: #595959;
        background-color: #fafafa;
      }

      /deep/ .ant-descriptions-item-content {
        color: #262626;
      }
    }

    .profile-section {
      margin-top: 16px;

      h4 {
        margin-bottom: 12px;
        color: #1890ff;
        font-size: 15px;
        font-weight: 600;
        position: relative;
        padding-left: 10px;

        &::before {
          content: '';
          position: absolute;
          left: 0;
          top: 2px;
          width: 4px;
          height: 16px;
          background-color: #1890ff;
          border-radius: 2px;
        }
      }

      p {
        color: #595959;
        line-height: 1.8;
        text-align: justify;
        font-size: 14px;
        padding: 0 10px;
      }
    }

    /deep/ .ant-tag {
      font-size: 12px;
      padding: 0 7px;
      border-radius: 4px;
      font-weight: 500;
    }

    /deep/ .ant-table {
      border-radius: 4px;

      &-thead > tr > th {
        border-bottom: 1px solid #e8e8e8;
      }

      &-tbody > tr:last-child > td {
        border-bottom: none;
      }
    }
  }
}
</style>
