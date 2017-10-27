const webpack = require('webpack')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const domains = require('./src/configs/domains')

module.exports = (webpackConfig, env) => {
    // 环境判断
    // process.env.NODE_ENV === 'development'
    // process.env.NODE_ENV === 'dev'
    // process.env.NODE_ENV === 'local'
    // process.env.NODE_ENV === 'remote'
    // webpackConfig配置
    const path = require('path')

    const ROOT_PATH = path.resolve(__dirname)
    const OUTPUT_PATH = path.resolve(ROOT_PATH, 'dist')

    // 输出配置暂时不配置hash串，[hash:8]
    webpackConfig.output = {
        path: OUTPUT_PATH,
        filename: 'static/[name]-[hash:6].js',
        pathinfo: true,
        // publicPath: './',
        // 按需加载
        chunkFilename: 'static/chunk/[name]-[chunkhash:6].js',
    }

    webpackConfig.module.loaders[0].exclude.push(/\.ejs$/)

    // 插件增加
    // 开发环境插件
    // let isExtractTextPlugin = false
    for (const x in webpackConfig.plugins) {
        if (Object.prototype.hasOwnProperty.call(webpackConfig.plugins, x)) {
            const constructorName = webpackConfig.plugins[x].constructor.name
            if (constructorName === 'DefinePlugin') {
                let pluginsDefinitions = {
                    IS_DEV: JSON.stringify(env === 'development'),
                    NODE_ENV: JSON.stringify(env),
                }
                const domain = process.env.DOMAIN || env
                if (domains[domain]) {
                    domains[domain].baseUrl && (pluginsDefinitions.BASE_URL = JSON.stringify(domains[domain].baseUrl))
                    const uploadUrl = domains[domain].uploadUrl
                    if (uploadUrl) {
                        if (/\/$/.test(uploadUrl)) {
                            pluginsDefinitions.UPLOAD_URL = JSON.stringify(uploadUrl)
                        } else {
                            pluginsDefinitions.UPLOAD_URL = JSON.stringify(`${uploadUrl}/`)
                        }
                    }
                } else {
                    pluginsDefinitions.BASE_URL = JSON.stringify('')
                    pluginsDefinitions.UPLOAD_URL = JSON.stringify('/upload/')
                }
                webpackConfig.plugins[x].definitions = Object.assign(
                    {},
                    webpackConfig.plugins[x].definitions,
                    pluginsDefinitions,
                )
            }
            // CommonsChunkPlugin
            if (constructorName === 'CommonsChunkPlugin') {
                webpackConfig.plugins[x].filenameTemplate = 'static/common-[chunkhash:6].js'
            }
        }
    }

    if (env === 'production') {
        webpackConfig.plugins.push(
            new webpack.optimize.UglifyJsPlugin({
                output: {
                    comments: false, // remove all comments
                    // drop_debugger: true,
                    // drop_console: true,
                },
                compress: {
                    warnings: false,
                },
            }),
        )
    }

    webpackConfig.plugins.push(
        new HtmlWebpackPlugin({
            template: 'ejs!src/template.ejs',
            inject: true,
            title: '中智仿真',
            filename: 'index.html',
            chunks: ['index'],
            hash: true,
            cache: false,
        }),
    )

    /* webpackConfig.plugins.push(new HtmlWebpackPlugin({
    title: '中智仿真',
    filename: 'index.html',
    template: './src/template.html',
    inject: true,
    chunks: ['index'],
    hash: true,
    cache: false,
  })) */

    // 全局暴露React
    webpackConfig.plugins.push(
        new webpack.ProvidePlugin({
            qs: 'qs',
            React: 'react',
            PropTypes: 'prop-types',
            ZMsg: 'ZMsg',
            ZButton: 'ZButton',
            R: 'ramda',
            _: 'lodash',
            moment: 'moment',
            PageUtils: `${__dirname}/src/components/carno/utils/page`,
            local: `${__dirname}/src/components/carno/utils/storage/localStorage`,
            session: `${__dirname}/src/components/carno/utils/storage/sessionStorage`,
            constant: `${__dirname}/src/configs/constant`, // 常量,
            projectConfig: `${__dirname}/src/configs/projectConfig`, // 一般配置,
        }),
    )

    // PreLoaders
    // webpackConfig.module.preLoaders = [{
    //   test: /\.js$/,
    //   enforce: 'pre',
    //   loader: 'eslint',
    // }]

    // Alias
    webpackConfig.resolve.alias = {
        src: `${__dirname}/src`, // 源码目录

        assets: `${__dirname}/src/assets`, // 静态文件目录

        configs: `${__dirname}/src/configs`, // 一般配置

        models: `${__dirname}/src/models`, // model 集合
        services: `${__dirname}/src/services`, // model 集合

        routes: `${__dirname}/src/routes`, // 路由集合
        pages: `${__dirname}/src/pages`, // 页面集合

        components: `${__dirname}/src/components`, // 组件集合
        /**
         * 自定义组件
         */
        ZButton: `${__dirname}/src/components/ZButton`,
        ZMsg: `${__dirname}/src/components/ZMsg`,

        carno: `${__dirname}/src/components/carno`, // 再封装 dva 集合
        ZForm: `${__dirname}/src/components/carno/components/Form/Form`, // 表单封装
        ZFormItem: `${__dirname}/src/components/carno/components/Form/FormItem`, // 表单封装
        ModelUtils: `${__dirname}/src/components/carno/utils/model`, // model 封装
        TableUtils: `${__dirname}/src/components/carno/utils/table`, // 表格封装
        FormUtils: `${__dirname}/src/components/carno/utils/form`, // 表格封装
        PageUtils: `${__dirname}/src/components/carno/utils/page`, // 表格封装
        ZModal: `${__dirname}/src/components/carno/components/Modal`, // 模态窗口封装
        ZSearch: `${__dirname}/src/components/carno/components/SearchBar`, // 搜索框封装

        utils: `${__dirname}/src/utils`, // 工具类集合

        enums: `${__dirname}/src/utils/enums`, // 枚举类集合
    }

    return webpackConfig
}
