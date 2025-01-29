package com.zerobase.owner.repository.model;

import lombok.Data;

@Data
public class StoreParam {
	private Long owner_id;
	private Long id;
    private String name;
    private String location;
    private String description;
}
