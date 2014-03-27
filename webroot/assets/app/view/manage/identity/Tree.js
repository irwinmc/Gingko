Ext.define('IDAT.view.manage.identity.Tree', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.identitytree',

    /**
    containerScroll: false,
    collapsible: false,
    autoScroll: false,
    border: false,
    frame: false,
    split: true,
    enableDD: false,
    layout: {
        // layout-specific configs go here
        type: 'accordion',
        titleCollapse: true,
        animate: false,
        activeOnTop: false
    },
     **/

    xtype: 'treepanel',
    store: 'IdentityMenus',
    bodyStyle: "padding:5 5 5 5",
    rootVisible: false,

    listeners: {
        'beforeexpand': function(view) {
            this.store.proxy.extraParams={identity : I_IDENTITY};
            this.store.reload();
        }
    }
});