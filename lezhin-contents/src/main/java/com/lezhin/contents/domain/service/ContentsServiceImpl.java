package com.lezhin.contents.domain.service;

import com.lezhin.contents.application.dto.ContentsCommand;
import com.lezhin.contents.application.dto.ContentsCommandMapper;
import com.lezhin.contents.domain.service.dto.ContentsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ContentsServiceImpl implements ContentsService {

    private final ContentsReader contentsReader;
    private final ContentsStore contentsStore;
    private final ContentsCommandMapper contentsCommandMapper;

    /**
     * 평가 등록 처리
     * @param command: 등록할 평가 정보
     * @return EvaluationTokenInfo: 평가 대체 식별키
     */
    @Override
    public Mono<ContentsDTO.EvaluationTokenInfo> evaluationRegister(ContentsCommand.ExchangedMemberIdForEvaluationRegister command) {

        // 1. 작품 정보 조회
        return contentsReader.findByContentsToken(command.getContentsToken())
            .flatMap(contents -> {

                ContentsCommand.ExchangedContentsIdForEvaluationRegister exchangedContentsIdForEvaluationRegister = contentsCommandMapper.of(contents.getContentsId(), command);

                // 2. 이미 등록된 평가가 있는지 확인
                return contentsReader.evaluationExistCheck(exchangedContentsIdForEvaluationRegister)
                    .then(contentsStore.evaluationRegister(contents, exchangedContentsIdForEvaluationRegister) // 3. 평가 등록
                        .flatMap(evaluation -> Mono.just(new ContentsDTO.EvaluationTokenInfo(evaluation.getEvaluationToken())))
                    );
            });
    }

    /**
     * 좋아요/싫어요 Top3 작품 조회 처리
     * @return EvaluationTop3Contents: 좋아요/싫어요 Top3 작품 정보
     */
    @Override
    public Mono<ContentsDTO.EvaluationTop3Contents> evaluationTop3Contents() {
        return contentsReader.evaluationTop3Contents(); // 좋아요/싫어요 Top3 작품 조회
    }

    /**
     * 작품 고유번호 가져오기
     * @param contentsToken: 작품 대체 식별키
     * @return ContentsIdInfo: 작품 고유번호
     */
    @Override
    public Mono<ContentsDTO.ContentsIdInfo> exchangeContentsToken(String contentsToken) {
        return contentsReader.findByContentsToken(contentsToken)
            .flatMap(contents -> Mono.just(new ContentsDTO.ContentsIdInfo(contents.getContentsId())));
    }
}
