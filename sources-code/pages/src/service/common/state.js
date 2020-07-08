var state = {
    // 图书类别
    "category": {
        "1001": "考试",
        "2001": "历史",
        "20011": "近代史"
    },
    "categoryList":[{"name":"考试","code":"1001"},{"name":"历史","code":"2001"},{"name":"近代史","code":"20011"}],

    //订单状态
    "orderState": {
        "0": "未付款",
        "1": "付款处理中",
        "2": "已付款",
        "3": "等待发货",
        "4": "已发货",
        "5": "交易成功",
        "6": "交易关闭",
        "7": "订单异常",
        "8": "已收货"
    },
    //制式
    "mobileCorp": {
        "TEL": "电信",
        "MOB": "移动"
        //"UNI": "联通"
    },
    //订单类型
    "orderType": {
        "1": "入网订单",
        "2": "企业订单",
        "3": "套餐充值",
        "5": "实名登记"
    },
    //号码状态
    "numState": {
        "10U": "待激活",
        "10A": "在用",
        "10B": "停用",
        "10C": "停机保号",
        "10X": "拆机",
        "10D": "激活中"
    },
    "payMode": {
        "1": "微信支付",
        "2": "支付宝",
        "3": "代理商扣款"
    },
    "payState": {
        "0": "未支付",
        "1": "支付中",
        "2": "已支付"
    },
    "state": {
        "01": "启用",
        "02": "禁用"
    },
    "payAttr": {
        "01": "自付费",
        "02": "代理商扣款"
    },
    "cardState": {
        "10U":"未初始化",
        "Active": "已激活-Active",
        "Deactivate": "去激活",
        "Pause": "停机-Pause",
        "Paused": "停机-Paused",
        "Terminated": "拆机",
        "1": "待激活",
        "2": "已激活-2",
        "4": "停机-4",
        "6": "可测试",
        "7": "库存",
        "8": "预销户",
        "9": "已销户"
    }
}

export default state;