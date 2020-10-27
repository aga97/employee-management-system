package yellowsunn.employee_management.repository.custom;

import yellowsunn.employee_management.entity.DeptManager;

import java.util.Optional;

public interface DeptManagerRepositoryCustom {

    Optional<DeptManager> findCurrentByDeptNo(String deptNo);
}
