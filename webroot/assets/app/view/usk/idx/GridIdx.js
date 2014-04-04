var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
    clicksToEdit: 2
});

Ext.define('IDAT.view.usk.idx.GridIdx' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.grididx',

    store: idxStore,
    columnLines: true,
    plugins: [cellEditing],
    columns: [
        {header: LANG.LABEL.cik, dataIndex: 'cik', editor: { xtype: 'textfield', allowBlank: false }},
        {header: LANG.LABEL.stock_code, dataIndex: 'code', editor: { xtype: 'textfield', allowBlank: false }},
        {header: LANG.LABEL.company_name, dataIndex: 'companyName', flex: 1, editor: { xtype: 'textfield', allowBlank: false }},
        {header: LANG.LABEL.form_type, dataIndex: 'formType', editor: { xtype: 'textfield', allowBlank: false }},
        {header: LANG.LABEL.date, dataIndex: 'date', renderer: RenderUtil.reportDate, editor: { xtype: 'textfield', allowBlank: false }}
    ],

    dockedItems: [{
        xtype: 'pagingtoolbar',
        store: idxStore,
        dock: 'bottom',
        displayInfo: true
    }],

    tbar: [{
        xtype: 'datefield',
        fieldLabel: LANG.LABEL.date,
        labelAlign: 'right',
        labelWidth : 60,
        name: 'date',
        altFormats: 'Y-m-d',
        format: 'Y-m-d',
        value: new Date(),
        listeners: {
            scope: this,
            'select': function (field, value) {
                REPORT.IDX.DATE = Ext.Date.format(new Date(value),'Y-m-d');
                idxPanelReload();
            }
        }
    }, {
        xtype: 'combo',
        name: 'formType',
        multiSelect: true,
        fieldLabel: LANG.LABEL.form_type,
        labelAlign: 'right',
        labelWidth : 60,
        store: 'ComboFormTypes',
        editable: true,
        valueField: 'value',
        displayField: 'display',
        emptyText: LANG.EMPTY_TIPS.empty_combo_type,
        listeners: {
            scope: this,
            'select': function (combo, records) {
                if (records.length > 0) {
                    var temp = "";
                    for (var i =0; i <records.length; i++) {
                        temp +=  records[i].get('value') + ",";
                    }
                    REPORT.IDX.FORM_TYPE = temp.substring(0, temp.length - 1);
                }
                idxPanelReload();
            }
        }
    }]
});

idxPanelReload = function() {
    idxStore.proxy.extraParams = {formType: REPORT.IDX.FORM_TYPE, date: REPORT.IDX.DATE};
    idxStore.reload();
    idxReportStore.removeAll();
}
