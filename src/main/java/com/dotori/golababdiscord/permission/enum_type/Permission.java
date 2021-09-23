package com.dotori.golababdiscord.permission.enum_type;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
public enum Permission {
    STUDENT(Feature.GOLABAB_VOTE),
    OPERATOR(Feature.GOLABAB_VOTE, Feature.GOLABAB_MANAGE),
    ADMINISTRATOR(Feature.GOLABAB_VOTE, Feature.GOLABAB_MANAGE, Feature.USER_PROMOTION),
    DEVELOPER(Feature.values());

    Permission(Feature... features) {
        this();
        addFeatures(features);
    }

    private final List<Feature> features = new ArrayList<>();

    public void addFeature(Feature feature) {
        features.add(feature);
    }

    public void addFeatures(Feature... features) {
        this.features.addAll(Arrays.asList(features));
    }

    public boolean isHaveFeature(Feature feature) {
        return features.stream().anyMatch(element -> element.equals(feature));
    }
}
