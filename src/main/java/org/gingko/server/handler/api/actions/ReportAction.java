package org.gingko.server.handler.api.actions;

import com.google.gson.Gson;
import org.gingko.app.persist.PersistContext;
import org.gingko.app.persist.domain.Report;
import org.gingko.app.persist.domain.SecForm;
import org.gingko.app.persist.domain.SecHtmlIdx;
import org.gingko.app.persist.domain.SecIdx;
import org.gingko.app.persist.domain.sys.Menu;
import org.gingko.app.persist.mapper.ReportMapper;
import org.gingko.app.persist.mapper.SecFormMapper;
import org.gingko.app.persist.mapper.SecHtmlIdxMapper;
import org.gingko.app.persist.mapper.SecIdxMapper;
import org.gingko.app.persist.mapper.usk.CikMapper;
import org.gingko.config.Lang;
import org.gingko.context.AppContext;
import org.gingko.server.handler.api.vo.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TangYing
 */
public enum ReportAction {

    INSTANCE;

    private static SecIdxMapper secIdxMapper = (SecIdxMapper) AppContext.getBean(PersistContext.SEC_IDX_MAPPER);
    private static SecFormMapper secFormMapper = (SecFormMapper) AppContext.getBean(PersistContext.SEC_FORM_MAPPER);
    private static SecHtmlIdxMapper secHtmlIdxMapper = (SecHtmlIdxMapper) AppContext.getBean(PersistContext.SEC_HTML_IDX_MAPPER);
    private static CikMapper cikMapper = (CikMapper) AppContext.getBean(PersistContext.CIK_MAPPER);
    private static ReportMapper reportMapper = (ReportMapper) AppContext.getBean(PersistContext.REPORT_MAPPER);

    /**
     * SEC Idx laod
     *
     * @param start
     * @param limit
     * @return
     */
    public String secIdxLoad(String formType, String date, String start, String limit) {
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

        date = date.replaceAll("-", "");

        List<SecIdx> list = secIdxMapper.selectByPage(formTypes, date, l, o);
        for (SecIdx secIdx : list) {
            List<String> codes = cikMapper.selectCodeByCik(secIdx.getCik());
            String code = codes.toString().replace("]", "").replace("[", "");
            secIdx.setCode(code);
        }

        int totalCount = secIdxMapper.selectTotalCount(formTypes, date);

        ExtPagingData<SecIdx> items = new ExtPagingData<SecIdx>(true, totalCount, list);
        return new Gson().toJson(items);
    }

    /**
     * Idx idx laod
     *
     * @param siid
     * @return
     */
    public String secIdxHtmlLoad(String siid) {
        List<SecHtmlIdx> list = secHtmlIdxMapper.selectBySiid(siid);

        ExtPagingData<SecHtmlIdx> items = new ExtPagingData<SecHtmlIdx>(true, list.size(), list);
        return new Gson().toJson(items);
    }

    /**
     * SEC Idx form laod
     *
     * @param siid
     * @return
     */
    public String secIdxFormLoad(String siid) {
        List<SecForm> list = secFormMapper.selectBySiid(siid);
        ExtPagingData<SecForm> items = new ExtPagingData<SecForm>(true, list.size(), list);
        return new Gson().toJson(items);
    }

    /**
     * SEC Idx form state change
     *
     * @param id
     * @return
     */
    public String idxFormComplete(String id) {
        int i = 0;
        try {
            i = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            return new Gson().toJson(new ExtMessage(false, Lang.paramError));
        }

        // Update state
        SecForm secForm = secFormMapper.select(i);
        if (secForm == null) {
            ExtMessage message = new ExtMessage(false, Lang.operateFailure);
            return new Gson().toJson(message);
        }

        secForm.setState(1);
        secFormMapper.update(secForm);
        ExtMessage message = new ExtMessage(true, Lang.operateSuccess);
        return new Gson().toJson(message);
    }


    /**
     * Report type combo
     *
     * @return
     */
    public String reportTypeCombo() {
        List<Report> reports = reportMapper.select();
        List<ExtCombo> list = new ArrayList<ExtCombo>();
        list.add(new ExtCombo("ALL", "全部"));
        for (Report report : reports) {
            list.add(new ExtCombo(report));
        }
        return new Gson().toJson(list);
    }

    /**
     * Generate grid
     *
     * @return
     */
    public String generateReportGrid() {
        List<GridData> list = new ArrayList<GridData>();
        list.add(new GridData("Revenues", 744, 0, 0));
        list.add(new GridData("Cost of revenues", 762, 0, 0));
        list.add(new GridData("Gross loss", 18, 0, 0));

        ExtEditor extEditor = new ExtEditor("textfield", false);

        List<ExtColumnModle> columnModle = new ArrayList<ExtColumnModle>();
        ExtColumnModle extColumnModle1 = new ExtColumnModle("列名", "column", 30, 1, extEditor);
        columnModle.add(extColumnModle1);
        ExtColumnModle extColumnModle2 = new ExtColumnModle("2013", "y2013", 30, 1, extEditor);
        columnModle.add(extColumnModle2);
        ExtColumnModle extColumnModle3 = new ExtColumnModle("2012", "y2012", 30, 1, extEditor);
        columnModle.add(extColumnModle3);
        ExtColumnModle extColumnModle4 = new ExtColumnModle("2011", "y2011", 30, 1, extEditor);
        columnModle.add(extColumnModle4);

        List<ExtField> fieldsNames = new ArrayList<ExtField>();
        fieldsNames.add(new ExtField("column"));
        fieldsNames.add(new ExtField("y2013"));
        fieldsNames.add(new ExtField("y2012"));
        fieldsNames.add(new ExtField("y2011"));

        ExtGrid<GridData> grid = new ExtGrid<GridData> (list, columnModle, fieldsNames);
        return new Gson().toJson(grid);
    }
}
