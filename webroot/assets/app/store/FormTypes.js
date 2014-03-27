Ext.define('IDAT.store.FormTypes', {
    extend: 'Ext.data.Store',
    model: 'IDAT.model.FormType',

    proxy: {
        type: 'ajax',
        url: 'action/setting_loadFormType',
        method: 'Get',
        reader: {
            type: 'json',
            root: 'items',
            totalProperty: 'count',
            successProperty: 'success'
        }
    }
});