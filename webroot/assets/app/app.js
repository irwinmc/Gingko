/**
 * Created by TangYing
 */
Ext.onReady(function () {
    var loginForm = Ext.create("Ext.form.Panel", {
        title: LANG.TITLE.login,
        frame: true,
        bodyPadding: 10,
        defaultType: 'textfield',
        defaults: {
            anchor: '100%',
            labelWidth: 50,
            labelAlign: "right"
        },
        items: [{
            allowBlank: false,
            fieldLabel:  LANG.LABEL.account,
            name: 'account',
            emptyText: LANG.EMPTY_TIPS.empty_account
        }, {
            allowBlank: false,
            fieldLabel: LANG.LABEL.password,
            name: 'password',
            emptyText: LANG.EMPTY_TIPS.empty_password,
            inputType: 'password'
        }],

        buttonAlign: 'center',
        buttons: [ {
            text: LANG.BTN.login,
            iconCls: 'icon-tick',
            handler : function() {
                if (loginForm.isValid()) {
                    loginForm.submit({
                        clientValidation : true,
                        url : ACTION.USER_LOGIN,
                        method : 'Get',
                        success : function(form, action) {
                            //Ext.Msg.alert(LANG.TITLE.success, action.result.msg);
                            GLOBAL.ACCOUNT = action.result.account;
                            GLOBAL.IDENTITY = action.result.identity;
                            loginForm.close();
                            Ext.application({
                                name: 'IDAT',
                                appFolder: 'assets/app',
                                controllers: [
                                    'Main',
                                    'Manage',
                                    'Setting',
                                    'UskReport'
                                ],
                                launch : function() {
                                    Ext.create('IDAT.view.Viewport');
                                }
                            });
                        },
                        failure : function(form, action) {
                            Ext.Msg.alert(LANG.TITLE.failure, action.result.msg);
                            loginForm.getForm().reset();
                        }
                    });
                }
            }
        }, {
            text: LANG.BTN.reset,
            iconCls: 'icon-undo',
            handler : function() {
                loginForm.getForm().reset();
            }
        }],
        renderTo: "loginDiv"
    });
});
