package yellowsunn.employee_management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yellowsunn.employee_management.dto.TestDepEmpDto;
import yellowsunn.employee_management.entity.Department;
import yellowsunn.employee_management.entity.Employee;
import yellowsunn.employee_management.repository.DeptEmpRepository;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final DeptEmpRepository deptEmpRepository;

    @GetMapping("/")
    public Page<TestDepEmpDto> deptEmpJoin(Pageable pageable) {
        return deptEmpRepository.findCurrentAll(pageable)
                .map(deptEmp -> {
                    Employee employee = deptEmp.getEmployee();
                    Department department = deptEmp.getDepartment();
                    return new TestDepEmpDto(employee.getEmpNo(), employee.getFirstName(), employee.getLastName(), employee.getGender(),
                            employee.getBirthDate(), employee.getHireDate(), department.getDeptName(), deptEmp.getFromDate(), deptEmp.getToDate());
                });
    }
}
