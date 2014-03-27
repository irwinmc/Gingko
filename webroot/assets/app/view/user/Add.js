Ext.define('IDAT.view.user.Add', {
	extend : 'Ext.window.Window',
	title: LANG.TITLE.user_add,
	iconCls: 'icon-form-add',
	layout: 'fit',
	modal : true,
	plain : true,
	initComponent : function() {
		this.items = [{
			xtype : 'form',
			frame : true,
			bodyPadding : 10,
			fieldDefaults : {
				labelAlign: 'right',
				labelWidth : 60,
				anchor : '100%'
			},
			
			items : [{
				xtype : 'textfield',
				fieldLabel : LANG.LABEL.account,
				name : 'account',
				allowBlank : false
			}, {
				xtype : 'textfield',
				fieldLabel : LANG.LABEL.password,
				name : 'password',
				allowBlank : false
			}, {
                xtype : 'textfield',
                fieldLabel : LANG.LABEL.name,
                name : 'name',
                allowBlank : false
            }, {
                xtype: 'combobox',
                name: 'identity',
                fieldLabel: LANG.LABEL.identity,
                store: 'ComboIdentity',
                editable: false,
                valueField: 'value',
                displayField: 'display',
                emptyText: LANG.EMPTY_TIPS.empty_combo_identity
            }],

            buttonAlign: 'center',
            buttons: [{
                text: LANG.BTN.confirm,
                scope: this,
                iconCls: 'icon-accept',
                handler: this.onConfirm
            }, {
                text: LANG.BTN.reset,
                scope: this,
                iconCls: 'icon-undo',
                handler: function() {
                    console.log(this);
                    //var form = this.down('form').getForm();
                    this.reset();
                }
            }]
		}];

		this.callParent(arguments);
	},

	onConfirm : function() {
		var form = this.down('form');
        console.log(form);
		if (form.isValid()) {
			form.submit({
                clientValidation : true,
				url : 'action/user_add',
                method : 'Get',
				success : function(form, action) {
					Ext.Msg.alert(LANG.TITLE.success, action.result.msg);
					userStore.reload();
				},
				failure : function(form, action) {
					Ext.Msg.alert(LANG.TITLE.failure, action.result.msg);
				}
			});
			this.close();
		}
	}
});