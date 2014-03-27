Ext.define('IDAT.store.IdxReports', {
    extend: 'Ext.data.Store',
    model: 'IDAT.model.IdxReport',
    
    proxy : {
		type : 'ajax',
		url : 'action/report_loadIdxReport',
        method: 'Get',
		
        reader: {
            type: 'json',
            root: 'items',
            totalProperty: 'count',
            successProperty: 'success'
        }
	}
});