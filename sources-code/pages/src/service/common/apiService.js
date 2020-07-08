import axios from './httpService'
import _router from '../../router'
import Vue from 'vue'

const pageList = (uri, page, size, fromData, callback, error) => {
    var reqData = {};
    reqData.sort = {};
    reqData.page = page;
    reqData.size = size;
    reqData.fromData = fromData;
    post(uri, reqData, callback, error);
}
const post = (uri, reqData, callback, error) => {
    var _this = this;
    axios.post("/lib-server/mg" + uri, reqData).then(function (response) {
        callback(response.data);
    }).catch(function (err) {
        if (err && err.response) {
            switch (err.response.status) {
                case 400: err.message = '请求错误(400)'; Vue.prototype.$message.error('请求错误(400)'); break;
                case 401: err.message = '未授权，请重新登录(401)'; Vue.prototype.$message.error('未授权，请重新登录(401)'); break;
                case 403: err.message = '拒绝访问(403)'; Vue.prototype.$message.error('请求出错(404)'); break;
                case 404: err.message = '请求出错(404)'; Vue.prototype.$message.error('请求出错(404)'); break;
                case 408: err.message = '请求超时(408)'; Vue.prototype.$message.error('请求超时(408))'); break;
                case 500: err.message = '服务器错误(500)'; Vue.prototype.$message.error('服务器错误(500)'); break;
                case 501: err.message = '服务未实现(501)'; Vue.prototype.$message.error('服务未实现(501)'); break;
                case 502: err.message = '网络错误(502)'; Vue.prototype.$message.error('网络错误(502)'); break;
                case 503: err.message = '服务不可用(503)'; Vue.prototype.$message.error('服务不可用(503)'); break;
                case 504: err.message = '网络超时(504)'; Vue.prototype.$message.error('网络超时(504)'); break;
                case 1000: sessionStorage.removeItem('loginInfo'); _router.push('/login'); break;
                case 1403: _router.push('/403'); break;
                default: err.message = `连接出错(${err.response.status})!`;
            }
        } else {
            err.message = '连接服务器失败!';
            Vue.prototype.$message.error('连接服务器失败!');
        }
        if (error) error(err);
        Vue.prototype.$loading({
            lock: true,
            text: 'Loading',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
        }).close();
    });
}
const expExcel = (uri, reqData, error) => {
    var fileName = "文件下载.xlsx";
    axios.post("/lib-server/mg" + uri, reqData, { responseType: 'blob' }).then(res => {
        // console.log(res);
        if(res.headers.filename != null && res.headers.filename != "undefined"){
            fileName = res.headers.filename;
        }
        const blob = new Blob([res.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8' })
        const downloadElement = document.createElement('a')
        const href = window.URL.createObjectURL(blob)
        downloadElement.href = href
        downloadElement.download = fileName;
        document.body.appendChild(downloadElement)
        downloadElement.click()
        document.body.removeChild(downloadElement) // 下载完成移除元素
        window.URL.revokeObjectURL(href) // 释放掉blob对象
    }).catch(function (err) {
        if (err && err.response) {
            switch (err.response.status) {
                case 400: err.message = '请求错误(400)'; Vue.prototype.$message.error('请求错误(400)'); break;
                case 401: err.message = '未授权，请重新登录(401)'; Vue.prototype.$message.error('未授权，请重新登录(401)'); break;
                case 403: err.message = '拒绝访问(403)'; Vue.prototype.$message.error('请求出错(404)'); break;
                case 404: err.message = '请求出错(404)'; Vue.prototype.$message.error('请求出错(404)'); break;
                case 408: err.message = '请求超时(408)'; Vue.prototype.$message.error('请求超时(408))'); break;
                case 500: err.message = '服务器错误(500)'; Vue.prototype.$message.error('服务器错误(500)'); break;
                case 501: err.message = '服务未实现(501)'; Vue.prototype.$message.error('服务未实现(501)'); break;
                case 502: err.message = '网络错误(502)'; Vue.prototype.$message.error('网络错误(502)'); break;
                case 503: err.message = '服务不可用(503)'; Vue.prototype.$message.error('服务不可用(503)'); break;
                case 504: err.message = '网络超时(504)'; Vue.prototype.$message.error('网络超时(504)'); break;
                case 1000: sessionStorage.removeItem('loginInfo'); _router.push('/login'); break;
                case 1403: _router.push('/403'); break;
                default: err.message = `连接出错(${err.response.status})!`;
            }
        } else {
            err.message = '连接服务器失败!';
            Vue.prototype.$message.error('连接服务器失败!');
        }
        if (error) error(err);
        Vue.prototype.$loading({
            lock: true,
            text: 'Loading',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
        }).close();
    });
}

const isOK = (respDate) => {
    return respDate.state == "0";
}
const getBody = (respDate) => {
    return respDate.body;
}
const getMsg = (respDate) => {
    return respDate.msg;
}
export default {
    post,
    isOK,
    getBody,
    getMsg,
    pageList,
    expExcel
}