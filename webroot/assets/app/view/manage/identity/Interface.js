var I_IDENTITY = 0;
Ext.define('IDAT.view.manage.identity.Interface' ,{
    extend: 'Ext.panel.Panel',
    alias : 'widget.identitymanage',

    items : [{
        layout : 'hbox',
        items: [{
            xtype: 'panel',
            title: LANG.TITLE.identity_menu,
            layout : 'fit',
            flex: 1,
            items: [{
                height: 575,
                xtype: 'identitytree'
            }]
        },{
            xtype: 'panel',
            title: LANG.TITLE.identity_grid,
            flex: 5,
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
                I_IDENTITY = records[0].get('value');

                identityStore.proxy.extraParams = {identity: I_IDENTITY};
                identityStore.reload();

                Ext.getStore('IdentityMenus').proxy.extraParams={identity : I_IDENTITY};
                Ext.getStore('IdentityMenus').reload();
            }
        }
    }]
});
