package com.lezhin.history.domain.service.dto;

import com.lezhin.history.domain.History;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ContentsHistoryDTOMapper {

    HistoryDTO.ContentsHistoryMemberInfo of(History history);

    HistoryDTO.ContentsHistoryPage of(HistoryDTO.pageInfo pageInfo, List<HistoryDTO.ContentsHistoryMemberInfo> historyList);
}
