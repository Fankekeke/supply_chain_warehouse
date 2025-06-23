<template>
  <a-modal v-model="show" title="供应物料详情" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="moduleData !== null"></div>
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
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: ''
    }
  },
  watch: {
    moduleShow: function (value) {
      if (value) {
        if (this.moduleData.materialsImages !== null && this.moduleData.materialsImages !== '') {
          this.imagesInit(this.moduleData.materialsImages)
        }
      }
    }
  },
  methods: {
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
