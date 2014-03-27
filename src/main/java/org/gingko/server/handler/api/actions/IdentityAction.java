package org.gingko.server.handler.api.actions;

import com.google.gson.Gson;
import org.gingko.app.persist.PersistContext;
import org.gingko.app.persist.domain.Identity;
import org.gingko.app.persist.mapper.IdentityMapper;
import org.gingko.config.Lang;
import org.gingko.context.AppContext;
import org.gingko.server.handler.api.vo.ExtMessage;
import org.gingko.server.handler.api.vo.ExtPagingData;

import java.util.List;

/**
 * Created by TangYing
 */
public enum IdentityAction {

    INSTANCE;

    private static IdentityMapper identityMapper = (IdentityMapper) AppContext.getBean(PersistContext.IDENTITY_MAPPER);

    /**
     * Identity laod
     *
     * @param start
     * @param limit
     * @return
     */
    public String load(String identity, String start, String limit) {
        int i = 0, l = 0, o = 0;      // limit & offset
        try {
            i = Integer.valueOf(identity);
            l = Integer.valueOf(limit);
            o = Integer.valueOf(start);
        } catch (NumberFormatException e) {
            return new Gson().toJson(new ExtMessage(false, Lang.paramError));
        }

        List<Identity> list = identityMapper.selectByPage(i, l, o);
        int totalCount = identityMapper.totalCount(i);

        ExtPagingData<Identity> items = new ExtPagingData<Identity>(true, totalCount, list);
        return new Gson().toJson(items);
    }

    /**
     * Identity add
     *
     * @param menuId
     * @param identity
     * @return
     */
    public String add( String identity, String menuId) {
        int i = 0;
        try {
            i = Integer.valueOf(identity);
        } catch (NumberFormatException e) {
            return new Gson().toJson(new ExtMessage(false, Lang.paramError));
        }

        // Check account existed.
        List<String> list = identityMapper.selectMenusByIdentity(i);
        if (list.contains(menuId)) {
            ExtMessage message = new ExtMessage(false, Lang.identityExisted);
            return new Gson().toJson(message);
        }
        Identity domain = new Identity();
        domain.setMenuId(menuId);
        domain.setIdentity(i);
        identityMapper.insert(domain);

        ExtMessage message = new ExtMessage(true, Lang.operateAddSuccess);
        return new Gson().toJson(message);
    }

    /**
     * Identity delete
     *
     * @param id
     * @return
     */
    public String deleteById(String id) {
        int i = 0;
        try {
            i = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            return new Gson().toJson(new ExtMessage(false, Lang.paramError));
        }

        identityMapper.deleteById(i);
        ExtMessage message = new ExtMessage(true, Lang.operateDeleteSuccess);
        return new Gson().toJson(message);
    }
}
