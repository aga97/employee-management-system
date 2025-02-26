package yellowsunn.employee_management.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import yellowsunn.employee_management.entity.Gender;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

/**
 * 직원별 세부 정보
 */
public class EmpDto {

    /**
     * 직원의 기본 정보
     */
    @Data
    @Builder
    public static class Info {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Content content; // 직원이 존재하는 경우에만 표시

        private boolean empty; // 해당 직원이 존재하지 않는경우 true

        @Data
        @Builder
        public static class Content {
            private Integer empNo;
            private String firstName;
            private String lastName;
            private LocalDate birthDate;
            private Gender gender;
            private LocalDate hireDate;
            private String deptNo;
            private String deptName;
            private String title;
            private Integer Salary;

            private boolean retirement; // 은퇴 유무

            @JsonInclude(JsonInclude.Include.NON_NULL)
            private LocalDate retireDate; // 은퇴한 경우에만 표시됨
        }
    }

    /**
     * 직원이 근무했던 부서 내역
     */
    @Data
    @Builder
    public static class DeptHistory {
        List<Content> content;
        private int size;

        @Data
        @Builder
        public static class Content {
            private String DeptName;
            private LocalDate fromDate;
            private LocalDate toDate;
        }
    }

    /**
     * 직원이 맡았던 직책 내역
     */
    @Data
    @Builder
    public static class TitleHistory {
        List<Content> content;
        private int size;

        @Data
        @Builder
        public static class Content {
            private String Title;
            private LocalDate fromDate;
            private LocalDate toDate;
        }
    }


    /**
     * 직원이 받았던 연봉 내역
     */
    @Data
    @Builder
    public static class SalaryHistory {
        List<Content> content;
        private int size;

        @Data
        @Builder
        public static class Content {
            private Integer salary;
            private LocalDate fromDate;
            private LocalDate toDate;
        }
    }

    /**
     * 직원 생성 dto
     */
    @Data
    public static class Create {
        @Valid
        private Content content;

        @Data
        public static class Content {
            @NotEmpty private String firstName;
            @NotEmpty private String lastName;
            @NotNull @PastOrPresent private LocalDate birthDate;
            @NotNull  private Gender gender;
            @NotNull @PastOrPresent private LocalDate hireDate;
            @NotEmpty private String deptNo;
            @NotEmpty private String title;
            @NotNull @Positive private Integer Salary;
        }
    }

    @Data
    public static class Update {
        @Valid
        private Content content;
        private boolean deptNo;
        private boolean title;
        private boolean salary;
        
        @Data
        public static class Content {
            private Integer empNo;
            private String deptNo;
            private String title;
            @Positive private Integer Salary;
        }
    }

    @Data
    @Builder
    public static class Success {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer empNo;
        private boolean success;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String error;
    }
}
