package yellowsunn.employee_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import yellowsunn.employee_management.entity.Gender;

import java.time.LocalDate;

/**
 * Current Employee Info
 */
@Data
@AllArgsConstructor
public class CurEmpInfoDto {

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
