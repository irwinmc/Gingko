Ext.define('IDAT.store.Idxs', {
    extend: 'Ext.data.Store',
    model: 'IDAT.model.Idx',

    pageSize: 15,
    proxy: {
        type: 'ajax',
        url: ACTION.REPORT_IDX_LOAD,
        method: 'GET',

        reader: {
            type: 'json',
            root: 'items',
            totalProperty: 'count',
            successProperty: 'success'
        }
    }
});