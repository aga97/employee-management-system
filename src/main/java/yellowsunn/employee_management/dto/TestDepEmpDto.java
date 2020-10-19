package yellowsunn.employee_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import yellowsunn.employee_management.entity.Gender;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TestDepEmpDto {
    private Integer empNo;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate birthDate;
    private LocalDate hireDate;
    private String deptName;
    private LocalDate fromDate;
    private LocalDate toDate;
}
