package com.zerobase.customer.mapper;


import com.zerobase.domain.dto.common.StoreDto;
import com.zerobase.domain.model.common.StoreParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper // db 에 대한 쿼리 실행 가능
public interface StoreMapper {
	List<StoreDto> selectList(StoreParam parameter);

}
