const { tokenSessionKey } = constant

const prefix = 'catchPhoto'
const name = '留影记录'
const storeName = `${prefix}Store`

// 搜索条
const searchCacheKey = `${prefix}_condin`
const defaultSearchFields = ['communityId', 'catchPhotoEnum', 'deviceId', 'cardId']

const allSearchFields = defaultSearchFields
// 上传文件
const importAction = `${BASE_URL}/${prefix}/import`
// 上传图片
const uploadAction = `${BASE_URL}/fileupload/image`
// 下载文件
const exportFileParam = defaultSearchFields
const exportFileAction = `${BASE_URL}/${prefix}/export`

// 表格
const defaultTableFields = ['communityId', 'catchPhotoEnum', 'deviceId', 'cardId']

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
