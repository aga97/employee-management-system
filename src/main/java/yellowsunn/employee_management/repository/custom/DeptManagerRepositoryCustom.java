package yellowsunn.employee_management.repository.custom;

import yellowsunn.employee_management.entity.DeptManager;

import java.util.List;
import java.util.Optional;

public interface DeptManagerRepositoryCustom {

    /**
     * 부서별 역대 매니저들을 전부 찾는다.
     */
    List<DeptManager> findByDeptNo(String deptNo);

    /**
     * 부서별 현직 매니저를 찾는다.
     */
    Optional<DeptManager> findCurrentByDeptNo(String deptNo);
}
