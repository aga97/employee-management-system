package yellowsunn.employee_management.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import yellowsunn.employee_management.entity.Employee;
import yellowsunn.employee_management.entity.Salary;
import yellowsunn.employee_management.entity.id.SalaryId;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Sql({
        "/sample_datasets/employees.sql",
        "/sample_datasets/salaries.sql"
})
@DataJpaTest
class SalaryRepositoryTest {

    @Autowired
    SalaryRepository salaryRepository;
    @Autowired
    TestEntityManager em;

    @Test
    public void save_test() throws Exception {
        //given
        Integer empNo = 10017;
        LocalDate fromDate = LocalDate.now();
        Employee employee = em.find(Employee.class, empNo);
        int salary = 100000;
        LocalDate toDate = LocalDate.parse("9999-01-01");

        SalaryId salaryId = SalaryId.builder()
                .empNo(empNo)
                .fromDate(fromDate)
                .build();

        long count = salaryRepository.count();

        //when
        salaryRepository.save(Salary.builder()
                .id(salaryId)
                .employee(employee)
                .salary(salary)
                .toDate(toDate)
                .build());

        //then
        assertThat(salaryRepository.count()).isEqualTo(count + 1);
    }

    @Test
    public void 기본키중복_테스트() throws Exception {
        //given
        Integer empNo = 10017;
        LocalDate fromDate = LocalDate.parse("1993-08-03");

        Employee employee = em.find(Employee.class, empNo);
        int salary = 100000;
        LocalDate toDate = LocalDate.parse("9999-01-01");

        SalaryId salaryId = SalaryId.builder()
                .empNo(empNo)
                .fromDate(fromDate)
                .build();

        salaryRepository.findById(salaryId);

        //when
        //then
        assertThrows(EntityExistsException.class, () -> {
            em.persist(Salary.builder()
                    .id(salaryId)
                    .employee(employee)
                    .salary(salary)
                    .toDate(toDate)
                    .build());
        });
    }

    @Test
    public void find_test() throws Exception {
        //given
        Integer empNo = 10017;
        LocalDate fromDate = LocalDate.parse("1993-08-03");

        SalaryId salaryId = SalaryId.builder()
                .empNo(empNo)
                .fromDate(fromDate)
                .build();

        //when
        Optional<Salary> findSalary = salaryRepository.findById(salaryId);

        //then
        findSalary.ifPresentOrElse(salary -> {
            assertThat(salary.getId()).isEqualTo(salaryId);
        }, Assertions::fail);
    }

    @Test
    public void delete_test() throws Exception {
        //given
        Integer empNo = 10017;
        LocalDate fromDate = LocalDate.parse("1993-08-03");

        SalaryId salaryId = SalaryId.builder()
                .empNo(empNo)
                .fromDate(fromDate)
                .build();

        long count = salaryRepository.count();

        //when
        salaryRepository.deleteById(salaryId);

        //then
        assertThat(salaryRepository.count()).isEqualTo(count - 1);
    }

    @Test
    public void findCurrentByEmployeeIn_test() throws Exception {
        //given
        List<Salary> findSalaries = salaryRepository.findAll();
        List<Employee> employees = findSalaries.stream()
                .map(Salary::getEmployee)
                .distinct()
                .filter(employee -> employee.getEmpNo() < 10100)
                .collect(Collectors.toList());

        //when
        List<Salary> curSalaries = salaryRepository.findLatestByEmployeeIn(employees);


        //then
        List<String> collect = curSalaries.stream()
                .map(salary -> salary.getId().getEmpNo() + "_" + salary.getSalary())
                .collect(Collectors.toList());

        assertThat(collect)
                .contains("10017_99651")
                .contains("10042_94868")
                .contains("10055_90843")
                .contains("10058_72542")
                .doesNotContain("10042_95035")
                .doesNotContain("10108_45664");
    }
}