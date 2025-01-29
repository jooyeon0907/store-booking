package com.zerobase.owner.dto;

import com.zerobase.owner.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDto {
    private Long id;

    private String name;
    private String location;
    private String description;

    public static StoreDto of(Store store) {
        return StoreDto.builder()
                .id(store.getId())
                .name(store.getName())
                .location(store.getLocation())
                .description(store.getDescription())
                .build();
    }

    public static List<StoreDto> of(List<Store> stores) { // use customer-api
		if (stores != null) {
			List<StoreDto> storeList = new ArrayList<>();
			for(Store s : stores) {
				storeList.add(of(s));
			}
			return storeList;
		}

		return null;
	}
}
