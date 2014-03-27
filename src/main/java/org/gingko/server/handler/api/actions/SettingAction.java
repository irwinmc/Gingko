package org.gingko.server.handler.api.actions;

import com.google.gson.Gson;
import org.gingko.app.persist.PersistContext;
import org.gingko.app.persist.domain.usk.Cik;
import org.gingko.app.persist.domain.usk.FormType;
import org.gingko.app.persist.mapper.remote.CikRemoteMapper;
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
        List<FormType> list = formTypeMapper.selectAll();
        ExtPagingData<FormType> items = new ExtPagingData<FormType>(true, list.size(), list);
        return new Gson().toJson(items);
    }

    private static FormTypeMapper formTypeMapper = (FormTypeMapper) AppContext.getBean(PersistContext.FORM_TYPE_MAPPER);
    private static CikMapper cikMapper = (CikMapper) AppContext.getBean(PersistContext.CIK_MAPPER);
    private static CikRemoteMapper cikRemoteMapper = (CikRemoteMapper) AppContext.getBean(PersistContext.CIK_REMOTE_MAPPER);

    // FORM TYPE START -----------------------------------------------------------------------
    /**
     * Setting type laod
     *
     * @return
     */
    public String loadFormType() {
        List<FormType> list = formTypeMapper.selectAll();
        ExtPagingData<FormType> items = new ExtPagingData<FormType>(true, list.size(), list);
        return new Gson().toJson(items);
    }

    /**
     * Setting type combo
     *
     * @return
    */
    public String comboType() {
        List<String> list = formTypeMapper.selectByUsed();
        List<ExtCombo> combos = new ArrayList<ExtCombo>();
        combos.add(new ExtCombo("ALL", "全部"));
        for (String formType : list) {
            combos.add(new ExtCombo(formType, formType));
        }
        return new Gson().toJson(combos);
    }

    /**
     * Identity add
     *
     * @param formType
     * @return
     */
    public String addFormType( String formType) {
        // Check account existed.
        FormType type = formTypeMapper.selectByFormType(formType);
        if (type != null) {
            ExtMessage message = new ExtMessage(false, Lang.settingTypeExisted);
            return new Gson().toJson(message);
        }
        type = new FormType();
        type.setFormType(formType);
        type.setUsed(1);
        formTypeMapper.insert(type);

        ExtMessage message = new ExtMessage(true, Lang.operateAddSuccess);
        return new Gson().toJson(message);
    }

    /**
     * Setting type delete
     *
     * @param formType
     * @return
     */
    public String deleteFormType(String formType) {
        formTypeMapper.delete(formType);
        ExtMessage message = new ExtMessage(true, Lang.operateDeleteSuccess);
        return new Gson().toJson(message);
    }

    /**
     * Setting type use
     *
     * @return
     */
    public String useFormType(String formType, String used) {
        int u = 0;
        try {
            u = Integer.valueOf(used);
        } catch (NumberFormatException e) {
            return new Gson().toJson(new ExtMessage(false, Lang.paramError));
        }

        // Check is empty
        if (formType == null || formType.equals("")) {
            return new Gson().toJson(new ExtMessage(false, Lang.paramError));
        }

        String[] formTypes = formType.split(",");
        formTypeMapper.update(formTypes, u);

        ExtMessage message = new ExtMessage(true, Lang.operateSuccess);
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
        List<Cik> localelist = cikMapper.selectAll();

        List<Cik> deleteList = new ArrayList<Cik>();
        for (Cik cik : localelist) {
            boolean success = remoteList.remove(cik);
            if (!success) {
                deleteList.add(cik);
                cikMapper.delete(cik);
            }
        }
        // Insert
        cikMapper.insertList(remoteList);
        ExtMessage message = new ExtMessage(true, Lang.operateSynCikSuccess.replace("#{deleteCount}", deleteList.size() + "").replace("#{addCount}", remoteList.size() + ""));
        return new Gson().toJson(message);
    }
    // CIK END ---------------------------------------------------------------------------
}
