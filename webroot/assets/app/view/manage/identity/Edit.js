Ext.define('IDAT.view.manage.identity.Edit', {
    extend: 'Ext.window.Window',
    alias: 'widget.identityedit',

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
                name : 'id',
                value: this.record.get('id')
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
            }, {
                xtype: 'combobox',
                name: 'menuId',
                fieldLabel: LANG.LABEL.menu,
                store: 'ComboMenus',
                editable: false,
                valueField: 'value',
                displayField: 'display',
                allowBlank : false,
                emptyText: LANG.EMPTY_TIPS.empty_combo_menu,
                value: this.record.get('menuId')
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
                            url: 'action/identity_edit',
                            method: 'Get',
                            success: function (form, action) {
                                Ext.Msg.alert(LANG.TITLE.success, action.result.msg);
                                identityStore.reload();
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
