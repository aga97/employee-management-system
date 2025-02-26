package yellowsunn.employee_management.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yellowsunn.employee_management.dto.EmpDto;
import yellowsunn.employee_management.dto.EmpSearchDto;
import yellowsunn.employee_management.entity.*;
import yellowsunn.employee_management.entity.id.DeptEmpId;
import yellowsunn.employee_management.entity.id.DeptManagerId;
import yellowsunn.employee_management.entity.id.SalaryId;
import yellowsunn.employee_management.entity.id.TitleId;
import yellowsunn.employee_management.repository.*;
import yellowsunn.employee_management.service.EmployeeService;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final DeptEmpRepository deptEmpRepository;
    private final SalaryRepository salaryRepository;
    private final TitleRepository titleRepository;
    private final DeptManagerRepository deptManagerRepository;

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
                            .retirement(!deptEmp.getToDate().isEqual(LocalDate.of(9999, 1, 1)))
                            .build();
                }).collect(Collectors.toList());

        return new SliceImpl<>(content, pageable, deptEmpSlice.hasNext());
    }

    @Override
    public EmpDto.Info findInfoByEmpNo(Integer empNo) {
        Optional<Employee> empOptional = employeeRepository.findById(empNo);

        EmpDto.Info.InfoBuilder builder = EmpDto.Info.builder()
                .empty(empOptional.isEmpty());

        empOptional.ifPresent(employee -> {
            EmpDto.Info.Content.ContentBuilder contentBuilder = EmpDto.Info.Content.builder()
                    .empNo(employee.getEmpNo())
                    .firstName(employee.getFirstName())
                    .lastName(employee.getLastName())
                    .birthDate(employee.getBirthDate())
                    .gender(employee.getGender())
                    .hireDate(employee.getHireDate());

            deptEmpRepository.findLatestByEmployee(employee).ifPresent(deptEmp -> {
                contentBuilder
                        .deptNo(deptEmp.getDepartment().getDeptNo())
                        .deptName(deptEmp.getDepartment().getDeptName());

                // 은퇴한 경우
                if (!deptEmp.getToDate().isAfter(LocalDate.now())) {
                    contentBuilder.retirement(true);
                    contentBuilder.retireDate(deptEmp.getToDate());
                }
            });

            salaryRepository.findLatestByEmployee(employee).ifPresent(salary -> {
                contentBuilder.Salary(salary.getSalary());
            });

            titleRepository.findLatestByEmployee(employee).ifPresent(title -> {
                contentBuilder.title(title.getId().getTitle());
            });

            builder.content(contentBuilder.build());
        });

        return builder.build();
    }

    @Override
    public EmpDto.DeptHistory findDeptHistoryByEmpNo(Integer empNo) {
        EmpDto.DeptHistory.DeptHistoryBuilder builder = EmpDto.DeptHistory.builder();
        Optional<Employee> empOptional = employeeRepository.findById(empNo);

        List<EmpDto.DeptHistory.Content> contentList = new ArrayList<>();
        empOptional.ifPresentOrElse(employee -> {
            List<DeptEmp> deptEmps = deptEmpRepository.findByEmployee(employee);
            deptEmps.forEach(deptEmp -> {
                contentList.add(EmpDto.DeptHistory.Content.builder()
                        .DeptName(deptEmp.getDepartment().getDeptName())
                        .fromDate(deptEmp.getFromDate())
                        .toDate(deptEmp.getToDate())
                        .build());
            });
            builder.size(deptEmps.size());
        }, () -> {
            builder.size(0);
        });

        return builder.content(contentList).build();
    }

    @Override
    public EmpDto.TitleHistory findTitleHistoryByEmpNo(Integer empNo) {
        EmpDto.TitleHistory.TitleHistoryBuilder builder = EmpDto.TitleHistory.builder();
        Optional<Employee> empOptional = employeeRepository.findById(empNo);

        List<EmpDto.TitleHistory.Content> contentList = new ArrayList<>();
        empOptional.ifPresentOrElse(employee -> {
            List<Title> titles = titleRepository.findByEmployee(employee);
            titles.forEach(title -> {
                contentList.add(EmpDto.TitleHistory.Content.builder()
                        .Title(title.getId().getTitle())
                        .fromDate(title.getId().getFromDate())
                        .toDate(title.getToDate())
                        .build());
            });
            builder.size(titles.size());
        }, () -> {
            builder.size(0);
        });

        return builder.content(contentList).build();
    }

    @Override
    public EmpDto.SalaryHistory findSalaryHistoryByEmpNo(Integer empNo) {
        EmpDto.SalaryHistory.SalaryHistoryBuilder builder = EmpDto.SalaryHistory.builder();
        Optional<Employee> empOptional = employeeRepository.findById(empNo);

        List<EmpDto.SalaryHistory.Content> contentList = new ArrayList<>();
        empOptional.ifPresentOrElse(employee -> {
            List<Salary> salaries = salaryRepository.findByEmployee(employee);
            salaries.forEach(salary -> {
                contentList.add(EmpDto.SalaryHistory.Content.builder()
                        .salary(salary.getSalary())
                        .fromDate(salary.getId().getFromDate())
                        .toDate(salary.getToDate())
                        .build());
            });
            builder.size(salaries.size());
        }, () -> {
            builder.size(0);
        });

        return builder.content(contentList).build();
    }

    @Override
    @Transactional
    public EmpDto.Success create(EmpDto.Create dto) {
        EmpDto.Create.Content content = dto.getContent();

        Optional<Department> deptOptional = departmentRepository.findById(content.getDeptNo());
        if (deptOptional.isEmpty()) {
            throw new IllegalStateException("Invalid department.");
        }

        // 부서에 현직 매니저가 있는지 확인
        if (content.getTitle().equals("Manager")) {
            if (deptManagerRepository.findCurrentByDeptNo(content.getDeptNo()).isPresent()) {
                throw new IllegalStateException("Manager is already exists.");
            }
        }

        Employee employee = employeeRepository.save(Employee.builder()
                .firstName(content.getFirstName())
                .lastName(content.getLastName())
                .birthDate(content.getBirthDate())
                .gender(content.getGender())
                .hireDate(content.getHireDate())
                .build());

        deptEmpRepository.save(DeptEmp.builder()
                .id(DeptEmpId.builder()
                        .empNo(employee.getEmpNo())
                        .deptNo(content.getDeptNo())
                        .build())
                .employee(employee)
                .department(deptOptional.get())
                .fromDate(content.getHireDate())
                .toDate(LocalDate.of(9999, 1, 1))
                .build());

        titleRepository.save(Title.builder()
                .id(TitleId.builder()
                        .empNo(employee.getEmpNo())
                        .title(content.getTitle())
                        .fromDate(content.getHireDate())
                        .build())
                .employee(employee)
                .toDate(LocalDate.of(9999, 1, 1))
                .build());

        salaryRepository.save(Salary.builder()
                .id(SalaryId.builder()
                        .empNo(employee.getEmpNo())
                        .fromDate(content.getHireDate())
                        .build())
                .employee(employee)
                .salary(content.getSalary())
                .toDate(LocalDate.of(9999, 1, 1))
                .build());

        return EmpDto.Success.builder()
                .empNo(employee.getEmpNo())
                .success(true)
                .build();
    }

    @Override
    @Transactional
    public EmpDto.Success update(EmpDto.Update dto) {
        EmpDto.Update.Content content = dto.getContent();
        Optional<Employee> empOptional = employeeRepository.findById(content.getEmpNo());

        if (empOptional.isEmpty()) {
            throw new IllegalStateException("The employee could not be found.");
        }
        Employee employee = empOptional.get();

        if (!dto.isDeptNo() && !dto.isTitle() && !dto.isSalary()) {
            return EmpDto.Success.builder()
                    .success(false)
                    .build();
        }

        // 연봉, 부서, 직책은 각각 하루에 한번만 변경허용
        validateOnce(dto, employee);

        // 변화 감지
        if (dto.isSalary()) updateSalary(content, employee);
        updateDeptManager(dto, employee, deptManagerRepository.findCurrentByEmployee(employee)); //매니저에서 변경하거나, 매니저로 변경하는 경우 deptManager 수정
        if (dto.isDeptNo()) updateDepartment(content, employee);
        if (dto.isTitle()) updateTitle(content, employee);

        return EmpDto.Success.builder()
                .success(true)
                .build();
    }

    @Override
    @Transactional
    public EmpDto.Success retire(Integer empNo) {
        Optional<Employee> empOptional = employeeRepository.findById(empNo);
        if (empOptional.isEmpty()) {
            throw new IllegalStateException("The employee could not be found.");
        }
        Employee employee = empOptional.get();

        deptEmpRepository.findCurrentByEmployee(employee).ifPresentOrElse(DeptEmp::changeToDateNow, () -> {
            throw new IllegalStateException("Retired employee cannot be changed.");
        });

        titleRepository.findCurrentByEmployee(employee).ifPresentOrElse(Title::changeToDateNow, () -> {
            throw new IllegalStateException("Retired employee cannot be changed.");
        });

        salaryRepository.findCurrentByEmployee(employee).ifPresentOrElse(Salary::changeToDateNow, () -> {
            throw new IllegalStateException("Retired employee cannot be changed.");
        });

        // 매니저인 경우
        deptManagerRepository.findCurrentByEmployee(employee).ifPresent(DeptManager::changeToDateNow);

        return EmpDto.Success.builder()
                .success(true)
                .build();
    }

    private void updateDepartment(EmpDto.Update.Content content, Employee employee) {
        Optional<Department> deptOptional = departmentRepository.findById(content.getDeptNo());
        Optional<DeptEmp> deptEmpOptional = deptEmpRepository.findCurrentByEmployee(employee);
        if (deptOptional.isEmpty()) {
            throw new IllegalStateException("Invalid department.");
        }
        if (deptEmpOptional.isEmpty()) {
            throw new IllegalStateException("Retired employee cannot be changed.");
        }

        DeptEmp deptEmp = deptEmpOptional.get();
        if (content.getDeptNo().equals(deptEmp.getDepartment().getDeptNo())) {
            throw new IllegalStateException("The department has not changed.");
        }

        deptEmp.changeToDateNow();
        deptEmpRepository.persist(DeptEmp.builder()
                .id(DeptEmpId.builder()
                        .empNo(deptEmp.getEmployee().getEmpNo())
                        .deptNo(deptOptional.get().getDeptNo())
                        .build())
                .employee(employee)
                .department(deptOptional.get())
                .fromDate(LocalDate.now())
                .toDate(LocalDate.of(9999, 1, 1))
                .department(deptOptional.get())
                .build());
    }

    private void updateDeptManager(EmpDto.Update dto, Employee employee, Optional<DeptManager> deptManagerOptional) {
        EmpDto.Update.Content content = dto.getContent();
        Optional<Department> deptOptional = departmentRepository.findById(content.getDeptNo());
        if (deptOptional.isEmpty()) {
            throw new IllegalStateException("Invalid department.");
        }

        // 매니저에서 변경하는 경우
        if (deptManagerOptional.isPresent() && (dto.isDeptNo() || dto.isTitle())) {
            deptManagerOptional.get().changeToDateNow();
        }
        // 매니저로 변경하는 경우
        if (content.getTitle().equals("Manager") && (dto.isDeptNo() || dto.isTitle())) {
            if (deptManagerRepository.findCurrentByDeptNo(content.getDeptNo()).isPresent()) {
                throw new IllegalStateException("Manager is already exists.");
            }
            deptManagerRepository.persist(DeptManager.builder()
                    .id(DeptManagerId.builder()
                            .empNo(content.getEmpNo())
                            .deptNo(content.getTitle())
                            .build())
                    .employee(employee)
                    .department(deptOptional.get())
                    .fromDate(LocalDate.now())
                    .toDate(LocalDate.of(9999, 1, 1))
                    .build());
        }
    }

    private void updateTitle(EmpDto.Update.Content content, Employee employee) {
        Optional<Title> titleOptional = titleRepository.findCurrentByEmployee(employee);
        if (titleOptional.isEmpty()) {
            throw new IllegalStateException("Retired employee cannot be changed.");
        }

        Title title = titleOptional.get();
        if (content.getTitle().equals(title.getId().getTitle())) {
            throw new IllegalStateException("The Title has not changed.");
        }

        title.changeToDateNow();
        titleRepository.persist(Title.builder()
                .id(TitleId.builder()
                        .empNo(title.getEmployee().getEmpNo())
                        .title(content.getTitle())
                        .fromDate(LocalDate.now())
                        .build())
                .employee(employee)
                .toDate(LocalDate.of(9999, 1, 1))
                .build());
    }

    private void updateSalary(EmpDto.Update.Content content, Employee employee) {
        Optional<Salary> salaryOptional = salaryRepository.findCurrentByEmployee(employee);
        if (salaryOptional.isEmpty()) {
            throw new IllegalStateException("Retired employee cannot be changed.");
        }

        Salary salary = salaryOptional.get();
        if (content.getSalary() == salary.getSalary()) {
            throw new IllegalStateException("The salary has not changed.");
        }

        salary.changeToDateNow();
        salaryRepository.persist(Salary.builder()
                .id(SalaryId.builder()
                        .empNo(salary.getEmployee().getEmpNo())
                        .fromDate(LocalDate.now())
                        .build())
                .employee(employee)
                .salary(content.getSalary())
                .toDate(LocalDate.of(9999, 1, 1))
                .build());
    }

    /**
     * 하루에 여러번 변경할 수 없다.
     */
    private void validateOnce(EmpDto.Update dto, Employee employee) {
        if (dto.isSalary()) {
            salaryRepository.findCurrentByEmployee(employee).ifPresent(salary -> {
                if (salary.getId().getFromDate().isEqual(LocalDate.now()))
                    throw new IllegalStateException("You can only change it once a day");
            });
        }
        if (dto.isDeptNo()) {
            deptEmpRepository.findCurrentByEmployee(employee).ifPresent(deptEmp -> {
                if (deptEmp.getFromDate().isEqual(LocalDate.now()))
                    throw new IllegalStateException("You can only change it once a day");
            });
        }
        if (dto.isTitle()) {
            titleRepository.findCurrentByEmployee(employee).ifPresent(title -> {
                if (title.getId().getFromDate().isEqual(LocalDate.now()))
                    throw new IllegalStateException("You can only change it once a day");
            });
        }
    }
}
