### 基于SpringBoot + Vue的供应链协同系统.

#### 安装环境

JAVA 环境 

Node.js环境 [https://nodejs.org/en/] 选择14.17

Yarn 打开cmd， 输入npm install -g yarn !!!必须安装完毕nodejs

Mysql 数据库 [https://blog.csdn.net/qq_40303031/article/details/88935262] 一定要把账户和密码记住

redis

Idea 编译器 [https://blog.csdn.net/weixin_44505194/article/details/104452880]

WebStorm OR VScode 编译器 [https://www.jianshu.com/p/d63b5bae9dff]

#### 采用技术及功能

后端：SpringBoot、MybatisPlus、MySQL、Redis、
前端：Vue、Apex、Antd、Axios

平台前端：vue(框架) + vuex(全局缓存) + rue-router(路由) + axios(请求插件) + apex(图表)  + antd-ui(ui组件)

平台后台：springboot(框架) + redis(缓存中间件) + shiro(权限中间件) + mybatisplus(orm) + restful风格接口 + mysql(数据库)

开发环境：windows10 or windows7 ， vscode or webstorm ， idea + lambok


#### 前台启动方式
安装所需文件 yarn install 
运行 yarn run dev

#### 默认后台账户密码
[管理员]
admin
1234qwer

###### 管理员：
公告管理，代办任务，供应商管理，供应商审核，供应商物料， 消息通知，仓库管理，库存管理，库存预警，入库记录，出库记录，员工管理，库存下限管理，采购需求，采购计划，采购订单， 订单物流，采购计划下发，采购计划比价，异常反馈，物料历史报价，出入库明细，供应商绩效评价，订单退货，数据统计。

###### 供应商：
供应商信息管理，供应商提交审核，供应商物料，采购计划接收，采购计划报价，采购订单，订单物流，消息通知，在线沟通，异常反馈，供应商绩效。

###### 员工：
员工信息，代办任务，消息通知，采购计划，采购订单，订单物流，采购计划比价，物料历史报价，异常反馈记录，在线沟通。

#### 项目截图
暂无

|  |  |
|---------------------|---------------------|
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/97ef025b-5b0b-4fcc-9ad6-e368b38623ad.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/e3689b2a-c41c-4421-96a4-04b4b897c624.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/34fc0b7f-1a19-4879-b0fe-af41cec0eed7.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/e6bb4458-27c5-4beb-a484-780b791635f7.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/20fdc94a-0373-4344-86da-58e706622cfa.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/d1afd948-f267-4327-ad34-95b7adedc4e0.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/8a9d4d02-4386-4e98-b509-6b078db82693.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/ccb7a11c-a98b-470d-832e-9a39c1e5dda5.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/5da36a26-d203-4d9d-9db2-cf5782cb51bd.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/cc302dea-aec6-4397-806f-b90c5e690967.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/5c792ab9-361f-423e-96a3-c3654935a07d.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/cb2327fa-ce0a-4fcc-80ed-312b69a00c95.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/3a7b7340-fd3e-4164-9719-019a0d3661f5.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/ca51e515-c8be-420f-83f1-af18925aa8c9.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1c952abb-d59c-4799-a2e5-a5b73b42a9db.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/baa2ed0e-ad28-4d23-be0a-9c001a805052.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1b757330-4026-4a04-8369-568b903c09fb.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/b19ddb94-7c2d-43ab-806a-736596e4969a.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/0f206722-7037-47d8-bc0f-eb2fe44411ba.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/acadd584-e8fe-4aa9-88e0-8475d153d463.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/0a74439f-06e3-4558-ad73-e75988c26cf5.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/93225b53-5974-4898-a78d-910694d14333.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/ff7fbcb1-3e38-4006-93e9-c35b78cde1f0.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/24474dfc-bbda-4658-947f-63365bd077fb.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/f59bed13-b5ce-408b-8b59-f8b843199699.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/999b3eeb-c374-4c7c-aa9f-05a27590e2ef.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/ec96243e-9105-49e0-9a72-866b94866cef.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/563a9857-e870-4102-8dbe-d482b4920022.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/ebd94652-7292-4696-abd8-abc2d03afac9.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/work/936e9baf53eb9a217af4f89c616dc19.png) |

#### 演示视频

暂无

#### 获取方式

Email: fan1ke2ke@gmail.com

WeChat: `Storm_Berserker`

`附带部署与讲解服务，因为要恰饭资源非免费，伸手党勿扰，谢谢理解😭`

> 1.项目纯原创，不做二手贩子 2.一次购买终身有效 3.项目讲解持续到答辩结束 4.非常负责的答辩指导 5.黑奴价格

> 项目部署调试不好包退！功能逻辑没讲明白包退！

#### 其它资源

[2025年-答辩顺利通过-客户评价🍜](https://berserker287.github.io/2025/06/18/2025%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2024年-答辩顺利通过-客户评价👻](https://berserker287.github.io/2024/06/06/2024%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2023年-答辩顺利通过-客户评价🐢](https://berserker287.github.io/2023/06/14/2023%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2022年-答辩通过率100%-客户评价🐣](https://berserker287.github.io/2022/05/25/%E9%A1%B9%E7%9B%AE%E4%BA%A4%E6%98%93%E8%AE%B0%E5%BD%95/)

[毕业答辩导师提问的高频问题](https://berserker287.github.io/2023/06/13/%E6%AF%95%E4%B8%9A%E7%AD%94%E8%BE%A9%E5%AF%BC%E5%B8%88%E6%8F%90%E9%97%AE%E7%9A%84%E9%AB%98%E9%A2%91%E9%97%AE%E9%A2%98/)

[50个高频答辩问题-技术篇](https://berserker287.github.io/2023/06/13/50%E4%B8%AA%E9%AB%98%E9%A2%91%E7%AD%94%E8%BE%A9%E9%97%AE%E9%A2%98-%E6%8A%80%E6%9C%AF%E7%AF%87/)

[计算机毕设答辩时都会问到哪些问题？](https://www.zhihu.com/question/31020988)

[计算机专业毕业答辩小tips](https://zhuanlan.zhihu.com/p/145911029)

#### 接JAVAWEB毕设，纯原创，价格公道，诚信第一

`网站建设、小程序、H5、APP、各种系统 选题+开题报告+任务书+程序定制+安装调试+项目讲解+论文+答辩PPT`

More info: [悲伤的橘子树](https://berserker287.github.io/)
