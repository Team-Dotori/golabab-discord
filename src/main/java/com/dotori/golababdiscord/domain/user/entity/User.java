package com.dotori.golababdiscord.domain.user.entity;

import com.dotori.golababdiscord.domain.authorize.enum_type.DepartmentType;
import com.dotori.golababdiscord.domain.permission.enum_type.SogoPermission;
import com.dotori.golababdiscord.domain.user.dto.UserDto;
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
    @Column(name = "department_type") @Enumerated(value = EnumType.STRING)
    private DepartmentType departmentType;
    @Column @Enumerated(value = EnumType.STRING)
    private SogoPermission permission;

    public UserDto toDto() {
        return new UserDto(id, name, email, departmentType, permission);
    }
}
