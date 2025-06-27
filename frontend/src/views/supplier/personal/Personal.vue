<template>
  <a-row :gutter="8" style="width: 100%">
    <a-col :span="8">
      <div style="background:#ECECEC; padding:30px;margin-top: 30px">
        <a-card :bordered="false">
          <b style="font-size: 15px">我的信息</b>
        </a-card>
        <a-card :bordered="false">
          <a-form :form="form" layout="vertical">
            <a-row :gutter="10">
              <a-col :span="12">
                <a-form-item label='供应商名称'>
                  <a-input v-decorator="[
            'name',
            { rules: [{ required: true, message: '请输入名称!' }] }
            ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label='可供类型'>
                  <a-input v-decorator="[
            'supplyType',
            { rules: [{ required: true, message: '请输入可供类型!' }] }
            ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label='地址'>
                  <a-input-search
                    v-decorator="[
              'address',
              { rules: [{ required: true, message: '请输入地址!' }] }
              ]"
                    enter-button="选择"
                    @search="showChildrenDrawer"
                  />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label='经度'>
                  <a-input v-decorator="[
            'longitude',
            { rules: [{ required: true, message: '请输入经度!' }] }
            ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label='纬度'>
                  <a-input v-decorator="[
            'latitude',
            { rules: [{ required: true, message: '请输入纬度!' }] }
            ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label='信用代码'>
                  <a-input v-decorator="[
            'creditCode',
            { rules: [{ required: true, message: '请输入信用代码!' }] }
            ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label='营业执照号'>
                  <a-input v-decorator="[
            'businessLicense',
            { rules: [{ required: true, message: '请输入营业执照号!' }] }
            ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label='开户银行'>
                  <a-input v-decorator="[
            'bankName',
            { rules: [{ required: true, message: '请输入开户银行!' }] }
            ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label='银行账号'>
                  <a-input v-decorator="[
            'bankAccount',
            { rules: [{ required: true, message: '请输入银行账号!' }] }
            ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label='负责人'>
                  <a-input v-decorator="[
            'chargePerson',
            { rules: [{ required: true, message: '请输入负责人!' }] }
            ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label='联系电话'>
                  <a-input v-decorator="[
            'phone',
            { rules: [{ required: true, message: '请输入联系电话!' }] }
            ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="24">
                <a-form-item label='备注内容' v-bind="formItemLayout">
                  <a-textarea :rows="6" v-decorator="[
            'content'
            ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="24">
                <a-form-item label='供应商图片' v-bind="formItemLayout">
                  <a-upload
                    name="avatar"
                    action="http://127.0.0.1:9527/file/fileUpload/"
                    list-type="picture-card"
                    :file-list="fileList"
                    @preview="handlePreview"
                    @change="picHandleChange"
                  >
                    <div v-if="fileList.length < 8">
                      <a-icon type="plus"/>
                      <div class="ant-upload-text">
                        Upload
                      </div>
                    </div>
                  </a-upload>
                  <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
                    <img alt="example" style="width: 100%" :src="previewImage"/>
                  </a-modal>
                </a-form-item>
              </a-col>
            </a-row>
          </a-form>
        </a-card>
      </div>
    </a-col>
    <a-col :span="16">
      <div style="background:#ECECEC; padding:30px;margin-top: 30px">
        <a-card :bordered="false">
          <a-spin :spinning="dataLoading">
            <a-calendar>
              <ul slot="dateCellRender" slot-scope="value" class="events">
                <li v-for="item in getListData(value)" :key="item.content">
                  <a-badge :status="item.type" :text="item.content" />
                </li>
              </ul>
            </a-calendar>
          </a-spin>
        </a-card>
      </div>
      <drawerMap :childrenDrawerShow="childrenDrawer" @handlerClosed="handlerClosed"></drawerMap>
    </a-col>
  </a-row>
</template>

<script>
import baiduMap from '@/utils/map/baiduMap'
import drawerMap from '@/utils/map/searchmap/drawerMap'
import {mapState} from 'vuex'
import moment from 'moment'
moment.locale('zh-cn')
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'User',
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    })
  },
  data () {
    return {
      rowId: null,
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      courseInfo: [],
      dataLoading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      localPoint: {},
      stayAddress: '',
      childrenDrawer: false
    }
  },
  components: {
    drawerMap
  },
  mounted () {
    this.dataInit()
  },
  methods: {
    handlerClosed (localPoint) {
      this.childrenDrawer = false
      if (localPoint !== null && localPoint !== undefined) {
        this.localPoint = localPoint
        let address = baiduMap.getAddress(localPoint)
        address.getLocation(localPoint, (rs) => {
          if (rs != null) {
            if (rs.address !== undefined && rs.address.length !== 0) {
              this.stayAddress = rs.address
            }
            if (rs.surroundingPois !== undefined) {
              if (rs.surroundingPois.address !== undefined && rs.surroundingPois.address.length !== 0) {
                this.stayAddress = rs.surroundingPois.address
              }
            }
            let obj = {}
            obj['address'] = this.stayAddress
            obj['longitude'] = localPoint.lng
            obj['latitude'] = localPoint.lat
            this.form.setFieldsValue(obj)
          }
        })
      }
    },
    addPoint (point) {
      this.localPoint = point
    },
    showChildrenDrawer () {
      this.childrenDrawer = true
    },
    onChildrenDrawerClose () {
      this.childrenDrawer = false
    },
    isDuringDate (beginDateStr, endDateStr, curDataStr) {
      let curDate = new Date(curDataStr)
      let beginDate = new Date(beginDateStr)
      let endDate = new Date(endDateStr)
      if (curDate >= beginDate && curDate <= endDate) {
        return true
      }
      return false
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
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    getListData (value) {
      let listData = []
      this.courseInfo.forEach(item => {
        if ((moment(value).format('YYYY-MM-DD')) === (moment(item.createDate).format('YYYY-MM-DD'))) {
          listData.push({type: 'success', content: item.remark})
        }
      })
      return listData || []
    },
    dataInit () {
      this.dataLoading = true
      this.$get(`/business/supplier-info/querySupplierBySysUserId`, {sysUserId: this.currentUser.userId}).then((r) => {
        this.rowId = r.data.user.id
        this.setFormValues(r.data.user)
        this.courseInfo = r.data.order
        this.dataLoading = false
      })
    },
    setFormValues ({...student}) {
      this.rowId = student.id
      let fields = ['name', 'address', 'content', 'longitude', 'latitude', 'content', 'creditCode', 'chargePerson', 'phone', 'supplyType', 'businessLicense', 'bankName', 'bankAccount']
      let obj = {}
      Object.keys(student).forEach((key) => {
        if (key === 'images') {
          this.fileList = []
          this.imagesInit(module['images'])
        }
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          obj[key] = student[key]
        }
      })
      this.form.setFieldsValue(obj)
    },
    handleSubmit () {
      // 获取图片List
      let images = []
      this.fileList.forEach(image => {
        if (image.response !== undefined) {
          images.push(image.response)
        } else {
          images.push(image.name)
        }
      })
      this.form.validateFields((err, values) => {
        values.id = this.rowId
        if (!err) {
          this.loading = true
          this.$put('/business/supplier-info', {
            ...values
          }).then((r) => {
            this.$message.success('修改信息成功')
            this.loading = false
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
