package org.gingko.server.handler.api;

/**
 * API接口定义
 *
 * @author Tang
 */
public interface Api {

    // 用户相关接口
    public static final String USER_LOGIN = "user_login"; // 用户登录
    public static final String USER_LOAD = "user_load";// 用户加载
    public static final String USER_ADD = "user_add";// 用户增加
    public static final String USER_DELETE = "user_delete";// 用户删除
    public static final String USER_EDIT = "user_edit";// 用户编辑

    // 菜单相关接口
    public static final String MENU_TREE_LOAD = "menu_tree_load";// 加载树菜单
    public static final String MENU_BY_IDENTITY = "menu_by_identity";// 加载权限菜单
    public static final String MENU_COMBO = "menu_combo";// 下拉菜单

    // 报表相关接口
    public static final String REPORT_IDX_LOAD = "report_idx_load";// 加载索引
    public static final String REPORT_HTML_LOAD = "report_html_load";// 加载索引对应的内容
    public static final String REPORT_FORM_LOAD = "report_form_load";// 加载索引对应的表格内容
    public static final String REPORT_STATE_CHANGE = "report_state_change";// 改变报表处理状态
    public static final String REPORT_GRID_GENERATE = "report_grid_generate";// 动态表格生成

    // 系统设置相关接口
    public static final String SET_IDENTITY_MENU_LOAD = "setting_identity_menu_load";// 权限菜单加载
    public static final String SET_IDENTITY_MENU_ADD = "setting_identity_menu_add";// 权限菜单增加
    public static final String SET_IDENTITY_MENU_DELETE = "setting_identity_menu_delete";// 权限菜单删除
    public static final String SET_FORM_TYPE_COMBO = "setting_form_type_combo";// 表单类型下拉
    public static final String SET_FORM_TYPE_LOAD = "setting_form_type_load";// 表单类型加载
    public static final String SET_FORM_TYPE_DELETE = "setting_form_type_delete";// 表单类型删除
    public static final String SET_FORM_TYPE_ADD = "setting_form_type_add";// 表单类型增加
    public static final String SET_GROUP_COMBO = "setting_group_combo";// 小组下拉
    public static final String SET_GROUP_LOAD = "setting_group_load";// 小组加载
    public static final String SET_GROUP_DELETE = "setting_group_delete";// 小组删除
    public static final String SET_GROUP_ADD = "setting_group_add";// 小组增加
    public static final String SET_SYNC_CIK = "setting_syncCik";// 同步本地CIK
}
