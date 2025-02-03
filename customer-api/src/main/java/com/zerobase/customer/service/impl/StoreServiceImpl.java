package com.zerobase.customer.service.impl;

import com.zerobase.customer.mapper.StoreMapper;
import com.zerobase.customer.repository.StoreRepository;
import com.zerobase.customer.service.StoreService;
import com.zerobase.domain.dto.common.StoreDto;
import com.zerobase.domain.model.common.StoreParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

	private final StoreRepository storeRepository;
	private final StoreMapper storeMapper;

	@Override
	public List<StoreDto> list(StoreParam parameter) {
		// 페이징 처리에 사용할 복잡한 쿼리문을 작성하기 위해 mybatis mapper 사용
		long totalCount = storeMapper.selectListCount(parameter);
		List<StoreDto> list = storeMapper.selectList(parameter);


		if (!CollectionUtils.isEmpty(list)) {
			for(StoreDto store: list) {
				store.setTotalCount(totalCount);
			}
		}

		return list;
	}

	@Override
	public StoreDto detail(Long id) {
		return StoreDto.of(storeRepository.findById(id).get());
	}

}
