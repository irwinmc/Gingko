package org.gingko.server.handler.api.actions;

import com.google.gson.Gson;
import org.gingko.app.persist.PersistContext;
import org.gingko.app.persist.domain.sys.Group;
import org.gingko.app.persist.domain.sys.IdentityMenu;
import org.gingko.app.persist.domain.usk.Cik;
import org.gingko.app.persist.domain.usk.FormType;
import org.gingko.app.persist.mapper.remote.CikRemoteMapper;
import org.gingko.app.persist.mapper.sys.GroupMapper;
import org.gingko.app.persist.mapper.sys.IdentityMenuMapper;
import org.gingko.app.persist.mapper.usk.CikMapper;
import org.gingko.app.persist.mapper.usk.FormTypeMapper;
import org.gingko.config.Lang;
import org.gingko.context.AppContext;
import org.gingko.server.handler.api.vo.ExtCombo;
import org.gingko.server.handler.api.vo.ExtMessage;
import org.gingko.server.handler.api.vo.ExtPagingData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TangYing
 */
public enum SettingAction {

    INSTANCE;

    public String test() {
        String json = syncCik();
        return new Gson().toJson(json);
    }

    private static IdentityMenuMapper identityMenuMapper = (IdentityMenuMapper) AppContext.getBean(PersistContext.IDENTITY_MENU_MAPPER);
    private GroupMapper groupMapper = (GroupMapper) AppContext.getBean(PersistContext.GROUP_MAPPER);
    private FormTypeMapper formTypeMapper = (FormTypeMapper) AppContext.getBean(PersistContext.FORM_TYPE_MAPPER);
    private CikMapper cikMapper = (CikMapper) AppContext.getBean(PersistContext.CIK_MAPPER);
    private CikRemoteMapper cikRemoteMapper = (CikRemoteMapper) AppContext.getBean(PersistContext.CIK_REMOTE_MAPPER);

    // IDENTITY MENU START -----------------------------------------------------------------------
    /**
     * Identity menu laod
     *
     * @param start
     * @param limit
     * @return
     */
    public String identityMenuLoad(String identity, String start, String limit) {
        int i = 0, l = 0, o = 0;      // limit & offset
        try {
            i = Integer.valueOf(identity);
            l = Integer.valueOf(limit);
            o = Integer.valueOf(start);
        } catch (NumberFormatException e) {
            return new Gson().toJson(new ExtMessage(false, Lang.paramError));
        }

        List<IdentityMenu> list = identityMenuMapper.selectByPage(i, l, o);
        int totalCount = identityMenuMapper.selectTotalCount(i);

        ExtPagingData<IdentityMenu> items = new ExtPagingData<IdentityMenu>(true, totalCount, list);
        return new Gson().toJson(items);
    }

    /**
     * Identity menu add
     *
     * @param menuId
     * @param identity
     * @return
     */
    public String identityMenuAdd( String identity, String menuId) {
        int i = 0;
        try {
            i = Integer.valueOf(identity);
        } catch (NumberFormatException e) {
            return new Gson().toJson(new ExtMessage(false, Lang.paramError));
        }

        // Check account existed.
        List<String> list = identityMenuMapper.selectMenusByIdentity(i);
        if (list.contains(menuId)) {
            ExtMessage message = new ExtMessage(false, Lang.identityExisted);
            return new Gson().toJson(message);
        }
        IdentityMenu domain = new IdentityMenu();
        domain.setMenuId(menuId);
        domain.setIdentity(i);
        identityMenuMapper.insert(domain);

        ExtMessage message = new ExtMessage(true, Lang.operateAddSuccess);
        return new Gson().toJson(message);
    }

    /**
     * Identity menu delete
     *
     * @param id
     * @return
     */
    public String identityMenudelete(String id) {
        int i = 0;
        try {
            i = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            return new Gson().toJson(new ExtMessage(false, Lang.paramError));
        }

        identityMenuMapper.deleteById(i);
        ExtMessage message = new ExtMessage(true, Lang.operateDeleteSuccess);
        return new Gson().toJson(message);
    }
    // IDENTITY MENU END -----------------------------------------------------------------------

    // GROUP START -------------------------------------------------------------------------------
    /**
     * Group combo
     *
     * @return
     */
    public String groupCombo() {
        List<Group> list = groupMapper.selectAll();
        List<ExtCombo> combos = new ArrayList<ExtCombo>();
        for (Group group : list) {
            combos.add(new ExtCombo(group));
        }
        return new Gson().toJson(combos);
    }

    /**
     * Setting group load
     *
     * @return
     */
    public String groupLoad() {
        List<Group> list = groupMapper.selectAll();
        ExtPagingData<Group> items = new ExtPagingData<Group>(true, list.size(), list);
        return new Gson().toJson(items);
    }

