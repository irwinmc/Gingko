Ext.define('IDAT.view.idx.Grid2' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.idxgrid2',

    store: idxReportStore,
    columns: [
        {header: LANG.LABEL.description,  dataIndex: 'description'},
        {header: LANG.LABEL.type, dataIndex: 'type'},
        {header: LANG.LABEL.anchor, dataIndex: 'anchor', flex: 1, renderer: RenderUtil.btnAnchor},
        {header: LANG.LABEL.operate, dataIndex: 'operate', align: 'center', width: 80, renderer: RenderUtil.btnOperate}
    ],

    dockedItems: [{
        xtype: 'pagingtoolbar',
        store: idxReportStore,
        dock: 'bottom'
        //displayInfo: true
    }]
});
