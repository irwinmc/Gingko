var formTypeStore = Ext.create('IDAT.store.FormTypes');

Ext.define('IDAT.controller.Setting', {
    extend: 'Ext.app.Controller',
    models: [ ],
    stores: [ 'FormTypes@IDAT.store'],
    views: [ 'List@IDAT.view.setting.type' ],
    refs: [
        {ref: 'formTypeSetting', selector: 'formtypesetting'}
    ],

    init : function() {
		this.control({
            // FORM TYPE SETTING START -------------------------------------------------------------
            'formtypesetting': {
                beforeactivate: function () {
                    formTypeStore.reload();
                },
                cellclick: function(view, td, cellIndex, record) {
                    if (cellIndex == this.getFormTypeSetting().columns.length - 0) {
                        Ext.Msg.show({
                            title: LANG.TITLE.delete,
                            msg: LANG.MESSAGE.delete,
                            buttons: Ext.Msg.YESNO,
                            icon: Ext.Msg.QUESTION,
                            fn:function(btn) {
                                if (btn == 'yes') {
                                    Ext.Ajax.request({
                                        url: 'action/setting_deleteFormType',
                                        method: 'Get',
                                        params: {
                                            formType: record.get('formType')
                                        },
                                        success : function(response) {
                                            var responseJson = Ext.JSON.decode(response.responseText);
                                            Ext.Msg.alert(LANG.TITLE.success, responseJson.msg);
                                            formTypeStore.reload();
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
            'formtypesetting button[action=add]': {
                click: function() {
                    Ext.create('IDAT.view.setting.type.Add').show();
                }
            },
            'formtypesetting button[action=use]': {
                click: function() {
                    var records = this.getFormTypeSetting().getSelectionModel().getSelection();
                    if (records.length == 0) {
                        return;
                    }
                    var temp = "";
                    for (var i =0; i <records.length; i++) {
                        temp +=  records[i].get('formType') + ",";
                    }
                    var formType = temp.substring(0, temp.length - 1);

                    Ext.Ajax.request({
                        url: 'action/setting_useFormType',
                        method: 'Get',
                        params: {
                            formType: formType,
                            used: 1
                        },
                        success : function(response) {
                            var responseJson = Ext.JSON.decode(response.responseText);
                            Ext.Msg.alert(LANG.TITLE.success, responseJson.msg);
                            formTypeStore.reload();
                        },
                        failure : function(response) {
                            var responseJson = Ext.JSON.decode(response.responseText);
                            Ext.Msg.alert(LANG.TITLE.failure, responseJson.msg);
                        }
                    });
                }
            },
            'formtypesetting button[action=unuse]': {
                click: function(button) {
                    var records = this.getFormTypeSetting().getSelectionModel().getSelection();
                    if (records.length == 0) {
                        return;
                    }

                    var temp = "";
                    for (var i =0; i <records.length; i++) {
                        temp +=  records[i].get('formType') + ",";
                    }
                    var formType = temp.substring(0, temp.length - 1);

                    Ext.Ajax.request({
                        url: 'action/setting_useFormType',
                        method: 'Get',
                        params: {
                            formType: formType,
                            used: 0
                        },
                        success : function(response) {
                            var responseJson = Ext.JSON.decode(response.responseText);
                            Ext.Msg.alert(LANG.TITLE.success, responseJson.msg);
                            formTypeStore.reload();
                        },
                        failure : function(response) {
                            var responseJson = Ext.JSON.decode(response.responseText);
                            Ext.Msg.alert(LANG.TITLE.failure, responseJson.msg);
                        }
                    });
                }
            }
            // FORM TYPE SETTING END -------------------------------------------------------------

		});
	}
});
