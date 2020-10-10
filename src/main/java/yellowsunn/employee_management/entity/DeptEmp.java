package yellowsunn.employee_management.entity;

import lombok.*;
import yellowsunn.employee_management.entity.id.DeptEmpId;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
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