    /**
     * Setting group add
     *
     * @return
     */
    public String groupAdd(String name, String host) {
        Group group = new Group();
        group.setName(name);
        group.setHost(host);
        groupMapper.insert(group);

        ExtMessage message = new ExtMessage(true, Lang.operateAddSuccess);
        return new Gson().toJson(message);
    }

    /**
     * Setting group delete
     *
     * @return
     */
    public String groupDelete(String groupId) {
        int id = 0;      // limit & offset
        try {
            id = Integer.valueOf(groupId);
        } catch (NumberFormatException e) {
            return new Gson().toJson(new ExtMessage(false, Lang.paramError));
        }

        groupMapper.deleteById(id);
        ExtMessage message = new ExtMessage(true, Lang.operateDeleteSuccess);
        return new Gson().toJson(message);
    }
    // GROUP END -----------------------------------------------------------------------

    // FORM TYPE START -----------------------------------------------------------------------
    /**
     * Setting type load
     *
     * @return
     */
    public String formTypeLoad(String groupId) {
        int id = 0;      // limit & offset
        try {
            id = Integer.valueOf(groupId);
        } catch (NumberFormatException e) {
            return new Gson().toJson(new ExtMessage(false, Lang.paramError));
        }

        List<FormType> list = formTypeMapper.selectByGroupId(id);
        ExtPagingData<FormType> items = new ExtPagingData<FormType>(true, list.size(), list);
        return new Gson().toJson(items);
    }

    /**
     * Form type combo
     *
     * @param groupId
     * @return
    */
    public String formTypeCombo(String groupId) {
        int id = 0;      // limit & offset
        try {
            id = Integer.valueOf(groupId);
        } catch (NumberFormatException e) {
            return new Gson().toJson(new ExtMessage(false, Lang.paramError));
        }

        List<FormType> list = formTypeMapper.selectByGroupId(id);
        List<ExtCombo> combos = new ArrayList<ExtCombo>();
        combos.add(new ExtCombo("ALL", "全部"));
        for (FormType formType : list) {
            combos.add(new ExtCombo(formType));
        }
        return new Gson().toJson(combos);
    }

    /**
     * Form type add
     *
     * @param formType
     * @param groupId
     * @return
     */
    public String formTypeAdd(String formType, String groupId) {
        int id = 0;      // limit & offset
        try {
            id = Integer.valueOf(groupId);
        } catch (NumberFormatException e) {
            return new Gson().toJson(new ExtMessage(false, Lang.paramError));
        }

        // Check account existed.
        FormType type = formTypeMapper.selectByGroupAndType(formType, id);
        if (type != null) {
            ExtMessage message = new ExtMessage(false, Lang.settingTypeExisted);
            return new Gson().toJson(message);
        }
        type = new FormType();
        type.setFormType(formType);
        type.setGroupId(id);
        formTypeMapper.insert(type);

        ExtMessage message = new ExtMessage(true, Lang.operateAddSuccess);
        return new Gson().toJson(message);
    }

    /**
     * Form type delete
     *
     * @param id
     * @return
     */
    public String formTypeDelete(String id) {
        int i = 0;      // limit & offset
        try {
            i = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            return new Gson().toJson(new ExtMessage(false, Lang.paramError));
        }

        formTypeMapper.deleteById(i);
        ExtMessage message = new ExtMessage(true, Lang.operateDeleteSuccess);
        return new Gson().toJson(message);
    }
    // FORM TYPE END -----------------------------------------------------------------------

    // CIK START ---------------------------------------------------------------------------
    /**
     * Sync Cik and code from remote
     * TODO: this action can be auto run or hand run
     *
     * @return
     */
    public String syncCik() {
        List<Cik> remoteList = cikRemoteMapper.select();
        List<Cik> localList = cikMapper.selectAll();

        List<Cik> deleteList = new ArrayList<Cik>();
        for (Cik cik : localList) {
            boolean success = remoteList.remove(cik);
            if (!success) {
                deleteList.add(cik);
                cikMapper.delete(cik);
            }
        }

        if (remoteList.isEmpty()) {
            ExtMessage message = new ExtMessage(true, Lang.operateSynCikSuccess.replace("#{deleteCount}", deleteList.size() + "").replace("#{addCount}", 0 + ""));
            return new Gson().toJson(message);
        }

        // Insert
        cikMapper.insertList(remoteList);
        ExtMessage message = new ExtMessage(true, Lang.operateSynCikSuccess.replace("#{deleteCount}", deleteList.size() + "").replace("#{addCount}", remoteList.size() + ""));
        return new Gson().toJson(message);
    }
    // CIK END ---------------------------------------------------------------------------
}
