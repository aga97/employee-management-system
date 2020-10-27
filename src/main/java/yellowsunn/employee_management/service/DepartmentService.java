package yellowsunn.employee_management.service;

import org.springframework.data.domain.Sort;
import yellowsunn.employee_management.dto.DeptDto;

public interface DepartmentService {

    DeptDto.All findAll(Sort sort);

    DeptDto.Info findInfoByDeptNo(String DeptNo);
}
