package yellowsunn.employee_management.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
@Getter
@EqualsAndHashCode
@Table(name = "departments")
public class Department {

    @Id
    @Column(columnDefinition = "char(4)")
    private String deptNo;

    @Column(length = 40, unique = true, nullable = false)
    private String deptName;
}
