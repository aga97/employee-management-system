package yellowsunn.employee_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yellowsunn.employee_management.entity.Employee;
import yellowsunn.employee_management.entity.Salary;
import yellowsunn.employee_management.entity.id.SalaryId;
import yellowsunn.employee_management.repository.custom.SalaryRepositoryCustom;

import java.util.List;

public interface SalaryRepository extends JpaRepository<Salary, SalaryId>, SalaryRepositoryCustom {

    /**
     * 특정 직원이 받았던 연봉 내역을 가져온다.
     */
    List<Salary> findByEmployee(Employee employee);
}
