package com.webtoon.history.presentation.request;

import com.webtoon.history.applicaiton.dto.HistoryCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Map;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ContentsHistoryRequestMapper {

    ContentsHistoryPageRequest of(Map<String, String> params);

    HistoryCommand.ContentsHistoryPage of(ContentsHistoryPageRequest request);
}
