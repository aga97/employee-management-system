package yellowsunn.employee_management.entity;

import lombok.*;
import yellowsunn.employee_management.entity.id.DeptEmpId;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class DeptEmp {

    @EmbeddedId
    private DeptEmpId id;

    @MapsId("empNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no")
    private Employee employee;

    @MapsId("deptNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_no")
    private Department department;

    @Column(nullable = false)
    private LocalDate fromDate;

    @Column(nullable = false)
    private LocalDate toDate;

    public void changeToDateNow() {
        // toDate가 9999-01-01인 경우만 change
        if (toDate.isEqual(LocalDate.of(9999, 1, 1))) {
            this.toDate = LocalDate.now();
        }
    }
}
