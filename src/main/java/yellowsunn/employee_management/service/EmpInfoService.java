package yellowsunn.employee_management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import yellowsunn.employee_management.dto.CurEmpInfoDto;
import yellowsunn.employee_management.dto.condition.EmpSearchCondition;
import yellowsunn.employee_management.entity.*;
import yellowsunn.employee_management.repository.DeptEmpRepository;
import yellowsunn.employee_management.repository.SalaryRepository;
import yellowsunn.employee_management.repository.TitleRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpInfoService {

    private final DeptEmpRepository deptEmpRepository;
    private final SalaryRepository salaryRepository;
    private final TitleRepository titleRepository;

    /**
     * 주어진 조건에서 가장 최근 직원 정보를 반환
     */
    public Page<CurEmpInfoDto> findCurrentEmployees(EmpSearchCondition condition, Pageable pageable) {
        Page<DeptEmp> deptEmpPage = deptEmpRepository.findCurrentByCondition(condition, pageable);
        List<DeptEmp> deptEmps = deptEmpPage.getContent();
        List<Employee> employees = deptEmps.stream()
                .map(DeptEmp::getEmployee)
                .collect(Collectors.toList());

        List<Salary> salaries = salaryRepository.findCurrentByEmployeeIn(employees);
        List<Title> titles = titleRepository.findCurrentByEmployeeIn(employees);

        // Map으로 만들어서 DTO 결합하는데 사용
        Map<Integer, Integer> salaryMap = new HashMap<>();
        salaries.forEach(salary -> {
            salaryMap.put(salary.getId().getEmpNo(), salary.getSalary());
        });

        Map<Integer, String> titleMap = new HashMap<>();
        titles.forEach(title -> {
            titleMap.put(title.getId().getEmpNo(), title.getId().getTitle());
        });

        List<CurEmpInfoDto> content = deptEmps.stream()
                .map(deptEmp -> {
                    Employee employee = deptEmp.getEmployee();
                    Department department = deptEmp.getDepartment();
                    return new CurEmpInfoDto(employee.getEmpNo(), employee.getFirstName(), employee.getLastName(), employee.getBirthDate(),
                            employee.getGender(), employee.getHireDate(), department.getDeptName(),
                            titleMap.get(employee.getEmpNo()), salaryMap.get(employee.getEmpNo()));
                }).collect(Collectors.toList());

        return new PageImpl<>(content, pageable, deptEmpPage.getTotalElements());
    }
}
