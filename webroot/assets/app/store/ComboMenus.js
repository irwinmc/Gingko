Ext.define('IDAT.store.ComboMenus', {
    extend: 'Ext.data.Store',
    model: 'IDAT.model.Combo',

    proxy: {
        type: 'ajax',
        url: 'action/menu_combo',
        method: 'Get'
    }
});