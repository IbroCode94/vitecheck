package com.vitmedics.vitcheck.dto.model.survey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class TailoredResponseDto {
    private String information;
    private String evidence;
}
