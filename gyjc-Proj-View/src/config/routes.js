import ippIndex from '@/page/ippIndex'
import inlineTable from '../page/poolZb/inline-edit-table'
import apiService from '@/page/frApi/apiService'
import frView from '@/page/frApi/frView'
import apiView from '@/page/frApi/apiView'
import subroute from '@/frame/subroute'
import newZbTable from '@/page/poolZb/newZbTable'
import tableManage from '@/page/poolZb/tableManage'
import ywsystem from '@/page/dataSource/ywSystemData'
import statistic from '@/page/dataSource/statisticData'
import secureData from '@/page/dataSource/securitySituationData'

import basestation from '@/page/dataSource/5GBasestation'
import coordinationSchedule from '@/components/basestationCoordinationSchedule'
import useSchedule from '@/components/basestationUseSchedule'
import earthStation from '@/components/satelliteEarthStation'
import tsgyzb from '@/page/dataSource/tsgyzb/tsgyzb'
//import activeConnect from '@/page/dataSource/secure/activeConnect'
import onlineMonitor from '@/page/dataSource/secure/onlineMonitor'
import threatTrapOnline from '@/page/dataSource/secure/threatTrapOnline'

import zsTableManage from '@/page/poolZb/zsTableManage'
import systemManage from '@/page/manage/systemManage'
import noticeManage from '@/page/manage/noticeManage'
import logManage from '@/page/manage/logManage'
import userManage from '@/page/manage/userManage'
import excelImp from '@/page/excel/excel-imp'
import handImport from '@/page/excel/excel-imp-hand'
import trapLog from '@/page/excel/trap-log'
import handImpData from '@/page/dataSource/handImportData'
import login from '@/page/login'
import register from '@/page/register'
import dataTransMenu from '@/page/excel/excel-menu'
import noticeInfo from '@/page/notice/noticeInfo'
import moreNotices from '@/page/notice/moreNotices'


export default[
    {
        //登录
        path:'/login',
        component:login
    },
    {
        //注册
        path:'/register',
        component:register
    },
    {
    	path:'/',
    	component:subroute,
    	children:[
  		    {
              //默认根节点页面，首页
              path:'/',component:ippIndex
          },

          {
              //指标池管理
              path:'inlineTable',
              component:inlineTable
          },
          {
              //接口管理
              path:'apiService',
              component:apiService,
              children:[{
                path:'/apiService',
                redirect: '/apiService/frView'
              },{
                path:'/apiService/frView',
                component:frView
              },{
                path:'/apiService/apiView',
                component:apiView
              }]
          },
        	{
              //创建主题
              path:'newZbTable',
              component:newZbTable
          },
          {
              //主题库
              path:'tableManage',
              component:tableManage
          },
          {
              //工业运行数据
              path:'dataSource/ywdatamove',
              component:ywsystem
          },
          {
              //国家数据网
              path:'dataSource/statisticData',
              component:statistic
          },
          {
              //安全态势数据
              path:'dataSource/secureData',
              component: secureData,
              redirect: '/dataSource/secureData/onlineMonitor',
              children:[{
                path:'/dataSource/secureData/onlineMonitor',
                component: onlineMonitor
              },{
                path:'/dataSource/secureData/threatTrapOnline',
                component: threatTrapOnline
              }]
          },
          {
              //手动导入
              path:'dataSource/handImpData',
              component:handImpData
          },
          {
              //5G基站、特色工业指标
              path:'dataSource/basestation',
              component:basestation,
              redirect: '/dataSource/basestation/earthStation',
              children:[{
                path:'/dataSource/basestation/coordinationSchedule',
                component:coordinationSchedule
              },{
                path:'/dataSource/basestation/useSchedule',
                component:useSchedule
              },{
                path:'/dataSource/basestation/earthStation',
                component:earthStation
              },{
                path:'/dataSource/basestation/tsgyzb',
                component:tsgyzb
              }/*,
                {
                    path:'/dataSource/basestation/activeConnect', component:activeConnect
                }*/
              ]
          },
          {
              //展示库
              path:'zsTableManage',
              component:zsTableManage
          },
          {
              //系统管理
              path:'systemManage',
              component:systemManage,
              children:[{
                path:'/systemManage',
                redirect: '/systemManage/userManage'
              },{
                path:'/systemManage/userManage',
                component:userManage
              },{
                path:'/systemManage/logManage',
                component:logManage
              },{
                path:'/systemManage/noticeManage/:title',
                name:'noticeManage',
                component:noticeManage,
              }]
          },
          {
              //数据传输
              path:'dataTrans',
              component:dataTransMenu,
              children:[{
                  path:'/dataTrans',redirect: '/dataTrans/excelImp'
              },{
                  path:'/dataTrans/excelImp',component:excelImp
              },{
                  path:'/dataTrans/handImport',component:handImport
              },{
                  path:'/dataTrans/trapLog',component:trapLog
              }]
          },{
              //消息详情
              path:'noticeInfo/:id-:flag',
              name:'noticeInfo',
              component:noticeInfo
          },{
              //更多详情
              path:'moreNotices/:title',
              name:'moreNotices',
              component:moreNotices
        }
    	]
    }
]
