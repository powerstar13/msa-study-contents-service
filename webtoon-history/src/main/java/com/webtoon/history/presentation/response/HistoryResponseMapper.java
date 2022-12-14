package com.webtoon.history.presentation.response;

import com.webtoon.history.domain.service.dto.HistoryDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface HistoryResponseMapper {

    ContentsHistoryPageResponse of(HistoryDTO.ContentsHistoryPage contentsHistoryPage);

    SearchHistoryPageResponse of(HistoryDTO.SearchHistoryPage searchHistoryPage);
}
