package yellowsunn.employee_management.entity.id;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
@Getter
@EqualsAndHashCode
public class DeptEmpId implements Serializable {

    private Integer empNo;

    private String deptNo;
}
