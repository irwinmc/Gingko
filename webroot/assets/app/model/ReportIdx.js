Ext.define('IDAT.model.ReportIdx', {
	extend: 'Ext.data.Model',

	requires: [
		'Ext.data.reader.Json'
	],

	fields: ['cik', 'code', 'companyName', 'formType', 'fileName', 'siid', 'date']
});