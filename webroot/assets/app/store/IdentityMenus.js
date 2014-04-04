Ext.define('IDAT.store.IdentityMenus', {
    extend: 'Ext.data.Store',
    model: 'IDAT.model.IdentityMenu',

    proxy: {
        type: 'ajax',
        url: ACTION.SET_IDENTITY_MENU_LOAD,
        method: 'GET',
        reader: {
            type: 'json',
            root: 'items',
            totalProperty: 'count',
            successProperty: 'success'
        }
    }
});