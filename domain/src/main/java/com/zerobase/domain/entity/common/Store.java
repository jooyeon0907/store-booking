package com.zerobase.domain.entity.common;

import com.zerobase.domain.entity.owner.Owner;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.zerobase.domain.entity.common.BookingStatus.PENDING;


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

    private Double averageScore;

    private LocalTime openTime;
    private LocalTime closeTime;

	@PrePersist
    public void prePersist() {
        this.averageScore = 0.0;
    }


	@CreatedDate
    private LocalDateTime registrationDate;

	@OneToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;
}
