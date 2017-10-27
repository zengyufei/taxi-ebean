import { Button, Popconfirm, Table } from 'antd'
import ZSearch from 'ZSearch'
import { getColumns } from 'TableUtils'
import { getFields } from 'FormUtils'
import { local } from 'utils/storage'

import {
    prefix,
    name,
    storeName,
    searchCacheKey,
    defaultSearchFields,
    allSearchFields,
    defaultTableFields, // 表格
} from './constant'

import fields from './fields'

const List = options => {
    const { loading, form, methods } = options
    const { page: { pageNo = 1, pageSize = 10, dataList = [], totalCount = 0 } } = options[storeName]
    const { openAddPage, openUpdatePage, onSearch, onShowSizeChange, onChange, handlerDelete } = methods

    const btns = (
        <Button type="primary" icon="plus-circle-o" onClick={openAddPage}>
            新增{name}
        </Button>
    )

    const searchBarProps = {
        form,
        showLabel: true,
        showReset: true,
        btns,
        fields: getFields(fields, local.get(searchCacheKey) || defaultSearchFields).values(),
        onSearch,
        onReset: onSearch,
    }

    const operatorColumn = [
        {
            key: 'operator',
            name: '操作',
            // 扩展字段的render支持自定义渲染
            render: (text, record) => {
                return (
                    <span>
                        <Button type="primary" onClick={() => openUpdatePage(record)} style={{ marginRight: '20px' }}>
                            编辑
                        </Button>
                        <Popconfirm title="是否确定要删除?" onConfirm={() => handlerDelete(record.id)}>
                            <Button type="primary">删除</Button>
                        </Popconfirm>
                    </span>
                )
            },
        },
    ]
    const tableColumns = getColumns(fields, defaultTableFields)
        .enhance(operatorColumn)
        .values()

    return (
        <div>
            <div>
                <ZSearch {...searchBarProps} />
            </div>
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
        openAddPage() {
            dispatch({
                type: `${storeName}/updateState`,
                visible: {
                    add: true,
                },
            })
        },

        openUpdatePage(record) {
            dispatch({
                type: `${storeName}/updateState`,
                [prefix]: record,
                visible: {
                    update: true,
                },
            })
        },

        handlerDelete(id) {
            dispatch({
                type: `${storeName}/removeById`,
                id,
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
