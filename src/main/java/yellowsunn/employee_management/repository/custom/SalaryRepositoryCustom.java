package yellowsunn.employee_management.repository.custom;

import yellowsunn.employee_management.dto.DeptDto;
import yellowsunn.employee_management.entity.Employee;
import yellowsunn.employee_management.entity.Salary;

import java.util.Collection;
import java.util.List;

public interface SalaryRepositoryCustom {

    /**
     * 주어진 직원들의 가장 최근 Salary 를 반환한다.
     */
    List<Salary> findCurrentByEmployeeIn(Collection<Employee> employees);

    List<DeptDto.SalaryInfo> findCurByDeptNoGroupByTitle(String deptNo);
}
