package yellowsunn.employee_management.dto.condition;

import lombok.Data;
import yellowsunn.employee_management.entity.Gender;

import java.time.LocalDate;

@Data
public class EmpSearchCondition {

    private Integer empNo;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private LocalDate hireDate;
    private String deptNo;
}
