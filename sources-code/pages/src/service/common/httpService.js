import axios from 'axios'
import common from './common'

// axios 配置
axios.defaults.timeout = 60000;
// http request 拦截器
axios.interceptors.request.use(
    config => {
        config.headers['X-Requested-With'] = 'XMLHttpRequest';
        // 处理POST请求JSON流
        if (config.method === 'post') {
            if (config.data != undefined) {
                // 后台需求requestBoby接受
                config.headers['Content-type'] = 'application/json;charset=UTF-8';
                config.data = JSON.stringify(config.data);
            }
        } else {
            config.params = config.data;
        }
        return config;
    },
    err => {
        return Promise.reject(err);
    });
axios.defaults.retry = 1;
axios.defaults.retryDelay = 1000;
// http response 拦截器
axios.interceptors.response.use(
    response => {
        return response;
    },
    error => {
        var config = error.config;
        // 如果配置不存在或未设置重试选项，则拒绝
        if (!config || !config.retry) {
            return Promise.reject(error);
        } 
        // 设置用于跟踪重试计数的变量
        config.__retryCount = config.__retryCount || 0;
        // 检查我们是否已达到最大重试次数
        if (config.__retryCount >= config.retry) {
            // Reject with the error
            return Promise.reject(error);
        }
        // 增加重试次数
        config.__retryCount += 1;
        // 创建新的承诺来处理指数回退
        var backoff = new Promise(function (resolve) {
            setTimeout(function () {
                resolve();
            }, config.retryDelay || 1);
        });
        // 返回调用AXIOS以重试请求
        return backoff.then(function () {
            return axios(config);
        });
        //return Promise.reject(error)
    });
export default axios;