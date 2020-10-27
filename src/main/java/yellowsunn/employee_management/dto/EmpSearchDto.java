package yellowsunn.employee_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import yellowsunn.employee_management.entity.Gender;

import java.time.LocalDate;

public class EmpSearchDto {

    @Data
    @AllArgsConstructor
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
    }

    @Data
    public static class Condition {
        private Integer empNo;
        private String firstName;
        private String lastName;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate birthDate;
        private Gender gender;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate hireDate;

        private String deptNo;
    }
}
