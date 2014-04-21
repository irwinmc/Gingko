Ext.define('IDAT.store.ComboIdentity', {
	extend: 'Ext.data.Store',
	model: 'IDAT.model.Combo',

	data: {
		'items': [
			{'display': '普通权限', "value": 1},
			{'display': '高级权限', "value": 2},
			{'display': '超级权限', "value": 3}
		]
	},

	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	}
});