Ext.define('IDAT.store.ComboState', {
    extend : 'Ext.data.Store',
    model : 'IDAT.model.Combo',

    data:{'items':[
        { 'display': '全部',  "value":-1},
        { 'display': '未处理',  "value":0},
        { 'display': '处理中',  "value":1},
        { 'display': '已完成',  "value":2}
    ]},
    proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            root: 'items'
        }
    }
});