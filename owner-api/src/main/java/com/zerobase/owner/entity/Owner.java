package com.zerobase.owner.entity;

import com.zerobase.libaray.entity.BaseUser;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class Owner extends BaseUser {
}
