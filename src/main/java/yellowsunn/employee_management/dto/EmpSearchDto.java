package yellowsunn.employee_management.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import yellowsunn.employee_management.entity.Gender;

import java.time.LocalDate;

/**
 * 직원 목록 조회 dto
 */
public class EmpSearchDto {

    /**
     * 직원 목록 조회에 사용하는 dto
     */
    @Data
    @Builder
    public static class Info {
        private Integer empNo;
        private String firstName;
        private String lastName;
        private LocalDate birthDate;
        private Gender gender;
        private LocalDate hireDate;
        private String deptName;
        private String title;
        private Integer Salary;
        private boolean retirement;
    }

    /**
     * 검색 및 정렬 조건
     */
    @Data
    public static class Condition {
        private Integer empNo;
        private String firstName;
        private String lastName;

        private String birthDate;
        private Gender gender;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate hireDate;

        @JsonAlias("deptNo")
        private String deptName;
    }
}
