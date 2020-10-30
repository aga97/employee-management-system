package yellowsunn.employee_management.repository.custom;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import yellowsunn.employee_management.dto.EmpSearchDto;
import yellowsunn.employee_management.entity.DeptEmp;
import yellowsunn.employee_management.entity.Employee;

import java.util.Optional;

public interface DeptEmpRepositoryCustom {

    /**
     * 주어진 조건에서 모든 직원들의 DeptEmp 를 반환한다. (직원별로 가장 최근 부서만 반환)
     */
    Slice<DeptEmp> findByCondition(EmpSearchDto.Condition condition, Pageable pageable);

    /**
     * 주어진 조건에서 현직 직원들의 DeptEmp 를 반환한다. (직원별로 현재 부서만 반환)
     */
    Slice<DeptEmp> findCurrentByCondition(EmpSearchDto.Condition condition, Pageable pageable);

    /**
     * 특정 직원의 DeptEmp 를 반환한다. (가장 최근 부서만 반)
     */
    Optional<DeptEmp> findLatestByEmployee(Employee employee);

    /**
     * 부서별 현직 직원들의 총인원를 구한다.
     */
    long countCurrentByDeptNo(String deptNo);
}
