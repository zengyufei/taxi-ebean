import List from './List'
import Add from './Add'
import Update from './Update'
import UpdatePermission from  './UpdatePermission'

const index = () => {
    return (
        <div>
            <Add/>
            <Update/>
            <UpdatePermission />
            <List/>
        </div>
    )
}

export default index
