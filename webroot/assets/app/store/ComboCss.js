Ext.define('IDAT.store.ComboCss', {
	extend : 'Ext.data.Store',
	model : 'IDAT.model.Combo',
	
	autoLoad: false,
	
	proxy : {
		type : 'ajax',
		url : 'css_combo.action',
		
		extraParams : {
			type : 1
		}
	}
});