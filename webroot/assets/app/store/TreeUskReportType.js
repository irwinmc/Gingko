Ext.define('IDAT.store.TreeUskReportType', {
    extend: 'Ext.data.TreeStore',
    model: 'IDAT.model.TreeMenu',
    autoLoad: true,

    proxy: {
        type: 'ajax',
        url: 'data/usk.json'
    }
});