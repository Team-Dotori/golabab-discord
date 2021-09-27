package com.dotori.golababdiscord.domain.permission.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;
    private Long id;
    private String name;

    @Builder
    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
