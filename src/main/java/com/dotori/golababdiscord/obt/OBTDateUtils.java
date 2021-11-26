package com.dotori.golababdiscord.obt;

import com.dotori.golababdiscord.global.utils.DateUtils;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

//@Component
public class OBTDateUtils implements DateUtils {
    Date now = new Date(0);
    @Override
    public Date getToday() {
        return now;
    }

    public void nextDay() {
        Calendar cDate = Calendar.getInstance();  // Calendar 클래스의 인스턴스 생성
        cDate.setTime(now);
        cDate.add(Calendar.DATE, 1);
        now = cDate.getTime();
    }

    @Override
    public int getWeekToDay() {
        Calendar cDate = Calendar.getInstance();  // Calendar 클래스의 인스턴스 생성
        return cDate.get(Calendar.WEEK_OF_MONTH);
    }
}
