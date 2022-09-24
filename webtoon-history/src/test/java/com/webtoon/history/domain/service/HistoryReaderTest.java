package com.webtoon.history.domain.service;

import com.webtoon.history.applicaiton.dto.HistoryCommand;
import com.webtoon.history.domain.History;
import com.webtoon.history.domain.service.dto.ContentsHistoryDTOMapper;
import com.webtoon.history.domain.service.dto.HistoryDTO;
import com.webtoon.history.infrastructure.dao.HistoryRepository;
import com.webtoon.history.infrastructure.factory.HistoryTestFactory;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class HistoryReaderTest {

    @Autowired
    private HistoryReader historyReader;

    @MockBean
    private HistoryRepository historyRepository;
    @MockBean
    private ContentsHistoryDTOMapper contentsHistoryDTOMapper;

    @DisplayName("작품별 조회 이력 페이지")
    @Test
    void findAllContentsHistoryPage() {

        given(historyRepository.findAllByContentsId(anyLong(), any(PageRequest.class))).willReturn(HistoryTestFactory.historyFlux());
        given(contentsHistoryDTOMapper.of(any(History.class))).willReturn(HistoryTestFactory.contentsHistoryMemberInfoDTO());
        given(historyRepository.countByContentsId(anyLong())).willReturn(Mono.just(320L));
        given(contentsHistoryDTOMapper.of(any(HistoryDTO.pageInfo.class), anyList())).willReturn(HistoryTestFactory.contentsHistoryPageDTO());

        Mono<HistoryDTO.ContentsHistoryPage> result = historyReader.findAllContentsHistoryPage(HistoryTestFactory.exchangedContentsHistoryPageCommand());

        verify(historyRepository).findAllByContentsId(anyLong(), any(PageRequest.class));

        StepVerifier.create(result.log())
            .assertNext(contentsHistoryPage -> assertNotNull(contentsHistoryPage.getHistoryList()))
            .verifyComplete();
    }

    @DisplayName("사용자 조회 이력 페이지")
    @Test
    void test() {

        given(historyRepository.getSearchHistoryPage(any(HistoryCommand.SearchHistoryPage.class))).willReturn(HistoryTestFactory.searchHistoryPageDTOMono());

        Mono<HistoryDTO.SearchHistoryPage> result = historyReader.findAllSearchHistoryPage(HistoryTestFactory.searchHistoryPageCommand());

        verify(historyRepository).getSearchHistoryPage(any(HistoryCommand.SearchHistoryPage.class));

        StepVerifier.create(result.log())
            .assertNext(searchHistoryPage -> assertNotNull(searchHistoryPage.getMemberList()))
            .verifyComplete();
    }

    @DisplayName("회원이 조회한 이력 목록 조회")
    @Test
    void findHistoryListByMemberId() {

        given(historyRepository.findAllByMemberId(anyLong())).willReturn(HistoryTestFactory.historyFlux());

        Flux<History> result = historyReader.findHistoryListByMemberId(RandomUtils.nextLong());

        verify(historyRepository).findAllByMemberId(anyLong());

        StepVerifier.create(result.log())
            .expectNextCount(2)
            .verifyComplete();
    }
}