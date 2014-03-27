Ext.define('IDAT.store.Users', {
    extend: 'Ext.data.Store',
    model: 'IDAT.model.User',
    
    proxy : {
		type : 'ajax',
		url : 'action/user_load',
		
		getMethod: function() {
			return 'GET';
		},
		
        reader: {
            type: 'json',
            root: 'items',
            totalProperty: 'count',
            successProperty: 'success'
        }
	}
});