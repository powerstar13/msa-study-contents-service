package com.lezhin.contents.application;

import com.lezhin.contents.application.dto.ContentsCommand;
import com.lezhin.contents.application.dto.ContentsCommandMapper;
import com.lezhin.contents.domain.service.ContentsService;
import com.lezhin.contents.domain.service.dto.ContentsDTO;
import com.lezhin.contents.infrastructure.exception.status.BadRequestException;
import com.lezhin.contents.infrastructure.webClient.MemberWebClientService;
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
}
