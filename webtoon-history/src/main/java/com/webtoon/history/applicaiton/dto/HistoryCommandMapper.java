package com.webtoon.history.applicaiton.dto;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface HistoryCommandMapper {

    HistoryCommand.ExchangedContentsHistoryPage of(long contentsId, HistoryCommand.ContentsHistoryPage contentsHistoryPage);
}
