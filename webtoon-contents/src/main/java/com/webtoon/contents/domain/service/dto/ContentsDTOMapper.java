package com.webtoon.contents.domain.service.dto;

import com.webtoon.contents.domain.Contents;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ContentsDTOMapper {

    ContentsDTO.EvaluationTop3Contents of(List<Contents> likeTop3Contents, List<Contents> dislikeTop3Contents);
}
