package yellowsunn.employee_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yellowsunn.employee_management.entity.DeptManager;
import yellowsunn.employee_management.entity.id.DeptManagerId;
import yellowsunn.employee_management.repository.custom.DeptManagerRepositoryCustom;

public interface DeptManagerRepository extends JpaRepository<DeptManager, DeptManagerId>, DeptManagerRepositoryCustom {
}
