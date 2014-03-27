var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
    clicksToMoveEditor: 1,
    autoCancel: true,
    hideTooltipOnAdd: true
});

Ext.define('IDAT.view.menu.List' ,{
    extend: 'Ext.grid.Panel',
    alias: 'widget.menumanage',

    //store: 'Menus',
    store: menuStore,
    overflowY : 'auto',
    
    selType: 'rowmodel',
    frame: false,
   
    plugins: [rowEditing],
    viewConfig: {
        stripeRows: true
    },
    
    columns: [
    	{header: '菜单ID',  dataIndex: 'menuId',  editor: {allowBlank: false}},
    	{header: '菜单名称',  dataIndex: 'text', editor: {allowBlank: false}, flex: 1},
    	{header: '父菜单ID',  dataIndex: 'parentId', editor: {allowBlank: false}, flex: 1},
    	{header: 'iconCls',  dataIndex: 'iconCls', editor: {}, flex: 1},
    	{header: 'qtip',  dataIndex: 'qtip', editor: {}, flex: 1},
    	{
            header: LANG.HEADER.edit,
            dataIndex: 'edit',
            align: 'center',
            width: 50,
            renderer: RenderUtil.btnEdit
		}, {
	        header: LANG.HEADER.del,
	        dataIndex: 'delete',
	        align: 'center',
	        width: 50,
	        renderer: RenderUtil.btnDel
		}
    ],
    
    tbar: [{
		xtype: 'button',
		iconCls: 'icon-add',
		action: 'add',
		text: LANG.BTN.add
	}, '-', {
		itemId: 'saveMenu',
		xtype: 'button',
		iconCls: 'icon-save',
		action: 'save',
		text: LANG.BTN.save,
		disabled: true
	}, '-', {
		itemId: 'removeMenu',
		iconCls: 'icon-delete',
		action: 'del',
		text: LANG.BTN.del,
        disabled: true
	}, '-', {
		itemId: 'refresh',
		iconCls: 'icon-refresh',
		action: 'refresh',
		text: LANG.BTN.refresh
	}, '->', {
		xtype: 'label',
		text: LANG.LABEL.keyword
	}, {
		xtype: 'textfield',
		id: 'menukeyword'
	}, {
		xtype: 'button',
		text: LANG.BTN.search,
		iconCls: 'icon-search',
		handler: function() {
			var keywaord = Ext.getCmp('menukeyword').value;
			if (keywaord) {
				menuStore.proxy.extraParams={keyword:keywaord};
	    		menuStore.reload({keyword:keywaord});
			}
		}
	}, {
		xtype: 'button',
		text: LANG.BTN.reset,
		iconCls: 'icon-undo',
		handler: function() {
			Ext.getCmp('menukeyword').setValue("");
			menuStore.proxy.extraParams={};
			menuStore.reload();
		}
	}],
	
	dockedItems: [{
        xtype: 'pagingtoolbar',
        store: menuStore,
        dock: 'bottom',
        displayInfo: true
    }],
	
	listeners: {
        'selectionchange': function(view, records) {
            this.down('#removeMenu').setDisabled(!records.length);
        },
        
        'edit': function(view) {
            this.down('#saveMenu').setDisabled(!this.getStore().getModifiedRecords().length);
        },
        
        'cellclick': function(view, td, cellIndex, record, tr, rowIndex) {
        	// Click edit
        	if (cellIndex == this.columns.length - 2) {
                var wndEdit = Ext.create('IDAT.view.menu.Edit', {record: record});
        		wndEdit.show();
        	} 
        	// Click del
        	else if (cellIndex == this.columns.length - 1) {
        		Ext.Msg.show({
				    title: LANG.TITLE.menu_del,
				    msg: TIPS.menu_del,
				    buttons: Ext.Msg.YESNO,
				    icon: Ext.Msg.QUESTION,
				    fn:function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								url: 'menu_delete.action',
								params: {
									menuId: record.get('menuId')
								},
								success : function(response) {
									var responseJson = Ext.JSON.decode(response.responseText);
									Ext.Msg.alert(LANG.TITLE.success, responseJson.msg);
									Ext.getStore('TreeMenus').reload();
									menuStore.reload();
								},
								failure : function(response) {
									Ext.Msg.alert(LANG.TITLE.failure, responseJson.msg);
								}
							});
						}
				    }
				});
        	}
        }
    }
});