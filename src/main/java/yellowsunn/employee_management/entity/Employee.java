package yellowsunn.employee_management.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Table(name = "employees")
public class Employee {

    @Id
    private Integer empNo;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(length = 14, nullable = false)
    private String firstName;

    @Column(length = 16, nullable = false)
    private String lastName;

    @Column(length = 1, nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender; // M, F

    @Column(nullable = false)
    private LocalDate hireDate;
}
