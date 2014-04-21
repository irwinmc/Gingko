Ext.define('IDAT.view.setting.usk.type.List' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.settingformtype',

    store: 'FormTypes',
    columnLines: true,
    columns: [
        {header: LANG.LABEL.id, dataIndex: 'id', hidden: true},
        {header: LANG.LABEL.form_type, dataIndex: 'formType', flex: 1},
        {header: LANG.LABEL.group_id, dataIndex: 'groupId', flex: 1},
        {header: LANG.LABEL.delete, dataIndex: 'delete', align: 'center', width: 80, renderer: RenderUtil.btnDel}
    ],

    tbar: [{
        xtype: 'button',
        iconCls: 'icon-add',
        action: 'add',
        text: LANG.BTN.add
    }, {
        xtype: 'combobox',
        name: 'groupId',
        fieldLabel: LANG.LABEL.group_name,
        labelAlign: 'right',
        labelWidth : 60,
        store: 'ComboGroups',
        editable: false,
        valueField: 'value',
        displayField: 'display',
        emptyText: LANG.EMPTY_TIPS.empty_combo_group,
        listeners: {
            scope: this,
            'select': function (combo, records) {
                var groupId = records[0].get('value');
                Ext.getStore('FormTypes').proxy.extraParams = {groupId: groupId};
                Ext.getStore('FormTypes').reload();
            }
        }
    }]
});
