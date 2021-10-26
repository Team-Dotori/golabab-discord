package com.dotori.golababdiscord.global.utils;

import com.dotori.golababdiscord.global.exception.DateParseFailureException;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DateUtilsImpl implements DateUtils{
    @Override
    public Date getToday() {
        Date now = new Date();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = transFormat.format(now);
        try {
            return transFormat.parse(date);
        } catch (ParseException e) {
            throw new DateParseFailureException();
        }
    }

    @Override
    public int getWeekToDay() {
        Calendar cDate = Calendar.getInstance();  // Calendar 클래스의 인스턴스 생성
        return cDate.get(Calendar.WEEK_OF_MONTH);
    }
}
