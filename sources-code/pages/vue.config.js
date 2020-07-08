module.exports = {
    publicPath: './',
    productionSourceMap: false,
    outputDir: 'lib-mg',
    devServer: {
        proxy: {
            
            '/lib-server/mg':{
                target:'http://127.0.0.1:9999/lib-server/mg',
                changeOrigin:true,
                pathRewrite:{
                    '^/lib-server/mg':''
                }
            }
        }
    }
}