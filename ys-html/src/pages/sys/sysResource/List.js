import { connect } from 'dva'
import ZSearch from 'ZSearch'
import { Form, Button, Popconfirm, Table } from 'antd'
import { getColumns } from 'TableUtils'

const List = option => {
  const { loading, methods, sysStore } = option
  const { openAddPage, openUpdatePage, handlerDelete } = methods
  const { resourceTree = [] } = sysStore

  const btns = (
    <Button type="primary" icon="plus-circle-o" onClick={openAddPage}>新增资源</Button>
  )

  const operatorColumn = [{
    key: 'operator',
    name: '操作',
    // 扩展字段的render支持自定义渲染
    render: (text, record) => {
      return (
        <span>
          <Button type="primary" onClick={() => openUpdatePage(record)} style={{ marginRight: '20px' }}>编辑</Button>
          <Popconfirm title="是否确定要删除?" onConfirm={() => handlerDelete(record.id)}>
            <Button type="primary">删除</Button>
          </Popconfirm>
        </span>
      )
    },
  }]
  const tableColumns = getColumns(fields).enhance(operatorColumn).values()


  const tableProp = {
    key: resourceTree.length,
    rowKey: 'id',
    columns: tableColumns,
    dataSource: resourceTree,
    bordered: true,
    loading,
    pagination: false,
    defaultExpandAllRows: true,
    rowSelection: {
      onChange: () => {
      },
      getCheckboxProps: record => ({
        disabled: record.name === 'Disabled User', // Column configuration not to be checked
      }),
    },
  }

  return (
    <div >
      <div>
        <ZSearch btns={btns} />
      </div>
      <Table
            scroll={{ x: 1200 }} {...tableProp} />
    </div>
  )
}

const mapStateToProps = ({ loading, sysStore }) => {
  return {
    loading: loading.models.sysStore,
    sysStore,
  }
}

const mapDispatchToProps = dispatch => {
  return {
    methods: {
      openAddPage() {
        dispatch({
          type: 'sysResourceStore/updateState',
          visible: {
            add: true,
          },
        })
        dispatch({
          type: 'selectStore/queryRoles',
        })
      },

      openUpdatePage(record) {
        dispatch({
          type: 'sysResourceStore/updateState',
          sysResource: record,
          visible: {
            update: true,
          },
        })
      },

      handlerDelete(id) {
        dispatch({
          type: 'sysResourceStore/remoteById',
          id,
        })
      },

    },
  }
}


const fields = [
  {
    key: 'name',
    name: '资源名称',
  }, {
    key: 'permission',
    name: '权限标识',
  },
]

export default Form.create()(connect(mapStateToProps, mapDispatchToProps)(List))
