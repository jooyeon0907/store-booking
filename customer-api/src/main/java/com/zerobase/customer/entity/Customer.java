package com.zerobase.customer.entity;

import com.zerobase.libaray.entity.BaseUser;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseUser {
	@Column(unique = true)
	private String phone;

}
