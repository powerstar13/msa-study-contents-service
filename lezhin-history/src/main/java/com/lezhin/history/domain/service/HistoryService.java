package com.lezhin.history.domain.service;

import com.lezhin.history.applicaiton.dto.HistoryCommand;
import com.lezhin.history.domain.service.dto.HistoryDTO;
import reactor.core.publisher.Mono;

public interface HistoryService {

    Mono<HistoryDTO.ContentsHistoryPage> contentsHistoryPage(HistoryCommand.ExchangedContentsHistoryPage command);
}
