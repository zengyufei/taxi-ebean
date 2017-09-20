/*
 * @Author: zengyufei 
 * @Date: 2017-09-06 16:28:01 
 * @Last Modified by: zengyufei
 * @Last Modified time: 2017-09-06 16:47:42
 */
import TweenOne from 'rc-tween-one'
import { Icon, Row, Col, Button, Card, Upload, Modal } from 'antd'
import ZForm from 'ZForm'
import { getFields, validate, formBindType } from 'FormUtils'

import { prefix, storeName, uploadAction } from './constant'
import fields from './fields'

const TweenOneGroup = TweenOne.TweenOneGroup

const Update = options => {
  const { loading, form, methods } = options
  const { toPage, updateAnnualVerification, handlePreview, handleCancel, drivingLicenseFileChange, taximeterFileChange, synthesizeFileChange } = methods

  const { annualVerification, plateList, previewVisible, previewImage, synthesizeFileList, synthesizeFile, drivingLicenseFileList, drivingLicenseFile, taximeterFileList, taximeterFile } = options[storeName]

  // 添加图片样式
  const uploadButton = (
    <div>
      <Icon type="plus" />
      <div className="ant-upload-text">点击上传文件</div>
    </div>
  )


  const formProps = {
    formType: 'update',
    fields: getFields(fields).values(),
    item: {
      ...annualVerification,
    },
    form,
    layout: {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 17 },
      },
    },
    onSubmit(e) {
      updateAnnualVerification(e, synthesizeFile, drivingLicenseFile, taximeterFile)
    },
    btn: <div>
      <Button key="registerButton" type="primary" htmlType="submit" size="large" loading={loading}>保存编辑</Button>
      <Button key="returnLoginButton" htmlType="button" size="large" style={{ marginLeft: '30px' }} onClick={toPage}>返回</Button>
    </div>,
  }


  formBindType({
    // 参数：初始值,meta(字段meta数据，例如: rows,min,max等), field字段定义对象
    plateImage: initialValue => {
      return {
        submit: false,
        input: <div >
          <Upload
            action=""
            listType="picture-card"
            fileList={plateList}
            onPreview={handlePreview}
          />
          <Modal visible={previewVisible} footer={null} onCancel={handleCancel}>
            <img alt="example" style={{ width: '100%' }} src={previewImage} />
          </Modal>
        </div>,
        initialValue,
      }
    },
    synthesizeFile: initialValue => {
      return {
        submit: false,
        input: <div >
          <Upload
            action={uploadAction}
            listType="picture-card"
            fileList={synthesizeFileList}
            onPreview={handlePreview}
            onChange={synthesizeFileChange}
          >
            { synthesizeFileList.length >= 1 ? null : uploadButton}
          </Upload>
          <Modal visible={previewVisible} footer={null} onCancel={handleCancel}>
            <img alt="example" style={{ width: '100%' }} src={previewImage} />
          </Modal>
        </div>,
        initialValue,
      }
    },
    drivingLicenseFile: initialValue => {
      return {
        submit: false,
        input: <div >
          <Upload
            action={uploadAction}
            listType="picture-card"
            fileList={drivingLicenseFileList}
            onPreview={handlePreview}
            onChange={drivingLicenseFileChange}
          >
            { drivingLicenseFileList.length >= 1 ? null : uploadButton}
          </Upload>
          <Modal visible={previewVisible} footer={null} onCancel={handleCancel}>
            <img alt="example" style={{ width: '100%' }} src={previewImage} />
          </Modal>
        </div>,
        initialValue,
      }
    },
    taximeterFile: initialValue => {
      return {
        submit: false,
        input: <div >
          <Upload
            action={uploadAction}
            listType="picture-card"
            fileList={taximeterFileList}
            onPreview={handlePreview}
            onChange={taximeterFileChange}
          >
            { taximeterFileList.length >= 1 ? null : uploadButton}
          </Upload>
          <Modal visible={previewVisible} footer={null} onCancel={handleCancel}>
            <img alt="example" style={{ width: '100%' }} src={previewImage} />
          </Modal>
        </div>,
        initialValue,
      }
    },
  })


  return (
    <div>
      <TweenOneGroup>
        <Row key="0">
          <Col span={16}>
            <Card title="修改车辆年审">
              <ZForm {...formProps} style={{ maxWidth: '100%', marginTop: '10px' }} />
            </Card>
          </Col>
          <Col span={12} />
        </Row>
      </TweenOneGroup>
    </div>
  )
}

const mapDispatchToProps = (dispatch, { form }) => {
  return {
    /* 返回分页 */
    toPage() {
      dispatch({
        type: 'annualVerificationStore/toPage',
      })
    },

    /* 提交事件 */
    updateAnnualVerification(e, synthesizeFile, drivingLicenseFile, taximeterFile) {
      e.preventDefault()
      validate(form, fields, 'update')(values => {
        dispatch({
          type: 'annualVerificationStore/updateNotNull',
          ...values,
          synthesizeFile,
          drivingLicenseFile,
          taximeterFile,
        })
      })
    },

    // 上传营运证年审有效扫描件
    synthesizeFileChange({ fileList }) {
      dispatch({
        type: 'annualVerificationStore/synthesizeFileChange',
        synthesizeFileList: fileList,
      })
    },
    // 上传行驶证有效扫描件
    drivingLicenseFileChange({ fileList }) {
      dispatch({
        type: 'annualVerificationStore/drivingLicenseFileChange',
        drivingLicenseFileList: fileList,
      })
    },
    // 上传计价器年审有效期截止时间扫描件
    taximeterFileChange({ fileList }) {
      dispatch({
        type: 'annualVerificationStore/taximeterFileChange',
        taximeterFileList: fileList,
      })
    },
    // 预览图片
    handlePreviewfile(file) {
      dispatch({
        type: 'annualVerificationStore/lookPreview',
        previewImage: file.url || file.thumbUrl,
        previewVisible: true,
      })
    },
    // 删除图片
    handleCancel() {
      dispatch({
        type: 'annualVerificationStore/unlookPreview',
      })
    },
  }
}

export default PageUtils.extend({ prefix, mapDispatchToProps })(Update)
