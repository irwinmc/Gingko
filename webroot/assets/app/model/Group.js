Ext.define('IDAT.model.Group', {
    extend: 'Ext.data.Model',

    requires: [
        'Ext.data.reader.Json'
    ],

    fields: ['groupId', 'name', 'host']
});