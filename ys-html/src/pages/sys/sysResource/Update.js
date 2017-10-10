import { connect } from 'dva'
import { Form } from 'antd'

import ZForm from 'ZForm'
import ZModal from 'ZModal'
import { getFields, validate } from 'FormUtils'

let UpdatePage = option => {
  const { form, sysResourceStore } = option
  const { confirmLoading, visible: { update }, sysResource = {} } = sysResourceStore
  const { onOk, onCancel } = option

  const updatePageModalProps = {
    maskClosable: false,
    visible: update,
    confirmLoading,
    onOk,
    onCancel,
    form,
  }

  const formProps = {
    fields: getFields(fields).values(),
    item: {
      ...sysResource,
      provinceAndCity: sysResource.province && sysResource.city ? [sysResource.province, sysResource.city] : null,
    },
    form,
  }

  return (
    <div>
      <ZModal title="修改资源" {...updatePageModalProps} >
        <ZForm {...formProps} />
      </ZModal>
    </div>
  )
}

function mapStateToProps({ sysResourceStore }) {
  return {
    sysResourceStore,
  }
}

function mapDispatchToProps(dispatch, { form }) {
  return {

    onOk() {
      validate(form, fields)(values => {
        if (values) {
          if (values.parentId) {
            values.level = values.parentId.split('-').length === 1 ? 2 : 'three'
          } else {
            values.level = 1
          }
        }
        dispatch({
          type: 'sysResourceStore/updateState',
          ...values,
        })
      })
    },

    onCancel() {
      dispatch({
        type: 'sysResourceStore/updateState',
        visible: {
          update: false,
        },
      })
    },

  }
}

const fields = [
  {
    key: 'name',
    name: '资源名称',
    rules: [{
      required: true,
      message: '请输入资源名称!',
    }],
  },
  {
    key: 'parentId',
    name: '父资源',
    hasFeedback: false,
    type: 'parentId',
  },
  {
    key: 'permission',
    name: '权限标识',
    rules: [{
      required: true,
      message: '请填写权限标识!',
    }],
  },
]

export default Form.create()(connect(mapStateToProps, mapDispatchToProps)(UpdatePage))
