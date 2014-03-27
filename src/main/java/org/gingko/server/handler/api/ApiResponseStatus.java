package org.gingko.server.handler.api;

public enum ApiResponseStatus {

	SUCCESS(-1),
	PARAM_ERROR(101),
	SIGN_FAILED(102),
	USER_LOGIN_ERROR(110);

	private final int value;
	
	ApiResponseStatus(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value + "";
	}
}
