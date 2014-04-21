Ext.define('IDAT.view.home.Header', {
	extend: 'Ext.panel.Header',
	alias: 'widget.layoutheader',

	region: 'north',
	height: 35,
	cls: 'logo',

    items: [{
        xtype: 'splitbutton',
        text: '报表处理',
        margin: '0 10 0 10',
        iconCls: 'icon-data',
        menu: new Ext.menu.Menu({
            items:[{
                text:"索引查询",
                icon:"assets/img/lib/16px/vote.png",
                handler: function() {
                    loadTabpanel('idxpanel');
                }
            }]
        })
    }, {
        xtype: 'splitbutton',
        text: '系统管理',
        margin: '0 10 0 0',
        iconCls: 'icon-user',
        menu: new Ext.menu.Menu({
            items:[{
                text:"用户管理",
                icon:"assets/img/lib/16px/user.png",
                handler: function() {
                    loadTabpanel('usermanage');
                }
            }]
        })
    }, {
        xtype: 'splitbutton',
        text: '系统设置',
        margin: '0 10 0 0',
        iconCls: 'icon-setting',
        menu: new Ext.menu.Menu({
            items:[{
                text:"美股设置",
                icon:"assets/img/lib/16px/user.png",
                menu: [{
                    text:"报表类型设置",
                    icon:"assets/img/lib/16px/user.png",
                    handler: function() {
                        loadTabpanel('settingformtype');
                    }
                }]
            }, {
                xtype: 'menuseparator'
            }, {
                text:"权限设置",
                icon:"assets/img/lib/16px/user.png",
                handler: function() {
                    loadTabpanel('settingidentity');
                }
            }, {
                xtype: 'menuseparator'
            }, {
                text:"小组设置",
                icon:"assets/img/lib/16px/user.png",
                handler: function() {
                    loadTabpanel('settinggroup');
                }
            }]
        })
    }]
});

loadTabpanel = function (id) {
    Ext.Ajax.request({
        url: ACTION.MENU_LOAD,
        method: 'GET',
        params: {
            menuId: id,
            identity: GLOBAL.IDENTITY
        },
        failure: function (response) {
            var responseJson = Ext.JSON.decode(response.responseText);
            Ext.Msg.alert(LANG.TITLE.failure, responseJson.msg);
        },
        success: function (response) {
            var responseJson = Ext.JSON.decode(response.responseText);
            var success = responseJson.success;
            if (success) {
                var tabs = Ext.getCmp("content-panel");
                var tab = tabs.getComponent(id);

                if (!tab) {
                    tabs.add({
                        id: id,
                        closable: true,
                        title: responseJson.menu.text,
                        iconCls: responseJson.menu.iconCls,
                        layout: 'fit',
                        xtype: id
                    }).show();
                } else {
                    tabs.setActiveTab(tab);
                }
            } else {
                var responseJson = Ext.JSON.decode(response.responseText);
                Ext.Msg.alert(LANG.TITLE.failure, responseJson.msg);
            }
        }
    });
};