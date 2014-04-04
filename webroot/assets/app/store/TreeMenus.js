Ext.define('IDAT.store.TreeMenus', {
	extend : 'Ext.data.TreeStore',
	model : 'IDAT.model.TreeMenu',
	autoLoad: true,

	proxy : {
		type : 'ajax',
		url : ACTION.MENU_TREE_LOAD,
        method: 'GET',
		// Send first menu parentId
		extraParams : {
			parentId : GLOBAL.PARENT_ID,
            identity : GLOBAL.IDENTITY
		}
	}
});