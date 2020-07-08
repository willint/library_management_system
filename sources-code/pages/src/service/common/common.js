import CryptoJS from 'crypto-js'

const isMobileNum = (num) => {
    var mobile = /^((\(\d{3}\))|(\d{3}\-))?1[3456789]\d{9}$/;
    return mobile.test(num);
}
const isNull = (value) => {
    if (value == undefined || value == null || value == "") {
        return true;
    } else {
        return false;
    }
}
const isInt = (num) => {
    var value = /^[1-9]+[0-9]*]*$/;
    return value.test(num);
}
const isFloat = (num) =>{
    var value = /^(-?\d+)(\.\d+)?$/
    return value.test(num);
}
const apiOk = (data) => {
    return data.header.respCode == "0";
}
const apiMsg = (data) => {
    return data.header.respMsg;
}
const encryptByAES = (ciphertext) => {
    return CryptoJS.AES.encrypt(ciphertext, "hnatelecom19").toString();
}
const findIndex = (array, value) => {
    if (array === null || array.length === 0) {
        return -1
    }
    for (let i = 0; i < array.length; i++) {
        if (array[i] === value) {
            return i;
        }
    }
    return -1;
}
const loginInfo = () => {
    var loginInfo = sessionStorage.getItem('loginInfo');
    if (loginInfo === null) {
        return null
    }
    return JSON.parse(loginInfo);
}
const isSys = () => {
    var info = loginInfo();
    return info.user.isSys === '1';
}
const permCheck = (perm) => {
    if (isSys()) {
        return true;
    }
    var info = loginInfo();
    if (info === null || info.permissions === null) {
        return false;
    }
    return findIndex(info.permissions, perm) > -1
}
const formatter = (thistime, fmt) => {
    if (thistime === null || thistime === undefined) {
        return '';
    }

    thistime = thistime.replace(/-/g,"/").replace("T"," ").split(".")[0];
    
    let $this = new Date(thistime);
    let o = {
        'M+': $this.getMonth() + 1,
        'd+': $this.getDate(),
        'h+': $this.getHours(),
        'm+': $this.getMinutes(),
        's+': $this.getSeconds(),
        'q+': Math.floor(($this.getMonth() + 3) / 3),
        'S': $this.getMilliseconds()
    }
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, ($this.getFullYear() + '').substr(4 - RegExp.$1.length))
    }
    for (var k in o) {
        if (new RegExp('(' + k + ')').test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)))
        }
    }
    return fmt
}
const concat = (originalArr, targetArr) => {
    if (originalArr == null) {
        originalArr = new Array();
    }
    if (targetArr == null) {
        return originalArr;
    }
    targetArr.forEach(function (data) {
        originalArr.push(data);
    });
    return originalArr;
}
const valuetoname = (arr, key, flag) => {
    var result = "";
    if (flag === 1) {
        arr.forEach(function (data) {
            if (data.value == key) {
                return result = data.name;
            }
        });
    } else {
        arr.forEach(function (data) {
            if (data.name == key) {
                return result = data.value;
            }
        });
    }
    return result;
}
export default {
    isMobileNum,
    isNull,
    isInt,
    isFloat,
    apiOk,
    apiMsg,
    encryptByAES,
    findIndex,
    loginInfo,
    permCheck,
    formatter,
    concat,
    isSys,
    valuetoname
}