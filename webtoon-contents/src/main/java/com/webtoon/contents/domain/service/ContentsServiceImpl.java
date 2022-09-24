package com.webtoon.contents.domain.service;

import com.webtoon.contents.application.dto.ContentsCommand;
import com.webtoon.contents.application.dto.ContentsCommandMapper;
import com.webtoon.contents.domain.service.dto.ContentsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(rollbackFor = Exception.class)
    public Mono<ContentsDTO.EvaluationTokenInfo> evaluationRegister(ContentsCommand.ExchangedMemberIdForEvaluationRegister command) {

        // 1. 작품 정보 조회
        return contentsReader.findByContentsToken(command.getContentsToken())
            .flatMap(contents -> {

                ContentsCommand.ExchangedContentsIdForEvaluationRegister exchangedContentsIdForEvaluationRegister = contentsCommandMapper.of(contents.getContentsId(), command);

                // 2. 이미 등록된 평가가 있는지 확인
                return contentsReader.evaluationExistCheck(exchangedContentsIdForEvaluationRegister)
                    .then(contentsStore.evaluationRegister(contents, exchangedContentsIdForEvaluationRegister) // 3. 평가 등록
                        .map(evaluation -> new ContentsDTO.EvaluationTokenInfo(evaluation.getEvaluationToken()))
                    );
            });
    }

    /**
     * 좋아요/싫어요 Top3 작품 조회 처리
     * @return EvaluationTop3Contents: 좋아요/싫어요 Top3 작품 정보
     */
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Mono<ContentsDTO.EvaluationTop3Contents> evaluationTop3Contents() {
        return contentsReader.evaluationTop3Contents(); // 좋아요/싫어요 Top3 작품 조회
    }

    /**
     * 작품 고유번호 가져오기
     * @param contentsToken: 작품 대체 식별키
     * @return ContentsIdInfo: 작품 고유번호
     */
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Mono<ContentsDTO.ContentsIdInfo> exchangeContentsToken(String contentsToken) {

        return contentsReader.findByContentsToken(contentsToken)
            .map(contents -> new ContentsDTO.ContentsIdInfo(contents.getContentsId()));
    }

    /**
     * 가격 변경 처리
     * @param command: 변경할 가격 정보
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<Void> pricingModify(ContentsCommand.PricingModify command) {

        return contentsReader.findByContentsToken(command.getContentsToken()) // 1. 작품 정보 조회
            .flatMap(contents -> {
                return contentsStore.pricingModify(contents, command); // 2. 가격 변경
            });
    }

    /**
     * 평가 삭제 처리
     * @param memberId: 회원 고유번호
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<Void> evaluationDeleteByMember(long memberId) {

        return contentsReader.findEvaluationListByMemberId(memberId) // 1. 평가 목록 조회
            .flatMap(evaluation ->
                contentsReader.findContentsByContentsId(evaluation.getContentsId()) // 2. 작품 정보 조회
                    .flatMap(contents -> contentsStore.evaluationDelete(contents, evaluation)) // 3. 평가 삭제
            )
            .then();
    }
}
