/*
 * @Author: zengyufei 
 * @Date: 2017-09-06 16:11:24 
 * @Last Modified by: zengyufei 
 * @Last Modified time: 2017-09-06 16:11:24 
 */
import TweenOne from 'rc-tween-one'

import { Form, Input, Row, Col,
  Button, Card, InputNumber, DatePicker, Upload, Modal } from 'antd'

import { prefix } from './constant'

const TweenOneGroup = TweenOne.TweenOneGroup
const FormItem = Form.Item
const { MonthPicker } = DatePicker

let Update = options => {
  const { dispatch, loading, form, carOperateLog } = options
  const { getFieldDecorator } = form
  const { plateList, previewVisible, previewImage } = options

  const formItemLayout = {
    labelCol: {
      xs: { span: 24 },
      sm: { span: 6 },
    },
    wrapperCol: {
      xs: { span: 24 },
      sm: { span: 14 },
    },
  }
  const tailFormItemLayout = {
    wrapperCol: {
      xs: {
        span: 24,
        offset: 0,
      },
      sm: {
        span: 14,
        offset: 6,
      },
    },
  }

  /* 提交事件 */
  const updateCarOperateLog = e => {
    e.preventDefault()
    form.validateFieldsAndScroll((err, values) => {
      if (!err) {
        const yearMonth = form.getFieldValue('yearMonth') ? form.getFieldValue('yearMonth').format('YYYYMM') : 0
        dispatch({
          type: 'carOperateLogStore/updateNotNull',
          ...values,
          yearMonth,
        })
      }
    })
  }

  /* 返回分页 */
  const toPage = () => {
    dispatch({
      type: 'carOperateLogStore/toPage',
    })
  }

  // 预览图片
  const handlePreview = file => {
    console.log('handlePreview')
    dispatch({
      type: 'carOperateLogStore/lookPreview',
      previewImage: file.url || file.thumbUrl,
      previewVisible: true,
    })
  }
  // 删除图片
  const handleCancel = () => {
    console.log('handleCancel')
    dispatch({
      type: 'carOperateLogStore/unlookPreview',
    })
  }

  return (
    <div>
      <TweenOneGroup>
        <Row key="0">
          <Col span={16}>
            <Form onSubmit={updateCarOperateLog} style={{ maxWidth: '100%', marginTop: '10px' }}>
              <Card title="修改车辆运营数据">
                <FormItem>
                  {getFieldDecorator('id', { initialValue: carOperateLog.id,
                  })(
                    <Input type="hidden" />
                  )}
                </FormItem>
                <FormItem
                  {...formItemLayout}
                  label={(
                    <span>
                        自编号&nbsp;
                    </span>
                  )}
                  hasFeedback
                >
                  {getFieldDecorator('carNo', {
                    rules: [{ required: true, message: '请输入自编号!', whitespace: true }], initialValue: carOperateLog.carNo,
                  })(
                    <Input disabled />
                  )}
                </FormItem>
                <FormItem>
                  {getFieldDecorator('carId', { initialValue: carOperateLog.carId,
                  })(
                    <Input type="hidden" />
                  )}
                </FormItem>
                <FormItem
                  {...formItemLayout}
                  label={(
                    <span>
                        车牌号&nbsp;
                    </span>
                  )}
                  hasFeedback
                >
                  {getFieldDecorator('plateNumber', {
                    rules: [{ required: true, message: '请输入车牌号!' }], initialValue: carOperateLog.plateNumber,
                  })(
                    <Input disabled />
                  )}
                </FormItem>
                <FormItem
                  {...formItemLayout}
                  label={(
                    <span>
                        车辆照片&nbsp;
                    </span>
                  )}
                >
                  {getFieldDecorator('plateImage')(
                    <div >
                      <Upload
                        action=""
                        listType="picture-card"
                        fileList={plateList}
                        onPreview={handlePreview}
                      />
                      <Modal visible={previewVisible} footer={null} onCancel={handleCancel}>
                        <img alt="example" style={{ width: '100%' }} src={previewImage} />
                      </Modal>
                    </div>
                  )}
                </FormItem>
                <FormItem
                  {...formItemLayout}
                  label={(
                    <span>
                        选择年月份&nbsp;
                    </span>
                  )}
                >
                  {getFieldDecorator('yearMonth', {
                    rules: [{ required: true, message: '请选择年月份!' }], initialValue: moment(carOperateLog.yearMonth, 'YYYY-MM'),
                  })(<MonthPicker format={'YYYY-MM'} />)}
                </FormItem>
                <FormItem
                  {...formItemLayout}
                  label={(
                    <span>
                       车辆月度行驶里程&nbsp;
                    </span>
                  )}
                  hasFeedback
                >
                  {getFieldDecorator('travelMileage', {
                    rules: [{ required: true, message: '请输入车辆月度行驶里程!' }], initialValue: carOperateLog.travelMileage,
                  })(
                    <InputNumber min={0} max={1000000} />
                  )}
                  (公里)
                </FormItem>
                <FormItem
                  {...formItemLayout}
                  label={(
                    <span>
                        车辆月度营运里程&nbsp;
                    </span>
                  )}
                  hasFeedback
                >
                  {getFieldDecorator('operateMileage', {
                    initialValue: carOperateLog.operateMileage,
                  })(
                    <InputNumber min={0} max={1000000} />
                  )}
                  (公里)
                </FormItem>
                <FormItem
                  {...formItemLayout}
                  label={(
                    <span>
                        车辆月度营业收入&nbsp;
                    </span>
                  )}
                  hasFeedback
                >
                  {getFieldDecorator('incomeAmount', {
                    rules: [{ required: true, message: '请输入自编号!' }], initialValue: carOperateLog.incomeAmount,
                  })(
                    <InputNumber min={0} max={1000000} />
                  )}
                  (元)
                </FormItem>
                <FormItem
                  {...formItemLayout}
                  label={(
                    <span>
                        车辆月度载客次数&nbsp;
                    </span>
                  )}
                  hasFeedback
                >
                  {getFieldDecorator('passengerTimes', {
                    rules: [{ required: true, message: '请输入车辆月度载客次数!' }], initialValue: carOperateLog.passengerTimes,
                  })(
                    <InputNumber min={0} max={100000} />
                  )}
                  (人次)
                </FormItem>
                <FormItem
                  {...formItemLayout}
                  label={(
                    <span>
                        车辆月度客运量&nbsp;
                    </span>
                  )}
                  hasFeedback
                >
                  {getFieldDecorator('passengerCapacity', {
                    initialValue: carOperateLog.passengerCapacity,
                  })(
                    <InputNumber min={0} max={100000} />
                  )}
                  (人次)
                </FormItem>
                <FormItem
                  {...formItemLayout}
                  label={(
                    <span>
                        车辆月度耗电量&nbsp;
                    </span>
                  )}
                  hasFeedback
                >
                  {getFieldDecorator('powerConsume', {
                    initialValue: carOperateLog.powerConsume,
                  })(
                    <InputNumber min={0} max={1000000} />
                  )}
                  (度)
                </FormItem>

                <FormItem {...tailFormItemLayout}>
                  <Button key="registerButton" type="primary" htmlType="submit" size="large" loading={loading}>保存编辑</Button>
                  <Button key="returnLoginButton" htmlType="button" size="large" style={{ marginLeft: '30px' }} onClick={toPage}>返回</Button>
                </FormItem>
              </Card>
            </Form>
          </Col>
          <Col span={12} />
        </Row>
      </TweenOneGroup>
    </div>
  )
}

function mapStateToProps({ carOperateLogStore }) {
  return {
    carOperateLog: carOperateLogStore.carOperateLog,
    plateList: carOperateLogStore.plateList,
    previewVisible: carOperateLogStore.previewVisible,
    previewImage: carOperateLogStore.previewImage,
  }
}

export default PageUtils.extend({ prefix, mapStateToProps })(Update)
