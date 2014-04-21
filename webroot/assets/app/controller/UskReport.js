Ext.define('IDAT.controller.UskReport', {
	extend: 'Ext.app.Controller',
	models: [],
	stores: [
		'ReportIdxs@IDAT.store',
        'ReportForms@IDAT.store',
        'ComboFormTypes@IDAT.store',
		'ComboState@IDAT.store',
        'ComboReportTypes@IDAT.store'
	],
	views: [
		'GridForm@IDAT.view.usk.report',
		'GridIdx@IDAT.view.usk.report',
		'Interface@IDAT.view.usk.report'
	],
	refs: [
		{ref: 'idxPanel', selector: 'idxpanel'},
		{ref: 'gridIdx', selector: 'grididx'},
		{ref: 'gridForm', selector: 'gridform'}
	],

	init: function () {
		this.control({
			'idxpanel': {
				beforeactivate: function () {
                    Ext.getStore('ReportIdxs').proxy.extraParams = {formType: REPORT.IDX.FORM_TYPE, date: REPORT.IDX.DATE};
                    Ext.getStore('ReportIdxs').reload();
				}
			},

			'grididx': {
				cellclick: function (view, td, cellIndex, record) {
					var siid = record.get('siid');
                    Ext.getStore('ReportForms').proxy.extraParams = {siid: siid};
                    Ext.getStore('ReportForms').reload();
				}
			},

			'gridform': {
				cellclick: function (view, td, cellIndex, record) {
					if (cellIndex == this.getGridForm().columns.length - 4) {
                        var state = record.get('state');
                        if (state == 0) {
                            Ext.Ajax.request({
                                url: ACTION.REPORT_STATE_CHANGE,
                                method: 'GET',
                                params: {
                                    id: record.get('id')
                                },
                                success: function (response) {
                                    Ext.getStore('ReportForms').proxy.extraParams = {siid: record.get('siid')};
                                    Ext.getStore('ReportForms').reload();
                                },
                                failure: function (response) {
                                    var responseJson = Ext.JSON.decode(response.responseText);
                                    Ext.Msg.alert(LANG.TITLE.failure, responseJson.msg);
                                }
                            });
                        }
                        // Open new form window
                        var path = "data/sec/html/form/" + record.get('date') + "/parser/" + record.get('localFile');
                        window.open(path, "newwindow", 'toolbar=no, menubar=no,scrollbars=no, resizable=no, location=no, status=no');
					}
				}
			}
		});
	}
});
