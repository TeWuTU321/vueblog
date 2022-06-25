// 跨域配置
module.exports = {
  devServer: {                //记住，别写错了devServer//设置本地默认端口  选填
      port: 8099,
      proxy: {                 //设置代理，必须填
          '/ap': {              //设置拦截器  拦截器格式   斜杠+拦截器名字，名字可以自己定
              target: 'http://localhost:8081',     //代理的目标地址
              changeOrigin: true,              //是否设置同源，输入是的,允许跨域
              pathRewrite: {                   //路径重写
                  '^/ap': '/'                     //选择选择目标路径中需要代替的内容例如/ap
              }
          }
      }
  }
}
