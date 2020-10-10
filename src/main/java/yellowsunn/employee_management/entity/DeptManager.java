package yellowsunn.employee_management.entity;

import lombok.*;
import yellowsunn.employee_management.entity.id.DeptManagerId;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
@Getter
public class DeptManager {

    /**
     * Department department;
     * Employee employee;
     */
    @EmbeddedId
    private DeptManagerId id;

    @Column(nullable = false)
    private LocalDate fromDate;

    @Column(nullable = false)
    private LocalDate toDate;
}
