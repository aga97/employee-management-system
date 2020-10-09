package yellowsunn.employee_management.entity.id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import yellowsunn.employee_management.entity.Employee;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Getter
@EqualsAndHashCode
public class SalaryId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no")
    private Employee employee;

    private LocalDate fromDate;
}
