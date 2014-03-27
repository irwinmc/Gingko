var userStore = Ext.create('IDAT.store.Users');
var identityStore = Ext.create('IDAT.store.Identitys');
//var identityTreeStore = Ext.create('IDAT.store.IdentityMenus');

Ext.define('IDAT.controller.Manage', {
    extend: 'Ext.app.Controller',
    models: [  ],

    stores: [ 'Users@IDAT.store',
        'Identitys@IDAT.store',
        'ComboMenus@IDAT.store',
        'ComboIdentity@IDAT.store',
        'IdentityMenus@IDAT.store'],

    views: [ 'List@IDAT.view.manage.user',
        'List@IDAT.view.manage.identity',
        'Tree@IDAT.view.manage.identity',
        'Interface@IDAT.view.manage.identity'],

    refs: [
        { ref: 'userManage', selector: 'usermanage'},
        { ref: 'identityManage', selector: 'identitymanage'},
        { ref: 'identityGrid', selector: 'identitygrid'}
    ],

    init : function() {
		this.control({
            // USER MANAGE START -------------------------------------------------------------
            'usermanage': {
                beforeactivate: function () {
                    userStore.reload();
                },

                cellclick: function(view, td, cellIndex, record) {
                    // Click edit, columns length start from 0
                    if (cellIndex == this.getUserManage().columns.length - 3) {
                        var wndEdit = Ext.create('IDAT.view.manage.user.Edit', {record: record});
                        wndEdit.show();
                    }
                    // Click del
                    else if (cellIndex == this.getUserManage().columns.length - 2) {
                        Ext.Msg.show({
                            title: LANG.TITLE.delete,
                            msg: LANG.MESSAGE.delete,
                            buttons: Ext.Msg.YESNO,
                            icon: Ext.Msg.QUESTION,
                            fn:function(btn) {
                                if (btn == 'yes') {
                                    Ext.Ajax.request({
                                        url: 'action/user_delete',
                                        method: 'Get',
                                        params: {
                                            account: record.get('account')
                                        },
                                        success : function(response) {
                                            var responseJson = Ext.JSON.decode(response.responseText);
                                            Ext.Msg.alert(LANG.TITLE.success, responseJson.msg);
                                            userStore.reload();
                                        },
                                        failure : function(response) {
                                            var responseJson = Ext.JSON.decode(response.responseText);
                                            Ext.Msg.alert(LANG.TITLE.failure, responseJson.msg);
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            },
            'usermanage button[action=add]': {
                click: function(button) {
                    Ext.create('IDAT.view.manage.user.Add').show();
                }
            },
            // USER MANAGE END ------------------------------------------------------------------

            // IDENTITY MANAGE START -------------------------------------------------------------
            'identitygrid': {
                beforeactivate: function () {
                    identityStore.proxy.extraParams = {identity: 0};
                    identityStore.reload();
                },
                cellclick: function(view, td, cellIndex, record) {
                    if (cellIndex == this.getIdentityGrid().columns.length - 2) {
                        Ext.Msg.show({
                            title: LANG.TITLE.delete,
                            msg: LANG.MESSAGE.delete,
                            buttons: Ext.Msg.YESNO,
                            icon: Ext.Msg.QUESTION,
                            fn:function(btn) {
                                if (btn == 'yes') {
                                    Ext.Ajax.request({
                                        url: 'action/identity_delete',
                                        method: 'Get',
                                        params: {
                                            id: record.get('id')
                                        },
                                        success : function(response) {
                                            var responseJson = Ext.JSON.decode(response.responseText);
                                            Ext.Msg.alert(LANG.TITLE.success, responseJson.msg);
                                            identityStore.reload();

                                            Ext.getStore('IdentityMenus').proxy.extraParams={identity : I_IDENTITY};
                                            Ext.getStore('IdentityMenus').reload();
                                        },
                                        failure : function(response) {
                                            var responseJson = Ext.JSON.decode(response.responseText);
                                            Ext.Msg.alert(LANG.TITLE.failure, responseJson.msg);
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
			},
            'identitymanage button[action=add]': {
                click: function(button) {
                    Ext.create('IDAT.view.manage.identity.Add').show();
                }
            }
            // IDENTITY MANAGE END -------------------------------------------------------------
		});
	}
});
