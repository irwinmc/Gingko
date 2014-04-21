Ext.define('IDAT.view.manage.user.Add', {
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
                allowBlank : false,
                emptyText: LANG.EMPTY_TIPS.empty_combo_identity
            }, {
                xtype: 'combobox',
                name: 'groupId',
                fieldLabel: LANG.LABEL.group_id,
                store: 'ComboGroups',
                editable: false,
                valueField: 'value',
                displayField: 'display',
                allowBlank : false,
                emptyText: LANG.EMPTY_TIPS.empty_combo_group
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
                            url : ACTION.USER_ADD,
                            method : 'GET',
                            success : function(form, action) {
                                Ext.Msg.alert(LANG.TITLE.success, action.result.msg);
                                Ext.getStore('Users').reload();
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