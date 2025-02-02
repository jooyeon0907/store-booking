package com.zerobase.owner.repository.model;

import lombok.Data;

import java.time.LocalTime;

@Data
public class StoreInput {
	private Long owner_id;
	private Long id;
    private String name;
    private String location;
    private String description;
    private LocalTime openTime;
    private LocalTime closeTime;
}
