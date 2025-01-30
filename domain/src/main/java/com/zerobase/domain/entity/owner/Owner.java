package com.zerobase.domain.entity.owner;

import com.zerobase.domain.entity.common.BaseUser;
import com.zerobase.domain.entity.common.Store;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Owner extends BaseUser {
	@OneToOne(mappedBy = "owner", cascade = CascadeType.ALL)
	private Store store;
}
