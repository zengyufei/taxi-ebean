import TweenOne from 'rc-tween-one'

import { connect } from 'dva'
import { Form, Icon, Button, Row, Col, Card } from 'antd'
import back from 'assets/back.png'
import ZFormItem from 'ZFormItem'
import { validate, getFields } from 'FormUtils'

const TweenOneGroup = TweenOne.TweenOneGroup

const { title, logo } = projectConfig
const isDev = process.env.NODE_ENV.indexOf('dev') > -1

const Login = option => {
  const { dispatch, loading, form, appStore } = option
  const { isSmall } = appStore

  const itemProps = {
    showLabel: false,
    form,
    item: {
      account: isDev ? 'admin' : 'admin',
      password: isDev ? 'admin' : 'admin',
    },
  }
  const fieldMap = getFields(fields).toMapValues()

  /**
   * 提交表单
   */
  const handleSubmit = e => {
    e.preventDefault() // 破坏form 原有的提交功能
    validate(form, fields)(values => {
      dispatch({
        type: 'loginStore/login',
        ...values,
      })
    })
  }

  let style = { backgroundImage: `url(${back})`, backgroundRepeat: 'no-repeat' }
  let headerStyle = { }
  let headerTitleStyle = {}
  let formStyle = {}
  let footerTitleStyle = {}
  let footerStyle = {}

  if (isSmall) {
    Object.assign(style, { backgroundPosition: '0px 60px', height: '470px' })
    headerStyle = { width: '100vw', textAlign: 'center' }
    headerTitleStyle = { fontSize: '22px', position: 'relative', top: '10px' }
    formStyle = { width: '280px', margin: '0 auto', position: 'relative', top: '133px' }
    footerStyle = { textAlign: 'center' }
  } else {
    Object.assign(style, { paddingTop: '250px', width: '100vw', height: '700px', backgroundPosition: 'center' })
    headerStyle = { width: '100vw', textAlign: 'center', position: 'relative', top: '133px', left: '27px', color: 'rgb(10, 36, 56)', fontFamily: 'monospace' }
    formStyle = { width: '350px', margin: '0 auto' }
    headerTitleStyle = { fontSize: '66px', position: 'relative', top: '-50px' }
    footerTitleStyle.fontSize = '12px'
    footerStyle = { textAlign: 'center' }
  }

  return (
    <div>
      <div style={headerStyle} >
        <span style={headerTitleStyle}>云哨智慧社区</span>
      </div>
      <div style={style}>
        <TweenOneGroup key="b">
          <Form onSubmit={handleSubmit} style={formStyle}>
            <Card title="登录">
              <ZFormItem {...itemProps} field={fieldMap.account} inputProps={{ prefix: <Icon type="user" style={{ fontSize: 13 }} /> }} placeholder="账户" />
              <ZFormItem {...itemProps} field={fieldMap.password} inputProps={{ prefix: <Icon type="lock" style={{ fontSize: 13 }} /> }} placeholder="密码" />
              <Button type="primary" htmlType="submit" style={{ width: '100%' }} loading={loading}>登录</Button>
            </Card>
          </Form>
        </TweenOneGroup>
      </div>
      <div style={footerStyle}>
        <div>
          <img alt="logo" src={logo} />
        </div>
        <span style={footerTitleStyle}>技术支持： 云哨智能</span>
      </div>
    </div>
  )
}

const fields = [
  {
    key: 'account',
    name: '账号',
    rules: [{
      required: true,
      message: '请输入账号!',
    }],
  }, {
    key: 'password',
    name: '密码',
    type: 'password',
    rules: [{
      required: true,
      message: '请输入密码!',
    }],
  },
]

const mapStateToProps = ({ appStore, loading }) => {
  return {
    appStore,
    loading: loading.models.loginStore,
  }
}

export default Form.create()(connect(mapStateToProps)(Login))
