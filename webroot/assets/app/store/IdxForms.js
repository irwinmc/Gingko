Ext.define('IDAT.store.IdxForms', {
    extend: 'Ext.data.Store',
    model: 'IDAT.model.IdxForm',

    proxy: {
        type: 'ajax',
        url: ACTION.REPORT_FORM_LOAD,
        method: 'GET',

        reader: {
            type: 'json',
            root: 'items',
            totalProperty: 'count',
            successProperty: 'success'
        }
    }
});