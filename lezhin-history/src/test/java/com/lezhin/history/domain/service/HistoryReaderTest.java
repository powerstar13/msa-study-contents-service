package com.lezhin.history.domain.service;

import com.lezhin.history.domain.History;
import com.lezhin.history.domain.service.dto.HistoryDTO;
import com.lezhin.history.domain.service.dto.HistoryDTOMapper;
import com.lezhin.history.infrastructure.dao.HistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.lezhin.history.infrastructure.factory.HistoryTestFactory.*;
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
    private HistoryDTOMapper historyDTOMapper;

    @DisplayName("작품별 조회 이력 페이지")
    @Test
    void findAllContentsHistoryPage() {

        given(historyRepository.findAllByContentsId(anyLong(), any(PageRequest.class))).willReturn(historyFlux());
        given(historyDTOMapper.of(any(History.class))).willReturn(historyMemberInfoDTO());
        given(historyRepository.countByContentsId(anyLong())).willReturn(Mono.just(320L));
        given(historyDTOMapper.of(any(HistoryDTO.pageInfo.class), anyList())).willReturn(contentsHistoryPageDTO());

        Mono<HistoryDTO.ContentsHistoryPage> result = historyReader.findAllContentsHistoryPage(exchangedContentsHistoryPageCommand());

        verify(historyRepository).findAllByContentsId(anyLong(), any(PageRequest.class));

        StepVerifier.create(result.log())
            .assertNext(contentsHistoryPage -> assertNotNull(contentsHistoryPage.getHistoryList()))
            .verifyComplete();
    }
}