package yellowsunn.employee_management.repository.custom;

import yellowsunn.employee_management.dto.DeptDto;

import java.util.List;

public interface EmployeeRepositoryCustom {

    /**
     * 부서별로 현직 직원의 성비를 구한다.
     */
    List<DeptDto.GenderInfo> findCurGenderByDeptNo(String DeptNo);
}
