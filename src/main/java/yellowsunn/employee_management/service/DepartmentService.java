package yellowsunn.employee_management.service;

import org.springframework.data.domain.Sort;
import yellowsunn.employee_management.dto.DeptDto;

public interface DepartmentService {

    /**
     * 모든 부서 목록을 가져온다.
     */
    DeptDto.All findAll(Sort sort);

    /**
     * 부서별로 해당 부서의 정보를 가져온다.
     */
    DeptDto.Info findInfoByDeptNo(String deptNo);
}
