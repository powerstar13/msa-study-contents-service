package com.lezhin.contents.infrastructure.factory;

import com.lezhin.contents.application.dto.ContentsCommand;
import com.lezhin.contents.domain.*;
import com.lezhin.contents.domain.service.dto.ContentsDTO;
import com.lezhin.contents.infrastructure.webClient.response.ExchangeMemberTokenResponse;
import com.lezhin.contents.presentation.request.EvaluationRegisterRequest;
import com.lezhin.contents.presentation.response.EvaluationRegisterResponse;
import com.lezhin.contents.presentation.response.EvaluationTop3ContentsResponse;
import com.lezhin.contents.presentation.response.dto.ContentsResponseDTO;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ContentsTestFactory {

    private static final String contentsName = "작품명";
    private static final String author = "작가";
    private static final PricingType pricingType = PricingType.FREE;
    private static final int coin = 0;
    private static final AdultOnly adultOnly = AdultOnly.N;
    private static final EvaluationType evaluationType = EvaluationType.LIKE;
    private static final String comment = "댓글";

    /**
     * 작품 정보
     */
    public static Contents contents(long likeCount, long dislikeCount) {

        return Contents.builder()
            .contentsId(RandomUtils.nextLong())
            .contentsToken(UUID.randomUUID().toString())
            .contentsName(contentsName)
            .author(author)
            .pricingType(pricingType)
            .coin(coin)
            .adultOnly(adultOnly)
            .openAt(LocalDate.now())
            .likeCount(likeCount)
            .dislikeCount(dislikeCount)
            .build();
    }

    public static Mono<Contents> contentsMono() {
        return Mono.just(contents(0, 0));
    }

    /**
     * 평가 정보
     */
    public static Evaluation evaluation() {

        return Evaluation.builder()
            .evaluationId(RandomUtils.nextLong())
            .evaluationToken(UUID.randomUUID().toString())
            .memberId(RandomUtils.nextLong())
            .contentsId(RandomUtils.nextLong())
            .evaluationType(evaluationType)
            .comment(comment)
            .build();
    }

    public static Mono<Evaluation> evaluationMono() {
        return Mono.just(evaluation());
    }

    public static ContentsCommand.ExchangedContentsIdForEvaluationRegister exchangedContentsIdForEvaluationRegisterCommand() {

        return ContentsCommand.ExchangedContentsIdForEvaluationRegister.builder()
            .memberId(RandomUtils.nextLong())
            .contentsId(RandomUtils.nextLong())
            .evaluationType(evaluationType)
            .comment(comment)
            .build();
    }

    public static ContentsCommand.ExchangedMemberIdForEvaluationRegister exchangedMemberIdForEvaluationRegisterCommand() {

        return ContentsCommand.ExchangedMemberIdForEvaluationRegister.builder()
            .memberId(RandomUtils.nextLong())
            .contentsToken(UUID.randomUUID().toString())
            .evaluationType(evaluationType)
            .comment(comment)
            .build();
    }

    public static ContentsCommand.EvaluationRegister evaluationRegisterCommand() {

        return ContentsCommand.EvaluationRegister.builder()
            .memberToken(UUID.randomUUID().toString())
            .contentsToken(UUID.randomUUID().toString())
            .evaluationType(evaluationType)
            .comment(comment)
            .build();
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

    public static ContentsDTO.EvaluationTokenInfo evaluationTokenInfoDTO() {

        return ContentsDTO.EvaluationTokenInfo.builder()
            .evaluationToken(UUID.randomUUID().toString())
            .build();
    }

    public static Mono<ContentsDTO.EvaluationTokenInfo> evaluationTokenInfoDTOMono() {
        return Mono.just(evaluationTokenInfoDTO());
    }

    /**
     * 평가 등록 Request
     */
    public static EvaluationRegisterRequest evaluationRegisterRequest() {

        return EvaluationRegisterRequest.builder()
            .memberToken(UUID.randomUUID().toString())
            .contentsToken(UUID.randomUUID().toString())
            .evaluationType(evaluationType)
            .comment(comment)
            .build();
    }

    /**
     * 평가 등록 Response
     */
    public static EvaluationRegisterResponse evaluationRegisterResponse() {

        return EvaluationRegisterResponse.builder()
            .evaluationToken(UUID.randomUUID().toString())
            .build();
    }

    public static Flux<Contents> likeTop3ContentsFlux() {

        return Flux.just(
            contents(3, 0),
            contents(2, 0),
            contents(1, 0)
        );
    }

    public static Flux<Contents> dislikeTop3ContentsFlux() {

        return Flux.just(
            contents(0, 3),
            contents(0, 2),
            contents(0, 1)
        );
    }

    public static ContentsDTO.ContentsInfo contentsInfoDTO() {

        return ContentsDTO.ContentsInfo.builder()
            .contentsToken(UUID.randomUUID().toString())
            .contentsName(contentsName)
            .author(author)
            .pricingType(pricingType)
            .coin(coin)
            .adultOnly(adultOnly)
            .openAt(LocalDate.now())
            .build();
    }

    public static List<ContentsDTO.ContentsInfo> contentsInfoDTOList() {
        return Arrays.asList(contentsInfoDTO(), contentsInfoDTO(), contentsInfoDTO());
    }

    public static ContentsDTO.EvaluationTop3Contents evaluationTop3ContentsDTO() {

        return ContentsDTO.EvaluationTop3Contents.builder()
            .likeTop3Contents(contentsInfoDTOList())
            .dislikeTop3Contents(contentsInfoDTOList())
            .build();
    }

    public static Mono<ContentsDTO.EvaluationTop3Contents> evaluationTop3ContentsDTOMono() {
        return Mono.just(evaluationTop3ContentsDTO());
    }

    public static ContentsResponseDTO.ContentsInfo contentsInfoResponseDTO() {

        return ContentsResponseDTO.ContentsInfo.builder()
            .contentsToken(UUID.randomUUID().toString())
            .contentsName(contentsName)
            .author(author)
            .pricingType(pricingType)
            .coin(coin)
            .adultOnly(adultOnly)
            .openAt(LocalDate.now())
            .build();
    }

    public static List<ContentsResponseDTO.ContentsInfo> contentsInfoResponseDTOList() {
        return Arrays.asList(contentsInfoResponseDTO(), contentsInfoResponseDTO(), contentsInfoResponseDTO());
    }

    /**
     * 좋아요/싫어요 Top3 작품 조회 Response
     */
    public static EvaluationTop3ContentsResponse evaluationTop3ContentsResponse() {

        return EvaluationTop3ContentsResponse.builder()
            .likeTop3Contents(contentsInfoResponseDTOList())
            .dislikeTop3Contents(contentsInfoResponseDTOList())
            .build();
    }
}
