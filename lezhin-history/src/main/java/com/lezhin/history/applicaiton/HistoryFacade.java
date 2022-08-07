package com.lezhin.history.applicaiton;

import com.lezhin.history.applicaiton.dto.HistoryCommand;
import com.lezhin.history.applicaiton.dto.HistoryCommandMapper;
import com.lezhin.history.domain.service.HistoryService;
import com.lezhin.history.domain.service.dto.HistoryDTO;
import com.lezhin.history.infrastructure.exception.status.BadRequestException;
import com.lezhin.history.infrastructure.webClient.ContentsWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class HistoryFacade {

    private final ContentsWebClientService contentsWebClientService;
    private final HistoryCommandMapper historyCommandMapper;
    private final HistoryService historyService;

    /**
     * 작품별 조회 이력 페이지
     * @param command: 조회할 정보
     * @return ContentsHistoryPage: 이력 페이지
     */
    public Mono<HistoryDTO.ContentsHistoryPage> contentsHistoryPage(HistoryCommand.ContentsHistoryPage command) {

        // 1. 작품 고유번호 가져오기
        return contentsWebClientService.exchangeContentsToken(command.getContentsToken())
            .flatMap(exchangeContentsTokenResponse -> {
                if (exchangeContentsTokenResponse.getRt() != 200) return Mono.error(new BadRequestException(exchangeContentsTokenResponse.getRtMsg()));

                HistoryCommand.ExchangedContentsHistoryPage exchangedContentsHistoryPage = historyCommandMapper.of(exchangeContentsTokenResponse.getContentsId(), command);

                return historyService.contentsHistoryPage(exchangedContentsHistoryPage); // 2. 작품별 조회 이력 페이지 조회 처리
            });
    }

    /**
     * 사용자 이력 페이지
     * @param command: 조회할 정보
     * @return SearchHistoryPage: 이력 페이지
     */
    public Mono<HistoryDTO.SearchHistoryPage> searchHistoryPage(HistoryCommand.SearchHistoryPage command) {
        return historyService.searchHistoryPage(command); // 사용자 이력 페이지 조회 처리
    }
}
