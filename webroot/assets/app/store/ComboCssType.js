Ext.define('IDAT.store.ComboCssType', {
	extend : 'Ext.data.Store',
	model : 'IDAT.model.Combo',
	
	data:{'items':[
        { 'display': '菜单图标',  "value":1},
        { 'display': '普通样式',  "value":2}
    ]},
    proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            root: 'items'
        }
    }
});