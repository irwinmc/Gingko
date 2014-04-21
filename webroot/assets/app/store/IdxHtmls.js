Ext.define('IDAT.store.IdxHtmls', {
    extend: 'Ext.data.Store',
    model: 'IDAT.model.IdxHtml',

    proxy: {
        type: 'ajax',
        url: ACTION.REPORT_HTML_LOAD,
        method: 'GET',
        reader: {
            type: 'json',
            root: 'items',
            totalProperty: 'count',
            successProperty: 'success'
        }
    }
});