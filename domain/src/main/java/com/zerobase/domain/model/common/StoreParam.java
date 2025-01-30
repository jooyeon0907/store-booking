package com.zerobase.domain.model.common;

import lombok.Data;

@Data
public class StoreParam {
	private Long owner_id;
	private Long id;
    private String name;
    private String location;
    private String description;
}
