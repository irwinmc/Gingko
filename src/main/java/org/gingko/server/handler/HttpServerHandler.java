package org.gingko.server.handler;

import com.google.gson.Gson;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.gingko.config.Lang;
import org.gingko.server.handler.api.Api;
import org.gingko.server.handler.api.actions.*;
import org.gingko.server.handler.api.vo.ExtMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static io.netty.handler.codec.http.HttpMethod.GET;

/**
 * @author Kyia
 */
@Sharable
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	private static final Logger LOG = LoggerFactory.getLogger(HttpServerHandler.class);

    private HttpStaticFileHandler staticFileHandler = new HttpStaticFileHandler();

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if (request.getMethod() != GET) {
			sendError(ctx, FORBIDDEN);
			return;
        }

        QueryStringDecoder decoder = new QueryStringDecoder(request.getUri());
        String path = decoder.path();
        Map<String, List<String>> parameters = decoder.parameters();

        if (!path.contains("action")) {
            staticFileHandler.handleStaticFile(ctx, request);
            return;
        }

        // Action request
        String actionUri = getActionUri(path);
        actionUri = actionUri.replaceAll("/", "").replaceAll("action", "");
        if (actionUri.contains("_")) {
            handleActionApi(ctx, parameters, actionUri);
            return;
        }

        // Get the parameters...
		sendError(ctx, FORBIDDEN);
	}

    /**
     * Action request
     *
     * @param ctx
     * @param parameters
     * @param action
     */
    protected void handleActionApi(ChannelHandlerContext ctx, Map<String, List<String>> parameters, String action) {
        // User Action start -----------------------------------------------------------------------------------
        if (action.equals(Api.USER_LOGIN)) {
            if (parameters.get("account") != null
                    && parameters.get("password") != null) {
                String account = parameters.get("account").get(0);
                String password = parameters.get("password").get(0);

                String json = UserAction.INSTANCE.login(account, password);
                sendJsonResponse(ctx, json);
                return;
            }
        }

        else if (action.equals(Api.USER_LOAD)) {
            if (parameters.get("start") != null
                    && parameters.get("limit") != null) {
                String start = parameters.get("start").get(0);
                String limit = parameters.get("limit").get(0);

                String json = UserAction.INSTANCE.load(start, limit);
                sendJsonResponse(ctx, json);
                return;
            }
        }

        else if (action.equals(Api.USER_ADD)) {
            if (parameters.get("account") != null
                    && parameters.get("password") != null
                    && parameters.get("name") != null
                    && parameters.get("identity") != null
                    && parameters.get("groupId") != null) {
                String account = parameters.get("account").get(0);
                String password = parameters.get("password").get(0);
                String name = parameters.get("name").get(0);
                String identity = parameters.get("identity").get(0);
                String groupId = parameters.get("groupId").get(0);

                String json = UserAction.INSTANCE.add(account, password, name, identity, groupId);
                sendJsonResponse(ctx, json);
                return;
            }
        }

        else if (action.equals(Api.USER_EDIT)) {
            if (parameters.get("account") != null
                    && parameters.get("password") != null
                    && parameters.get("name") != null
                    && parameters.get("identity") != null
                    && parameters.get("groupId") != null) {
                String account = parameters.get("account").get(0);
                String password = parameters.get("password").get(0);
                String name = parameters.get("name").get(0);
                String identity = parameters.get("identity").get(0);
                String groupId = parameters.get("groupId").get(0);

                String json = UserAction.INSTANCE.edit(account, password, name, identity, groupId);
                sendJsonResponse(ctx, json);
                return;
            }
        }

        else if (action.equals(Api.USER_DELETE)) {
            if (parameters.get("account") != null) {
                String account = parameters.get("account").get(0);

                String json = UserAction.INSTANCE.deleteByAccount(account);
                sendJsonResponse(ctx, json);
                return;
            }
        }
        // User Action end ----------------------------------------------------------------------------------------

        // Menu Action start --------------------------------------------------------------------------------------
        else if (action.equals(Api.MENU_LOAD)) {
            if (parameters.get("menuId") != null
                    && parameters.get("identity") != null) {
                String menuId = parameters.get("menuId").get(0);
                String identity = parameters.get("identity").get(0);

                String json = MenuAction.INSTANCE.loadMenu(menuId, identity);
                sendJsonResponse(ctx, json);
                return;
            }
        }

        else if (action.equals(Api.MENU_TREE_LOAD)) {
            if (parameters.get("parentId") != null
                    && parameters.get("identity") != null) {
                String parentId = parameters.get("parentId").get(0);
                String identity = parameters.get("identity").get(0);

                String json = MenuAction.INSTANCE.load(parentId, identity);
                sendJsonResponse(ctx, json);
                return;
            }
        }

        else if (action.equals(Api.MENU_BY_IDENTITY)) {
            if (parameters.get("identity") != null) {
                String identity = parameters.get("identity").get(0);

                String json = MenuAction.INSTANCE.loadAll(identity);
                sendJsonResponse(ctx, json);
                return;
            }
        }

        else if (action.equals(Api.MENU_COMBO)) {
            String json = MenuAction.INSTANCE.combo();
            sendJsonResponse(ctx, json);
            return;
        }
        // Menu Action end ----------------------------------------------------------------------------------------

        // Report Action start ------------------------------------------------------------------------------------
        else if (action.equals(Api.REPORT_IDX_LOAD)) {
            if (parameters.get("start") != null
                    && parameters.get("limit") != null) {
                String start = parameters.get("start").get(0);
                String limit = parameters.get("limit").get(0);
                String formType = parameters.get("formType") != null ? parameters.get("formType").get(0) : "ALL";
                String date = parameters.get("date") != null ? parameters.get("date").get(0) : "";

                String json = ReportAction.INSTANCE.secIdxLoad(formType, date, start, limit);
                sendJsonResponse(ctx, json);
                return;
            }
        }

        else if (action.equals(Api.REPORT_HTML_LOAD)) {
            if (parameters.get("siid") != null) {
                String siid = parameters.get("siid").get(0);

                String json = ReportAction.INSTANCE.secIdxHtmlLoad(siid);
                sendJsonResponse(ctx, json);
                return;
            }
        }

        else if (action.equals(Api.REPORT_FORM_LOAD)) {
            if (parameters.get("siid") != null) {
                String siid = parameters.get("siid").get(0);

                String json = ReportAction.INSTANCE.secIdxFormLoad(siid);
                sendJsonResponse(ctx, json);
                return;
            }
        }

        else if (action.equals(Api.REPORT_STATE_CHANGE)) {
            if (parameters.get("id") != null) {
                String id = parameters.get("id").get(0);

                String json = ReportAction.INSTANCE.idxFormComplete(id);
                sendJsonResponse(ctx, json);
                return;
            }
        }

        else if (action.equals(Api.REPORT_TYPE_COMBO)) {
            String json = ReportAction.INSTANCE.reportTypeCombo();
            sendJsonResponse(ctx, json);
            return;
        }

        else if (action.equals(Api.REPORT_GRID_GENERATE)) {
            String json = ReportAction.INSTANCE.generateReportGrid();
            sendJsonResponse(ctx, json);
            return;
        }
        // Report Action end ----------------------------------------------------------------------------------------

        // Setting Action start --------------------------------------------------------------------------------------
        else if (action.equals(Api.SET_FORM_TYPE_LOAD)) {
            if (parameters.get("groupId") != null) {
                String groupId = parameters.get("groupId").get(0);
                String json = SettingAction.INSTANCE.formTypeLoad(groupId);
                sendJsonResponse(ctx, json);
                return;
            }
        }

        else if (action.equals(Api.SET_FORM_TYPE_COMBO)) {
            if (parameters.get("groupId") != null) {
                String groupId = parameters.get("groupId").get(0);

                String json = SettingAction.INSTANCE.formTypeCombo(groupId);
                sendJsonResponse(ctx, json);
                return;
            }
        }

        else if (action.equals(Api.SET_FORM_TYPE_ADD)) {
            if (parameters.get("formType") != null
                    && parameters.get("groupId") != null) {
                String formType = parameters.get("formType").get(0);
                String groupId = parameters.get("groupId").get(0);

                String json = SettingAction.INSTANCE.formTypeAdd(formType, groupId);
                sendJsonResponse(ctx, json);
                return;
            }
        }

        else if (action.equals(Api.SET_FORM_TYPE_DELETE)) {
            if (parameters.get("id") != null) {
                String id = parameters.get("id").get(0);

                String json = SettingAction.INSTANCE.formTypeDelete(id);
                sendJsonResponse(ctx, json);
                return;
            }
        }

        else if (action.equals(Api.SET_GROUP_LOAD)) {
            String json = SettingAction.INSTANCE.groupLoad();
            sendJsonResponse(ctx, json);
            return;
        }

        else if (action.equals(Api.SET_GROUP_COMBO)) {
            String json = SettingAction.INSTANCE.groupCombo();
            sendJsonResponse(ctx, json);
            return;
        }

        else if (action.equals(Api.SET_GROUP_ADD)) {
            if (parameters.get("name") != null
                    && parameters.get("host") != null) {
                String name = parameters.get("name").get(0);
                String host = parameters.get("host").get(0);

                String json = SettingAction.INSTANCE.groupAdd(name, host);
                sendJsonResponse(ctx, json);
                return;
            }
        }

        else if (action.equals(Api.SET_GROUP_DELETE)) {
            if (parameters.get("groupId") != null) {
                String groupId = parameters.get("groupId").get(0);

                String json = SettingAction.INSTANCE.groupDelete(groupId);
                sendJsonResponse(ctx, json);
                return;
            }
        }

        else if (action.equals(Api.SET_IDENTITY_MENU_LOAD)) {
            if (parameters.get("start") != null
                    && parameters.get("limit") != null) {
                String start = parameters.get("start").get(0);
                String limit = parameters.get("limit").get(0);
                String identity = parameters.get("identity") != null ? parameters.get("identity").get(0) : null;

                String json = SettingAction.INSTANCE.identityMenuLoad(identity, start, limit);
                sendJsonResponse(ctx, json);
                return;
            }
        }

        else if (action.equals(Api.SET_IDENTITY_MENU_ADD)) {
            if (parameters.get("identity") != null
                    && parameters.get("menuId") != null) {
                String identity = parameters.get("identity").get(0);
                String menuId = parameters.get("menuId").get(0);

                String json = SettingAction.INSTANCE.identityMenuAdd(identity, menuId);
                sendJsonResponse(ctx, json);
                return;
            }
        }

        else if (action.equals(Api.SET_IDENTITY_MENU_DELETE)) {
            if (parameters.get("id") != null) {
                String id = parameters.get("id").get(0);

                String json = SettingAction.INSTANCE.identityMenudelete(id);
                sendJsonResponse(ctx, json);
                return;
            }
        }
        // Identity Action end ----------------------------------------------------------------------------------------
        // Setting Action end --------------------------------------------------------------------------------------

        sendJsonResponse(ctx, new Gson().toJson(new ExtMessage(false, Lang.paramError)));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

    /**
     * Send json data
     *
     * @param ctx
     * @param json
     */
    protected void sendJsonResponse(ChannelHandlerContext ctx, String json) {
        // Create http response
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK);
        response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
        response.content().writeBytes((Unpooled.copiedBuffer(json, CharsetUtil.UTF_8)));

        // Close the connection as soon as the error message is sent
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

	private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, status, Unpooled.copiedBuffer("Failure: " + status.toString() + "\r\n", CharsetUtil.UTF_8));
		response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");

		// Close the connection as soon as the error message is sent.
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

    /**
     * Get action URL
     *
     * @param uri
     * @return
     */
    protected String getActionUri(String uri) {
        String baseUri = getBaseUri(uri);
        return uri.substring(uri.indexOf(baseUri) + baseUri.length());
    }

    /**
     * Get base URL
     *
     * @param uri
     * @return
     */
    protected String getBaseUri(String uri) {
        int idx = uri.indexOf("/", 1);
        if (idx == -1) {
            return "/";
        } else {
            return uri.substring(0, idx);
        }
    }
}
