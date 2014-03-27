Ext.define('IDAT.view.manage.user.List' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.usermanage',

    store: userStore,

    columns: [
        {header: LANG.LABEL.id,  dataIndex: 'userId', hidden: true},
        {header: LANG.LABEL.account, dataIndex: 'account',  flex: 1},
        {header: LANG.LABEL.name, dataIndex: 'name', flex: 1},
        {header: LANG.LABEL.password, dataIndex: 'password', flex: 1},
        {header: LANG.LABEL.identity, dataIndex: 'identity', flex: 1, renderer: RenderUtil.identity},
        {header: LANG.LABEL.edit, dataIndex: 'edit', align: 'center', width: 60, renderer: RenderUtil.btnEdit},
        {header: LANG.LABEL.delete, dataIndex: 'delete', align: 'center', width: 60, renderer: RenderUtil.btnDel}
    ],

    tbar: [{
        xtype: 'button',
        iconCls: 'icon-add',
        action: 'add',
        text: LANG.BTN.add
    }],

    dockedItems: [{
        xtype: 'pagingtoolbar',
        store: userStore,
        dock: 'bottom',
        displayInfo: true
    }]
});
