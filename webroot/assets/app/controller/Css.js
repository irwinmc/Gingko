var cssTypeStore = Ext.create('IDAT.store.ComboCssType');
var cssStore = Ext.create('IDAT.store.Css');

Ext.define('IDAT.controller.Css', {
	extend: 'Ext.app.Controller',
    
	stores : [ 'ComboCss@IDAT.store', 'ComboCssType@IDAT.store' ],

	models : [ 'Css@IDAT.model' ],
	
	views: ['List@IDAT.view.css'],
      
    refs: [  
         {ref: 'cssManage', selector: 'cssmanage'}
    ],
    
    init: function() {
        this.control({
        	'cssmanage' : {
        		beforeactivate : function() {
        			cssStore.reload();
        		}
        	},
            'cssmanage button[action=add]': {
                click: function(button) {
                    Ext.create('IDAT.view.css.Add').show();
                }
            }
        });
    }
});