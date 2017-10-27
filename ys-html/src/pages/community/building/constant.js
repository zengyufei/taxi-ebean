const { tokenSessionKey } = constant

const prefix = 'building'
const name = '楼栋'
const storeName = `${prefix}Store`

// 搜索条
const searchCacheKey = `${prefix}_condin`
const defaultSearchFields = ['name', 'alias', 'communityId']
const allSearchFields = defaultSearchFields
// 上传文件
const importAction = `${BASE_URL}/${prefix}/import`
// 上传图片
const uploadAction = `${BASE_URL}/fileupload/image`
// 下载文件
const exportFileParam = defaultSearchFields
const exportFileAction = `${BASE_URL}/${prefix}/export`

// 表格
const defaultTableFields = ['name', 'alias', 'communityId']

module.exports = {
    tokenSessionKey,
    prefix,
    name,
    storeName,
    searchCacheKey,
    defaultSearchFields,
    allSearchFields,
    importAction,
    uploadAction,
    exportFileParam,
    exportFileAction,
    defaultTableFields,
}
