<template>
  <a-row :gutter="8" style="width: 100%">
    <a-col :span="10">
      <div style="background:#ECECEC; padding:30px;margin-top: 30px">
        <a-card :bordered="false">
          <b style="font-size: 15px">我的信息</b>
        </a-card>
        <a-card :bordered="false">
          <a-form :form="form" layout="vertical">
            <a-row :gutter="10">
              <a-col :span="12">
                <a-form-item label='员工名称'>
                  <a-input disabled v-decorator="[
            'name',
            { rules: [{ required: true, message: '请输入名称!' }] }
            ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label='性别' v-bind="formItemLayout">
                  <a-select v-decorator="[
              'staffSex',
              { rules: [{ required: true, message: '请输入性别!' }] }
              ]">
                    <a-select-option value="1">男</a-select-option>
                    <a-select-option value="2">女</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label='出生日期'>
                  <a-input v-decorator="[
            'birthDate',
            ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label='邮箱地址'>
                  <a-input v-decorator="[
            'email',
            { rules: [{ required: true, message: '请输邮箱地址!' }] }
            ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label='联系方式'>
                  <a-input v-decorator="[
            'phone',
            { rules: [{ required: true, message: '请输入联系方式!' }] }
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
                <a-form-item label='员工图片' v-bind="formItemLayout">
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
              <a-col :span="24">
                <a-form-item>
                  <a-button
                    size="large"
                    type="primary"
                    htmlType="submit"
                    class="register-button"
                    style="width: 35%;float: right;margin-top: 50px"
                    :loading="loading"
                    @click.stop.prevent="handleSubmit"
                    :disabled="loading">立即修改
                  </a-button>
                </a-form-item>
              </a-col>
            </a-row>
          </a-form>
        </a-card>
      </div>
    </a-col>
    <a-col :span="16">
<!--      <div style="background:#ECECEC; padding:30px;margin-top: 30px">-->
<!--        <a-card :bordered="false">-->
<!--          <a-spin :spinning="dataLoading">-->
<!--            <a-calendar>-->
<!--              <ul slot="dateCellRender" slot-scope="value" class="events">-->
<!--                <li v-for="item in getListData(value)" :key="item.content">-->
<!--                  <a-badge :status="item.type" :text="item.content" />-->
<!--                </li>-->
<!--              </ul>-->
<!--            </a-calendar>-->
<!--          </a-spin>-->
<!--        </a-card>-->
<!--      </div>-->
    </a-col>
  </a-row>
</template>

<script>
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
      previewImage: ''
    }
  },
  mounted () {
    this.dataInit()
  },
  methods: {
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
    isDuringDate (beginDateStr, endDateStr, curDataStr) {
      let curDate = new Date(curDataStr)
      let beginDate = new Date(beginDateStr)
      let endDate = new Date(endDateStr)
      if (curDate >= beginDate && curDate <= endDate) {
        return true
      }
      return false
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
      this.$get(`/business/staff-info/queryStaffBySysUserId`, {sysUserId: this.currentUser.userId}).then((r) => {
        this.rowId = r.data.data.id
        this.setFormValues(r.data.data)
        this.dataLoading = false
      })
    },
    setFormValues ({...student}) {
      this.rowId = student.id
      let fields = ['name', 'staffSex', 'birthDate', 'content', 'email', 'phone']
      let obj = {}
      Object.keys(student).forEach((key) => {
        if (key === 'images') {
          this.fileList = []
          this.imagesInit(student['images'])
        }
        if (key === 'staffSex' && student[key] != null) {
          student[key] = student[key].toString()
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
        values.images = images.length > 0 ? images.join(',') : null
        if (!err) {
          this.loading = true
          this.$put('/business/staff-info', {
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
