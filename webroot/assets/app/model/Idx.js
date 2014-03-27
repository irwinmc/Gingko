Ext.define('IDAT.model.Idx', {
    extend: 'Ext.data.Model',

    requires: [
        'Ext.data.reader.Json'
    ],

    fields: ['cik', 'code', 'companyName', 'formType', 'fileName', 'siid', 'state', 'operator', 'date']
});