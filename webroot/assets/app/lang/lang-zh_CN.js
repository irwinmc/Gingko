// 申明一些全局变量
var GLOBAL = {
    PARENT_ID: "dataanalytic",
    ACCOUNT: "",
    IDENTITY: 3
};

/**
 * Created by TangYing
 */
var LANG = {
    APP: {
        name: '<font size ="20" color="green">智能数据采集录入后台</font>',
        support: '技术支持:<a href=\'http://www.10jqka.com.cn/\' target=\'_blank\' style=\'text-decoration:none;\'><font color=\'#0000FF\'>杭州核新同花顺有限公司</font></a>&nbsp;&nbsp;'
    },

    BTN: {
        login: '登陆',
        logout: '注销',
        reg: '注册',
        add: '新增',
        edit: '编辑',
        save: '保存',
        del: '删除',
        refresh: '刷新',
        search: '搜索',
        user_info: '个人信息',
        cancel: '取消',
        reset: '重置',
        confirm: '确定',
        use: '使用选中',
        unuse: '不使用选中'
    },

    TITLE: {
        confirm: '确认',
        tip: '提示',
        warn: '警告',
        success: '操作成功',
        failure: '操作失败',
        login: '欢迎登录',
        logout: '退出登录',
        menu: '功能菜单',
        manage: '管理',
        add: '增加',
        edit: '编辑',
        delete: '删除',
        home_page: '主页',
        idx_grid1: '报表索引',
        idx_grid2: '报表链接',
        identity_menu: '权限菜单',
        identity_grid: '权限表格'
    },

    LABEL: {
        edit: '编辑',
        delete: '删除',
        operate: '操作',
        account: '账号',
        password: '密码',
        name: '员工姓名',
        identity: '权限',
        id: 'ID',
        menu_parent: '上级菜单',
        menu_id: '菜单ID',
        menu: '菜单',
        desc: '描述',
        type: '类型',
        keyword: '关键字',
        cik: 'cik',
        stockCode: '股票代码',
        siid: 'siid',
        companyName: '公司名称',
        formType: '报表类型',
        fileName: '文件名称',
        description: '描述',
        anchor: '链接',
        state: '报表状态',
        date: '报表日期',
        used: '是否使用'
    },

    EMPTY_TIPS: {
        empty_account: '请输入账号',
        empty_password: '请输入密码',
        empty_telephone: '请输入手机号',
        empty_name: '请输入员工姓名',
        empty_combo: '请选择',
        empty_combo_menu: '请选择菜单',
        empty_combo_type: '请选择类型',
        empty_combo_state: '请选择状态',
        empty_combo_identity: '请选择相应权限'
    },

    MESSAGE: {
        delete: '你确定要删除该条数据吗？',
        failure_connect: '系统连接错误！请刷新页面重新载入，如果还不能解决，请联系技术人员',
        empty_record_chose: '<font color="red">请先选择您要删除的行！<font>'
    }
}

/**
 * 功能：为全局提供Render的方法。
 *
 * @author TangYing
 */
RenderUtil = function () {

    return {
        /**
         * 功能：render Date 类型的数据。 使用方式：在
         * ColumnModel中需要显示Date数据的地方：renderer:renderDate('Y-m-d').
         *
         * @param format：如"Y-m-d"
         */
        date: function (format) {
            return function (v) {
                var JsonDateValue;
                if (Ext.isEmpty(v))
                    return '';
                else if (Ext.isEmpty(v.time))
                    JsonDateValue = new Date(v);
                else
                    JsonDateValue = new Date(v.time);
                return JsonDateValue.format(format || 'Y-m-d H:i:s');
            };
        },
        reportDate: function (value) {
            var y = value.substring(0, 4);
            var m = value.substring(4, 6);
            var d = value.substring(6, 8);
            return y + "-" + m + "-" + d;

        },
        btnAnchor: function (value) {
            return "<a href='" + value + "' onclick='return true' target='_blank'><font color='#808080'/>" + value + "</a>";
        },
        btnEdit: function () {
            return "<a href='' onclick='return false'><font color='blue'/>[编辑]</a>";
        },
        btnDel: function () {
            return "<a href='' onclick='return false' ><font color='red'/>[删除]</a>";
        },
        btnOperate: function (value, c, records) {
            switch (REPORT.RECORD_STATE) {
                case 0: return "<a href='' onclick='return false'><font color='#9acd32'/>[开始处理]</a>";
                case 1: return "<a href='' onclick='return false'><font color='green'/>[完成处理]</a>";
                case 2: return "<a href='' onclick='return false'><font color='red'/>[重新处理]</a>";
                default :
                     return "状态错误";
            }
        },
        state: function (value) {
            switch (value) {
                case 0: return "<font color='red'/>未处理";
                case 1: return "<font color='#9acd32'/>正在处理中";
                case 2: return "<font color='green'/>处理完成";
                default :
                    return "状态错误";
            }
        },
        used: function (value) {
            switch (value) {
                case 0: return "<font color='red'/>未使用";
                case 1: return "<font color='#9acd32'/>已使用";
                default :
                    return "未使用";
            }
        },
        identity: function (value) {
            switch (value) {
                case 1: return "普通权限";
                case 2: return "高级权限";
                case 3: return "超级权限";
                default :
                    return "权限错误或未分配";
            }
        }
    };
}();