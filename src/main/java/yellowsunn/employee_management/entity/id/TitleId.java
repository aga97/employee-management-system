package yellowsunn.employee_management.entity.id;

import lombok.*;
import yellowsunn.employee_management.entity.Employee;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
@Getter
@EqualsAndHashCode
public class TitleId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no")
    private Employee employee;

    @Column(length = 50)
    private String title;

    private LocalDate fromDate;
}
