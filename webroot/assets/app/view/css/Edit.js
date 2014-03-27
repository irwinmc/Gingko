Ext.define('IDAT.view.css.Edit', {
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
				labelWidth : 80,
				anchor : '100%'
			},
		
			defaultType: 'textfield',	
			
			items: [{ 
				name: 'cssId',
				xtype: 'hiddenfield',
				value: this.record.get('cssId')
			}, {
				xtype: 'combobox',
				name: 'type',
				fieldLabel: LANG.LABEL.type,
				store: 'ComboCssType',
				editable: false,
				valueField: 'value',
				displayField: 'display',
				value: this.record.get('type')
			}, { 
				name: 'css',
			    fieldLabel: LANG.LABEL.css,
			    value: this.record.get('css')
			}, { 
				name: 'text',
			    fieldLabel: LANG.LABEL.desc,
			    value: this.record.get('text')
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
				url : 'css_edit.action',
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