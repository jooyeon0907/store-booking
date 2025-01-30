package com.zerobase.domain.entity.common;

import com.zerobase.domain.entity.owner.Owner;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(value = {AuditingEntityListener.class})
public class Store {
	@Id
	@Column(name ="id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(unique = true)
    private String name;
    private String location;
    private String description;

	@CreatedDate
    private LocalDateTime registrationDate;

	@OneToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;
}
