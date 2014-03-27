Ext.define('IDAT.view.menu.Add', {
	extend : 'Ext.window.Window',
	
	title: LANG.TITLE.menu_add,
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
				xtype : 'combobox',
				fieldLabel : LANG.LABEL.menu_parent,
				name : 'parentId',
				store : comboMenuStore,
				mode : 'remote',
				editable : false,
				triggerAction : 'all',
				valueField : 'value',
				displayField : 'display',
				emptyText : LANG.EMPTY_TIPS.empty_combo_menu,
				allowBlank : false
			}, {
				xtype : 'textfield',
				fieldLabel : LANG.LABEL.menu_id,
				name : 'menuId',
				allowBlank : false
			}, {
				xtype : 'textfield',
				fieldLabel : LANG.LABEL.menu_name,
				name : 'text',
				allowBlank : false
			}, {
				xtype: 'combobox',
				name: 'cssType',
				fieldLabel: LANG.LABEL.css_type,
				labelClsExtra: 'label-style',
				store: 'ComboCssType',
				editable: false,
				valueField: 'value',
				displayField: 'display',
				emptyText: LANG.EMPTY_TIPS.empty_combo_type,
				listeners:{
			    	scope: this,
			        'select': function(combo, records) {
			        	Ext.getStore('ComboCss').proxy.extraParams={type:records[0].get('value')};
			        	Ext.getStore('ComboCss').reload();
			        	Ext.getCmp('comboIconCls').setValue('');
			        }
			    }
			}, {
				xtype: 'combobox',
				name: 'iconCls',
				id: 'comboIconCls',
				fieldLabel: LANG.LABEL.menu_css,
				labelClsExtra: 'label-style',
				store: 'ComboCss',
				editable: false,
				valueField: 'value',
				displayField: 'display',
				emptyText: LANG.EMPTY_TIPS.empty_combo_css
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
				url : 'menu_add.action',
				success : function(form, action) {
					Ext.Msg.alert(LANG.TITLE.success, action.result.msg);
					Ext.getStore('TreeMenus').reload();
					// Ext.getStore('Menus').reload();
					menuStore.reload();
				},
				failure : function(form, action) {
					Ext.Msg.alert(LANG.TITLE.failure, action.result.msg);
				}
			});	
			this.close();
		}
	}
});