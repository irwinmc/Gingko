Ext.define('IDAT.view.setting.identity.Add', {
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
                name: 'menuId',
                fieldLabel: LANG.LABEL.menu,
                store: 'ComboMenus',
                editable: false,
                valueField: 'value',
                displayField: 'display',
                allowBlank : false,
                emptyText: LANG.EMPTY_TIPS.empty_combo_menu
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
                            url : ACTION.SET_IDENTITY_MENU_ADD,
                            method : 'GET',
                            success : function(form, action) {
                                Ext.Msg.alert(LANG.TITLE.success, action.result.msg);

                                var identity = form.findField('identity').getValue();
                                identityMenuStore.proxy.extraParams = {identity: identity};
                                identityMenuStore.reload();
                                Ext.getStore('IdentityMenus').proxy.extraParams={identity : identity};
                                Ext.getStore('IdentityMenus').reload();
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