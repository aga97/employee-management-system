package yellowsunn.employee_management.entity;

import lombok.*;
import yellowsunn.employee_management.entity.id.SalaryId;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
@Getter
@Table(name = "salaries")
public class Salary {

    @EmbeddedId
    private SalaryId id;

    @MapsId("empNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no")
    private Employee employee;

    @Column(nullable = false)
    private int salary;

    /**
     * SalaryId
     * - private LocalDate fromDate;
     */

    @Column(nullable = false)
    private LocalDate toDate;

    public void changeToDateNow() {
        // toDate가 9999-01-01인 경우만 change
        if (toDate.isEqual(LocalDate.of(9999, 1, 1))) {
            this.toDate = LocalDate.now();
        }
    }
}
