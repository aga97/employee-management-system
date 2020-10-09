package yellowsunn.employee_management.entity;

import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "departments")
@Getter
public class Department {

    @Id
    @Column(columnDefinition = "char(4)")
    private String deptNo;

    @Column(length = 40, unique = true, nullable = false)
    private String deptName;
}
