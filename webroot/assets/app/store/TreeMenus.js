Ext.define('IDAT.store.TreeMenus', {
	extend : 'Ext.data.TreeStore',
	
	model : 'IDAT.model.TreeMenu',
	autoLoad: true,

	proxy : {
		type : 'ajax',
		url : 'action/menu_load',
		// Send first menu parentId
		extraParams : {
			parentId : GLOBAL.PARENT_ID,
            identity : GLOBAL.IDENTITY
		}
	}
});