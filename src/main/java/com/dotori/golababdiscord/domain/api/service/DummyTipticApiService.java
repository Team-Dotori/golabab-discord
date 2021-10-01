package com.dotori.golababdiscord.domain.api.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//@Service
public class DummyTipticApiService implements TipticApiService{
    List<String> improveMessages;

    public DummyTipticApiService() {
        improveMessages = new ArrayList<>();
        improveMessages.add("이번달에 실험적으로 나왔던 회오리감자가 앞으로 주기적으로 나오게됩니다!");
        improveMessages.add("쇠고기 미역국이 나오는 주기가 늘어났습니다");
        improveMessages.add("양념치킨이 나오는 주기가 늘어났습니다");
        improveMessages.add("이번에 특식으로 나온 찐빵은 저희가 하나하나 빚은 찐빵입니다");
        improveMessages.add("아니요 모르겠는데요");
        improveMessages.add("영양사님은 항상 여러분에게 맛있는 급식을 제공하기위해 최선을 다하고 계십니다");
        improveMessages.add("기존에 수요일 아침에만 나오던 콘푸로스트가 일주일에 네번꼴로 나오게 되었습니다!");
    }

    @Override
    public String getImproveMessage() {
        return improveMessages.get(new Random().nextInt(improveMessages.size()));
    }
}
