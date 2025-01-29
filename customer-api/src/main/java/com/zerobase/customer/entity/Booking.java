package com.zerobase.customer.entity;

import com.zerobase.owner.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(value = {AuditingEntityListener.class})
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreatedDate
	private LocalDateTime bookingDate;

    private LocalDateTime visitDate; //방문할 날짜
    private String bookingStatus; // 예약 상태 -> 예약 승인 전, 예약 승인, 예약 거부

	private boolean visitStatus; // 방문 여부

    @PrePersist
    public void prePersist() {
        this.visitStatus = false;  // 저장되기 전 false로 설정
    }

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "store_id")
	private Store store;


}
