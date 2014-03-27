Ext.define('IDAT.model.Css', {
	extend : 'Ext.data.Model',
	
    requires: [
        'Ext.data.reader.Json'
    ],
	
	fields : [ 'cssId', 'css', 'text', 'type']
});