package yellowsunn.employee_management.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import yellowsunn.employee_management.dto.EmpDto;
import yellowsunn.employee_management.entity.*;
import yellowsunn.employee_management.entity.id.DeptEmpId;
import yellowsunn.employee_management.entity.id.SalaryId;
import yellowsunn.employee_management.entity.id.TitleId;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@Sql({
        "/sample_datasets/employees.sql",
        "/sample_datasets/departments.sql",
        "/sample_datasets/dept_emp.sql",
        "/sample_datasets/dept_manager.sql",
        "/sample_datasets/salaries.sql",
        "/sample_datasets/titles.sql"
})
@DataJpaTest(showSql = false)
public class IntegrationRepositoryTest {

    @Autowired EmployeeRepository employeeRepository;
    @Autowired DepartmentRepository departmentRepository;
    @Autowired DeptEmpRepository deptEmpRepository;
    @Autowired TitleRepository titleRepository;
    @Autowired SalaryRepository salaryRepository;

    @Test
    public void 직원생성() throws Exception {
        EmpDto.Create.Content content = new EmpDto.Create.Content();
        content.setFirstName("first");
        content.setLastName("last");
        content.setBirthDate(LocalDate.of(1953, 9, 2));
        content.setGender(Gender.M);
        content.setHireDate(LocalDate.of(1986, 6, 26));
        content.setDeptNo("d009");
        content.setSalary(9870);
        content.setTitle("Staff");
        EmpDto.Create dto = new EmpDto.Create();
        dto.setContent(content);

        Employee employee = employeeRepository.save(Employee.builder()
                .firstName(content.getFirstName())
                .lastName(content.getLastName())
                .birthDate(content.getBirthDate())
                .gender(content.getGender())
                .hireDate(content.getHireDate())
                .build());
        assertThat(employee.getEmpNo()).isNotNull();

        Optional<Department> deptOptional = departmentRepository.findById(content.getDeptNo());
        if (deptOptional.isEmpty()) {
            Assertions.fail();
        }
        Department department = deptOptional.get();
        DeptEmp deptEmp = deptEmpRepository.save(DeptEmp.builder()
                .id(DeptEmpId.builder()
                        .empNo(employee.getEmpNo())
                        .deptNo(department.getDeptNo())
                        .build())
                .employee(employee)
                .department(department)
                .fromDate(content.getHireDate())
                .toDate(LocalDate.of(9999, 1, 1))
                .build());

        Title title = titleRepository.save(Title.builder()
                .id(TitleId.builder()
                        .empNo(employee.getEmpNo())
                        .title(content.getTitle())
                        .fromDate(content.getHireDate())
                        .build())
                .employee(employee)
                .toDate(LocalDate.of(9999, 1, 1))
                .build());

        Salary salary = salaryRepository.save(Salary.builder()
                .id(SalaryId.builder()
                        .empNo(employee.getEmpNo())
                        .fromDate(content.getHireDate())
                        .build())
                .employee(employee)
                .salary(content.getSalary())
                .toDate(LocalDate.of(9999, 1, 1))
                .build());

        assertThat(deptEmp.getId().getDeptNo()).isEqualTo(content.getDeptNo());
        assertThat(title.getId().getTitle()).isEqualTo(content.getTitle());
        assertThat(salary.getSalary()).isEqualTo(content.getSalary());
    }
}
