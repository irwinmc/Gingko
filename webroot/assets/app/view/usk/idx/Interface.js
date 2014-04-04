Ext.define('IDAT.view.usk.idx.Interface' ,{
    extend: 'Ext.panel.Panel',
    alias : 'widget.idxpanel',

    items : [{
        layout : 'hbox',
        items: [{
            xtype: 'panel',
            layout : 'fit',
            title: LANG.TITLE.grid_idx,
            flex: 1,
            items: [{
                height: 575,
                xtype: 'grididx'
            }]
        },{
            xtype: 'panel',
            title: LANG.TITLE.grid_idx_form,
            flex: 1,
            items: [{
                height: 575,
                xtype: 'grididxform'
            }]
        }]
    }]
});

