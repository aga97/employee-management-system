package yellowsunn.employee_management.entity.id;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
@Getter
@EqualsAndHashCode
public class SalaryId implements Serializable {

    private Integer empNo;

    private LocalDate fromDate;
}
