package com.zerobase.customer.mapper;


import com.zerobase.domain.entity.common.Review;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReviewMapper {
     @Select("SELECT r.* FROM review r " +
            "JOIN booking b ON r.booking_id = b.id " +
            "WHERE b.store_id = #{storeId}")
    List<Review> selectReviewsByStoreId(Long storeId);
}
