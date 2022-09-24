package com.webtoon.history.domain.service;

import com.webtoon.history.applicaiton.dto.HistoryCommand;
import com.webtoon.history.domain.service.dto.HistoryDTO;
import reactor.core.publisher.Mono;

public interface HistoryService {

    Mono<HistoryDTO.ContentsHistoryPage> contentsHistoryPage(HistoryCommand.ExchangedContentsHistoryPage command);

    Mono<HistoryDTO.SearchHistoryPage> searchHistoryPage(HistoryCommand.SearchHistoryPage command);

    Mono<Void> historyDeleteByMember(long memberId);
}
