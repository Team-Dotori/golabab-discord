package com.dotori.golababdiscord.domain.vote.entity;

import lombok.*;

import javax.persistence.*;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@NoArgsConstructor
@Getter @Setter
@Entity
@ToString
public class Menu {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;
    private String menuName;
    private long voteMessageId;

    public Menu(String menuName, long voteMessageId) {
        this.menuName = menuName;
        this.voteMessageId = voteMessageId;
    }
}
