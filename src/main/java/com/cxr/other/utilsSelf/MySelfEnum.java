package com.cxr.other.utilsSelf;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MySelfEnum {


    MONDAY(1, "Monday", "星期一"),
    FEBRUARY(2, "February", "星期二");


    private Integer code;
    private String source;
    private String desc;


    private static final Map<Integer, MySelfEnum> cache = new HashMap<>();


    static {
        for (MySelfEnum value : MySelfEnum.values()) {
            cache.put(value.code, value);
        }
    }

    public static String getDescByCodeWithLambda(Integer code) {
        return Optional.ofNullable(cache.get(code))
                .map(MySelfEnum::getDesc)
                .orElseThrow(() -> new IllegalArgumentException("invalid exception code!"));
    }


    public static String getDescByCode(Integer code) {
        if (code == null) {
            return null;
        }
        MySelfEnum[] values = MySelfEnum.values();
        for (MySelfEnum value : MySelfEnum.values()) {
            if (Objects.equals(value.getCode(), code)) {
                return value.getDesc();
            }
        }
        return null;
    }


    MySelfEnum(int code, String source, String desc) {
        this.code = code;
        this.source = source;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
