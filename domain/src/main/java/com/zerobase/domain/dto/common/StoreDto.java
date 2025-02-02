package com.zerobase.domain.dto.common;

import com.zerobase.domain.entity.common.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    private LocalTime openTime;
    private LocalTime closeTime;
	private String operatingTime;


    public static StoreDto of(Store store) {
        return StoreDto.builder()
                .id(store.getId())
                .name(store.getName())
                .location(store.getLocation())
                .description(store.getDescription())
				.averageScore(store.getAverageScore())
				.openTime(store.getOpenTime())
				.closeTime(store.getCloseTime())
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

	public String getOperatingTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String open = openTime != null ? openTime.format(formatter) : "";
        String close = closeTime != null ? closeTime.format(formatter) : "";
        return open + "~" + close;
    }

}
