Ext.define('IDAT.view.menu.Edit', {
	extend : 'Ext.window.Window',
	
	title: LANG.TITLE.menu_edit,
	iconCls: 'icon-form-edit',
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
				labelWidth : 70
			},
		
			defaultType: 'textfield',
			items: [{ 
				xtype: 'hiddenfield',
				name: 'id',
			    value: this.record.get('id')
			}, { 
				name: 'menuId',
			    fieldLabel: LANG.LABEL.menu_id,
			    labelClsExtra: 'label-style',
			    readOnly: true,
			    value: this.record.get('menuId')
			}, {
				xtype: 'combobox',
				name: 'parentId',
				fieldLabel: LANG.LABEL.menu_parent,
				labelClsExtra: 'label-style',
				value: this.record.get('parentId'),
				store: comboMenuStore,
				editable: false,
				valueField: 'value',
				displayField: 'display',
				emptyText: LANG.EMPTY_TIPS.empty_combo_menu
			}, { 
				name: 'text',
				fieldLabel: LANG.LABEL.menu_name,
				labelClsExtra: 'label-style',
				value: this.record.get('text')
			}, {
				xtype: 'combobox',
				name: 'ComboCssType',
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
				value: this.record.get('iconCls'),
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
	        text: LANG.BTN.cancel,
	        scope: this,
	        iconCls: 'icon-cancel',
	        handler: function() {
	        	this.close();
	        }
	    }];
		
		this.callParent(arguments);
	},
	
	onConfirm : function() {
		var form = this.down('form').getForm();
		if (form.isValid()) {
			form.submit({
				clientValidation : true,
				url : 'menu_edit.action',
				success : function(form, action) {
					Ext.Msg.alert(LANG.TITLE.success, action.result.msg);
					Ext.getStore('TreeMenus').reload();
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