Ext.define('IDAT.store.ComboMathSign', {
	extend: 'Ext.data.Store',
	model: 'IDAT.model.Combo',

	data: {
		'items': [
			{'display': '+', "value": 1},
			{'display': '-', "value": 2}
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