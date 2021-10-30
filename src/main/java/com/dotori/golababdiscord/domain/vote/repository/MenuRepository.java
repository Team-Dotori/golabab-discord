package com.dotori.golababdiscord.domain.vote.repository;

import com.dotori.golababdiscord.domain.vote.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> getAllByVoteMessageId(long voteMessageId);
}
