Ext.define('IDAT.store.ComboReportTypes', {
    extend: 'Ext.data.Store',
    model: 'IDAT.model.Combo',

    proxy: {
        type: 'ajax',
        url: ACTION.REPORT_TYPE_COMBO,
        method: 'GET'
    }
});