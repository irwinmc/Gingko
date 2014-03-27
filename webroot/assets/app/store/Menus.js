Ext.define('IDAT.store.Menus', {
	extend : 'Ext.data.Store',
	
	model: 'IDAT.model.Menu',
	
	proxy : {
		type : 'ajax',
		url : 'menu_load.action',
		
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