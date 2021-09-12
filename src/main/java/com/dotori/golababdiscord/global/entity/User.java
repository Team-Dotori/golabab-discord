package com.dotori.golababdiscord.global.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Getter @Setter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;
    private long id;
    @Length(max = 32)
    private String name;
    @Length(max = 30)
    private String email;
    @Column(name = "department_type")
    private String departmentType;
}
