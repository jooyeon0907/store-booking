package com.zerobase.domain.dto.common;

import com.zerobase.domain.entity.common.Review;
import com.zerobase.domain.entity.common.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
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

	long totalCount;

    private Double averageScore;

    public static StoreDto of(Store store) {
        return StoreDto.builder()
                .id(store.getId())
                .name(store.getName())
                .location(store.getLocation())
                .description(store.getDescription())
				.averageScore(store.getAverageScore())
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
