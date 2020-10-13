package yellowsunn.employee_management.entity;

import lombok.*;
import yellowsunn.employee_management.entity.id.TitleId;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
@Getter
@Table(name = "titles")
public class Title {

    @EmbeddedId
    private TitleId id;

    @MapsId("empNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no")
    private Employee employee;

    /**
     * TitleId
     * - String title;
     * - LocalDate fromDate
     */

    @Column(nullable = false)
    private LocalDate toDate;
}
