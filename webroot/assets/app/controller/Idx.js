var idxStore = Ext.create('IDAT.store.Idxs');
var idxReportStore = Ext.create('IDAT.store.IdxReports');
var REPORT = {
    RECORD_STATE: 0
}
Ext.define('IDAT.controller.Idx', {
	extend : 'Ext.app.Controller',
    models : [  ],
	stores : [ 'Idxs@IDAT.store', 'IdxReports@IDAT.store', 'ComboFormTypes@IDAT.store', 'ComboState@IDAT.store'],
	views : [ 'Grid1@IDAT.view.idx', 'Grid2@IDAT.view.idx', 'Interface@IDAT.view.idx' ],
	refs : [{
		ref : 'idxManage', selector : 'idxmanage',
        ref : 'idxGrid1', selector : 'idxgrid1',
        ref : 'idxGrid2', selector : 'idxgrid2'
	}],

	init : function() {
		this.control({
            'idxmanage': {
                beforeactivate: function () {
                    var date = Ext.Date.format(new Date(),'Y-m-d');
                    idxStore.proxy.extraParams = {date: date};
                    idxStore.reload();
                }
			},

            'idxgrid1': {
                cellclick: function(view, td, cellIndex, record) {
                    var siid = record.get('siid');
                    idxReportStore.proxy.extraParams={siid: siid};
                    idxReportStore.reload();
                    // Set state
                    REPORT.RECORD_STATE = record.get('state');
                }
            },

            'idxgrid2': {
                cellclick: function(view, td, cellIndex, record) {
                    if (cellIndex == this.getIdxGrid2().columns.length - 1) {
                        Ext.Ajax.request({
                            url: 'action/report_operate',
                            method: 'Get',
                            params: {
                                state: REPORT.RECORD_STATE,
                                siid: record.get('siid')
                            },
                            success : function(response) {
                                idxStore.reload();
                                REPORT.RECORD_STATE = REPORT.RECORD_STATE == 2 ? 1 : REPORT.RECORD_STATE + 1;
                                idxReportStore.proxy.extraParams={siid: record.get('siid')};
                                idxReportStore.reload();

                                if (REPORT.RECORD_STATE == 1) {
                                    window.open(record.get('anchor'));
                                }
                            },
                            failure : function(response) {
                                idxStore.reload();
                                var responseJson = Ext.JSON.decode(response.responseText);
                                Ext.Msg.alert(LANG.TITLE.failure, responseJson.msg);
                            }
                        });
                    }
                }
            }
		});
	}
});
