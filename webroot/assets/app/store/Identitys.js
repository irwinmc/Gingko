Ext.define('IDAT.store.Identitys', {
    extend: 'Ext.data.Store',
    model: 'IDAT.model.Identity',

    proxy: {
        type: 'ajax',
        url: 'action/identity_load',
        method: 'Get',
        reader: {
            type: 'json',
            root: 'items',
            totalProperty: 'count',
            successProperty: 'success'
        }
    }
});