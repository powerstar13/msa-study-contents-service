package com.lezhin.contents.presentation.request;

import com.lezhin.contents.domain.PricingType;
import com.lezhin.contents.infrastructure.exception.status.BadRequestException;
import com.lezhin.contents.infrastructure.exception.status.ExceptionMessage;
import com.lezhin.contents.presentation.shared.request.RequestVerify;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PricingModifyRequest implements RequestVerify {

    private String contentsToken; // 작품 대체 식별키
    private PricingType pricingType; // 가격 유형
    private Integer coin; // 금액

    @Override
    public void verify() {
        if (StringUtils.isBlank(contentsToken)) throw new BadRequestException(ExceptionMessage.IsRequiredContentsToken.getMessage());
        if (pricingType == null) throw new BadRequestException(ExceptionMessage.IsRequiredPricingType.getMessage());
        if (pricingType.equals(PricingType.PAY) && (coin == null || coin <= 0)) throw new BadRequestException(ExceptionMessage.IsRequiredCoin.getMessage());
    }

    @Override
    public String toString() {
        return "{"
            + "\"contentsToken\":\"" + contentsToken + "\""
            + ", \"pricingType\":\"" + pricingType + "\""
            + ", \"coin\":" + coin
            + "}";
    }
}
