package org.gingko.server.handler.api;

/**
 * API接口定义
 * 
 * @author Tang
 */
public interface Api {
	
	// 用户相关接口
	public static final String USER_LOGIN	              = "login";    // 用户登录
    public static final String USER_LOAD	              = "load";		// 用户加载
    public static final String USER_ADD	              = "add";		// 用户增加
    public static final String USER_DELETE	          = "delete";	// 用户删除
    public static final String USER_EDIT	              = "edit";		// 用户编辑

    // 权限相关接口
    public static final String IDENTITY_LOAD	          = "load";		// 权限加载
    public static final String IDENTITY_ADD	          = "add";		// 权限增加
    public static final String IDENTITY_DELETE	      = "delete";	// 权限删除

    // 菜单相关接口
    public static final String MENU_LOAD	              = "load";		// 加载菜单
    public static final String MENU_LOAD_ALL   	      = "loadAll";	// 加载菜单
    public static final String MENU_COMBO	              = "combo";	// 下拉菜单

    // 报表相关接口
    public static final String LOAD_IDX	              = "loadIdx";	          // 加载索引
    public static final String LOAD_IDX_REPORT	      = "loadIdxReport";	  // 加载索引对应的内容
    public static final String OPERATE	              = "operate";	          // 处理索引

    // 设置相关接口
    public static final String SET_COMBO_FORM_TYPE	  = "comboFormType";	  // 表单类型下拉
    public static final String SET_LOAD_FORM_TYPE	      = "loadFormType";	      // 表单类型加载
    public static final String SET_DELETE_FORM_TYPE	  = "deleteFormType";	  // 表单类型删除
    public static final String SET_ADD_FORM_TYPE	      = "addFormType";	      // 表单类型增加
    public static final String SET_USE_FORM_TYPE	      = "useFormType";	      // 表单类型使用
    public static final String SET_SYNC_CIK	          = "syncCik";	          // 同步本地CIK
}
