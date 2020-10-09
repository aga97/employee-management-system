package yellowsunn.employee_management.entity.id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import yellowsunn.employee_management.entity.Department;
import yellowsunn.employee_management.entity.Employee;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Getter
@EqualsAndHashCode
public class DeptManagerId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_no")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no")
    private Employee employee;
}
