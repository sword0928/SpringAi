package com.asksword.ai.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum YesOrNoEnum {
    YES(1, "是"),
    NO(2, "否"),
    ;
    private final Integer code;
    private final String desc;
}
