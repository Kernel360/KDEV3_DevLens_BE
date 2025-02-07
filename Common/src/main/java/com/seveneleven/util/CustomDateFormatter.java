package com.seveneleven.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomDateFormatter {

    public static String formatDate(LocalDate date) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter;

        // 날짜의 연도가 현재 연도와 같으면 "M.d" 포맷, 아니면 "yyyy.M.d" 포맷 사용
        if (date.getYear() == today.getYear()) {
            formatter = DateTimeFormatter.ofPattern("M.d");
        } else {
            formatter = DateTimeFormatter.ofPattern("yyyy.M.d");
        }

        return date.format(formatter);
    }
}
