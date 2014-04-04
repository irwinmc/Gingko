Ext.define('IDAT.model.IdxHtml', {
    extend: 'Ext.data.Model',

    requires: [
        'Ext.data.reader.Json'
    ],

    fields: ['description','type', 'anchor', 'siid']
});