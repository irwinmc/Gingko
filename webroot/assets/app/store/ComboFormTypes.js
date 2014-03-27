Ext.define('IDAT.store.ComboFormTypes', {
    extend: 'Ext.data.Store',
    model: 'IDAT.model.Combo',

    proxy: {
        type: 'ajax',
        url: 'action/setting_comboFormType',
        method: 'Get'
    }
});