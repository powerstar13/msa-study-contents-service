package com.webtoon.contents.infrastructure.restdocs;

import com.webtoon.contents.domain.AdultOnly;
import com.webtoon.contents.domain.EvaluationType;
import com.webtoon.contents.domain.PricingType;
import org.springframework.restdocs.snippet.Attributes;

import static org.springframework.restdocs.snippet.Attributes.key;

public class RestdocsDocumentFormat {
    
    public static Attributes.Attribute setFormat(String value) {
        return key("format").value(value);
    }

    public static Attributes.Attribute pricingTypeFormat() {
        return setFormat(String.format(
            "%s: %s, " +
                "%s: %s",
            PricingType.FREE.name(), PricingType.FREE.getDescription(),
            PricingType.PAY.name(), PricingType.PAY.getDescription()
        ));
    }

    public static Attributes.Attribute adultOnlyFormat() {
        return setFormat(String.format(
            "%s: %s, " +
                "%s: %s",
            AdultOnly.Y.name(), AdultOnly.Y.getDescription(),
            AdultOnly.N.name(), AdultOnly.N.getDescription()
        ));
    }

    public static Attributes.Attribute evaluationTypeFormat() {
        return setFormat(String.format(
            "%s: %s, " +
                "%s: %s",
            EvaluationType.LIKE.name(), EvaluationType.LIKE.getDescription(),
            EvaluationType.DISLIKE.name(), EvaluationType.DISLIKE.getDescription()
        ));
    }

    public static Attributes.Attribute commentFormat() {
        return setFormat("댓글에 특수문자는 불가");
    }

    public static Attributes.Attribute yyyyMMddFormat() {
        return setFormat("yyyy-MM-dd");
    }

    public static Attributes.Attribute coinFormat() {
        return setFormat("유료의 경우 필수");
    }
}
