package com.webtoon.history.domain.service;

import com.webtoon.history.domain.History;
import reactor.core.publisher.Mono;

import java.util.List;

public interface HistoryStore {

    Mono<Void> historyListDelete(List<History> historyList);
}
