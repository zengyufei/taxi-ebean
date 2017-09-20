const { tokenSessionKey } = constant

const prefix = 'carOperateLog'
const name = '车辆运营'
const storeName = `${prefix}Store`

// 搜索条
const searchCacheKey = `${prefix}_condin`
const defaultSearchFields = ['carNo', 'plateNumber', 'carType']
const allSearchFields = ['carNo', 'plateNumber', 'carType', 'carFrame', 'ownershipNo', 'engineNumber', 'carColor', 'carStatus', 'certificateNo']
// 上传文件
const importAction = `${BASE_URL}/${prefix}/import.htm`
// 上传图片
const uploadAction = `${BASE_URL}/fileupload/image.htm`
// 下载文件
const exportFileParam = ['carNo', 'plateNumber']
const exportFileAction = `${BASE_URL}/${prefix}/export.htm`

// 表格
const defaultTableFields = ['carNo', 'plateNumber', 'carType', 'carFrame', 'ownershipNo', 'ownershipBeginDate', 'ownershipEndDate', 'engineNumber', 'drivingLicenseDate', 'roadTransportBeginDate', 'roadTransportEndDate', 'carColor', 'carStatus', 'certificateNo']

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
