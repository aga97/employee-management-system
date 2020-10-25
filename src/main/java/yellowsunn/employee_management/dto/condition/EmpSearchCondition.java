package yellowsunn.employee_management.dto.condition;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import yellowsunn.employee_management.entity.Gender;

import java.time.LocalDate;

/**
 * 직원 검색 조건
 */
@Data
public class EmpSearchCondition {

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
