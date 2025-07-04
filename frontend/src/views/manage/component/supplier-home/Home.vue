<template>
  <div>
    <a-row style="margin-top: 15px">
      <a-col :span="24">
        <div style="background: #ECECEC; padding: 30px;" v-if="user.roleId == 76">
          <a-row :gutter="16">
            <a-col :span="6">
              <a-card hoverable :style="{ boxShadow: '0 4px 8px rgba(0,0,0,0.1)' }">
                <a-row>
                  <a-col :span="24" style="font-size: 13px;margin-bottom: 8px;font-family: SimHei">本月订单量</a-col>
                  <a-col :span="4"><a-icon type="arrow-up" style="font-size: 30px;margin-top: 3px" :style="{ transition: 'transform 0.3s ease', transform: 'translateY(-2px)' }"/></a-col>
                  <a-col :span="18" style="font-size: 28px;font-weight: 500;font-family: SimHei;">
                    <span style="color: #ff6600; text-shadow: 0 0 5px rgba(255,102,0,0.3);">{{ titleData.monthOrderNum }}</span>
                    <span style="font-size: 20px;margin-top: 3px">单</span>
                  </a-col>
                </a-row>
              </a-card>
            </a-col>
            <a-col :span="6">
              <a-card hoverable :style="{ boxShadow: '0 4px 8px rgba(0,0,0,0.1)' }">
                <a-row>
                  <a-col :span="24" style="font-size: 13px;margin-bottom: 8px;font-family: SimHei">本月收入</a-col>
                  <a-col :span="4"><a-icon type="arrow-up" style="font-size: 30px;margin-top: 3px" :style="{ transition: 'transform 0.3s ease', transform: 'translateY(-2px)' }"/></a-col>
                  <a-col :span="18" style="font-size: 28px;font-weight: 500;font-family: SimHei">
                    <span style="color: #ff6600; text-shadow: 0 0 5px rgba(255,102,0,0.3);">{{ titleData.monthOrderPrice }}</span>
                    <span style="font-size: 20px;margin-top: 3px">元</span>
                  </a-col>
                </a-row>
              </a-card>
            </a-col>
            <a-col :span="6">
              <a-card hoverable :style="{ boxShadow: '0 4px 8px rgba(0,0,0,0.1)' }">
                <a-row>
                  <a-col :span="24" style="font-size: 13px;margin-bottom: 8px;font-family: SimHei">本年订单量</a-col>
                  <a-col :span="4"><a-icon type="arrow-up" style="font-size: 30px;margin-top: 3px" :style="{ transition: 'transform 0.3s ease', transform: 'translateY(-2px)' }"/></a-col>
                  <a-col :span="18" style="font-size: 28px;font-weight: 500;font-family: SimHei">
                    <span style="color: #ff6600; text-shadow: 0 0 5px rgba(255,102,0,0.3);">{{ titleData.yearOrderNum }}</span>
                    <span style="font-size: 20px;margin-top: 3px">单</span>
                  </a-col>
                </a-row>
              </a-card>
            </a-col>
            <a-col :span="6">
              <a-card hoverable :style="{ boxShadow: '0 4px 8px rgba(0,0,0,0.1)' }">
                <a-row>
                  <a-col :span="24" style="font-size: 13px;margin-bottom: 8px;font-family: SimHei">本年收入</a-col>
                  <a-col :span="4"><a-icon type="arrow-up" style="font-size: 30px;margin-top: 3px" :style="{ transition: 'transform 0.3s ease', transform: 'translateY(-2px)' }"/></a-col>
                  <a-col :span="18" style="font-size: 28px;font-weight: 500;font-family: SimHei">
                    <span style="color: #ff6600; text-shadow: 0 0 5px rgba(255,102,0,0.3);">{{ titleData.yearOrderPrice }}</span>
                    <span style="font-size: 20px;margin-top: 3px">元</span>
                  </a-col>
                </a-row>
              </a-card>
            </a-col>
          </a-row>
        </div>
      </a-col>
    </a-row>
    <a-row style="margin-top: 15px" v-if="user.roleId == 76">
      <a-col :span="12">
        <a-card hoverable :bordered="false" style="width: 100%">
          <a-skeleton active v-if="loading" />
          <apexchart  v-if="!loading" type="line" height="300" :options="chartOptions" :series="series"></apexchart>
        </a-card>
      </a-col>
      <a-col :span="12">
        <a-card hoverable :bordered="false" style="width: 100%">
          <a-skeleton active v-if="loading" />
          <apexchart v-if="!loading" type="bar" height="300" :options="chartOptions1" :series="series1"></apexchart>
        </a-card>
      </a-col>
    </a-row>
    <a-row :gutter="15">
