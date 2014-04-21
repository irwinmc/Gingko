Ext.define('IDAT.view.home.TabPanel', {
	extend: 'Ext.tab.Panel',
	alias: 'widget.layouttabpanel',

	id: 'content-panel',
	region: 'center',
	autoScroll: false,
	bodyPadding: 0,
	activeTab: 0,
	border: false,
	frame: true,

	items: [{
		id: 'HomePage',
		title: LANG.TITLE.home_page,
		iconCls: 'icon-app-home',
		layout: 'fit',
		items: [{

		}]
	}]
});