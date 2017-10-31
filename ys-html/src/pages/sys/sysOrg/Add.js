import { connect } from 'dva'
import { Form } from 'antd'

import ZForm from 'ZForm'
import ZModal from 'ZModal'

import { getFields, validate } from 'FormUtils'

const Add = options => {
    const { form, methods, sysOrgStore } = options
    const { confirmLoading, visible: { add } } = sysOrgStore
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
        fields: getFields(fields).values(),
        item: {
            available: true,
        },
        form,
    }

    return (
        <div>
            <ZModal title="新增组织机构" {...addPageModalProps}>
                <ZForm {...formProps} />
            </ZModal>
        </div>
    )
}

/**
 * 订阅 model
 */
function mapStateToProps({ sysOrgStore }) {
    return {
        sysOrgStore,
    }
}

function mapDispatchToProps(dispatch, { form }) {
    return {
        methods: {
            onOk() {
                validate(form, fields)(values => {
                    dispatch({
                        type: 'sysOrgStore/add',
                        ...values,
                    })
                })
            },

            onCancel() {
                dispatch({
                    type: 'sysOrgStore/updateState',
                    visible: {
                        add: false,
                    },
                })
            },
        },
    }
}

const fields = [
    {
        key: 'parentOrgNo',
        name: '上级组织',
        type: 'parentOrgNo',
    },
    {
        key: 'orgName',
        name: '组织名称',
        required: true,
    },
    {
        key: 'description',
        name: '描述',
        type: 'textarea',
    },
    {
        key: 'address',
        name: '地址',
    },
]

export default Form.create()(connect(mapStateToProps, mapDispatchToProps)(Add))
