package com.dotori.golababdiscord.domain.permission.enum_type;

import com.dotori.golababdiscord.domain.discord.dto.RoleDto;
import com.dotori.golababdiscord.domain.permission.exception.PermissionNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.Permission;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@RequiredArgsConstructor
public enum SogoPermission {
    STUDENT("학생", new RoleDto("학생", new Color(51, 217, 97)),
            Feature.GOLABAB_VOTE),
    OPERATOR("학생회", new RoleDto("학생회", new Color(226, 124, 35)),
            Feature.GOLABAB_VOTE, Feature.GOLABAB_MANAGE),
    ADMINISTRATOR("학생회장", new RoleDto("학생회장", new Color(252, 61, 61), Permission.ADMINISTRATOR),
            Feature.GOLABAB_VOTE, Feature.GOLABAB_MANAGE, Feature.USER_PROMOTION),
    DEVELOPER("개발팀", new RoleDto("골라밥-개발팀", new Color(168, 53, 250), Permission.ADMINISTRATOR),
            Feature.values());

    SogoPermission(String display, RoleDto role, Feature... features) {
        this(display, role);
        addFeatures(features);
    }

    private final List<Feature> features = new ArrayList<>();
    @Getter
    private final String display;
    @Getter
    private final RoleDto role;

    public void addFeature(Feature feature) {
        features.add(feature);
    }
    public void addFeatures(Feature... features) {
        this.features.addAll(Arrays.asList(features));
    }

    public boolean isHaveFeature(Feature feature) {
        return features.stream().anyMatch(element -> element.equals(feature));
    }

    public SogoPermission getFirstByFeature(Feature feature) {
        Optional<SogoPermission> result = Arrays.stream(values()).filter(permission -> permission.isHaveFeature(feature)).findFirst();
        if(result.isEmpty()) throw new PermissionNotFoundException(feature);
        return result.get();
    }
}
