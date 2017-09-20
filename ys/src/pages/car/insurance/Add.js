/*
 * @Author: zengyufei
 * @Date: 2017-08-25 14:03:50
 * @Last Modified by: zengyufei
 * @Last Modified time: 2017-09-06 16:10:47
 */
import TweenOne from 'rc-tween-one'

import { connect } from 'dva'
import { Form, Input, Icon, Row, Col, Checkbox, Switch,
  Button, Card, InputNumber, DatePicker, Upload, Modal, AutoComplete } from 'antd'

import { prefix } from './constant'

const TweenOneGroup = TweenOne.TweenOneGroup
const FormItem = Form.Item

let Add = options => {
  const { dispatch, form, businessState, trafficState, car, carNos } = options
  const { getFieldDecorator } = form
  const { plateList, previewVisible, previewImage, trafficInsuranceFile, trafficInsuranceList, businessInsuranceFile, businessInsuranceList } = options
  const { oneInsurance, twoInsurance, threeInsurance, fourInsurance, fiveInsurance } = options

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
  const addInsurance = e => {
    if (form.getFieldValue('trafficBoolean') || form.getFieldValue('businessBoolean')) {
      e.preventDefault()
      form.validateFieldsAndScroll((err, values) => {
        let bizInsuranceStr = ''
        if (form.getFieldValue('businessBoolean')) {
          if (form.getFieldValue('insurance').includes('车损险赔偿金额')) {
            bizInsuranceStr += `车损险赔偿金额_${form.getFieldValue('oneNumber')?form.getFieldValue('oneNumber'):0},`
          }
          if (form.getFieldValue('insurance').includes('第三者责任险最高赔偿金额')) {
            bizInsuranceStr += `第三者责任险最高赔偿金额_${form.getFieldValue('twoNumber')?form.getFieldValue('twoNumber'):0},`
          }
          if (form.getFieldValue('insurance').includes('不计免赔险最高赔偿金额')) {
            bizInsuranceStr += `不计免赔险最高赔偿金额_${form.getFieldValue('threeNumber')?form.getFieldValue('threeNumber'):0},`
          }
          if (form.getFieldValue('insurance').includes('自燃险赔偿金额')) {
            bizInsuranceStr += `自燃险赔偿金额_${form.getFieldValue('fourNumber')?form.getFieldValue('fourNumber'):0},`
          }
          if (form.getFieldValue('insurance').includes('承运人责任险最高赔偿金额（每座）')) {
            bizInsuranceStr += `承运人责任险最高赔偿金额（每座）_${form.getFieldValue('fiveNumber')?form.getFieldValue('fiveNumber'):0},`
          }
          if (bizInsuranceStr.length > 0) {
            bizInsuranceStr = bizInsuranceStr.substring(0, bizInsuranceStr.length - 1)
          }
        }

        if (!err) {
          if (form.getFieldValue('plateNumber') === '') {
            Modal.info({
              title: '温馨提示',
              content: (
                '自编号不存在'
              ),
            })
            return
          }
          let trafficInsuranceBuyDate = null
          let trafficInsuranceExpireDate = null
          let businessInsuranceBuyDate = null
          let businessInsuranceExpireDate = null

          if (form.getFieldValue('trafficBoolean')) {
            trafficInsuranceBuyDate = form.getFieldValue('trafficInsuranceBuyDate').format('YYYY-MM-DD') ? form.getFieldValue('trafficInsuranceBuyDate').format('YYYY-MM-DD') : null
            trafficInsuranceExpireDate = form.getFieldValue('trafficInsuranceExpireDate').format('YYYY-MM-DD') ? form.getFieldValue('trafficInsuranceBuyDate').format('YYYY-MM-DD') : null
          }
          if (form.getFieldValue('businessBoolean')) {
            businessInsuranceBuyDate = form.getFieldValue('businessInsuranceBuyDate').format('YYYY-MM-DD') ? form.getFieldValue('businessInsuranceBuyDate').format('YYYY-MM-DD') : null
            businessInsuranceExpireDate = form.getFieldValue('businessInsuranceExpireDate').format('YYYY-MM-DD') ? form.getFieldValue('businessInsuranceExpireDate').format('YYYY-MM-DD') : null
          }
          dispatch({
            type: 'insuranceStore/insert',
            ...values,
            trafficInsuranceBuyDate,
            trafficInsuranceExpireDate,
            businessInsuranceBuyDate,
            businessInsuranceExpireDate,
            trafficInsuranceFile,
            businessInsuranceFile,
            bizInsuranceStr,
          })
        }
      })
    } else {
      e.preventDefault()
      alert('交强险和商业险必须选择一个')
    }
  }

  /* 返回分页 */
  const toPage = () => {
    dispatch({
      type: 'insuranceStore/toPage',
    })
  }

  function traffic() {
    dispatch({
      type: 'insuranceStore/traffic',
      trafficState: !form.getFieldValue('trafficBoolean'),
    })
  }
  function business() {
    dispatch({
      type: 'insuranceStore/business',
      businessState: !form.getFieldValue('businessBoolean'),
    })
  }

  let carno = form.getFieldValue('carNo')
  /** 模糊查询 车辆自编号 */
  const handleSearch = value => {
    carno = value
    dispatch({
      type: 'driverCommonStore/queryLikeCarNo',
      str: value,
    })
  }
  const queryByCarNo = () => {
    dispatch({
      type: 'carStore/queryByCarNo',
      carNo: form.getFieldValue('carNo'),
    })
  }

  // 上传交强险图片
  const trafficInsuranceChange = ({ fileList }) => {
    dispatch({
      type: 'insuranceStore/trafficInsuranceChange',
      trafficInsuranceList: fileList,
    })
  }
  // 上传商业保险图片
  const businessInsuranceChange = ({ fileList }) => {
    dispatch({
      type: 'insuranceStore/businessInsuranceChange',
      businessInsuranceList: fileList,
    })
  }
  // 预览图片
  const handlePreview = file => {
    dispatch({
      type: 'insuranceStore/lookPreview',
      previewImage: file.url || file.thumbUrl,
      previewVisible: true,
    })
  }
  // 删除图片
  const handleCancel = e => {
    console.log('handleCancel')
    dispatch({
      type: 'insuranceStore/unlookPreview',
    })
  }
  // 添加图片样式
  const uploadButton = (
    <div>
      <Icon type="plus" />
      <div className="ant-upload-text">点击上传文件</div>
    </div>
  )

  // 根据选择保险种类  可填写数字更改
  const inInsurance = e => {
    dispatch({
      type: 'insuranceStore/inInsurance',
      oneInsurance: e.includes('车损险赔偿金额'),
      twoInsurance: e.includes('第三者责任险最高赔偿金额'),
      threeInsurance: e.includes('不计免赔险最高赔偿金额'),
      fourInsurance: e.includes('自燃险赔偿金额'),
      fiveInsurance: e.includes('承运人责任险最高赔偿金额（每座）'),
    })
  }

  return (
    <div>
      <TweenOneGroup>
        <Row key="0">
          <Col span={16}>
            <Form onSubmit={addInsurance} style={{ maxWidth: '100%', marginTop: '10px' }}>
              <Card title="新增车辆保险">
                <FormItem>
                  {getFieldDecorator('carId', { initialValue: car ? car.id : 0,
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
                  <Col span={18}>
                    {getFieldDecorator('carNo', {
                      rules: [{ required: true, message: '请输入自编号!' }],
                    })(
                      <AutoComplete
                        dataSource={carNos}
                        onSearch={handleSearch}
                        placeholder="车辆自编号"
                      />
                    )}
                  </Col>
                  <Col span={4}>
                    <Button style={{ marginLeft: '30px' }} onClick={queryByCarNo}>查询</Button>
                  </Col>
                </FormItem>
                <FormItem
                  {...formItemLayout}
                  label={(
                    <span>
                        车牌号&nbsp;
                    </span>
                  )}
                >
                  {getFieldDecorator('plateNumber', { initialValue: car && car.carNo === carno ? car.plateNumber : '',
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
                <FormItem>
                  {getFieldDecorator('carId', { initialValue: car ? car.id : 0,
                  })(
                    <Input type="hidden" />
                  )}
                </FormItem>

                <Card title="交强险" style={{ border: '2px solid 	#C0C0C0' }}>
                  <FormItem
                    {...formItemLayout}
                    label={(
                      <span>
                          是否购买交强险&nbsp;
                      </span>
                    )}
                  >
                    {getFieldDecorator('trafficBoolean', { initialValue: true })(<Switch checkedChildren="是" onChange={traffic} unCheckedChildren="否" defaultChecked />)}
                  </FormItem>
                  <FormItem
                    {...formItemLayout}
                    label={(
                      <span>
                          保险公司&nbsp;
                      </span>
                    )}
                    hasFeedback
                  >
                    {
                      trafficState ? <div>
                        {getFieldDecorator('trafficInsuranceCompany', { rules: [{ required: true, message: '请输入保险公司!', whitespace: true }, { max: 64, message: '保险公司最多长度64', whitespace: true }] }
                        )(
                          <Input />
                        )}
                      </div> : <div>
                        {getFieldDecorator('trafficInsuranceCompany')(
                          <Input disabled />
                        )}
                      </div>
                    }
                  </FormItem>
                  <FormItem
                    {...formItemLayout}
                    label={(
                      <span>
                          保险单号&nbsp;
                      </span>
                    )}
                    hasFeedback
                  >
                    {
                      trafficState ? <div>
                        {getFieldDecorator('trafficPolicyNo', {
                          rules: [{ required: true, message: '请输入保险单号!', whitespace: true }, { max: 64, message: '保险单号最多长度64', whitespace: true }],
                        })(
                          <Input />
                        )}
                      </div> : <div>
                        {getFieldDecorator('trafficPolicyNo')(
                          <Input disabled />
                        )}
                      </div>
                    }
                  </FormItem>
                  <FormItem
                    {...formItemLayout}
                    label={(
                      <span>
                          保险费用 &nbsp;
                      </span>
                    )}
                    hasFeedback
                  >
                    {
                      trafficState ? <div>
                        {getFieldDecorator('trafficInsuranceMoney')(
                          <InputNumber min={0} max={1000000} />
                        )}(元)
                      </div> : <div>
                        {getFieldDecorator('trafficInsuranceMoney')(
                          <InputNumber disabled />
                        )} (元)
                      </div>
                    }
                  </FormItem>
                  <FormItem
                    {...formItemLayout}
                    label={(
                      <span>
                          交强险有效扫描件&nbsp;
                      </span>
                    )}
                  >
                    {
                      trafficState ? <div>
                        {getFieldDecorator('trafficInsuranceFile')(
                          <div >
                            <Upload
                              action={`${BASE_URL}/fileupload/image.htm`}
                              listType="picture-card"
                              fileList={trafficInsuranceList}
                              onPreview={handlePreview}
                              onChange={trafficInsuranceChange}
                            >
                              { trafficInsuranceList.length >= 1 ? null : uploadButton}
                            </Upload>
                            <Modal visible={previewVisible} footer={null} onCancel={handleCancel}>
                              <img alt="example" style={{ width: '100%' }} src={previewImage} />
                            </Modal>
                          </div>
                        )}
                      </div> : <div>
                        {getFieldDecorator('trafficInsuranceFile')(
                          <div >
                            <Upload
                              action=""
                              listType="picture-card"
                              fileList={trafficInsuranceList}
                              onPreview={handlePreview}
                            />
                            <Modal visible={previewVisible} footer={null} onCancel={handleCancel}>
                              <img alt="example" style={{ width: '100%' }} src={previewImage} />
                            </Modal>
                          </div>
                        )}
                      </div>
                    }
                  </FormItem>
                  <FormItem
                    {...formItemLayout}
                    label={(
                      <span>
                          交强险生效时间&nbsp;
                      </span>
                    )}
                    hasFeedback
                  >
                    {
                      trafficState ? <div>
                        {getFieldDecorator('trafficInsuranceBuyDate', {
                          rules: [{ required: true, message: '请选择交强险生效时间!' }],
                        })(<DatePicker />)}
                      </div> : <div>
                        {getFieldDecorator('trafficInsuranceBuyDate')(<DatePicker disabled />)}
                      </div>
                    }
                  </FormItem>
                  <FormItem
                    {...formItemLayout}
                    label={(
                      <span>
                           交强险截止时间&nbsp;
                      </span>
                    )}
                    hasFeedback
                  >
                    {
                      trafficState ? <div>
                        {getFieldDecorator('trafficInsuranceExpireDate', {
                          rules: [{ required: true, message: '请选择交强险截止时间!' }],
                        })(<DatePicker />)}
                      </div> : <div>
                        {getFieldDecorator('trafficInsuranceExpireDate')(<DatePicker disabled />)}
                      </div>
                    }
                  </FormItem>
                </Card>

                <Card title="商业险" style={{ border: '2px solid 	#C0C0C0', margin: '20px 0px 20px 0px' }}>
                  <FormItem
                    {...formItemLayout}
                    label={(
                      <span>
                          是否购买商业险&nbsp;
                      </span>
                    )}
                  >
                    {getFieldDecorator('businessBoolean', { initialValue: true })(<Switch checkedChildren="是" unCheckedChildren="否" onChange={business} defaultChecked />)}
                  </FormItem>
                  <FormItem
                    {...formItemLayout}
                    label={(
                      <span>
                          保险公司&nbsp;
                      </span>
                    )}
                    hasFeedback
                  >
                    {
                      businessState ?
                        <div>{getFieldDecorator('businessInsuranceCompany', {
                          rules: [{ required: true, message: '请输入保险公司!' }, { max: 64, message: '保险公司最多长度64', whitespace: true }],
                        })(
                          <Input />
                        )}</div>
                        :
                        <div>{getFieldDecorator('businessInsuranceCompany')(
                          <Input disabled />
                        )}</div>
                    }
                  </FormItem>
                  <FormItem
                    {...formItemLayout}
                    label={(
                      <span>
                          保险种类&nbsp;
                      </span>
                    )}
                  >
                    {
                      businessState ? <div>
                        {getFieldDecorator('insurance')(
                          <Checkbox.Group onChange={inInsurance}>
                            <Row>
                              <Col span={20}><Checkbox value="车损险赔偿金额">车损险赔偿金额</Checkbox></Col>
                              {
                                oneInsurance ? <Col span={2}>
                                  {getFieldDecorator('oneNumber', { initialValue: 0 })(
                                    <InputNumber min={0} />
                                  )}
                                </Col> : <Col span={2}><InputNumber min={0} disabled /></Col>
                              }
                              <Col span={20}><Checkbox value="第三者责任险最高赔偿金额">第三者责任险最高赔偿金额</Checkbox></Col>
                              {
                                twoInsurance ? <Col span={2}>
                                  {getFieldDecorator('twoNumber', { initialValue: 0 })(
                                    <InputNumber min={0} />
                                  )}
                                </Col> : <Col span={2}><InputNumber min={0} disabled /></Col>
                              }
                              <Col span={20}><Checkbox value="不计免赔险最高赔偿金额">不计免赔险最高赔偿金额</Checkbox></Col>
                              {
                                threeInsurance ? <Col span={2}>
                                  {getFieldDecorator('threeNumber', { initialValue: 0 })(
                                    <InputNumber min={0} />
                                  )}
                                </Col> : <Col span={2}><InputNumber min={0} disabled /></Col>
                              }
                              <Col span={20}><Checkbox value="自燃险赔偿金额">自燃险赔偿金额</Checkbox></Col>
                              {
                                fourInsurance ? <Col span={2}>
                                  {getFieldDecorator('fourNumber', { initialValue: 0 })(
                                    <InputNumber min={0} />
                                  )}
                                </Col> : <Col span={2}><InputNumber min={0} disabled /></Col>
                              }
                              <Col span={20}><Checkbox value="承运人责任险最高赔偿金额（每座）">承运人责任险最高赔偿金额（每座）</Checkbox></Col>
                              {
                                fiveInsurance ? <Col span={2}>
                                  {getFieldDecorator('fiveNumber', { initialValue: 0 })(
                                    <InputNumber min={0} />
                                  )}
                                </Col> : <Col span={2}><InputNumber min={0} disabled /></Col>
                              }
                            </Row>
                          </Checkbox.Group>
                        )}
                      </div>
                        :
                        <div>
                        {getFieldDecorator('insurance')(
                            <Checkbox.Group disabled>
                            <Row>
                                <Col span={20}><Checkbox value="车损险赔偿金额">车损险赔偿金额</Checkbox></Col>
                                {
                                  oneInsurance ? <Col span={2}><InputNumber min={0} /></Col> : <Col span={2}><InputNumber min={0} disabled /></Col>
                                }
                                <Col span={20}><Checkbox value="第三者责任险最高赔偿金额">第三者责任险最高赔偿金额</Checkbox></Col>
                                {
                                  twoInsurance ? <Col span={2}><InputNumber min={0} /></Col> : <Col span={2}><InputNumber min={0} disabled /></Col>
                                }
                                <Col span={20}><Checkbox value="不计免赔险最高赔偿金额">不计免赔险最高赔偿金额</Checkbox></Col>
                                {
                                  threeInsurance ? <Col span={2}><InputNumber min={0} /></Col> : <Col span={2}><InputNumber min={0} disabled /></Col>
                                }
                                <Col span={20}><Checkbox value="自燃险赔偿金额">自燃险赔偿金额</Checkbox></Col>
                                {
                                  fourInsurance ? <Col span={2}><InputNumber min={0} /></Col> : <Col span={2}><InputNumber min={0} disabled /></Col>
                                }
                                <Col span={20}><Checkbox value="承运人责任险最高赔偿金额（每座）">承运人责任险最高赔偿金额（每座）</Checkbox></Col>
                                {
                                  fiveInsurance ? <Col span={2}><InputNumber min={0} /></Col> : <Col span={2}><InputNumber min={0} disabled /></Col>
                                }
                              </Row>
                          </Checkbox.Group>
                          )}
                      </div>
                    }
                  </FormItem>
                  <FormItem
                    {...formItemLayout}
                    label={(
                      <span>
                          保险单号&nbsp;
                      </span>
                    )}
                    hasFeedback
                  >
                    {
                      businessState ?
                        <div>{getFieldDecorator('businessPolicyNo', {
                          rules: [{ required: true, message: '请输入保险单号!', whitespace: true }, { max: 64, message: '保险单号最多长度64', whitespace: true }],
                        })(
                          <Input />
                        )}</div>
                        :
                        <div>{getFieldDecorator('businessPolicyNo')(
                          <Input disabled />
                        )}</div>
                    }
                  </FormItem>
                  <FormItem
                    {...formItemLayout}
                    label={(
                      <span>
                          保险费用 &nbsp;
                      </span>
                    )}
                    hasFeedback
                  >
                    {
                      businessState ? <div>
                        {getFieldDecorator('businessInsuranceMoney')(
                          <InputNumber min={0} max={1000000} />
                        )}(元)
                      </div> : <div>
                        {getFieldDecorator('businessInsuranceMoney')(
                          <InputNumber disabled />
                        )}(元)
                      </div>
                    }
                  </FormItem>
                  <FormItem
                    {...formItemLayout}
                    label={(
                      <span>
                          商业险有效扫描件 &nbsp;
                      </span>
                    )}
                  >
                    {
                      businessState ? <div>
                        {getFieldDecorator('businessInsuranceFile')(
                          <div >
                            <Upload
                              action={`${BASE_URL}/fileupload/image.htm`}
                              listType="picture-card"
                              fileList={businessInsuranceList}
                              onPreview={handlePreview}
                              onChange={businessInsuranceChange}
                            >
                              { businessInsuranceList.length >= 1 ? null : uploadButton}
                            </Upload>
                            <Modal visible={previewVisible} footer={null} onCancel={handleCancel}>
                              <img alt="example" style={{ width: '100%' }} src={previewImage} />
                            </Modal>
                          </div>
                        )}
                      </div> : <div>
                        {getFieldDecorator('businessInsuranceFile')(
                          <div >
                            <Upload
                              action=""
                              listType="picture-card"
                              fileList={businessInsuranceList}
                              onPreview={handlePreview}
                            />
                            <Modal visible={previewVisible} footer={null} onCancel={handleCancel}>
                              <img alt="example" style={{ width: '100%' }} src={previewImage} />
                            </Modal>
                          </div>
                        )}
                      </div>
                    }
                  </FormItem>
                  <FormItem
                    {...formItemLayout}
                    label={(
                      <span>
                          商业险生效时间 &nbsp;
                      </span>
                    )}
                    hasFeedback
                  >
                    {
                      businessState ?
                        <div>
                          {getFieldDecorator('businessInsuranceBuyDate', {
                            rules: [{ required: true, message: '请选择商业险生效时间!' }],
                          })(<DatePicker />)}
                        </div> : <div>
                          {getFieldDecorator('businessInsuranceBuyDate')(
                            <DatePicker disabled />
                          )}
                        </div>
                    }
                  </FormItem>
                  <FormItem
                    {...formItemLayout}
                    label={(
                      <span>
                           商业险截止时间&nbsp;
                      </span>
                    )}
                    hasFeedback
                  >
                    {
                      businessState ? <div>
                        {getFieldDecorator('businessInsuranceExpireDate', {
                          rules: [{ required: true, message: '请选择商业险截止时间!' }],
                        })(<DatePicker />)}
                      </div> : <div>
                        {getFieldDecorator('businessInsuranceExpireDate')(
                          <DatePicker disabled />
                        )}
                      </div>
                    }
                  </FormItem>
                </Card>

                <FormItem {...tailFormItemLayout}>
                  <Button key="registerButton" type="primary" htmlType="submit" size="large">保存</Button>
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
function mapStateToProps({ insuranceStore, carStore, driverCommonStore }) {
  return {
    car: carStore.car,
    plateList: carStore.plateList,
    carNos: driverCommonStore.carNos,
    previewVisible: insuranceStore.previewVisible,
    previewImage: insuranceStore.previewImage,
    businessState: insuranceStore.businessState,
    trafficState: insuranceStore.trafficState,
    trafficInsuranceFile: insuranceStore.trafficInsuranceFile,
    trafficInsuranceList: insuranceStore.trafficInsuranceList,
    businessInsuranceFile: insuranceStore.businessInsuranceFile,
    businessInsuranceList: insuranceStore.businessInsuranceList,

    oneInsurance: insuranceStore.oneInsurance,
    twoInsurance: insuranceStore.twoInsurance,
    threeInsurance: insuranceStore.threeInsurance,
    fourInsurance: insuranceStore.fourInsurance,
    fiveInsurance: insuranceStore.fiveInsurance,
  }
}

export default PageUtils.extend({ prefix, mapStateToProps })(Add)
