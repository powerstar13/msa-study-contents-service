package com.lezhin.history.presentation.response;

import com.lezhin.history.domain.service.dto.HistoryDTO;
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
}
