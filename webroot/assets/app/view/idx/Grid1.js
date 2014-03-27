var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
    clicksToEdit: 2
});

Ext.define('IDAT.view.idx.Grid1' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.idxgrid1',

    store: idxStore,
    columnLines: true,
    columns: [
        {header: LANG.LABEL.cik,  dataIndex: 'cik', editor: { xtype:'textfield', allowBlank:false }},
        {header: LANG.LABEL.stockCode,  dataIndex: 'code', editor: { xtype:'textfield', allowBlank:false }},
        {header: LANG.LABEL.companyName, dataIndex: 'companyName', flex: 1, editor: { xtype:'textfield', allowBlank:false }},
        {header: LANG.LABEL.formType, dataIndex: 'formType', editor: { xtype:'textfield', allowBlank:false }},
        {header: LANG.LABEL.date,  dataIndex: 'date', renderer: RenderUtil.reportDate, editor: { xtype:'textfield', allowBlank:false }},
        {header: LANG.LABEL.state, dataIndex: 'state', align: 'center', width: 100, renderer: RenderUtil.state}
    ],
    plugins: [cellEditing],
    dockedItems: [{
        xtype: 'pagingtoolbar',
        store: idxStore,
        dock: 'bottom',
        displayInfo: true
    }]
});
