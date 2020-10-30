package yellowsunn.employee_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yellowsunn.employee_management.entity.DeptEmp;
import yellowsunn.employee_management.entity.Employee;
import yellowsunn.employee_management.entity.id.DeptEmpId;
import yellowsunn.employee_management.repository.custom.DeptEmpRepositoryCustom;

import java.util.List;

public interface DeptEmpRepository extends JpaRepository<DeptEmp, DeptEmpId>, DeptEmpRepositoryCustom {

    List<DeptEmp> findByEmployee(Employee employee);
}
