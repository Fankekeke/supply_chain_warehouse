<template>
  <a-modal v-model="show" title="在线沟通" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="moduleData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span
          class="view-title">在线沟通</span></a-col>
        <a-col :span="24">
          <a-list
            class="comment-list"
            item-layout="horizontal"
            :data-source="replyList"
          >
            <a-list-item slot="renderItem" slot-scope="item, index">
              <a-comment :author="item.author">
                <p slot="author">
                  <span v-if="item.type == 1">
                      {{ moduleData.supplierName ? moduleData.supplierName : '' }}
                    </span>
                  <span v-if="item.type == 2">
                      管理员
                    </span>
                </p>
                <p slot="avatar">
                  <a-avatar
                    slot="avatar"
                    shape="square"
                    :style="{ verticalAlign: 'middle' }"
                  >
                    <span v-if="item.type == 1">
                      {{ moduleData.supplierName ? moduleData.supplierName.charAt(0) : '' }}
                    </span>
                    <span v-if="item.type == 2">
                      管理员
                    </span>
                  </a-avatar>
                </p>
                <div slot="content" style="font-size: 13px">
                  {{ item.content }}
                </div>
                <a-tooltip slot="datetime" :title="item.createDate">
                  <span>{{ item.createDate }}</span>
                </a-tooltip>
              </a-comment>
            </a-list-item>
          </a-list>
          <div>
            <a-textarea :rows="4" v-model="content" />
            <a-button html-type="submit" type="primary" @click="handleSubmit" style="margin-top: 15px">
              回复
            </a-button>
          </div>
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
    }
  },
  data () {
    return {
      content: '',
      loading: false,
      fileList: [],
      replyList: [],
      previewVisible: false,
      previewImage: ''
    }
  },
  watch: {
    moduleShow: function (value) {
      if (value) {
        this.queryReplyByQuotationId(this.moduleData.id)
      }
    }
  },
  methods: {
    queryReplyByQuotationId (id) {
      this.$get('/business/purchase-quotation-info/queryReplyByQuotationId', {
        quotationId: id
      }).then(res => {
        this.replyList = res.data.data
        console.log(this.replyList)
      })
    },
    handleSubmit () {
      if (!this.content) {
        this.$message.error('请输入回复内容')
        return false
      }
      this.$get('/business/purchase-quotation-info/replyAdmin', {
        content: this.content,
        quotationId: this.moduleData.id
      }).then(res => {
        this.content = ''
        this.queryReplyByQuotationId(this.moduleData.id)
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
 >>> .ant-list-item {
   padding: 0;
 }
</style>
