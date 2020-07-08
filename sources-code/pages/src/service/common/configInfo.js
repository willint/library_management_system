/* eslint-disable */
// 需要区别生产和测试配置
var adConfigInfo = {
    "prod": {
        serverUrl: "http://localhost:8080/lib-server/mg",
        imagesUrl: "http://localhost:8080/lib-server/images",
        fileUrl: "http://localhost:7009/lib-server/files"
    },
    "dev": {
        serverUrl: "http://localhost:8080/lib-server/mg",
        imagesUrl: "http://localhost:8080/lib-server/images",
        fileUrl: "http://localhost:7009/lib-server/files"
    },
    "local":{
        serverUrl: "http://localhost:8080/lib-server/mg",
        imagesUrl: "http://localhost:8080/lib-server/images",
        fileUrl: "http://localhost:7009/lib-server/files"
    }
};
var configInfo = {};
if (location.hostname == "127.0.0.1") {
    configInfo = adConfigInfo["prod"];
}if (location.hostname == "localhost") {
    configInfo = adConfigInfo["local"];
} else {
    configInfo = adConfigInfo["dev"];
}
if (location.hostname == "www.baidu.com") {
    configInfo = adConfigInfo["prod"];
}
// configInfo = adConfigInfo["prod"];
export default configInfo;
