Ext.define('IDAT.store.ComboYesNo', {
	extend : 'Ext.data.Store',
	model : 'IDAT.model.Combo',
	
	data:{'items':[
        { 'display': '是',  "value":1},
        { 'display': '否',  "value":0}
    ]},
    proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            root: 'items'
        }
    }
});