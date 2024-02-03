package com.skwzz.sample.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Builder
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SampleDto {

    private String name;

    @NotBlank
    private String requiredField;

    private int unlimitedRangeIntField;

    @Min(1)
    @Max(10)
    private int limitedRangeIntField;
}
