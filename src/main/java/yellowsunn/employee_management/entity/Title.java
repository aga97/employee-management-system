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

    /**
     * TitleId
     * - Employee employee;
     * - String title;
     * - LocalDate fromDate
     */
    @EmbeddedId
    private TitleId id;

    @Column(nullable = false)
    private LocalDate toDate;
}
