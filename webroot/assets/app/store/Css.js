Ext.define('IDAT.store.Css', {
	extend : 'Ext.data.Store',
	
	model: 'IDAT.model.Css',
	
	proxy : {
		type : 'ajax',
		url : 'css_load.action',
		
		getMethod: function() {
			return 'POST';
		},
		
        reader: {
            type: 'json',
            root: 'items',
            totalProperty: 'count',
            successProperty: 'success'
        }
	}
});