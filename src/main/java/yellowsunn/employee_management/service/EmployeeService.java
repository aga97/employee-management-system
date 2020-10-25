package yellowsunn.employee_management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import yellowsunn.employee_management.dto.CurEmpInfoDto;
import yellowsunn.employee_management.dto.condition.EmpSearchCondition;

public interface EmployeeService {

    /**
     * 주어진 조건에 맞는 가장 최근 직원 정보들을 CurEmpInfoDto로 매핑해 Page로 반환
     */
    Page<CurEmpInfoDto> findCurrentEmployees(EmpSearchCondition condition, Pageable pageable);
}
