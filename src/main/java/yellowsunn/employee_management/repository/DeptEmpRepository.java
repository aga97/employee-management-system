package yellowsunn.employee_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yellowsunn.employee_management.entity.DeptEmp;
import yellowsunn.employee_management.entity.id.DeptEmpId;

public interface DeptEmpRepository extends JpaRepository<DeptEmp, DeptEmpId> {
}
