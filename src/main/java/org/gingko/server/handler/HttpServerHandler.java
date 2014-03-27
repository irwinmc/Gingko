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

    private static final String USER_ACTION = "/user";			// USER ACTION
    private static final String IDENTITY_ACTION = "/identity";	// IDENTITY ACTION
    private static final String MENU_ACTION = "/menu";			// MENU ACTION
    private static final String REPORT_ACTION = "/report";	    // REPORT ACTION
    private static final String SETTING_ACTION = "/setting";	    // SETTING ACTION

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

        String actionUri = getActionUri(path);
        String[] array = actionUri.split("_");

        // user request
        if (array[0].equals(USER_ACTION)) {
            handleUserApi(ctx, parameters, array[1]);
            return;
        }

        // user request
        if (array[0].equals(IDENTITY_ACTION)) {
            handleIdentityApi(ctx, parameters, array[1]);
            return;
        }

        // menu request
        if (array[0].equals(MENU_ACTION)) {
            handleMenuApi(ctx, parameters, array[1]);
            return;
        }

        // report request
        if (array[0].equals(REPORT_ACTION)) {
            handleReportApi(ctx, parameters, array[1]);
            return;
        }

        // report request
        if (array[0].equals(SETTING_ACTION)) {
            handleSettingApi(ctx, parameters, array[1]);
            return;
        }

        // Get the parameters...
		sendError(ctx, FORBIDDEN);
	}

    /**
     * Deal user request
     *
     * @param ctx
     * @param parameters
     * @param function
     */
    protected void handleUserApi(ChannelHandlerContext ctx, Map<String, List<String>> parameters, String function) {
        switch(function) {
        case Api.USER_LOGIN: {
            if (parameters.get("account") != null
                    && parameters.get("password") != null) {
                String account = parameters.get("account").get(0);
                String password = parameters.get("password").get(0);

                String json = UserAction.INSTANCE.login(account, password);
                sendJsonResponse(ctx, json);
                return;
            }
            break;
        }
        case Api.USER_LOAD: {
            if (parameters.get("start") != null
                    && parameters.get("limit") != null) {
                String start = parameters.get("start").get(0);
                String limit = parameters.get("limit").get(0);

                String json = UserAction.INSTANCE.load(start, limit);
                sendJsonResponse(ctx, json);
                return;
            }
            break;
        }
        case Api.USER_ADD: {
            if (parameters.get("account") != null
                    && parameters.get("password") != null
                    && parameters.get("name") != null
                    && parameters.get("identity") != null) {
                String account = parameters.get("account").get(0);
                String password = parameters.get("password").get(0);
                String name = parameters.get("name").get(0);
                String identity = parameters.get("identity").get(0);


                String json = UserAction.INSTANCE.add(account, password, name, identity);
                sendJsonResponse(ctx, json);
                return;
            }
            break;
        }
        case Api.USER_EDIT: {
            if (parameters.get("account") != null
                    && parameters.get("password") != null
                    && parameters.get("name") != null
                    && parameters.get("identity") != null) {
                String account = parameters.get("account").get(0);
                String password = parameters.get("password").get(0);
                String name = parameters.get("name").get(0);
                String identity = parameters.get("identity").get(0);

                String json = UserAction.INSTANCE.edit(account, password, name, identity);
                sendJsonResponse(ctx, json);
                return;
            }
            break;
        }
        case Api.USER_DELETE: {
            if (parameters.get("account") != null) {
                String account = parameters.get("account").get(0);

                String json = UserAction.INSTANCE.deleteByAccount(account);
                sendJsonResponse(ctx, json);
                return;
            }
            break;
        }
        default:
            break;
        }

        sendJsonResponse(ctx, new Gson().toJson(new ExtMessage(false, Lang.paramError)));
    }

    /**
     * Deal menu request
     *
     * @param ctx
     * @param parameters
     * @param function
     */
    protected void handleMenuApi(ChannelHandlerContext ctx, Map<String, List<String>> parameters, String function) {
        switch(function) {
            case Api.MENU_LOAD: {
                if (parameters.get("parentId") != null
                        && parameters.get("identity") != null) {
                    String parentId = parameters.get("parentId").get(0);
                    String identity = parameters.get("identity").get(0);

                    String json = MenuAction.INSTANCE.load(parentId, identity);
                    sendJsonResponse(ctx, json);
                    return;
                }
                break;
            }
            case Api.MENU_LOAD_ALL: {
                if (parameters.get("identity") != null) {
                    String identity = parameters.get("identity").get(0);

                    String json = MenuAction.INSTANCE.loadAll(identity);
                    sendJsonResponse(ctx, json);
                    return;
                }
                break;
            }
            case Api.MENU_COMBO: {
                String json = MenuAction.INSTANCE.combo();
                sendJsonResponse(ctx, json);
                return;
            }
            default:
                break;
        }

        sendJsonResponse(ctx, new Gson().toJson(new ExtMessage(false, Lang.paramError)));
    }

    /**
     * Deal report request
     *
     * @param ctx
     * @param parameters
     * @param function
     */
    protected void handleReportApi(ChannelHandlerContext ctx, Map<String, List<String>> parameters, String function) {
        switch(function) {
            case Api.LOAD_IDX: {
                if (parameters.get("start") != null
                        && parameters.get("limit") != null) {
                    String start = parameters.get("start").get(0);
                    String limit = parameters.get("limit").get(0);
                    String formType = parameters.get("formType") != null ? parameters.get("formType").get(0) : "ALL";
                    String state = parameters.get("state") != null ? parameters.get("state").get(0) : "-1";
                    String date = parameters.get("date") != null ? parameters.get("date").get(0) : "";

                    String json = ReportAction.INSTANCE.loadIdx(formType, state, date, start, limit);
                    sendJsonResponse(ctx, json);
                    return;
                }
                break;
            }
            case Api.LOAD_IDX_REPORT: {
                if (parameters.get("siid") != null) {
                    String siid = parameters.get("siid").get(0);

                    String json = ReportAction.INSTANCE.loadIdxReport(siid);
                    sendJsonResponse(ctx, json);
                    return;
                }
                break;
            }
            case Api.OPERATE: {
                if (parameters.get("siid") != null
                        && parameters.get("state") != null) {
                    String siid = parameters.get("siid").get(0);
                    String state = parameters.get("state").get(0);

                    String json = ReportAction.INSTANCE.operate(siid, state);
                    sendJsonResponse(ctx, json);
                    return;
                }
                break;
            }
            default:
                break;
        }

        sendJsonResponse(ctx, new Gson().toJson(new ExtMessage(false, Lang.paramError)));
    }

    /**
     * Deal identity request
     *
     * @param ctx
     * @param parameters
     * @param function
     */
    protected void handleIdentityApi(ChannelHandlerContext ctx, Map<String, List<String>> parameters, String function) {
        switch(function) {
            case Api.IDENTITY_LOAD: {
                if (parameters.get("start") != null
                        && parameters.get("limit") != null) {
                    String start = parameters.get("start").get(0);
                    String limit = parameters.get("limit").get(0);
                    String identity = parameters.get("identity") != null ? parameters.get("identity").get(0) : null;

                    String json = IdentityAction.INSTANCE.load(identity, start, limit);
                    sendJsonResponse(ctx, json);
                    return;
                }
                break;
            }
            case Api.IDENTITY_ADD: {
                if (parameters.get("identity") != null
                        && parameters.get("menuId") != null) {
                    String identity = parameters.get("identity").get(0);
                    String menuId = parameters.get("menuId").get(0);

                    String json = IdentityAction.INSTANCE.add(identity, menuId);
                    sendJsonResponse(ctx, json);
                    return;
                }
                break;
            }
            case Api.IDENTITY_DELETE: {
                if (parameters.get("id") != null) {
                    String id = parameters.get("id").get(0);

                    String json = IdentityAction.INSTANCE.deleteById(id);
                    sendJsonResponse(ctx, json);
                    return;
                }
                break;
            }
            default:
                break;
        }

        sendJsonResponse(ctx, new Gson().toJson(new ExtMessage(false, Lang.paramError)));
    }

    /**
     * Deal setting request
     *
     * @param ctx
     * @param parameters
     * @param function
     */
    protected void handleSettingApi(ChannelHandlerContext ctx, Map<String, List<String>> parameters, String function) {
        switch(function) {
            case Api.SET_LOAD_FORM_TYPE: {
                String json = SettingAction.INSTANCE.loadFormType();
                sendJsonResponse(ctx, json);
                return;
            }
            case Api.SET_COMBO_FORM_TYPE: {
                String json = SettingAction.INSTANCE.comboType();
                sendJsonResponse(ctx, json);
                return;
            }
            case Api.SET_ADD_FORM_TYPE: {
                if (parameters.get("formType") != null) {
                    String formType = parameters.get("formType").get(0);

                    String json = SettingAction.INSTANCE.addFormType(formType);
                    sendJsonResponse(ctx, json);
                    return;
                }
                break;
            }
            case Api.SET_DELETE_FORM_TYPE: {
                if (parameters.get("formType") != null) {
                    String formType = parameters.get("formType").get(0);

                    String json = SettingAction.INSTANCE.deleteFormType(formType);
                    sendJsonResponse(ctx, json);
                    return;
                }
                break;
            }
            case Api.SET_USE_FORM_TYPE: {
                if (parameters.get("formType") != null
                        && parameters.get("used") != null) {
                    String formType = parameters.get("formType").get(0);
                    String used = parameters.get("used").get(0);

                    String json = SettingAction.INSTANCE.useFormType(formType, used);
                    sendJsonResponse(ctx, json);
                    return;
                }
                break;
            }
            case "test": {
                String json = SettingAction.INSTANCE.test();
                sendJsonResponse(ctx, json);
                return;
            }
            default:
                break;
        }

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
