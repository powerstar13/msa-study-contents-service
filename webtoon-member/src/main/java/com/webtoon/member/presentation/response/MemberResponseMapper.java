package com.webtoon.member.presentation.response;

import com.webtoon.member.domain.dto.MemberDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MemberResponseMapper {

    ExchangeMemberTokenResponse of(MemberDTO.MemberIdInfo memberIdInfo);
}
