import React from 'react'
import { Icon } from 'antd'
import styles from './index.less'

const Error = () => (<div className="content-inner">
  <div className={styles.error}>
    <Icon type="frown-o" />
    <h1>404 Not Found</h1>
    <div>
      <a href="/#/">返回主页</a>
    </div>
  </div>
</div>)

export default Error
