Ext.define('IDAT.model.Identity', {
    extend: 'Ext.data.Model',

    requires: [
        'Ext.data.reader.Json'
    ],

    fields: ['id','identity', 'menuId']
});