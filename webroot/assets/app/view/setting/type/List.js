var sm = Ext.create('Ext.selection.CheckboxModel');

Ext.define('IDAT.view.setting.type.List' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.formtypesetting',

    store: formTypeStore,
    selModel: sm ,
    columnLines: true,
    columns: [
        {header: LANG.LABEL.formType, dataIndex: 'formType', flex: 1},
        {header: LANG.LABEL.used, dataIndex: 'used', flex: 1, renderer: RenderUtil.used},
        {header: LANG.LABEL.delete, dataIndex: 'delete', align: 'center', width: 80, renderer: RenderUtil.btnDel}
    ],

    tbar: [{
        xtype: 'button',
        iconCls: 'icon-add',
        action: 'add',
        text: LANG.BTN.add
    }, {
        xtype: 'button',
        iconCls: 'icon-accept',
        action: 'use',
        text: LANG.BTN.use
    }, {
        xtype: 'button',
        iconCls: 'icon-cross',
        action: 'unuse',
        text: LANG.BTN.unuse
    }]
});
