Ext.define('IDAT.view.setting.type.Add', {
	extend : 'Ext.window.Window',
	title: LANG.TITLE.add,
	iconCls: 'icon-form-add',
	layout: 'fit',
	modal : true,
	plain : true,
	initComponent : function() {
        var me = this;
		this.items = [{
			xtype : 'form',
			frame : false,
			bodyPadding : 10,
			fieldDefaults : {
				labelAlign: 'right',
				labelWidth : 60,
				anchor : '100%'
			},
			
			items : [{
                xtype: 'textfield',
                name: 'formType',
                fieldLabel: LANG.LABEL.formType,
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
                            clientValidation : true,
                            url : 'action/setting_addFormType',
                            method : 'Get',
                            success : function(form, action) {
                                Ext.Msg.alert(LANG.TITLE.success, action.result.msg);
                                formTypeStore.reload();
                                me.close();
                            },
                            failure : function(form, action) {
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