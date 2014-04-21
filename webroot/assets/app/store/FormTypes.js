Ext.define('IDAT.store.FormTypes', {
	extend: 'Ext.data.Store',
	model: 'IDAT.model.FormType',

	proxy: {
		type: 'ajax',
		url: ACTION.SET_FORM_TYPE_LOAD,
		method: 'GET',
		reader: {
			type: 'json',
			root: 'items',
			totalProperty: 'count',
			successProperty: 'success'
		}
	}
});