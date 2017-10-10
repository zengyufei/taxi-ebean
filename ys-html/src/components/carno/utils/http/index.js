import axios from 'axios'
import qs from 'qs'
import { Modal } from 'antd'
import { session } from '../storage'

const domains = require('configs/domains')

const baseUrl = domains[process.env.NODE_ENV] && domains[process.env.NODE_ENV].baseUrl

const { tokenSessionKey, redirectLoginUrl, redirectNotPermissionUrl } = constant

function setHeader(header) {
  // 每次发送请求之前检测都vuex存有token,那么都要放在请求头发送给服务器
  const token = session.get(tokenSessionKey)
  token && (header.token = token)
  return header
}
// 设置全局axios默认值
//axios.defaults.withCredentials = false // 是否允许带 cookies
axios.defaults.baseURL = baseUrl || ''
// axios.defaults.timeout = 20000 // 5000的超时验证
// axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';

// 添加请求拦截器
axios.interceptors.request.use(config => {
  // 添加请求后缀
  /* if (!/.htm$/.test(config.url)) {
    config.url += '.htm'
  } */
  if (/update/.test(config.url)) {
    let temp = qs.parse(config.data)
    delete temp.createTime
    delete temp.updateTime
    config.data = qs.stringify(temp)
  }

  setHeader(config.headers)
  // config.withCredentials = true  // 需要跨域打开此配置

  // post提交 data存在 并且 data不是FormData对象时对数据进行json化处理
  // if(config.method==='post' && config.data && config.data.constructor !== FormData){
  //   config.data = qs.stringify(config.data)
  //   config.headers['Content-Type'] = 'application/x-www-form-urlencoded'
  // }

  // 开启loading动画

  return config
}, error => {
  // Do something with request error
  return Promise.reject(error)
})

// 添加响应拦截器
axios.interceptors.response.use(response => {
  const token = session.get(tokenSessionKey)
  if (!token) {
    window.location.href = redirectLoginUrl
  }

  let resData = response.data
  if (resData) {
    if (resData.code) {
      if (+resData.code !== 200) {
        Modal.error({ title: '错误提示', content: resData.msg ? resData.msg : '出现异常，但系统没有对此异常的说明', okText: '确定', maskClosable: true })
        // return Promise.reject(response)
      } else if (+resData.code === 200) {
        resData.code = +resData.code
      }
    } else {
      resData.code = 0
    }

    if (!resData.result) {
      resData.result = {}
    }
  }
  /*
  else if (resData && resData.code && +resData.code === 200) {
    resData.msg && Modal.success({ title: '成功', content: resData.msg, okText: '确定', maskClosable: true })
  }
  */
  // 关闭loading动画
  return resData || { code: 0, result: {} }
}, error => {
  if (error.response) {
    switch (error.response.status) {
      case 401:
        window.location.href = redirectLoginUrl
        break
      case 403:
        window.location.href = redirectNotPermissionUrl
        break
      case 404:
        console.error('系统没有该链接，请添加')
        break
      case 501:
        Modal.error({
          title: '异常',
          content: error.response.data.msg,
        })
        break
      default:
        if (!/^dev/.test(process.env.NODE_ENV)) {
          const token = session.get(tokenSessionKey)
          if (token) {
            Modal.error({
              title: '异常',
              content: error.response,
            })
          }
        }
        break
    }
  }
  // return Promise.reject(error)
  return { code: 0, result: {} }
})

export default {
  setHeader,
  baseUrl,
  get(url, params) {
    delete params.type
    return axios({
      method: 'get',
      url,
      params,
    })
  },
  post(url, data) {
    delete data.type
    return axios({
      method: 'post',
      url,
      data: /^dev/.test(process.env.NODE_ENV) ? data : qs.stringify(data),
      headers: {
        'Content-Type': /^dev/.test(process.env.NODE_ENV) ? 'application/json; charset=UTF-8' : 'application/x-www-form-urlencoded; charset=UTF-8',
      },
    })
  },
  form(url, formdata) {
    return axios({
      method: 'post',
      url,
      data: formdata,
    })
  },
}
