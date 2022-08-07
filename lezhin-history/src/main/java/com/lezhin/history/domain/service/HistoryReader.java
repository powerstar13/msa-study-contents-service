package com.lezhin.history.domain.service;

import com.lezhin.history.applicaiton.dto.HistoryCommand;
import com.lezhin.history.domain.service.dto.HistoryDTO;
import reactor.core.publisher.Mono;

public interface HistoryReader {

    Mono<HistoryDTO.ContentsHistoryPage> findAllContentsHistoryPage(HistoryCommand.ExchangedContentsHistoryPage command);

    Mono<HistoryDTO.SearchHistoryPage> findAllSearchHistoryPage(HistoryCommand.SearchHistoryPage command);
}
