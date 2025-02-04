package com.zerobase.domain.entity.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
public class BaseUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String name;

	private String password;

	@CreatedDate
	private LocalDateTime registrationDate;

	public BaseUser() {
    }
}
