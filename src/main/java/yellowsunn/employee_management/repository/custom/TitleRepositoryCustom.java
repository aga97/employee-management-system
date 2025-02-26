package yellowsunn.employee_management.repository.custom;

import org.springframework.data.domain.Sort;
import yellowsunn.employee_management.entity.Employee;
import yellowsunn.employee_management.entity.Salary;
import yellowsunn.employee_management.entity.Title;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TitleRepositoryCustom {

    /**
     * 주어진 직원들의 가장 최근 Title 을 반환한다.
     */
    List<Title> findLatestByEmployeeIn(Collection<Employee> employees);

    /**
     * 주어진 직원들의 현재 Title 를 반환한다. (퇴사한 직원 반환 X)
     */
    List<Title> findCurrentByEmployeeIn(Collection<Employee> employees);


    /**
     * 특정 직원이 맡았던 Title들을 전부 반환한다.
     */
    List<Title> findByEmployee(Employee employee);

    /**
     * 특정 직원의 가장 최근 Title 를 반환한다.
     */
    Optional<Title> findLatestByEmployee(Employee employee);

    /**
     * 특정 직원의 현재 Title 을 반환한다. (퇴사한 직원 반환 X)
     */
    Optional<Title> findCurrentByEmployee(Employee employee);

    /**
     * 모든 직책 목록을 반환한다.
     */
    List<String> findTitles(Sort sort);

    <S extends Title> void persist(S entity);
}
