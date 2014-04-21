Ext.define('IDAT.view.setting.group.List' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.settinggroup',

    store: 'Groups',
    columnLines: true,
    columns: [
        {header: LANG.LABEL.group_id, dataIndex: 'groupId', flex: 1},
        {header: LANG.LABEL.group_name, dataIndex: 'name', flex: 1},
        {header: LANG.LABEL.group_host, dataIndex: 'host', flex: 1},
        {header: LANG.LABEL.delete, dataIndex: 'delete', align: 'center', width: 80, renderer: RenderUtil.btnDel}
    ],

    tbar: [{
        xtype: 'button',
        iconCls: 'icon-add',
        action: 'add',
        text: LANG.BTN.add
    }]
});
