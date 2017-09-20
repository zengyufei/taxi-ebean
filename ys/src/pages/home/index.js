import { connect } from 'dva'
import { Collapse } from 'antd'

const Home = options => {
  const { methods, homeStore } = options

  return (
    <div>
    asd
    </div>
  )
}

const mapStateToProps = ({ homeStore }) => {
  return {
    homeStore,
  }
}

const mapDispatchToProps = dispatch => {
  return {
    methods: {}
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Home)
