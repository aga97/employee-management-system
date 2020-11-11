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

    public void changeToDateNow() {
        // toDate가 9999-01-01인 경우만 change
        if (toDate.isEqual(LocalDate.of(9999, 1, 1))) {
            this.toDate = LocalDate.now();
        }
    }
}
