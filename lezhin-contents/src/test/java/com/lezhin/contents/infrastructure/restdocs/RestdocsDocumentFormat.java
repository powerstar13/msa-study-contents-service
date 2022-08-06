package com.lezhin.contents.infrastructure.restdocs;

import com.lezhin.contents.domain.AdultOnly;
import com.lezhin.contents.domain.EvaluationType;
import com.lezhin.contents.domain.PricingType;
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
}
