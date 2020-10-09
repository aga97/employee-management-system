package yellowsunn.employee_management.entity;

import yellowsunn.employee_management.entity.id.SalaryId;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "salaries")
public class Salary {

    /**
     * Employee employee;
     * LocalDate fromDate;
     */
    @EmbeddedId
    private SalaryId id;

    @Column(nullable = false)
    private int salary;

    @Column(nullable = false)
    private LocalDate toDate;
}
