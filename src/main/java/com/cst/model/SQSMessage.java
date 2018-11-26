package com.cst.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode
public class SQSMessage {
    private String name;
    private Integer totalPoints;
}
