package yellowsunn.employee_management.service;

import org.springframework.data.domain.Sort;
import yellowsunn.employee_management.dto.DepartmentsDto;

public interface DepartmentService {

    DepartmentsDto findAll(Sort sort);
}