<!--      <a-col :span="9" v-if="user.roleId == 74">-->
<!--        <a-card hoverable :bordered="false" style="width: 100%;margin-bottom: 10px">-->
<!--          <a-skeleton active v-if="loading" />-->
<!--          <apexchart v-if="!loading" type="donut" height="270" :options="chartOptions2" :series="series2"></apexchart>-->
<!--        </a-card>-->
<!--        <a-card hoverable :bordered="false" style="width: 100%">-->
<!--          <a-skeleton active v-if="loading" />-->
<!--          <apexchart v-if="!loading" type="pie" height="270" :options="chartOptions3" :series="series3"></apexchart>-->
<!--        </a-card>-->
<!--      </a-col>-->
      <a-col :span="24">
        <a-card hoverable :loading="loading" :bordered="false" title="公告信息">
          <div style="padding: 0 22px">
            <a-list item-layout="vertical" :pagination="pagination" :data-source="bulletinList">
              <a-list-item slot="renderItem" key="item.title" slot-scope="item, index">
                <template slot="actions">
              <span key="message">
                <a-icon type="message" style="margin-right: 8px" />
                {{ item.date }}
              </span>
                </template>
                <a-list-item-meta :description="item.content" style="font-size: 14px">
                  <a slot="title">{{ item.title }}</a>
                </a-list-item-meta>
              </a-list-item>
            </a-list>
          </div>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script>
import {mapState} from 'vuex'
export default {
  name: 'Home',
  computed: {
    ...mapState({
      multipage: state => state.setting.multipage,
      user: state => state.account.user
    })
  },
  data () {
    return {
      pagination: {
        onChange: page => {
          console.log(page)
        },
        pageSize: 2
      },
      bulletinList: [],
      titleData: {
        orderCode: 0,
        orderPrice: 0,
        pharmacyNum: 0,
        staffNum: 0
      },
      loading: false,
      series: [{
        name: '收益',
        data: []
      }],
      chartOptions: {
        chart: {
          type: 'line',
          height: 300
        },
        xaxis: {
          categories: []
        },
        stroke: {
          curve: 'stepline'
        },
        dataLabels: {
          enabled: false
        },
        title: {
          text: '近十天支出统计',
          align: 'left'
        },
        markers: {
          hover: {
            sizeOffset: 4
          }
        }
      },
      series1: [],
      chartOptions1: {
        chart: {
          type: 'bar',
          height: 300
        },
        title: {
          text: '近十天订单统计',
          align: 'left'
        },
        plotOptions: {
          bar: {
            horizontal: false,
            columnWidth: '55%'
          }
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          show: true,
          width: 2,
          colors: ['transparent']
        },
        xaxis: {
          categories: []
        },
        yaxis: {
          title: {
            text: ''
          }
        },
        fill: {
          opacity: 1
        },
        tooltip: {
          y: {
            formatter: function (val) {
              return val + ' 单'
            }
          }
        }
      },
      series2: [10, 39, 51],
      chartOptions2: {
        chart: {
          type: 'donut',
          height: 300
        },
        labels: ['电器', '零部件', '焊件'],
        title: {
          text: '采购类型价格统计',
          align: 'left'
        },
        responsive: [{
          breakpoint: 380,
          options: {
            chart: {
              width: 200
            },
            legend: {
              position: 'bottom'
            }
          }
        }]
      },
      series3: [10, 39, 51],
      chartOptions3: {
        chart: {
          type: 'pie',
          height: 300
        },
        labels: ['电器', '零部件', '焊件'],
        title: {
          text: '采购类型数量统计',
          align: 'left'
        },
        responsive: [{
          breakpoint: 380,
          options: {
            chart: {
              width: 200
            },
            legend: {
              position: 'bottom'
            }
          }
        }]
      }
    }
  },
  mounted () {
    console.log(this.user)
    this.loading = true
    this.selectHomeData()
    setTimeout(() => {
      this.loading = false
    }, 200)
  },
  methods: {
    selectHomeData () {
      this.$get('/business/order-info/home/data/supplier', {userId: this.user.userId}).then((r) => {
        this.titleData.monthOrderNum = r.data.monthOrderNum
        this.titleData.monthOrderPrice = r.data.monthOrderPrice
        this.titleData.yearOrderNum = r.data.yearOrderNum
        this.titleData.yearOrderPrice = r.data.yearOrderPrice
        this.bulletinList = r.data.bulletin
        let values = []
        if (r.data.orderNumWithinDays !== null && r.data.orderNumWithinDays.length !== 0) {
          if (this.chartOptions1.xaxis.categories.length === 0) {
            console.log(r.data.orderNumWithinDays)
            this.chartOptions1.xaxis.categories = Array.from(r.data.orderNumWithinDays, ({days}) => days)
          }
          let itemData = { name: '订单数', data: Array.from(r.data.orderNumWithinDays, ({count}) => count) }
          values.push(itemData)
          this.series1 = values
        }
        this.series[0].data = Array.from(r.data.orderPriceWithinDays, ({price}) => price)
        this.chartOptions.xaxis.categories = Array.from(r.data.orderPriceWithinDays, ({days}) => days)
      })
    }
  }
}
</script>

<style scoped>

</style>
