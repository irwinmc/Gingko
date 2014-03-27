Ext.define('IDAT.view.home.Menu', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.layoutmenu',

	region: 'west',
    width: 180,
    height: 300,
    title: LANG.TITLE.menu,
    iconCls: 'icon-menu',
    containerScroll: true,
	collapsible: true,
	autoScroll: false,
	border: false,
    frame: true,
	split: true,
	enableDD: false,
    
    layout: {
        // layout-specific configs go here
        type: 'accordion',
        titleCollapse: true,
        animate: false,
        activeOnTop: false
    },
    items: [{
        title: '报表处理',
        iconCls: 'icon-data',
        xtype: 'treepanel',
        store: 'TreeMenus',
        bodyStyle: "padding:5 5 5 5",
        rootVisible: false,

        listeners: {
            'beforeexpand': function(view) {
                this.store.proxy.extraParams={parentId:'dataanalytic',  identity : GLOBAL.IDENTITY};
                this.store.reload();
            },

            'itemmousedown': function(selModel, record) {
                loadMenu(selModel, record);
            }
        }
    }, {
        title: '系统管理',
        iconCls: 'icon-user',
        xtype: 'treepanel',
        store: 'TreeMenus',
        bodyStyle: "padding:5 5 5 5",
        rootVisible: false,

        listeners: {
            'beforeexpand': function(view) {
                this.store.proxy.extraParams={parentId:'manage',  identity : GLOBAL.IDENTITY};
                this.store.reload();
            },

            'itemmousedown': function(selModel, record) {
                loadMenu(selModel, record);
            }
        }
    }, {
        title: '系统设置',
        iconCls: 'icon-setting',
        xtype: 'treepanel',
        store: 'TreeMenus',
        bodyStyle: "padding:5 5 5 5",
        rootVisible: false,

        listeners: {
            'beforeexpand': function(view) {
                this.store.proxy.extraParams={parentId:'setting',  identity : GLOBAL.IDENTITY};
                this.store.reload();
            },

            'itemmousedown': function(selModel, record) {
                loadMenu(selModel, record);
            }
        }
    }]
});

loadMenu = function(selModel, record) {
	var tabs = Ext.getCmp("content-panel");
	var tab = tabs.getComponent(record.get('id'));
	
	if (record.get('leaf')) {
		if (!tab) {
			tabs.add({
				id : record.get('id'),
				closable : true,
				title : record.get('text'),
				iconCls : record.get('iconCls'),
				layout : 'fit',
				xtype : record.get('id')
			}).show();
		} else {
			tabs.setActiveTab(tab);
		}
	} else {
		var expand = record.get('expanded');
		if (expand) {
			selModel.collapse(record);
		} else {
			selModel.expand(record);
		}
	}
}; 