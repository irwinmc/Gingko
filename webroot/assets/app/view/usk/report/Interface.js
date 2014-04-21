Ext.define('IDAT.view.usk.report.Interface' ,{
    extend: 'Ext.panel.Panel',
    alias : 'widget.idxpanel',

    items : [{
        layout : 'border',
        hideBorders : true,
        items: [{
			region: 'west',
            xtype: 'grididx',
			width: 500,
			split: true,
            title: LANG.TITLE.grid_idx
        },{
			region: 'center',
            xtype: 'gridform',
            title: LANG.TITLE.grid_idx_form
        }]
    }]
});

