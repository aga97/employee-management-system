package yellowsunn.employee_management.repository.custom;

import yellowsunn.employee_management.entity.Employee;
import yellowsunn.employee_management.entity.Title;

import java.util.Collection;
import java.util.List;

public interface TitleRepositoryCustom {

    /**
     * 주어진 직원들의 가장 최근 Title 을 반환한다.
     */
    List<Title> findCurrentByEmployeeIn(Collection<Employee> employees);
}
