package yellowsunn.employee_management.repository.custom;

import yellowsunn.employee_management.dto.DeptDto;
import yellowsunn.employee_management.entity.DeptEmp;
import yellowsunn.employee_management.entity.Employee;
import yellowsunn.employee_management.entity.Salary;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface SalaryRepositoryCustom {

    /**
     * 주어진 직원들의 가장 최근 Salary 를 반환한다.
     */
    List<Salary> findLatestByEmployeeIn(Collection<Employee> employees);

    /**
     * 주어진 현직 직원들의 현재 Salary 를 반환한다.
     */
    List<Salary> findCurrentByEmployeeIn(Collection<Employee> employees);

    /**
     * 특정 직원의 가장 최근 Salary 를 반환한다.
     */
    Optional<Salary> findLatestByEmployee(Employee employee);

    /**
     * 부서별 현직 직원들의 직책별 연봉 정보를 반환한다.
     */
    List<DeptDto.SalaryInfo> findCurByDeptNoGroupByTitle(String deptNo);
}
