package yellowsunn.employee_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yellowsunn.employee_management.entity.Employee;
import yellowsunn.employee_management.entity.Salary;
import yellowsunn.employee_management.entity.id.SalaryId;
import yellowsunn.employee_management.repository.custom.SalaryRepositoryCustom;

import java.util.List;

public interface SalaryRepository extends JpaRepository<Salary, SalaryId>, SalaryRepositoryCustom {

    List<Salary> findByEmployee(Employee employee);
}
