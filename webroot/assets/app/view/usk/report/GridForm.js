Ext.define('IDAT.view.usk.report.GridForm' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.gridform',

    store: 'ReportForms',
    columns: [
        {header: LANG.LABEL.id,  dataIndex: 'id', hidden: true},
        {header: LANG.LABEL.siid,  dataIndex: 'siid', hidden: true},
        {header: LANG.LABEL.form_type,  dataIndex: 'formType', hidden: true},
        {header: LANG.LABEL.report_type, dataIndex: 'reportType', flex: 1},
        {header: LANG.LABEL.form_name, dataIndex: 'name', flex: 1},
        {header: LANG.LABEL.state, dataIndex: 'state', flex: 1, align: 'center', width: 100, renderer: RenderUtil.state},
        {header: LANG.LABEL.anchor, dataIndex: 'anchor', flex: 1, renderer: RenderUtil.btnAnchor},
        {header: LANG.LABEL.local_file, dataIndex: 'open', align: 'center', width: 80, renderer: RenderUtil.btnOpen}
    ],

    dockedItems: [{
        xtype: 'pagingtoolbar',
        store: 'ReportForms',
        dock: 'bottom',
        displayInfo: true
    }],

    tbar: [{
        xtype: 'combo',
        name: 'state',
        fieldLabel: LANG.LABEL.state,
        labelAlign: 'right',
        labelWidth : 60,
        store: 'ComboState',
        editable: true,
        valueField: 'value',
        displayField: 'display',
        emptyText: LANG.EMPTY_TIPS.empty_combo_state,
        listeners: {
            scope: this,
            'select': function (combo, records) {
                Ext.getStore('ReportForms').clearFilter("state");
                var state =  records[0].get('value');
                if (state != -1) {
                    Ext.getStore('ReportForms').filter([{property: "state", value: state}]);
                }
            }
        }
    }, {
        xtype: 'combo',
        name: 'reportType',
        fieldLabel: LANG.LABEL.report_type,
        labelAlign: 'right',
        labelWidth : 60,
        store: 'ComboReportTypes',
        editable: true,
        valueField: 'value',
        displayField: 'display',
        emptyText: LANG.EMPTY_TIPS.empty_combo,
        listeners: {
            scope: this,
            'select': function (combo, records) {
                Ext.getStore('ReportForms').clearFilter("reportType");
                var reportType =  records[0].get('value');
                if (reportType != "ALL") {
                    Ext.getStore('ReportForms').filter([{property: "reportType", value: reportType}]);
                }
            }
        }
    }]
});
