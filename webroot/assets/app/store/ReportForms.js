Ext.define('IDAT.store.ReportForms', {
	extend: 'Ext.data.Store',
	model: 'IDAT.model.ReportForm',

	proxy: {
		type: 'ajax',
		url: ACTION.REPORT_FORM_LOAD,
		method: 'GET',
		reader: {
			type: 'json',
			root: 'items',
			totalProperty: 'count',
			successProperty: 'success'
		}
	}
});