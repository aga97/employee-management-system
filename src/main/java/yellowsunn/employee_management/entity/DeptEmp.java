package yellowsunn.employee_management.entity;

import lombok.Getter;
import yellowsunn.employee_management.entity.id.DeptEmpId;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Getter
public class DeptEmp {

    /**
     * DeptEmpId
     * - Employee employee;
     * - Department department;
     */
    @EmbeddedId
    private DeptEmpId id;

    @Column(nullable = false)
    private LocalDate fromDate;

    @Column(nullable = false)
    private LocalDate toDate;
}
