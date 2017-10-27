import { connect } from 'dva'
import { Form } from 'antd'

import ZModal from 'ZModal'
import { getFields, validate } from 'FormUtils'
import Permission from './Permission'
import styles from './UpdatePermission.less'

let UpdatePage = option => {
    const { form, dispatch, cardStore } = option
    const { confirmLoading, visible: { updatePermission }, card = {} } = cardStore

    const onOk = () => {
        dispatch({
            type: 'cardStore/updatePermission',
        })
    }

    const onCancel = () => {
        dispatch({
            type: 'cardStore/updateState',
            visible: {
                updatePermission: false,
            },
        })
    }

    const updatePageModalProps = {
        maskClosable: false,
        visible: updatePermission,
        confirmLoading,
        onOk,
        onCancel,
        form,
        className: styles.modalWidth,
    }

    return (
        <div>
            <ZModal title="修改角色" {...updatePageModalProps}>
                <Permission
                    permissions={
                        card && typeof card.permissions === 'string' ? (
                            card.permissions.split(',')
                        ) : (
                            card.permissions || []
                        )
                    }
                />
            </ZModal>
        </div>
    )
}

/**
 * 订阅 model
 */
function mapStateToProps({ loading, cardStore }) {
    return {
        loading: loading.models.cardStore,
        cardStore,
    }
}

export default Form.create()(connect(mapStateToProps)(UpdatePage))
