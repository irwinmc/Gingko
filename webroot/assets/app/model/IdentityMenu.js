Ext.define('IDAT.model.IdentityMenu', {
	extend: 'Ext.data.Model',

	requires: [
		'Ext.data.reader.Json'
	],

	fields: ['id', 'identity', 'menuId']
});