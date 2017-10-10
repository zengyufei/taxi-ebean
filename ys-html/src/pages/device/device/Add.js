import ZForm from 'ZForm'
import ZModal from 'ZModal'
import { getFields, validate } from 'FormUtils'

import { prefix, name, storeName } from './constant'
import fields from './fields'

const Add = options => {
  const { form, methods } = options
  const { confirmLoading, visible: { add } } = options[storeName]
  const { onOk, onCancel } = methods

  const addPageModalProps = {
    maskClosable: false,
    visible: add,
    confirmLoading,
    onOk,
    onCancel,
    form,
  }

  const formProps = {
    formType: '',
    fields: getFields(fields).values(),
    item: {
    },
    form,
  }

  return (
    <div>
      <ZModal title={`新增${name}`} {...addPageModalProps} >
        <ZForm {...formProps} />
      </ZModal>
    </div>
  )
}

function mapDispatchToProps(dispatch, { form }) {
  return {
    onOk() {
      validate(form, fields)(values => {
        dispatch({
          type: `${storeName}/insert`,
          ...values,
        })
      })
    },

    onCancel() {
      dispatch({
        type: `${storeName}/updateState`,
        visible: {
          add: false,
        },
      })
    },
  }
}

export default PageUtils.extend({ prefix, mapDispatchToProps })(Add)
