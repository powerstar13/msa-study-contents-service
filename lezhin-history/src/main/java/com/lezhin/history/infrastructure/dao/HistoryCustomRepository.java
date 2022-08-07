package com.lezhin.history.infrastructure.dao;

import com.lezhin.history.applicaiton.dto.HistoryCommand;
import com.lezhin.history.domain.service.dto.HistoryDTO;
import reactor.core.publisher.Mono;

public interface HistoryCustomRepository {

    Mono<HistoryDTO.SearchHistoryPage> getSearchHistoryPage(HistoryCommand.SearchHistoryPage command);
}
