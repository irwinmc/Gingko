Ext.define('IDAT.model.FormType', {
    extend: 'Ext.data.Model',

    requires: [
        'Ext.data.reader.Json'
    ],

    fields: ['formType', 'used']
});