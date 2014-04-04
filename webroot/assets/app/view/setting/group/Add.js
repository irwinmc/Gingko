Ext.define('IDAT.view.setting.group.Add', {
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
                xtype: 'textfield',
                name: 'name',
                fieldLabel: LANG.LABEL.group_name,
                allowBlank : false
            }, {
                xtype: 'textfield',
                name: 'host',
                fieldLabel: LANG.LABEL.group_host,
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
                            url: ACTION.SET_GROUP_ADD,
                            method: 'GET',
                            success: function (form, action) {
                                Ext.Msg.alert(LANG.TITLE.success, action.result.msg);
                                groupStore.reload();
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