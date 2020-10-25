package yellowsunn.employee_management.dto;

import lombok.Data;
import yellowsunn.employee_management.entity.Department;

import java.util.List;

/**
 * 전체 부서 정보
 */
@Data
public class DepartmentsDto {

    private List<Department> content;
    private int size;
}
