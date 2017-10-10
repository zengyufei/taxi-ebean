import { connect } from 'dva'
import { Table, Checkbox } from 'antd'
import _ from 'lodash'

import { getColumns } from 'TableUtils'

const CheckboxGroup = Checkbox.Group

class PermissionComponet extends React.Component {
  static propTypes = {
    loading: PropTypes.bool,
    resourceList: PropTypes.array,
    sysStore: PropTypes.object.isRequired,
    updateState: PropTypes.func.isRequired,
  }

  state = {
    key: new Date(),
    expandedRowKeys: [],
    roleResIdList: this.props.resourceList && typeof this.props.resourceList === 'string' ? this.props.resourceList.split(',') : (this.props.resourceList || []),
    resources: this.props.sysStore.resources,
    resourceMapByparentId: this.props.sysStore.resourceMapByparentId,
  }

  onExpandedRowsChange(expandedRowKeys) {
    this.setState({ expandedRowKeys })
  }
  onChangePower(checkedValues, item) {
    const { updateState } = this.props
    let { resources, roleResIdList, resourceMapByparentId } = this.state
    const selectedIds = _.map(_.map(_.filter(resources, e => _.includes(checkedValues, e.id)), 'id'), e => +e)
    if (+item.level === 1) {
      if (selectedIds.length) {
        roleResIdList = _.concat(roleResIdList, selectedIds)
      } else {
        _.remove(roleResIdList, e => e === +item.id)
      }
    } else {
      const deleteArray = _.map(_.map(resourceMapByparentId[item.id], 'id'), e => +e)
      _.remove(roleResIdList, e => _.includes(deleteArray, e))
      roleResIdList = _.concat(roleResIdList, selectedIds)
      if (selectedIds.length === item.childrenResNo.length) {
        roleResIdList = _.concat(roleResIdList, +item.id)
      } else if (selectedIds.length === 0) {
        _.remove(roleResIdList, e => e === +item.id)
      }
    }
    this.setState({ roleResIdList, key: `${item.id}${roleResIdList.length}` })
    updateState(roleResIdList)
  }

  render() {
    let { resources, roleResIdList, resourceMapByparentId } = this.state
    const { loading, sysStore, updateState } = this.props
    const { resourceSecondTree = [] } = sysStore

    const operatorColumn = [{
      key: 'operator',
      name: '操作',
      // 扩展字段的render支持自定义渲染
      render: (text, record) => {
        let options
        let values = []
        if (record.level === 1) {
          options = [{ label: '查看', value: record.id }]
          record.childrenResNo = [record.id]
          if (_.includes(roleResIdList, record.id)) {
            values = record.childrenResNo
          }
        } else if (record.level === 2) {
          options = _.transform(resourceMapByparentId[record.id], (result, resource) => {
            result.push({
              label: resource.name,
              value: resource.id,
            })
          }, [])
          record.childrenResNo = _.transform(resourceMapByparentId[record.id], (result, resource) => {
            result.push(resource.id)
          }, [])
          const idsArray = _.map(_.map(resourceMapByparentId[record.id], 'id'), e => +e)
          if (_.intersection(roleResIdList, idsArray).length === idsArray.length) {
            values = record.childrenResNo
            values.push(record.id)
          } else {
            values = _.transform(resourceMapByparentId[record.id], (result, resource) => {
              if (_.includes(roleResIdList, +resource.id)) {
                result.push(resource.id)
              }
            }, [])
            if (!values.length) {
              _.remove(roleResIdList, e => e === +record.id)
            }
          }
        }
        return (
          <CheckboxGroup options={options} value={values} onChange={checkedValues => this.onChangePower(checkedValues, record)} />
        )
      },
    }]
    const tableColumns = getColumns(resourceFields).enhance(operatorColumn).values()

    const rowSelection = {
      onSelect: (record, selected) => {
        if (selected) {
          if (record.level === 1) {
            _.remove(roleResIdList, e => e === +record.id)
            roleResIdList = _.concat(roleResIdList, +record.id)
          } else if (record.level === 2) {
            const deleteArray = _.transform(resourceMapByparentId[record.id], (result, resource) => {
              result.push(+resource.id)
            }, [])
            _.remove(roleResIdList, e => _.includes(deleteArray, e))
            roleResIdList = _.concat(roleResIdList, +record.id, _.transform(resourceMapByparentId[record.id], (result, resource) => {
              result.push(+resource.id)
            }, []))
          }
        } else if (record.level === 1) {
          _.remove(roleResIdList, e => e === +record.id)
        } else if (record.level === 2) {
          const deleteArray = _.transform(resourceMapByparentId[record.id], (result, resource) => {
            result.push(+resource.id)
          }, [])
          _.remove(roleResIdList, e => _.includes(deleteArray, e))
        }
        this.setState({ roleResIdList })
        updateState(roleResIdList)
      },
      onSelectAll: selected => {
        if (selected) {
          const allIds = _.map(_.map(resources, 'id'), e => +e)
          this.setState({ roleResIdList: allIds })
          updateState(allIds)
        } else {
          this.setState({ roleResIdList: [] })
          updateState([])
        }
      },
      getCheckboxProps: record => ({
        defaultChecked: _.includes(roleResIdList, +record.id) || _.includes(roleResIdList, `${record.id}`),
      }),
    }

    const tableProp = {
      key: this.state.key,
      rowKey: record => record.id,
      columns: tableColumns,
      dataSource: resourceSecondTree,
      bordered: true,
      pagination: false,
      loading,
      defaultExpandAllRows: false,
      rowSelection,
      expandedRowKeys: this.state.expandedRowKeys,
      onExpandedRowsChange: this.onExpandedRowsChange.bind(this),
    }

    return (
      <Table scroll={{ x: 1200 }} {...tableProp} />
    )
  }
}

/**
 * 订阅 model
 */
function mapStateToProps({ loading, sysStore }) {
  return {
    loading: loading.models.sysStore,
    sysStore,
  }
}


function mapDispatchToProps(dispatch) {
  return {

    updateState(resourceList) {
      dispatch({
        type: 'sysRoleStore/updatePermission',
        resourceList,
      })
    },

  }
}


const resourceFields = [
  {
    key: 'name',
    name: '资源名称',
    width: '20%',
  },
  {
    key: 'permission',
    name: '权限标识',
    width: '20%',
  },
]

export default connect(mapStateToProps, mapDispatchToProps)(PermissionComponet)
