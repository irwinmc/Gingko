Ext.define('IDAT.view.setting.identity.List' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.identitygrid',

    store: 'IdentityMenus',
    columns: [
        {header: LANG.LABEL.id,  dataIndex: 'id', hidden: true},
        {header: LANG.LABEL.identity, dataIndex: 'identity', flex: 1, renderer: RenderUtil.identity},
        {header: LANG.LABEL.menu_id, dataIndex: 'menuId', flex: 1},
        {header: LANG.LABEL.delete, dataIndex: 'delete', align: 'center', width: 80, renderer: RenderUtil.btnDel}
    ],

    dockedItems: [{
        xtype: 'pagingtoolbar',
        store: 'IdentityMenus',
        dock: 'bottom',
        displayInfo: true
    }]
});
