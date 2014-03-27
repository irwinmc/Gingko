package org.gingko.server.handler.api.vo;

import java.util.List;

public class ExtPagingData<T> {

	private boolean success;
	private int count;
	private List<T> items;

	public ExtPagingData(boolean success, int count, List<T> items) {
		this.success = success;
		this.count = count;
		this.items = items;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}
}
