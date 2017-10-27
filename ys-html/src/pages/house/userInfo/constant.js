const { tokenSessionKey } = constant

const prefix = 'userInfo'
const name = '住户'
const storeName = `${prefix}Store`

// 搜索条
const searchCacheKey = `${prefix}_condin`
const defaultFormFields = [
    'communityId',
    'buildingId',
    'unitId',
    'roomId',
    'userId',
    'authFlag',
    'bindCallFlag',
    'mark',
    'stayEnum',
    'authEnum',
    'applicationTime',
]
const defaultSearchFields = [
    'communityId',
    'buildingId',
    'unitId',
    'roomId',
    'userId',
    'authFlag',
    'bindCallFlag',
    'mark',
    'stayEnum',
    'authEnum',
    'applicationTime',
]
const allSearchFields = defaultSearchFields
// 上传文件
const importAction = `${BASE_URL}/${prefix}/import`
// 上传图片
const uploadAction = `${BASE_URL}/fileupload/image`
// 下载文件
const exportFileParam = defaultSearchFields
const exportFileAction = `${BASE_URL}/${prefix}/export `

// 表格
const defaultTableFields = [
    'communityId',
    'buildingId',
    'unitId',
    'roomId',
    'userId',
    'authFlag',
    'bindCallFlag',
    'mark',
    'stayEnum',
    'authEnum',
    'applicationTime',
]

module.exports = {
    tokenSessionKey,
    prefix,
    name,
    storeName,
    searchCacheKey,
    defaultFormFields,
    defaultSearchFields,
    allSearchFields,
    importAction,
    uploadAction,
    exportFileParam,
    exportFileAction,
    defaultTableFields,
}
