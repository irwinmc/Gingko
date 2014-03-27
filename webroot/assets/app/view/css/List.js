Ext.define('IDAT.view.css.List' ,{
    extend: 'Ext.grid.Panel',
    alias: 'widget.cssmanage',

    title : LANG.TITLE.css_manage,
    store: cssStore,
   
    columns: [
    	{header: 'ID',  dataIndex: 'cssId'},
    	{header: 'css',  dataIndex: 'css',  flex: 1},
    	{header: '类型',  dataIndex: 'type',  flex: 1, renderer: RenderUtil.cssType},
    	{header: '描述',  dataIndex: 'text', flex: 1},
    	{header: LANG.HEADER.edit, dataIndex: 'edit', align: 'center', width: 50, renderer: RenderUtil.btnEdit}, 
    	{header: LANG.HEADER.del, dataIndex: 'delete', align: 'center', width: 50, renderer: RenderUtil.btnDel} 
    ],
    
    tbar: [{
		xtype: 'button',
		iconCls: 'icon-add',
		action: 'add',
		text: LANG.BTN.add
	}, '->', {
		xtype: 'label',
		text: LANG.LABEL.keyword
	}, {
		xtype: 'textfield',
		id: 'csskeyword'
	}, {
		xtype: 'button',
		text: LANG.BTN.search,
		iconCls: 'icon-search',
		handler: function() {
			var keyword = Ext.getCmp('csskeyword').value;
			if (keyword) {
				cssStore.proxy.extraParams={keyword:keyword};
	    		cssStore.reload({keyword:keyword});
			}
		}
	}, {
		xtype: 'button',
		text: LANG.BTN.reset,
		iconCls: 'icon-undo',
		handler: function() {
			Ext.getCmp('csskeyword').setValue("");
			cssStore.proxy.extraParams={};
			cssStore.reload();
		}
	}],
	
	dockedItems: [{
        xtype: 'pagingtoolbar',
        store: cssStore,
        dock: 'bottom',
        displayInfo: true
    }],
    
    listeners: {
        'cellclick': function(view, td, cellIndex, record, tr, rowIndex) {
        	// Click edit
        	if (cellIndex == this.columns.length - 2) {
                var wndEdit = Ext.create('IDAT.view.css.Edit', {record: record});
        		wndEdit.show();
        	} 
        	// Click del
        	else if (cellIndex == this.columns.length - 1) {
        		Ext.Msg.show({
				    title: LANG.TITLE.css_del,
				    buttons: Ext.Msg.YESNO,
				    icon: Ext.Msg.QUESTION,
				    fn:function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								url: 'css_delete.action',
								params: {
									css: record.get('css')
								},
								success : function(response) {
									var responseJson = Ext.JSON.decode(response.responseText);
									Ext.Msg.alert(LANG.TITLE.success, responseJson.msg);
									cssStore.reload();
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