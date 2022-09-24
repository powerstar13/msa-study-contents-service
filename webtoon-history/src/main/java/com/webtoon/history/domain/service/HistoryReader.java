package com.webtoon.history.domain.service;

import com.webtoon.history.applicaiton.dto.HistoryCommand;
import com.webtoon.history.domain.History;
import com.webtoon.history.domain.service.dto.HistoryDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HistoryReader {

    Mono<HistoryDTO.ContentsHistoryPage> findAllContentsHistoryPage(HistoryCommand.ExchangedContentsHistoryPage command);

    Mono<HistoryDTO.SearchHistoryPage> findAllSearchHistoryPage(HistoryCommand.SearchHistoryPage command);

    Flux<History> findHistoryListByMemberId(long memberId);
}
