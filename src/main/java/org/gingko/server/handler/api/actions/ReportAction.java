package org.gingko.server.handler.api.actions;

import com.google.gson.Gson;
import org.gingko.app.persist.PersistContext;
import org.gingko.app.persist.domain.SecHtmlIdx;
import org.gingko.app.persist.domain.SecIdx;
import org.gingko.app.persist.mapper.SecHtmlIdxMapper;
import org.gingko.app.persist.mapper.SecIdxMapper;
import org.gingko.app.persist.mapper.usk.CikMapper;
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
public enum ReportAction {

    INSTANCE;

    private static SecIdxMapper secIdxMapper = (SecIdxMapper) AppContext.getBean(PersistContext.SEC_IDX_MAPPER);
    private static SecHtmlIdxMapper secHtmlIdxMapper = (SecHtmlIdxMapper) AppContext.getBean(PersistContext.SEC_HTML_IDX_MAPPER);
    private static CikMapper cikMapper = (CikMapper) AppContext.getBean(PersistContext.CIK_MAPPER);

    /**
     * Idx laod
     *
     * @param start
     * @param limit
     * @return
     */
    public String loadIdx(String formType, String state, String date, String start, String limit) {
        int l = 0, o = 0;      // limit & offset
        try {
            l = Integer.valueOf(limit);
            o = Integer.valueOf(start);
        } catch (NumberFormatException e) {
            return new Gson().toJson(new ExtMessage(false, Lang.paramError));
        }
        String[] formTypes = null;
        if (formType != null && !formType.contains("ALL")) {
            formTypes = formType.split(",");
        }

        int s = -1;
        try {
            s = Integer.valueOf(state);
        } catch (NumberFormatException e) { }

        date = date.replaceAll("-", "");

        List<SecIdx> list = secIdxMapper.selectByPage(formTypes, s, date, l, o);
        for (SecIdx secIdx : list) {
            List<String> codes = cikMapper.selectCodeByCik(secIdx.getCik());
            String code = codes.toString().replace("]", "").replace("[", "");
            secIdx.setCode(code);
        }

        int totalCount = secIdxMapper.selectTotalCount(formTypes, s, date);

        ExtPagingData<SecIdx> items = new ExtPagingData<SecIdx>(true, totalCount, list);
        return new Gson().toJson(items);
    }

    /**
     * operate
     *
     * @param state
     * @return
     */
    public String operate(String siid, String state) {
        int s = 0;
        try {
            s = Integer.valueOf(state);
        } catch (NumberFormatException e) {
            return new Gson().toJson(new ExtMessage(false, Lang.paramError));
        }

        // Update state
        s = s >= 2 ? 1 : s + 1;
        SecIdx secIdx = secIdxMapper.selectBySiid(siid);
        if (secIdx == null) {
            ExtMessage message = new ExtMessage(false, Lang.operateFailure);
            return new Gson().toJson(message);
        }

        secIdx.setState(s);
        secIdxMapper.update(secIdx);

        ExtMessage message = new ExtMessage(true, Lang.operateSuccess);
        return new Gson().toJson(message);
    }

    /**
     * Idx report laod
     *
     * @param siid
     * @return
     */
    public String loadIdxReport(String siid) {
        List<SecHtmlIdx> list = secHtmlIdxMapper.selectBySiid(siid);

        ExtPagingData<SecHtmlIdx> items = new ExtPagingData<SecHtmlIdx>(true, list.size(), list);
        return new Gson().toJson(items);
    }
}
