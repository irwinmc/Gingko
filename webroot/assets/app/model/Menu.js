Ext.define('IDAT.model.Menu', {
	extend : 'Ext.data.Model',
	
    requires: [
        'Ext.data.reader.Json'
    ],
	
	fields : [ 'menuId', 'text', 'iconCls', 'parentId', 'qtip', 'leaf']
});