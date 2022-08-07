package com.lezhin.contents.presentation.request;

import com.lezhin.contents.application.dto.ContentsCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ContentsRequestMapper {

    ContentsCommand.EvaluationRegister of(EvaluationRegisterRequest request);

    ContentsCommand.PricingModify of(PricingModifyRequest request);
}
