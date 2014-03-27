Ext.define('IDAT.model.User', {
    extend: 'Ext.data.Model',

    requires: [
        'Ext.data.reader.Json'
    ],

    fields: ['userId','account', 'password', 'name', 'identity']
});