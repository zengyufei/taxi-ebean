import { Button, Table } from 'antd'
import { getColumns } from 'TableUtils'

import {
    prefix,
    name,
    storeName,
    defaultTableFields, // 表格
} from './constant'

import fields from './fields'

const List = options => {
    const { loading, methods } = options
    const { page: { pageNo = 1, pageSize = 10, dataList = [], totalCount = 0 } } = options[storeName]
    const { updatePage, onShowSizeChange, onChange } = methods

    const tableColumns = getColumns(fields, defaultTableFields, {}, updatePage).values()

    return (
        <div>
            <Table
                scroll={{ x: 1200 }}
                rowKey="id"
                columns={tableColumns}
                dataSource={dataList}
                bordered
                loading={loading}
                pagination={{
                    // 分页
                    total: +totalCount, // 总数量
                    current: +pageNo,
                    defaultCurrent: +pageNo,
                    pageSize: +pageSize, // 显示几条一页
                    defaultPageSize: +pageSize, // 默认显示几条一页
                    showSizeChanger: true, // 是否显示可以设置几条一页的选项
                    onShowSizeChange,
                    onChange,
                    showTotal() {
                        // 设置显示一共几条数据
                        return `共 ${totalCount} 条数据`
                    },
                }}
            />
        </div>
    )
}

const mapDispatchToProps = (dispatch, { form }) => {
    return {
        updatePage(value, field, record) {
            const diff = record.alias !== value
            if (diff) {
                record.alias = value
            }
            dispatch({
                type: `${storeName}/update`,
                diff,
                ...record,
            })
        },

        onSearch(values) {
            if (values) {
            }
            dispatch({
                type: `${storeName}/queryPage`,
                ...values,
                pageNo: 1,
                pageSize: 10,
            })
        },

        onShowSizeChange(current, pageSize) {
            // 当几条一页的值改变后调用函数，current：改变显示条数时当前数据所在页；pageSize:改变后的一页显示条数
            let fieldsValue = form.getFieldsValue()
            if (fieldsValue && fieldsValue.provinceAndCity && fieldsValue.provinceAndCity.length) {
                fieldsValue.province = fieldsValue.provinceAndCity[0]
                fieldsValue.city = fieldsValue.provinceAndCity[1]
            }
            dispatch({
                type: `${storeName}/queryPage`,
                ...fieldsValue,
                pageNo: current,
                pageSize,
            })
        },
        onChange(page, pageSize) {
            // 点击改变页数的选项时调用函数，current:将要跳转的页数
            let fieldsValue = form.getFieldsValue()
            dispatch({
                type: `${storeName}/queryPage`,
                ...fieldsValue,
                pageNo: page,
                pageSize,
            })
        },
    }
}

export default PageUtils.extend({ prefix, mapDispatchToProps, formOptions: true })(List)

// export default Form.create()(connect(mapStateToProps, mapDispatchToProps)(List))
