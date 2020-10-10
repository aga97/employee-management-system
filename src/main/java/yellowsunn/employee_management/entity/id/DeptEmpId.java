package yellowsunn.employee_management.entity.id;

import lombok.*;
import yellowsunn.employee_management.entity.Department;
import yellowsunn.employee_management.entity.Employee;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
@Getter
@EqualsAndHashCode
public class DeptEmpId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_no")
    private Department department;
}
