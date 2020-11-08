package yellowsunn.employee_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

public class TitleDto {

    /**
     * 모든 직책 목록을 가져온다.
     */
    @Data
    @Builder
    public static class All {
        private List<Content> content;
        private int size;

        @Data
        @AllArgsConstructor
        public static class Content {
            private String title;
        }
    }
}
