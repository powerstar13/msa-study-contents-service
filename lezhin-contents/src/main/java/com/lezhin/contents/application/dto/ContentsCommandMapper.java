package com.lezhin.contents.application.dto;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ContentsCommandMapper {

    ContentsCommand.ExchangedMemberIdForEvaluationRegister of(long memberId, ContentsCommand.EvaluationRegister evaluationRegister);

    ContentsCommand.ExchangedContentsIdForEvaluationRegister of(long contentsId, ContentsCommand.ExchangedMemberIdForEvaluationRegister exchangedMemberIdForEvaluationRegister);
}
