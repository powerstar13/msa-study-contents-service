package com.webtoon.contents.application;

import com.webtoon.contents.application.dto.ContentsCommand;
import com.webtoon.contents.application.dto.ContentsCommandMapper;
import com.webtoon.contents.domain.service.ContentsService;
import com.webtoon.contents.domain.service.dto.ContentsDTO;
import com.webtoon.contents.infrastructure.exception.status.BadRequestException;
import com.webtoon.contents.infrastructure.webClient.MemberWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ContentsFacade {

    private final MemberWebClientService memberWebClientService;
    private final ContentsCommandMapper contentsCommandMapper;
    private final ContentsService contentsService;

    /**
     * 평가 등록
     * @param command: 등록할 평가 정보
     * @return EvaluationTokenInfo: 평가 대체 식별키
     */
    public Mono<ContentsDTO.EvaluationTokenInfo> evaluationRegister(ContentsCommand.EvaluationRegister command) {

        return memberWebClientService.exchangeMemberToken(command.getMemberToken()) // 1. 회원 대체 식별키로 회원 고유번호 가져오기
            .flatMap(exchangeMemberTokenResponse -> {
                if (exchangeMemberTokenResponse.getRt() != 200) return Mono.error(new BadRequestException(exchangeMemberTokenResponse.getRtMsg()));

                ContentsCommand.ExchangedMemberIdForEvaluationRegister exchangedMemberIdForEvaluationRegister = contentsCommandMapper.of(exchangeMemberTokenResponse.getMemberId(), command);

                return contentsService.evaluationRegister(exchangedMemberIdForEvaluationRegister); // 2. 평가 등록 처리
            });
    }

    /**
     * 좋아요/싫어요 Top3 작품 조회
     * @return EvaluationTop3Contents: 좋아요/싫어요 Top3 작품 정보
     */
    public Mono<ContentsDTO.EvaluationTop3Contents> evaluationTop3Contents() {
        return contentsService.evaluationTop3Contents(); // 좋아요/싫어요 Top3 작품 조회 처리
    }

    /**
     * 작품 고유번호 가져오기
     * @param contentsToken: 작품 대체 식별키
     * @return ContentsIdInfo: 작품 고유번호
     */
    public Mono<ContentsDTO.ContentsIdInfo> exchangeContentsToken(String contentsToken) {
        return contentsService.exchangeContentsToken(contentsToken); // 작품 고유번호 가져오기 처리
    }

    /**
     * 가격 변경
     * @param command: 변경할 가격 정보
     */
    public Mono<Void> pricingModify(ContentsCommand.PricingModify command) {
       return contentsService.pricingModify(command); // 가격 변경 처리
    }

    /**
     * 평가 삭제
     * @param memberToken: 회원 대체 식별키
     */
    public Mono<Void> evaluationDeleteByMember(String memberToken) {

        return memberWebClientService.exchangeMemberToken(memberToken) // 1. 회원 대체 식별키로 회원 고유번호 가져오기
            .flatMap(exchangeMemberTokenResponse -> {
                if (exchangeMemberTokenResponse.getRt() != 200) return Mono.error(new BadRequestException(exchangeMemberTokenResponse.getRtMsg()));

                return contentsService.evaluationDeleteByMember(exchangeMemberTokenResponse.getMemberId()); // 2. 평가 삭제 처리
            });
    }
}
