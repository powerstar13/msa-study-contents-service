package com.webtoon.contents.presentation.response;

import com.webtoon.contents.domain.service.dto.ContentsDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ContentsResponseMapper {

    EvaluationRegisterResponse of(ContentsDTO.EvaluationTokenInfo evaluationTokenInfo);

    EvaluationTop3ContentsResponse of(ContentsDTO.EvaluationTop3Contents evaluationTop3Contents);

    ExchangeContentsTokenResponse of(ContentsDTO.ContentsIdInfo memberIdInfo);
}
