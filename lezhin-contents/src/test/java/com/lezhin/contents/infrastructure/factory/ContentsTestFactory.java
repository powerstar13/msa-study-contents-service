package com.lezhin.contents.infrastructure.factory;

import com.lezhin.contents.application.dto.ContentsCommand;
import com.lezhin.contents.domain.*;
import com.lezhin.contents.domain.service.dto.ContentsDTO;
import com.lezhin.contents.infrastructure.webClient.response.ExchangeMemberTokenResponse;
import com.lezhin.contents.presentation.request.EvaluationRegisterRequest;
import com.lezhin.contents.presentation.response.EvaluationRegisterResponse;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
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
    public static Contents contents() {

        return Contents.builder()
            .contentsId(RandomUtils.nextLong())
            .contentsToken(UUID.randomUUID().toString())
            .contentsName(contentsName)
            .author(author)
            .pricingType(pricingType)
            .coin(coin)
            .adultOnly(adultOnly)
            .openAt(LocalDate.now())
            .build();
    }

    public static Mono<Contents> contentsMono() {
        return Mono.just(contents());
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
}
