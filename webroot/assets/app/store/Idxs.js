Ext.define('IDAT.store.Idxs', {
    extend: 'Ext.data.Store',
    model: 'IDAT.model.Idx',

    pageSize: 15,
    proxy: {
        type: 'ajax',
        url: 'action/report_loadIdx',
        method: 'Get',
        reader: {
            type: 'json',
            root: 'items',
            totalProperty: 'count',
            successProperty: 'success'
        }
    }
});