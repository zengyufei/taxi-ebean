export default {
    'entry': 'src/index.js',
    'theme': './theme.js',
    'env': {

        'development': {
            'extraBabelPlugins': [
                'dva-hmr',
                'transform-runtime',
                ['import', { 'libraryName': 'antd', 'libraryDirectory': 'lib', 'style': true }],
            ],
        },

        'production': {
            'extraBabelPlugins': [
                'transform-runtime',
                ['import', { 'libraryName': 'antd', 'libraryDirectory': 'lib', 'style': true }],
            ],
        },

        'remote': {
            'extraBabelPlugins': [
                'dva-hmr',
                'transform-runtime',
                ['import', { 'libraryName': 'antd', 'libraryDirectory': 'lib', 'style': true }],
            ],
        },

        'local': {
            'extraBabelPlugins': [
                'dva-hmr',
                'transform-runtime',
                ['import', { 'libraryName': 'antd', 'libraryDirectory': 'lib', 'style': 'css' }],
            ],
        },

        'localIp': {
            'extraBabelPlugins': [
                'dva-hmr',
                'transform-runtime',
                ['import', { 'libraryName': 'antd', 'libraryDirectory': 'lib', 'style': 'css' }],
            ],
        },
    },
}
