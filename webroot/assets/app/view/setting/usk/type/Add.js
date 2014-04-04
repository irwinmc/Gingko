Ext.define('IDAT.view.setting.usk.type.Add', {
    extend: 'Ext.window.Window',
    title: LANG.TITLE.add,
    iconCls: 'icon-form-add',
    layout: 'fit',
    modal: true,
    plain: true,
	initComponent : function() {
        var me = this;
		this.items = [{
            xtype: 'form',
            frame: false,
            bodyPadding: 10,
            fieldDefaults: {
                labelAlign: 'right',
                labelWidth: 60,
                anchor: '100%'
            },

			items : [{
                xtype: 'combobox',
                name: 'groupId',
                fieldLabel: LANG.LABEL.group_name,
                labelAlign: 'right',
                labelWidth : 60,
                store: 'ComboGroups',
                editable: false,
                valueField: 'value',
                displayField: 'display',
                emptyText: LANG.EMPTY_TIPS.empty_combo_group
            }, {
                xtype: 'textfield',
                name: 'formType',
                fieldLabel: LANG.LABEL.form_type,
                allowBlank : false
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
                            url: ACTION.SET_FORM_TYPE_ADD,
                            method: 'GET',
                            success: function (form, action) {
                                Ext.Msg.alert(LANG.TITLE.success, action.result.msg);

                                var groupId = form.findField('groupId').getValue()
                                formTypeStore.proxy.extraParams = {groupId: groupId};
                                formTypeStore.reload();
                                me.close();
                            },
                            failure: function (form, action) {
                                Ext.Msg.alert(LANG.TITLE.failure, action.result.msg);
                            }
                        });
                    }
                }
            }, {
                text: LANG.BTN.reset,
                scope: this,
                iconCls: 'icon-undo',
                handler: function() {
                    var form = this.down('form').getForm();
                    form.reset();
                }
            }]
		}];

		this.callParent(arguments);
	}
});