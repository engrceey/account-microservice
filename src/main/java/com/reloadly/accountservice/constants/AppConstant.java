package com.reloadly.accountservice.constants;

import lombok.Getter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AppConstant {

    public interface DateFormatters {
        public SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    }


    @Getter
    public enum Status {
        SUCCESSFUL("00"),
        ERROR("06"),
        REQUEST_IN_PROGRESS("09"),
        INSUFFICIENT_FUNDS("51"),
        SYSTEM_ERROR("96"),
        NOT_FOUND("56"),
        FORMAT_ERROR("30");

        private final String code;

        Status(String code) {
            this.code = code;
        }

    }
}