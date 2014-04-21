Ext.define('IDAT.controller.Setting', {
	extend: 'Ext.app.Controller',
	models: [],
	stores: [
		'ComboMenus@IDAT.store',
		'ComboIdentity@IDAT.store',
		'ComboGroups@IDAT.store',
		'FormTypes@IDAT.store',
		'Groups@IDAT.store',
		'IdentityMenus@IDAT.store',
		'TreeIdentityMenus@IDAT.store'
	],
	views: [
		'List@IDAT.view.setting.usk.type',
		'List@IDAT.view.setting.group',
		'List@IDAT.view.setting.identity',
		'Tree@IDAT.view.setting.identity',
		'Interface@IDAT.view.setting.identity'
	],
	refs: [
		{ref: 'settingFormType', selector: 'settingformtype'},
		{ref: 'settingGroup', selector: 'settinggroup'},
		{ref: 'settingIdentity', selector: 'settingidentity'},
		{ref: 'identityGrid', selector: 'identitygrid'}
	],

	init: function () {
		this.control({
			// SETTING GROUP START -------------------------------------------------------------
			'settinggroup': {
				beforeactivate: function () {
                    Ext.getStore('Groups').reload();
				},
				cellclick: function (view, td, cellIndex, record) {
					if (cellIndex == this.getSettingGroup().columns.length - 1) {
						Ext.Msg.show({
							title: LANG.TITLE.delete,
							msg: LANG.MESSAGE.delete,
							buttons: Ext.Msg.YESNO,
							icon: Ext.Msg.QUESTION,
							fn: function (btn) {
								if (btn == 'yes') {
									Ext.Ajax.request({
										url: ACTION.SET_GROUP_DELETE,
										method: 'GET',
										params: {
											groupId: record.get('groupId')
										},
										success: function (response) {
											var responseJson = Ext.JSON.decode(response.responseText);
											Ext.Msg.alert(LANG.TITLE.success, responseJson.msg);
                                            Ext.getStore('Groups').reload();
										},
										failure: function (response) {
											var responseJson = Ext.JSON.decode(response.responseText);
											Ext.Msg.alert(LANG.TITLE.failure, responseJson.msg);
										}
									});
								}
							}
						});
					}
				}
			},

			'settinggroup button[action=add]': {
				click: function () {
					Ext.create('IDAT.view.setting.group.Add').show();
				}
			},
			// SETTING GROUP END ---------------------------------------------------------------

			// SETTING FORM TYPE START -------------------------------------------------------------
			'settingformtype': {
				beforeactivate: function () {

				},
				cellclick: function (view, td, cellIndex, record) {
					if (cellIndex == this.getSettingFormType().columns.length - 2) {
						Ext.Msg.show({
							title: LANG.TITLE.delete,
							msg: LANG.MESSAGE.delete,
							buttons: Ext.Msg.YESNO,
							icon: Ext.Msg.QUESTION,
							fn: function (btn) {
								if (btn == 'yes') {
									Ext.Ajax.request({
										url: ACTION.SET_FORM_TYPE_DELETE,
										method: 'GET',
										params: {
											id: record.get('id')
										},
										success: function (response) {
											var responseJson = Ext.JSON.decode(response.responseText);
											Ext.Msg.alert(LANG.TITLE.success, responseJson.msg);
                                            Ext.getStore('FormTypes').reload();
										},
										failure: function (response) {
											var responseJson = Ext.JSON.decode(response.responseText);
											Ext.Msg.alert(LANG.TITLE.failure, responseJson.msg);
										}
									});
								}
							}
						});
					}
				}
			},
			'settingformtype button[action=add]': {
				click: function () {
					Ext.create('IDAT.view.setting.usk.type.Add').show();
				}
			},
			// SETTING FORM TYPE END -------------------------------------------------------------

			// IDENTITY MANAGE START -------------------------------------------------------------
			'identitygrid': {
				cellclick: function (view, td, cellIndex, record) {
					if (cellIndex == this.getIdentityGrid().columns.length - 2) {
						Ext.Msg.show({
							title: LANG.TITLE.delete,
							msg: LANG.MESSAGE.delete,
							buttons: Ext.Msg.YESNO,
							icon: Ext.Msg.QUESTION,
							fn: function (btn) {
								if (btn == 'yes') {
									Ext.Ajax.request({
										url: ACTION.SET_IDENTITY_MENU_DELETE,
										method: 'GET',
										params: {
											id: record.get('id')
										},
										success: function (response) {
											var responseJson = Ext.JSON.decode(response.responseText);
											Ext.Msg.alert(LANG.TITLE.success, responseJson.msg);
											Ext.getStore('IdentityMenus').proxy.extraParams = {identity: record.get('identity')};
											Ext.getStore('IdentityMenus').reload();

                                            Ext.getStore('TreeIdentityMenus').proxy.extraParams={identity : record.get('identity')};
                                            Ext.getStore('TreeIdentityMenus').reload();
										},
										failure: function (response) {
											var responseJson = Ext.JSON.decode(response.responseText);
											Ext.Msg.alert(LANG.TITLE.failure, responseJson.msg);
										}
									});
								}
							}
						});
					}
				}
			},
			'settingidentity button[action=add]': {
				click: function (button) {
					Ext.create('IDAT.view.setting.identity.Add').show();
				}
			}
			// IDENTITY MANAGE END -------------------------------------------------------------
		});
	}
});
