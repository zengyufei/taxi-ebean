import { connect } from 'dva'
import { Form } from 'antd'

import ZForm from 'ZForm'
import ZModal from 'ZModal'
import { getFields, validate } from 'FormUtils'

let UpdatePage = option => {
    const { form, dispatch, sysOrgStore } = option
    const { confirmLoading, visible: { update }, sysOrg = {} } = sysOrgStore

    const onOk = () => {
        validate(form, fields)(values => {
            dispatch({
                type: 'sysOrgStore/updateState',
                ...values,
            })
        })
    }

    const onCancel = () => {
        dispatch({
            type: 'sysOrgStore/updateState',
            visible: {
                update: false,
            },
        })
    }

    const updatePageModalProps = {
        maskClosable: false,
        visible: update,
        confirmLoading,
        onOk,
        onCancel,
        form,
    }

    const formProps = {
        formType: 'update',
        fields: getFields(fields).values(),
        item: {
            ...sysOrg,
        },
        form,
    }

    return (
        <div>
            <ZModal title="修改组织机构" {...updatePageModalProps}>
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

const fields = [
    {
        key: 'orgNo',
        name: '组织编号',
        form: {
            update: false,
        },
    },
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

export default Form.create()(connect(mapStateToProps)(UpdatePage))
