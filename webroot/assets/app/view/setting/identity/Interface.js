Ext.define('IDAT.view.setting.identity.Interface' ,{
    extend: 'Ext.panel.Panel',
    alias : 'widget.settingidentity',

    items: [{
        layout : 'border',
        items: [{
            region: 'west',
            xtype: 'identitytree',
            title: LANG.TITLE.identity_menu
        },{
            region: 'center',
            xtype: 'identitygrid',
            title: LANG.TITLE.grid_idx_form
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
                Ext.getStore('IdentityMenus').proxy.extraParams = {identity: identity};
                Ext.getStore('IdentityMenus').reload();
                // Reload identity menu tree
                Ext.getStore('TreeIdentityMenus').proxy.extraParams={identity : identity};
                Ext.getStore('TreeIdentityMenus').reload();
            }
        }
    }]
});
