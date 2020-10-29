package yellowsunn.employee_management.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import yellowsunn.employee_management.dto.EmpSearchDto;
import yellowsunn.employee_management.entity.*;
import yellowsunn.employee_management.repository.DeptEmpRepository;
import yellowsunn.employee_management.repository.SalaryRepository;
import yellowsunn.employee_management.repository.TitleRepository;
import yellowsunn.employee_management.service.EmployeeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final DeptEmpRepository deptEmpRepository;
    private final SalaryRepository salaryRepository;
    private final TitleRepository titleRepository;

    /**
     * 주주어진 조건에 맞는 직원 검색 정보를 가져온다. <br/>
     * 조건에 맞는 DeptEmp 객체들을 가져오면 @ManyToOne으로 조인된 Employee 객체들도 가져올 수 있다.
     * 가져온 Employee 객체들로 다시 Salary와 Title 객체들을 가져와서 Map형식으로 만든 다음 DTO로 조합하였다.
     */
    @Override
    public Slice<EmpSearchDto.Info> findSearchInfoByCondition(EmpSearchDto.Condition condition, Pageable pageable) {
        Slice<DeptEmp> deptEmpSlice = deptEmpRepository.findByCondition(condition, pageable);
        List<DeptEmp> deptEmps = deptEmpSlice.getContent();
        List<Employee> employees = deptEmps.stream()
                .map(DeptEmp::getEmployee)
                .collect(Collectors.toList());

        List<Salary> salaries = salaryRepository.findLatestByEmployeeIn(employees);
        List<Title> titles = titleRepository.findLatestByEmployeeIn(employees);

        // Map으로 만들어서 DTO 결합하는데 사용
        Map<Integer, Integer> salaryMap = new HashMap<>();
        salaries.forEach(salary -> {
            salaryMap.put(salary.getId().getEmpNo(), salary.getSalary());
        });

        Map<Integer, String> titleMap = new HashMap<>();
        titles.forEach(title -> {
            titleMap.put(title.getId().getEmpNo(), title.getId().getTitle());
        });

        List<EmpSearchDto.Info> content = deptEmps.stream()
                .map(deptEmp -> {
                    Employee employee = deptEmp.getEmployee();
                    Department department = deptEmp.getDepartment();
                    return EmpSearchDto.Info.builder()
                            .empNo(employee.getEmpNo())
                            .firstName(employee.getFirstName())
                            .lastName(employee.getLastName())
                            .birthDate(employee.getBirthDate())
                            .gender(employee.getGender())
                            .hireDate(employee.getHireDate())
                            .deptName(department.getDeptName())
                            .title(titleMap.get(employee.getEmpNo()))
                            .Salary(salaryMap.get(employee.getEmpNo()))
                            .build();
                }).collect(Collectors.toList());

        return new SliceImpl<>(content, pageable, deptEmpSlice.hasNext());
    }
}
