Ext.define('IDAT.controller.Main', {
	extend: 'Ext.app.Controller',
	stores: [
        'ComboYesNo@IDAT.store'
    ],
	models: [
        'TreeMenu@IDAT.model',
        'Combo@IDAT.model'
    ],
	views: [
        'Header@IDAT.view.home',
        'Bottom@IDAT.view.home',
        'TabPanel@IDAT.view.home'
    ],
	refs: [
		{ref: 'layoutHeader', selector: 'layoutheader'},
		{ref: 'layoutBottom', selector: 'layoutbottom'},
		{ref: 'layoutTabPanel', selector: 'layouttabpanel'}
	],

	init: function () {
		this.control({
		});
	}
});