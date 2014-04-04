Ext.define('IDAT.view.setting.identity.Interface' ,{
    extend: 'Ext.panel.Panel',
    alias : 'widget.settingidentity',

    items : [{
        layout : 'hbox',
        items: [{
            xtype: 'panel',
            title: LANG.TITLE.identity_menu,
            layout : 'fit',
            collapsible: true,
            collapseDirection: 'left',
            flex: 1,
            items: [{
                height: 575,
                xtype: 'identitytree'
            }]
        },{
            xtype: 'panel',
            title: LANG.TITLE.identity_grid,
            flex: 6,
            items: [{
                height: 575,
                xtype: 'identitygrid'
            }]
        }]
    }],

    tbar: [{
        xtype: 'button',
        iconCls: 'icon-add',
        action: 'add',
        text: LANG.BTN.add
    }, {
        xtype: 'combobox',
        name: 'formType',
        fieldLabel: LANG.LABEL.identity,
        labelAlign: 'right',
        labelWidth : 60,
        store: 'ComboIdentity',
        editable: true,
        valueField: 'value',
        displayField: 'display',
        emptyText: LANG.EMPTY_TIPS.empty_combo_identity,
        listeners: {
            scope: this,
            'select': function (combo, records) {
                var identity = records[0].get('value');
                identityMenuStore.proxy.extraParams = {identity: identity};
                identityMenuStore.reload();
                // Reload identity menu tree
                Ext.getStore('TreeIdentityMenus').proxy.extraParams={identity : identity};
                Ext.getStore('TreeIdentityMenus').reload();
            }
        }
    }]
});
