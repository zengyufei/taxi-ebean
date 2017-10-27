const syncRequest = require('sync-request')
const qs = require('qs')

const { setHeader, baseUrl } = require('../http')

module.exports = {
    syncGet(url, params) {
        console.log(`${baseUrl + url}?${qs.stringify(params)}`)
        const res = syncRequest('GET', `${baseUrl + url}?${qs.stringify(params)}`, {
            headers: setHeader({}),
        })
        if (res.statusCode === 200) {
            return JSON.parse(res.getBody())
        }
        return res.getBody()
    },
    syncPost(url, params) {
        const res = syncRequest('POST', baseUrl + url, {
            json: params,
            headers: setHeader({}),
        })
        if (res.statusCode === 200) {
            return JSON.parse(res.getBody('utf8'))
        }
        return res.getBody('utf8')
    },
}
