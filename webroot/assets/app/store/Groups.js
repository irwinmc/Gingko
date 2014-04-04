Ext.define('IDAT.store.Groups', {
    extend: 'Ext.data.Store',
    model: 'IDAT.model.Group',

    proxy: {
        type: 'ajax',
        url: ACTION.SET_GROUP_LOAD,
        method: 'GET',
        reader: {
            type: 'json',
            root: 'items',
            totalProperty: 'count',
            successProperty: 'success'
        }
    }
});