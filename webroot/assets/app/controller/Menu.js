var menuStore = Ext.create('IDAT.store.Menus');
var comboMenuStore = Ext.create('IDAT.store.ComboMenus');

Ext.define('IDAT.controller.Menu', {
	extend: 'Ext.app.Controller',
    
	stores : [ ],

	models : [ ],
	
	views: ['List@IDAT.view.menu'],
      
    refs: [  
         {ref: 'menuManage', selector: 'menumanage'}
    ],
    
    init: function() {
        this.control({
        	'menumanage' : {
        		beforeactivate : function() {
        			menuStore.reload();
        			comboMenuStore.reload();
        		}
        	},
            'menumanage button[action=add]': {
                click: function(button) {
                    Ext.create('IDAT.view.menu.Add').show();
                }
            },
            'menumanage button[action=save]': {
                click: function(button) {
                	var panelStore = this.getMenuManage().getStore();
                	var records = panelStore.getModifiedRecords();
                	var params = new Array();
                	for (var i = 0; i < records.length; i++) {
                		params[i] = records[i].data;
                	}
                	
                	Ext.Ajax.request({
						url: 'menu_save.action',
						params: {
							records: Ext.JSON.encode(params)
						},
						success : function(response) {
							Ext.Msg.alert(LANG.TITLE.success, response.msg);
							Ext.getStore('TreeMenus').reload();
							panelStore.commitChanges();
						},
						failure : function(response) {
							Ext.Msg.alert(LANG.TITLE.failure, response.msg);
						}
					});
                }
            },
            'menumanage button[action=del]': {
                click: function(button) {
                	var gridStore = this.getMenuManage().getStore();
                   	var sm = this.getMenuManage().getSelectionModel();
		            rowEditing.cancelEdit();
		            gridStore.remove(sm.getSelection());
		            if (gridStore.getCount() > 0) {
		                sm.select(0);
		            }
                }
            },
            'menumanage button[action=refresh]': {
                click: function(button) {
                	var gridStore = this.getMenuManage().getStore();
                	gridStore.reload();
                }
            }
        });
    }
});