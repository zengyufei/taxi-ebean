import { connect } from 'dva'
import { Form } from 'antd'

import ZForm from 'ZForm'
import ZModal from 'ZModal'
import { getFields, validate } from 'FormUtils'

import { prefix, name, storeName } from './constant'
import fields from './fields'

let UpdatePage = options => {
    const { form, methods } = options
    const oldObj = options[storeName][prefix]
    const { confirmLoading, visible: { update } } = options[storeName]
    const { onOk, onCancel } = methods

    const updatePageModalProps = {
        maskClosable: false,
        visible: update,
        confirmLoading,
        onOk,
        onCancel,
        form,
    }

    const formProps = {
        formType: 'addOrUpdate',
        fields: getFields(fields).values(),
        item: {
            ...oldObj,
        },
        form,
    }

    return (
        <div>
            <ZModal title={`修改${name}`} {...updatePageModalProps}>
                <ZForm {...formProps} />
            </ZModal>
        </div>
    )
}

function mapDispatchToProps(dispatch, { form }) {
    return {
        onOk() {
            validate(form, fields, 'addOrUpdate')(values => {
                dispatch({
                    type: `${storeName}/update`,
                    ...values,
                })
            })
        },

        onCancel() {
            dispatch({
                type: `${storeName}/updateState`,
                visible: {
                    update: false,
                },
            })
        },
    }
}

export default PageUtils.extend({ prefix, mapDispatchToProps })(UpdatePage)
