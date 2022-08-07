package com.lezhin.history.domain.service.dto;

import com.lezhin.history.domain.History;
import org.mapstruct.*;

import java.util.List;
import java.util.Map;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface SearchHistoryDTOMapper {

    @Mappings({
        @Mapping(expression = "java(com.lezhin.history.domain.MemberType.valueOf(String.valueOf(history.get(\"memberType\"))))", target = "memberType"),
        @Mapping(expression = "java(String.valueOf(history.get(\"memberEmail\")))", target = "memberEmail"),
        @Mapping(expression = "java(String.valueOf(history.get(\"memberName\")))", target = "memberName"),
        @Mapping(expression = "java(com.lezhin.history.domain.MemberGender.valueOf(String.valueOf(history.get(\"memberGender\"))))", target = "memberGender")
    })
    HistoryDTO.SearchHistoryMemberInfo of(Map<String, Object> history);

    HistoryDTO.SearchHistoryPage of(HistoryDTO.pageInfo pageInfo, List<HistoryDTO.SearchHistoryMemberInfo> memberList);
}
