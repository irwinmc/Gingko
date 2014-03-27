Ext.define('IDAT.view.css.Add', {
	extend : 'Ext.window.Window',
	title: LANG.TITLE.css_add,
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
				xtype: 'combobox',
				name: 'type',
				fieldLabel: LANG.LABEL.type,
				store: 'ComboCssType',
				editable: false,
				valueField: 'value',
				displayField: 'display',
				emptyText: LANG.EMPTY_TIPS.empty_combo_type
			}, {
				xtype : 'textfield',
				fieldLabel : LANG.LABEL.css,
				name : 'css',
				allowBlank : false
			}, {
				xtype : 'textfield',
				fieldLabel : LANG.LABEL.desc,
				name : 'text',
				allowBlank : true
			}]
		}];
		
		this.buttonAlign = 'center',
		this.buttons = [{
			text: LANG.BTN.confirm,
			scope: this,
			iconCls: 'icon-accept',
			handler: this.onConfirm
		}, {
			text: LANG.BTN.reset,
			scope: this,
			iconCls: 'icon-undo',
			handler: function() {
				var form = this.down('form').getForm();
				form.reset();
			}
		}];
		
		this.callParent(arguments);
	},

	onConfirm : function() {
		var form = this.down('form').getForm();
		if (form.isValid()) {
			form.submit({
				clientValidation : true,
				url : 'css_add.action',
				success : function(form, action) {
					Ext.Msg.alert(LANG.TITLE.success, action.result.msg);
					cssStore.reload();
				},
				failure : function(form, action) {
					Ext.Msg.alert(LANG.TITLE.failure, action.result.msg);
				}
			});	
			this.close();
		}
	}
});