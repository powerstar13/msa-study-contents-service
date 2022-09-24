package com.webtoon.history.infrastructure.restdocs;

import com.webtoon.history.domain.AdultOnly;
import com.webtoon.history.domain.MemberGender;
import com.webtoon.history.domain.MemberType;
import org.springframework.restdocs.snippet.Attributes;

import static org.springframework.restdocs.snippet.Attributes.key;

public class RestdocsDocumentFormat {
    
    public static Attributes.Attribute setFormat(String value) {
        return key("format").value(value);
    }

    public static Attributes.Attribute memberTypeFormat() {
        return setFormat(String.format(
            "%s: %s, " +
                "%s: %s, " +
                "%s: %s",
            MemberType.ADMIN.name(), MemberType.ADMIN.getDescription(),
            MemberType.NORMAL.name(), MemberType.NORMAL.getDescription(),
            MemberType.ADULT.name(), MemberType.ADULT.getDescription()
        ));
    }

    public static Attributes.Attribute memberGenderFormat() {
        return setFormat(String.format(
            "%s: %s, " +
                "%s: %s",
            MemberGender.M.name(), MemberGender.M.getDescription(),
            MemberGender.W.name(), MemberGender.W.getDescription()
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

    public static Attributes.Attribute yyyyMMddHHmmssFormat() {
        return setFormat("yyyy-MM-dd HH:mm:ss");
    }
}
