package com.zerobase.domain.model.common;

import lombok.Data;

@Data
public class StoreParam {
	private Long ownerId;
	private Long id;
    private String name;
    private String location;
    private String description;

	private String sort;



	long pageIndex;
	long pageSize;

	/*
	limit 0, 10 --> pageIndex : 1
	limit 10, 10 --> pageIndex : 2
	limit 20, 10 --> pageIndex : 3
	limit 30, 10 --> pageIndex : 4
	 */
	public long getPageStart() {
		init();
		return (pageIndex - 1) * pageSize;
	}

	public long getPageEnd() {
		init();
		return pageSize;
	}

	public void init() {
		if (pageIndex < 1) {
			pageIndex = 1;
		}

		if (pageSize < 10) {
			pageSize = 10;
		}

	}
	
	public String getQueryString() {
		init();

		StringBuilder sb = new StringBuilder();

		if (name != null && name.length() > 0){
			sb.append(String.format("name=%s", name));
		}

		return sb.toString();
	}

}
