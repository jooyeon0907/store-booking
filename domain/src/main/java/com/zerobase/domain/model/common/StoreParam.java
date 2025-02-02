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
}
