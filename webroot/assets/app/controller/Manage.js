var userStore = Ext.create('IDAT.store.Users');

Ext.define('IDAT.controller.Manage', {
    extend: 'Ext.app.Controller',
    models: [  ],
    stores: [ 'Users@IDAT.store'],
    views: [ 'List@IDAT.view.manage.user'],
    refs: [
        { ref: 'userManage', selector: 'usermanage'}
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
                                        url: ACTION.USER_DELETE,
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
            }
            // USER MANAGE END ------------------------------------------------------------------
        });
	}
});
