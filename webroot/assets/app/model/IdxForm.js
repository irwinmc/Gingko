Ext.define('IDAT.model.IdxForm', {
    extend: 'Ext.data.Model',

    requires: [
        'Ext.data.reader.Json'
    ],

    fields: ['id', 'siid', 'formType', 'reportType', 'name', 'date', 'localFile', 'anchor', 'state']
});