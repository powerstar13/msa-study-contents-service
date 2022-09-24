package com.webtoon.history.infrastructure.factory;

import com.webtoon.history.applicaiton.dto.HistoryCommand;
import com.webtoon.history.domain.AdultOnly;
import com.webtoon.history.domain.History;
import com.webtoon.history.domain.MemberGender;
import com.webtoon.history.domain.MemberType;
import com.webtoon.history.domain.service.dto.HistoryDTO;
import com.webtoon.history.infrastructure.webClient.response.ExchangeContentsTokenResponse;
import com.webtoon.history.infrastructure.webClient.response.ExchangeMemberTokenResponse;
import com.webtoon.history.presentation.request.ContentsHistoryPageRequest;
import com.webtoon.history.presentation.request.SearchHistoryPageRequest;
import com.webtoon.history.presentation.response.ContentsHistoryPageResponse;
import com.webtoon.history.presentation.response.SearchHistoryPageResponse;
import com.webtoon.history.presentation.response.dto.HistoryResponseDTO;
import com.webtoon.history.presentation.shared.response.PageResponseDTO;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
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

    public static List<History> historyList() {
        return Arrays.asList(history(), history());
    }

    public static HistoryDTO.ContentsHistoryMemberInfo contentsHistoryMemberInfoDTO() {

        return HistoryDTO.ContentsHistoryMemberInfo.builder()
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
                    contentsHistoryMemberInfoDTO(), contentsHistoryMemberInfoDTO()
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
     * 회원 고유번호 가져오기 통신 결과
     */
    public static ExchangeMemberTokenResponse exchangeMemberTokenResponse() {

        return ExchangeMemberTokenResponse.builder()
            .rt(HttpStatus.OK.value())
            .rtMsg(HttpStatus.OK.getReasonPhrase())
            .memberId(RandomUtils.nextLong())
            .build();
    }

    public static Mono<ExchangeMemberTokenResponse> exchangeMemberTokenResponseMono() {
        return Mono.just(exchangeMemberTokenResponse());
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

    public static HistoryResponseDTO.ContentsHistoryMemberInfo contentsHistoryMemberInfoResponseDTO() {

        return HistoryResponseDTO.ContentsHistoryMemberInfo.builder()
            .historyToken(UUID.randomUUID().toString())
            .memberType(memberType)
            .memberEmail(memberEmail)
            .memberName(memberName)
            .memberGender(memberGender)
            .createdAt(createdAt)
            .build();
    }

    /**
     * 이력 페이지 Response
     */
    public static ContentsHistoryPageResponse contentsHistoryPageResponse() {

        return ContentsHistoryPageResponse.builder()
            .pageInfo(pageResponseDTO())
            .historyList(
                Arrays.asList(
                    contentsHistoryMemberInfoResponseDTO(), contentsHistoryMemberInfoResponseDTO()
                )
            )
            .build();
    }

    public static HistoryCommand.SearchHistoryPage searchHistoryPageCommand() {

        return HistoryCommand.SearchHistoryPage.builder()
            .weekInterval(1)
            .adultOnly(AdultOnly.Y)
            .historyCount(3)
            .build();
    }

    public static HistoryDTO.SearchHistoryMemberInfo searchHistoryMemberInfoDTO() {

        return HistoryDTO.SearchHistoryMemberInfo.builder()
            .memberType(memberType)
            .memberEmail(memberEmail)
            .memberName(memberName)
            .memberGender(memberGender)
            .build();
    }

    public static HistoryDTO.SearchHistoryPage searchHistoryPageDTO() {

        return HistoryDTO.SearchHistoryPage.builder()
            .pageInfo(pageInfo())
            .memberList(
                Arrays.asList(
                    searchHistoryMemberInfoDTO(), searchHistoryMemberInfoDTO()
                )
            )
            .build();
    }

    public static Mono<HistoryDTO.SearchHistoryPage> searchHistoryPageDTOMono() {
        return Mono.just(searchHistoryPageDTO());
    }

    /**
     * 사용자 조회 이력 페이지 Request
     */
    public static SearchHistoryPageRequest searchHistoryPageRequest() {

        return SearchHistoryPageRequest.builder()
            .page(1)
            .size(10)
            .weekInterval(1)
            .adultOnly(AdultOnly.Y)
            .historyCount(3)
            .build();
    }

    public static HistoryResponseDTO.SearchHistoryMemberInfo searchHistoryMemberInfoResponseDTO() {

        return HistoryResponseDTO.SearchHistoryMemberInfo.builder()
            .memberType(memberType)
            .memberEmail(memberEmail)
            .memberName(memberName)
            .memberGender(memberGender)
            .build();
    }

    /**
     * 사용자 조회 이력 페이지 Response
     */
    public static SearchHistoryPageResponse searchHistoryPageResponse() {

        return SearchHistoryPageResponse.builder()
            .pageInfo(pageResponseDTO())
            .memberList(
                Arrays.asList(
                    searchHistoryMemberInfoResponseDTO(), searchHistoryMemberInfoResponseDTO()
                )
            )
            .build();
    }
}
