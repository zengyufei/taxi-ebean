import { connect } from 'dva'
import { Tree, Select } from 'antd'

import styles from './tree.less'

const TreeNode = Tree.TreeNode
const Option = Select.Option

class Demo extends React.Component {
  state = {
    key: new Date(),
    selectProps: {},
  }

  componentWillReceiveProps() {
    const allCommunity = this.props.communityTreeStore.allCommunity
    if (allCommunity.length > 0) {
      this.setState({
        selectProps: {
          defaultValue: !!allCommunity.length && allCommunity[0].id,
        },
      })
    }
  }

  render() {
    const activeModel = this.props.appStore.activeModel
    const { defaultCommunityId, allCommunity, allBuilding, allUnit, allRoom } = this.props.communityTreeStore

    let buildingIds = (!!allBuilding.length || []) && allBuilding.reduce((result, b) => {
      result.push(`building_${b.id}`)
      return result
    }, [])

    let selectProps = this.state.selectProps

    // 递归生成菜单
    const getAllUnit = (allUnits, id) => {
      const sss = []
      allUnits.forEach(item => {
        if (item.buildingId === id) {
          sss.push(<TreeNode title={item.name} key={`unit_${item.id}`}/>)
        }
      })
      return sss
    }

    return (
      <div>
        {
          !!allCommunity.length &&
          <Select defaultValue={`${defaultCommunityId}`} className={styles.select}
                  onChange={e => this.props.onChange(e, activeModel)}>
            {
              allCommunity.map(item => <Option key={`${item.id}`} value={`${item.id}`}>{item.name}</Option>)
            }
          </Select>
        }
        <Tree
          key={`${defaultCommunityId}${allBuilding.length}${allUnit.length}`}
          checkable
          expandedKeys={buildingIds}
          onSelect={(selectedKeys, info) => this.props.onSelect(selectedKeys, info, activeModel)}
          onCheck={(checkedKeys, info) => this.props.onCheck(checkedKeys, info, activeModel)}
        >
          {
            allBuilding.map(item => {
              return (<TreeNode title={item.name} key={`building_${item.id}`}>
                {getAllUnit(allUnit, item.id)}
              </TreeNode>)
            })
          }
        </Tree>
      </div>
    )
  }
}

function mapStateToProps({ appStore, communityTreeStore }) {
  return {
    appStore,
    communityTreeStore,
  }
}

function mapDispatchToProps(dispatch) {
  return {

    onChange(id, activeModel) {
      dispatch({
        type: 'communityTreeStore/updateState',
        defaultCommunityId: id,
      })
      dispatch({
        type: 'communityTreeStore/queryAll',
      })
      dispatch({
        type: `${activeModel}/queryPage`,
      })
    },
    onSelect(selectedKeys, info, activeModel) {
      console.log('selected', selectedKeys, info)
    },
    onCheck(checkedKeys, info, activeModel) {
      if (checkedKeys.length) {
        let buildings = []
        let units = []
        checkedKeys.forEach(e => {
          if (/^building/.test(e)) {
            buildings.push(e.split('_')[1])
          } else if (/^unit/.test(e)) {
            units.push(e.split('_')[1])
          }
        })
        dispatch({
          type: 'communityTreeStore/updateState',
          defaultBuildingIds: buildings,
          defaultUnitIds: units,
        })
      }
      console.log('onCheck', checkedKeys, info)
    },

  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Demo)

