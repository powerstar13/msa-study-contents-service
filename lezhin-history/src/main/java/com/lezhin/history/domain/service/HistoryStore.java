package com.lezhin.history.domain.service;

import com.lezhin.history.domain.History;
import reactor.core.publisher.Mono;

import java.util.List;

public interface HistoryStore {

    Mono<Void> historyListDelete(List<History> historyList);
}
