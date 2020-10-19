package yellowsunn.employee_management.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import yellowsunn.employee_management.entity.DeptEmp;

public interface DeptEmpRepositoryCustom {

    /**
     * 직원들의 가장 최근 DeptEmp 를 반환한다.
     */
    Page<DeptEmp> findCurrentAll(Pageable pageable);
}
