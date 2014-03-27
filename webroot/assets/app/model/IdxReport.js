Ext.define('IDAT.model.IdxReport', {
    extend: 'Ext.data.Model',

    requires: [
        'Ext.data.reader.Json'
    ],

    fields: ['description','type', 'anchor', 'siid']
});