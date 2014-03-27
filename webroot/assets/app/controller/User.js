var userStore = Ext.create('IDAT.store.Users');

Ext.define('IDAT.controller.User', {
	extend : 'Ext.app.Controller',

	stores : [ 'Users@IDAT.store',  'ComboIdentity@IDAT.store' ],
	
	models : [  ],
	
	views : [ 'List@IDAT.view.user' ],

	refs : [ {
		ref : 'userManage', selector : 'usermanage'
	} ],

	init : function() {
		this.control({
			'usermanage' : {
				beforeactivate : function() {
					userStore.reload();
				}
			},
			'usermanage button[action=add]': {
                click: function(button) {
                    Ext.create('IDAT.view.user.Add').show();
                }
            }
		});
	}
});
