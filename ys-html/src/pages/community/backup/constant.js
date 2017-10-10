const { tokenSessionKey } = constant

const prefix = 'community'
const name = '小区'
const storeName = `${prefix}Store`

// 搜索条
const searchCacheKey = `${prefix}_condin`
const defaultSearchFields = ['name']
const allSearchFields = defaultSearchFields
// 上传文件
const importAction = `${BASE_URL}/${prefix}/import.htm`
// 上传图片
const uploadAction = `${BASE_URL}/fileupload/image.htm`
// 下载文件
const exportFileParam = defaultSearchFields
const exportFileAction = `${BASE_URL}/${prefix}/export.htm`

// 表格
const defaultTableFields = ['name', 'buildMax', 'unitMax', 'floorMax', 'roomMax']

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
