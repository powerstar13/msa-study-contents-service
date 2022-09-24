package com.webtoon.history.infrastructure.dao;

import com.webtoon.history.applicaiton.dto.HistoryCommand;
import com.webtoon.history.domain.service.dto.HistoryDTO;
import reactor.core.publisher.Mono;

public interface HistoryCustomRepository {

    Mono<HistoryDTO.SearchHistoryPage> getSearchHistoryPage(HistoryCommand.SearchHistoryPage command);
}
