package org.gingko.server.handler.api.actions;

import com.google.gson.Gson;
import org.gingko.app.persist.PersistContext;
import org.gingko.app.persist.domain.sys.Menu;
import org.gingko.app.persist.mapper.sys.MenuMapper;
import org.gingko.config.Lang;
import org.gingko.context.AppContext;
import org.gingko.server.handler.api.vo.ExtCombo;
import org.gingko.server.handler.api.vo.ExtMessage;
import org.gingko.server.handler.api.vo.MenuNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TangYing
 */
public enum MenuAction {

    INSTANCE;

    private static MenuMapper menuMapper = (MenuMapper) AppContext.getBean(PersistContext.MENU_MAPPER);

    /**
     * Menu load
     *
     * @return
     */
    public String loadMenu(String menuId, String identity) {
        int i = 0;
        try {
            i = Integer.valueOf(identity);
        } catch (NumberFormatException e) {
            return new Gson().toJson(new ExtMessage(false, Lang.paramError));
        }

        Menu menu = menuMapper.selectByMenuId(menuId, i);
        if (menu == null) {
            ExtMessage message = new ExtMessage(false, Lang.operateOutOfIdentity);
            return new Gson().toJson(message);
        } else {
            ExtMessage message = new ExtMessage(true, Lang.operateSuccess);
            message.setMenu(menu);
            return new Gson().toJson(message);
        }
    }

    /**
     * Menu load
     *
     * @return
     */
    public String load(String parentId, String identity) {
        int i = 0;
        try {
            i = Integer.valueOf(identity);
        } catch (NumberFormatException e) {
            return new Gson().toJson(new ExtMessage(false, Lang.paramError));
        }

        List<Menu> menus = menuMapper.selectByParentId(parentId, i);
        List<MenuNode> nodes = new ArrayList<MenuNode>();
        for (Menu menu : menus) {
            MenuNode node = generateNode(menu, i);
            nodes.add(node);
        }
        return new Gson().toJson(nodes);
    }

    /**
     * Menu load all
     *
     * @return
     */
    public String loadAll(String identity) {
        int i = 0;
        try {
            i = Integer.valueOf(identity);
        } catch (NumberFormatException e) {
            return new Gson().toJson(new ExtMessage(false, Lang.paramError));
        }

        List<Menu> menus = menuMapper.selectByParentId("top", i);
        List<MenuNode> nodes = new ArrayList<MenuNode>();
        for (Menu menu : menus) {
            MenuNode node = generateNode(menu, i);
            nodes.add(node);
        }
        return new Gson().toJson(nodes);
    }

    /**
     * Generate menu node
     * @param menu
     *
     * @return
     */
    private MenuNode generateNode(Menu menu, int identity) {
        MenuNode node = new MenuNode(menu);
        List<Menu> childList = menuMapper.selectByParentId(menu.getMenuId(), identity);
        for (Menu childMenu : childList) {
            MenuNode childNode = generateNode(childMenu, identity);
            node.addChildNode(childNode);
        }
        return node;
    }

    /**
     * Menu combo
     *
     * @return
     */
    public String combo() {
        List<Menu> menus = menuMapper.selectAll();
        List<ExtCombo> list = new ArrayList<ExtCombo>();
        for (Menu menu : menus) {
            list.add(new ExtCombo(menu));
        }
        return new Gson().toJson(list);
    }
}
