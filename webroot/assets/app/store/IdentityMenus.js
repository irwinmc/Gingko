Ext.define('IDAT.store.IdentityMenus', {
	extend : 'Ext.data.TreeStore',
	
	model : 'IDAT.model.TreeMenu',
	autoLoad: false,
	proxy : {
		type : 'ajax',
		url : 'action/menu_loadAll',
        method: 'Get'
	}
});