Ext.define('IDAT.view.user.Edit', {
    extend: 'Ext.window.Window',
    alias: 'widget.useredit',

    title: LANG.TITLE.user_edit,
    iconCls: 'icon-form-edit',
    layout: 'fit',
    modal : true,
    plain : true,

    initComponent: function () {
        this.items = [{
            xtype: 'form',
            frame: true,
            bodyPadding: 10,
            fieldDefaults: {
                labelAlign: 'right',
                labelWidth: 70
            },

            items: [{
                xtype: 'textfield',
                name: 'name',
                fieldLabel: 'Name'
            }, {
                xtype: 'textfield',
                name: 'email',
                fieldLabel: 'Email'
            }]
        }];

        this.buttonAlign = 'center',
        this.buttons = [{
            text: LANG.BTN.confirm,
            scope: this,
            iconCls: 'icon-accept',
            handler: this.onConfirm
        }, {
            text: LANG.BTN.cancel,
            scope: this,
            iconCls: 'icon-cancel',
            handler: function() {
                this.close();
            }
        }];
        this.callParent(arguments);
    },

    onConfirm: function () {
        var form = this.down('form').getForm();
        if (form.isValid()) {
            form.submit({
                clientValidation: true,
                url: 'user_edit.action',
                success: function (form, action) {
                    Ext.Msg.alert(LANG.TITLE.success, action.result.msg);
                    userStore.reload();
                },
                failure: function (form, action) {
                    Ext.Msg.alert(LANG.TITLE.failure, action.result.msg);
                }
            });
            this.close();
        }
    }
});
