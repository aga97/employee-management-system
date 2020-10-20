package yellowsunn.employee_management.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import yellowsunn.employee_management.dto.condition.EmpSearchCondition;
import yellowsunn.employee_management.entity.DeptEmp;

public interface DeptEmpRepositoryCustom {

    /**
     * 주어진 조건에서 직원들의 가장 최근 DeptEmp 를 반환한다.
     */
    Page<DeptEmp> findCurrentByCondition(EmpSearchCondition condition, Pageable pageable);
}
