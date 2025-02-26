package yellowsunn.employee_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yellowsunn.employee_management.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, String> {
}
