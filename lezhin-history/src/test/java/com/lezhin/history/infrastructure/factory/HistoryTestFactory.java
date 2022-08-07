package com.lezhin.history.infrastructure.factory;

import com.lezhin.history.applicaiton.dto.HistoryCommand;
import com.lezhin.history.domain.AdultOnly;
import com.lezhin.history.domain.History;
import com.lezhin.history.domain.MemberGender;
import com.lezhin.history.domain.MemberType;
import com.lezhin.history.domain.service.dto.HistoryDTO;
import com.lezhin.history.infrastructure.webClient.response.ExchangeContentsTokenResponse;
import com.lezhin.history.presentation.request.ContentsHistoryPageRequest;
import com.lezhin.history.presentation.response.ContentsHistoryPageResponse;
import com.lezhin.history.presentation.response.dto.HistoryResponseDTO;
import com.lezhin.history.presentation.shared.response.PageResponseDTO;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public class HistoryTestFactory {

    private static final MemberType memberType = MemberType.NORMAL;
    private static final String memberEmail = "test@gmail.com";
    private static final String memberName = "이름";
    private static final MemberGender memberGender = MemberGender.M;
    private static final AdultOnly adultOnly = AdultOnly.N;
    private static final LocalDateTime createdAt = LocalDateTime.now();

    /**
     * 이력 정보
     */
    public static History history() {

        return History.builder()
            .historyId(RandomUtils.nextLong())
            .historyToken(UUID.randomUUID().toString())
            .memberId(RandomUtils.nextLong())
            .memberType(memberType)
            .memberEmail(memberEmail)
            .memberName(memberName)
            .memberGender(memberGender)
            .contentsId(RandomUtils.nextLong())
            .adultOnly(adultOnly)
            .createdAt(createdAt)
            .build();
    }

    public static Flux<History> historyFlux() {
        return Flux.just(history(), history());
    }

    public static HistoryDTO.HistoryMemberInfo historyMemberInfoDTO() {

        return HistoryDTO.HistoryMemberInfo.builder()
            .historyToken(UUID.randomUUID().toString())
            .memberType(memberType)
            .memberEmail(memberEmail)
            .memberName(memberName)
            .memberGender(memberGender)
            .createdAt(createdAt)
            .build();
    }

    public static HistoryDTO.pageInfo pageInfo() {
        return HistoryDTO.pageInfo.builder()
            .currentSize(10)
            .currentPage(1)
            .totalPage(32)
            .totalCount(320)
            .build();
    }

    public static HistoryDTO.ContentsHistoryPage contentsHistoryPageDTO() {

        return HistoryDTO.ContentsHistoryPage.builder()
            .pageInfo(pageInfo())
            .historyList(
                Arrays.asList(
                    historyMemberInfoDTO(), historyMemberInfoDTO()
                )
            )
            .build();
    }

    public static Mono<HistoryDTO.ContentsHistoryPage> contentsHistoryPageDTOMono() {
        return Mono.just(contentsHistoryPageDTO());
    }

    public static HistoryCommand.ExchangedContentsHistoryPage exchangedContentsHistoryPageCommand() {

        return HistoryCommand.ExchangedContentsHistoryPage.builder()
            .page(1)
            .size(10)
            .contentsId(RandomUtils.nextLong())
            .build();
    }

    public static HistoryCommand.ContentsHistoryPage contentsHistoryPageCommand() {

        return HistoryCommand.ContentsHistoryPage.builder()
            .page(1)
            .size(10)
            .contentsToken(UUID.randomUUID().toString())
            .build();
    }

    /**
     * 작품 고유번호 가져오기 통신 결과
     */
    public static ExchangeContentsTokenResponse exchangeContentsTokenResponse() {

        return ExchangeContentsTokenResponse.builder()
            .rt(HttpStatus.OK.value())
            .rtMsg(HttpStatus.OK.getReasonPhrase())
            .contentsId(RandomUtils.nextLong())
            .build();
    }

    public static Mono<ExchangeContentsTokenResponse> exchangeContentsTokenResponseMono() {

        return Mono.just(exchangeContentsTokenResponse());
    }

    /**
     * 작품별 조회 이력 페이지 Request
     */
    public static ContentsHistoryPageRequest contentsHistoryPageRequest() {

        return ContentsHistoryPageRequest.builder()
            .page(1)
            .size(10)
            .contentsToken(UUID.randomUUID().toString())
            .build();
    }

    public static PageResponseDTO pageResponseDTO() {

        return PageResponseDTO.builder()
            .currentSize(10)
            .currentPage(1)
            .totalPage(32)
            .totalCount(320)
            .build();
    }

    public static HistoryResponseDTO.HistoryMemberInfo historyMemberInfoResponseDTO() {

        return HistoryResponseDTO.HistoryMemberInfo.builder()
            .historyToken(UUID.randomUUID().toString())
            .memberType(memberType)
            .memberEmail(memberEmail)
            .memberName(memberName)
            .memberGender(memberGender)
            .createdAt(createdAt)
            .build();
    }

    /**
     * 작품별 조회 이력 페이지 Response
     */
    public static ContentsHistoryPageResponse contentsHistoryPageResponse() {

        return ContentsHistoryPageResponse.builder()
            .pageInfo(pageResponseDTO())
            .historyList(
                Arrays.asList(
                    historyMemberInfoResponseDTO(), historyMemberInfoResponseDTO()
                )
            )
            .build();
    }
}