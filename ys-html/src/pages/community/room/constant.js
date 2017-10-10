const { tokenSessionKey } = constant

const prefix = 'room'
const name = '房间'
const storeName = `${prefix}Store`

// 搜索条
const searchCacheKey = `${prefix}_condin`
const defaultSearchFields = ['name', 'alias', 'communityId', 'buildingId', 'unitId']
const allSearchFields = defaultSearchFields
// 上传文件
const importAction = `${BASE_URL}/${prefix}/import.htm`
// 上传图片
const uploadAction = `${BASE_URL}/fileupload/image.htm`
// 下载文件
const exportFileParam = defaultSearchFields
const exportFileAction = `${BASE_URL}/${prefix}/export.htm`

// 表格
const defaultTableFields = ['name', 'alias', 'communityId', 'buildingId', 'unitId']

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
