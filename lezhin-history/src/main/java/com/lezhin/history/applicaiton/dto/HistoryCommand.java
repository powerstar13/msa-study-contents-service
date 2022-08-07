package com.lezhin.history.applicaiton.dto;

import com.lezhin.history.presentation.shared.request.PageRequestDTO;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

public class HistoryCommand {

    @Getter
    @SuperBuilder
    public static class ContentsHistoryPage extends PageRequestDTO {

        private String contentsToken; // 작품 대체 식별키

        @Override
        public String toString() {
            return "{"
                + super.toString().replace("}", "")
                + ", \"contentsToken\":\"" + contentsToken + "\""
                + "}";
        }
    }

    @Getter
    @SuperBuilder
    public static class ExchangedContentsHistoryPage extends PageRequestDTO {

        private long contentsId; // 작품 고유번호

        @Override
        public String toString() {
            return "{"
                + super.toString().replace("}", "")
                + ", \"contentsId\":" + contentsId
                + "}";
        }
    }
}
