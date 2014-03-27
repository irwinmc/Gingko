var IDX_FORM_TYPE = "ALL";
var IDX_STATE = "-1";
var IDX_DATE = "";

Ext.define('IDAT.view.idx.Interface' ,{
    extend: 'Ext.panel.Panel',
    alias : 'widget.idxmanage',

    items : [{
        layout : 'hbox',
        items: [{
            xtype: 'panel',
            layout : 'fit',
            title: LANG.TITLE.idx_grid1,
            flex: 6,
            items: [{
                height: 575,
                xtype: 'idxgrid1'
            }]
        },{
            xtype: 'panel',
            title: LANG.TITLE.idx_grid2,
            flex: 4,
            items: [{
                height: 575,
                xtype: 'idxgrid2'
            }]
        }]
    }],

    tbar: [{
        xtype: 'combo',
        name: 'formType',
        multiSelect: true,
        fieldLabel: LANG.LABEL.formType,
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
                    IDX_FORM_TYPE = temp.substring(0, temp.length - 1);
                }

                idxStore.proxy.extraParams = {formType: IDX_FORM_TYPE, state: IDX_STATE, date: IDX_DATE};
                idxStore.reload();
                idxReportStore.removeAll();
            }
        }
    }, {
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
                IDX_STATE =  records[0].get('value');

                idxStore.proxy.extraParams = {formType: IDX_FORM_TYPE, state: IDX_STATE, date: IDX_DATE};
                idxStore.reload();
                idxReportStore.removeAll();
            }
        }
    }, {
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
                IDX_DATE = Ext.Date.format(new Date(value),'Y-m-d');

                idxStore.proxy.extraParams = {formType: IDX_FORM_TYPE, state: IDX_STATE, date: IDX_DATE};
                idxStore.reload();
                idxReportStore.removeAll();
            }
        }
    }]
});
