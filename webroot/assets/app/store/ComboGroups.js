Ext.define('IDAT.store.ComboGroups', {
    extend: 'Ext.data.Store',
    model: 'IDAT.model.Combo',

    proxy: {
        type: 'ajax',
        url: ACTION.SET_GROUP_COMBO,
        method: 'GET'
    }
});