package yellowsunn.employee_management.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import yellowsunn.employee_management.entity.DeptEmp;

public interface DeptEmpRepositoryCustom {
    Page<DeptEmp> findCurrentAll(Pageable pageable);
}
