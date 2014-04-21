Ext.define('IDAT.model.ReportForm', {
	extend: 'Ext.data.Model',

	requires: [
		'Ext.data.reader.Json'
	],

	fields: ['id', 'siid', 'formType', 'reportType', 'name', 'date', 'localFile', 'anchor', 'state']
});