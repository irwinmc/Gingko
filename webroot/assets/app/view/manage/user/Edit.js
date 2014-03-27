Ext.define('IDAT.view.manage.user.Edit', {
    extend: 'Ext.window.Window',
    alias: 'widget.useredit',

    title: LANG.TITLE.edit,
    iconCls: 'icon-form-edit',
    layout: 'fit',
    modal : true,
    plain : true,
    initComponent: function () {
        var me = this;
        this.items = [{
            xtype: 'form',
            frame: false,
            bodyPadding: 10,
            fieldDefaults: {
                labelAlign: 'right',
                labelWidth: 70
            },

            items: [{
                xtype : 'hiddenfield',
                name : 'account',
                value: this.record.get('account')
            }, {
                xtype : 'textfield',
                fieldLabel : LANG.LABEL.name,
                name : 'name',
                allowBlank : false,
                value: this.record.get('name')
            }, {
                xtype : 'textfield',
                fieldLabel : LANG.LABEL.password,
                name : 'password',
                allowBlank : false,
                value: this.record.get('password')
            }, {
                xtype: 'combobox',
                name: 'identity',
                fieldLabel: LANG.LABEL.identity,
                store: 'ComboIdentity',
                editable: false,
                valueField: 'value',
                displayField: 'display',
                emptyText: LANG.EMPTY_TIPS.empty_combo_identity,
                value: this.record.get('identity')
            }],

            buttonAlign: 'center',
            buttons: [{
                text: LANG.BTN.confirm,
                scope: this,
                iconCls: 'icon-accept',
                handler: function() {
                    var form = this.down('form').getForm();
                    if (form.isValid()) {
                        form.submit({
                            clientValidation: true,
                            url: 'action/user_edit',
                            method: 'Get',
                            success: function (form, action) {
                                Ext.Msg.alert(LANG.TITLE.success, action.result.msg);
                                userStore.reload();
                                me.close();
                            },
                            failure: function (form, action) {
                                Ext.Msg.alert(LANG.TITLE.failure, action.result.msg);
                            }
                        });
                    }
                }
            }, {
                text: LANG.BTN.cancel,
                scope: this,
                iconCls: 'icon-cancel',
                handler: function() {
                    me.close();
                }
            }]
        }];

        this.callParent(arguments);
    }
});
