Ext.define('IDAT.store.TreeIdentityMenus', {
	extend : 'Ext.data.TreeStore',
	
	model : 'IDAT.model.TreeMenu',
	autoLoad: false,
	proxy : {
		type : 'ajax',
		url : ACTION.MENU_BY_IDENTITY,
        method: 'GET'
	}
});