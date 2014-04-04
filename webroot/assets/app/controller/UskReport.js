// Stores
var idxStore = Ext.create('IDAT.store.Idxs');
var idxFormStore = Ext.create('IDAT.store.IdxForms');
var idxReportStore = Ext.create('IDAT.store.IdxHtmls');
var reportTreeStore = Ext.create('IDAT.store.TreeUskReportType');

Ext.define('IDAT.controller.UskReport', {
    extend: 'Ext.app.Controller',
    models: [  ],
    stores: [ 'Idxs@IDAT.store',
        'IdxForms@IDAT.store',
        'IdxHtmls@IDAT.store',
        'ComboFormTypes@IDAT.store',
        'ComboState@IDAT.store'],
    views: [ 'GridIdxForm@IDAT.view.usk.idx',
        'GridIdx@IDAT.view.usk.idx',
        'Interface@IDAT.view.usk.idx'],
    refs: [
        {ref: 'idxPanel', selector: 'idxpanel'},
        {ref: 'gridIdx', selector: 'grididx'},
        {ref: 'gridIdxForm', selector: 'grididxform'}
    ],

	init : function() {
		this.control({
            'idxpanel': {
                beforeactivate: function () {
                    idxStore.proxy.extraParams = {formType: REPORT.IDX.FORM_TYPE, date: REPORT.IDX.DATE};
                    idxStore.reload();
                }
            },

            'grididx': {
                cellclick: function(view, td, cellIndex, record) {
                    var siid = record.get('siid');
                    idxFormStore.proxy.extraParams={siid: siid};
                    idxFormStore.reload();
                }
            },

            'grididxform': {
                cellclick: function(view, td, cellIndex, record) {
                    if (cellIndex == this.getGridIdxForm().columns.length - 4) {
                        Ext.Ajax.request({
                            url: ACTION.REPORT_STATE_CHANGE,
                            method: 'GET',
                            params: {
                                id: record.get('id')
                            },
                            success : function(response) {
                                idxFormStore.proxy.extraParams={siid: record.get('siid')};
                                idxFormStore.reload();
                                window.open(record.get('localFile'),  "newwindow", 'toolbar=no, menubar=no,scrollbars=no, resizable=no, location=no, status=no');
                            },
                            failure : function(response) {
                                var responseJson = Ext.JSON.decode(response.responseText);
                                Ext.Msg.alert(LANG.TITLE.failure, responseJson.msg);
                            }
                        });
                    }
                }
            },

            'reporttree': {
                itemmousedown: function(selModel, record) {
                    // Show grid
                    var me = this;
                    Ext.Ajax.request({
                        url: ACTION.REPORT_GRID_GENERATE,
                        method: 'GET',
                        success:function(response){
                            var responseJson = Ext.JSON.decode(response.responseText);
                            var store = new Ext.data.JsonStore({
                                data: responseJson.data,
                                fields: responseJson.fieldsNames
                            });
                            me.getReportGrid().reconfigure(store, responseJson.columnModle);
                        }
                    });

                    // TODO: 根据菜单得到的地址，拼装html表格路径
                    var url = record.get('id');
                    console.log(url);
                    window.open("data/test31.html", "newwindow", 'toolbar=no, menubar=no,scrollbars=no, resizable=no, location=no, status=no');
                }
            }

		});
	}
});
