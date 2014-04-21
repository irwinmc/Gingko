Ext.define('IDAT.store.Users', {
	extend: 'Ext.data.Store',
	model: 'IDAT.model.User',

	proxy: {
		type: 'ajax',
		url: ACTION.USER_LOAD,
		method: 'GET',
		reader: {
			type: 'json',
			root: 'items',
			totalProperty: 'count',
			successProperty: 'success'
		}
	}
});