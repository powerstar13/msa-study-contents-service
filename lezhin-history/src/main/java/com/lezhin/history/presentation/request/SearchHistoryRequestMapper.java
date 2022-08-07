package com.lezhin.history.presentation.request;

import com.lezhin.history.applicaiton.dto.HistoryCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Map;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface SearchHistoryRequestMapper {

    SearchHistoryPageRequest of(Map<String, String> params);

    HistoryCommand.SearchHistoryPage of(SearchHistoryPageRequest request);
}