package com.seveneleven.devlens.global.entity.converter;

import com.seveneleven.devlens.global.entity.YesNo;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class YesNoConverter implements AttributeConverter<YesNo, String> {
    @Override
    public String convertToDatabaseColumn(YesNo yesNo) {
        return yesNo == null ? null : yesNo.getValue();
    }

    @Override
    public YesNo convertToEntityAttribute(String s) {
        return "Y".equals(s) ? YesNo.YES : YesNo.NO;
    }
}
