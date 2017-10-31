import ZForm from 'ZForm'
import ZModal from 'ZModal'
import { getFields, validate } from 'FormUtils'
import { Select, AutoComplete } from 'antd'

const AOption = AutoComplete.Option

import { prefix, name, storeName } from './constant'
import fields from './fields'

const Add = options => {
    const { form, methods } = options
    const { confirmLoading, visible: { add }, userInfoList } = options[storeName]
    const { onOk, onCancel, onSelect } = methods

    const addPageModalProps = {
        maskClosable: false,
        visible: add,
        confirmLoading,
        onOk,
        onCancel,
        form,
    }

    fields.forEach(function (x) {
        if (x.key === 'userId') {
            x.onSelect = onSelect
        }
    })

    const formProps = {
        formType: 'addOrUpdate',
        fields: getFields(fields, ['communityId', 'cardNo', 'cardEnum', 'userId', 'validDate', 'validTime',]).values(),
        item: {
            userId: userInfoList.map(function (x) {
                return <AOption key={x.id} value={x.id + ''}>
                    {x.phone}
                </AOption>
            }),
        },
        form,
    }

    return (
        <div>
            <ZModal title={`新增${name}`} {...addPageModalProps}>
                <ZForm {...formProps} />
            </ZModal>
        </div>
    )
}

function mapDispatchToProps(dispatch, { form }) {
    return {
        onSelect(val) {
            dispatch({
                type: `${storeName}/queryByPhone`,
                phone: val,
            })
        },

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
