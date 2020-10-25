package yellowsunn.employee_management.entity;

import lombok.*;
import yellowsunn.employee_management.entity.id.DeptManagerId;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
@Getter
public class DeptManager {

    @EmbeddedId
    private DeptManagerId id;

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
}
