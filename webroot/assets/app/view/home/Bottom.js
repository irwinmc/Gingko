Ext.define('IDAT.view.home.Bottom', {
	extend: 'Ext.Toolbar',
	alias: 'widget.layoutbottom',

	region: 'south',
	height: 40,
	items: [{
		xtype: 'button',
		text: LANG.BTN.logout,
		iconCls: 'icon-logout',
		handler: function () {
			window.location = 'index.html'
		}
	}, '-', LANG.LABEL.account, GLOBAL.ACCOUNT, '->', '-', LANG.APP.support
	]
});