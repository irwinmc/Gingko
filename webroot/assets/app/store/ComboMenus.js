Ext.define('IDAT.store.ComboMenus', {
    extend: 'Ext.data.Store',
    model: 'IDAT.model.Combo',

    proxy: {
        type: 'ajax',
        url: ACTION.MENU_COMBO,
        method: 'GET'
    }
});