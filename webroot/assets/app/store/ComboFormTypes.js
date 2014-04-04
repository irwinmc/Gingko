Ext.define('IDAT.store.ComboFormTypes', {
    extend: 'Ext.data.Store',
    model: 'IDAT.model.Combo',

    proxy: {
        type: 'ajax',
        url: ACTION.SET_FORM_TYPE_COMBO,
        method: 'GET',
        extraParams : {
            groupId : GLOBAL.GROUP_ID
        }
    }
});