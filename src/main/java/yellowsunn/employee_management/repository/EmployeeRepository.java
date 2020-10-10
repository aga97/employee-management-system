package yellowsunn.employee_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yellowsunn.employee_management.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
