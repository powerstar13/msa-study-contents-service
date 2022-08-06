package com.lezhin.member.infrastructure.restdocs;

import com.lezhin.member.domain.MemberGender;
import com.lezhin.member.domain.MemberType;
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
}
