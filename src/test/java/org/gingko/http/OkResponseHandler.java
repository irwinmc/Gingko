package org.gingko.http;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class OkResponseHandler extends SimpleChannelInboundHandler<Object> {

	@Override
	public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		final DefaultHttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		response.headers().set("custom-response-header", "Some value");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
}
