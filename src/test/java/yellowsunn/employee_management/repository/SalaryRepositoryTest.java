package yellowsunn.employee_management.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import yellowsunn.employee_management.entity.Employee;
import yellowsunn.employee_management.entity.Gender;
import yellowsunn.employee_management.entity.Salary;
import yellowsunn.employee_management.entity.id.SalaryId;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SalaryRepositoryTest {

    @Autowired SalaryRepository salaryRepository;
    @Autowired TestEntityManager em;

    Integer empNo; //employee id
    SalaryId salaryId;

    @BeforeEach
    public void persist_employee() {
        empNo = 10001;
        LocalDate birthDate = LocalDate.parse("1953-09-02");
        String firstName = "Georgi";
        String lastName = "Facello";
        Gender gender = Gender.M;
        LocalDate hireDate = LocalDate.parse("1986-06-26");

        Employee saveEmployee = em.persist(Employee.builder()
                .empNo(empNo)
                .birthDate(birthDate)
                .firstName(firstName)
                .lastName(lastName)
                .gender(gender)
                .hireDate(hireDate)
                .build());

        assertThat(saveEmployee).isNotNull();
        assertThat(saveEmployee.getEmpNo()).isEqualTo(empNo);
    }

    private Salary saveSalary() {
        Employee employee = em.find(Employee.class, empNo);
        int salary = 60117;
        LocalDate fromData = LocalDate.parse("1986-06-26");
        LocalDate toDate = LocalDate.parse("1987-06-26");

        salaryId = SalaryId.builder()
                .employee(employee)
                .fromDate(fromData)
                .build();

        return salaryRepository.save(Salary.builder()
                .id(salaryId)
                .salary(salary)
                .toDate(toDate)
                .build());
    }

    @Test
    public void save_test() throws Exception {
        Salary saveSalary = saveSalary();

        //then
        assertThat(saveSalary.getId()).isEqualTo(salaryId);
    }

    @Test
    public void find_test() throws Exception {
        //given
        saveSalary();

        //when
        Salary findSalary = salaryRepository.findById(salaryId).orElse(null);

        //then
        assertThat(findSalary).isNotNull();
        assertThat(findSalary.getId()).isEqualTo(salaryId);
    }

    @Test
    public void delete_test() throws Exception {
        //given
        saveSalary();
        long count = salaryRepository.count();

        //when
        salaryRepository.deleteById(salaryId);

        //then
        assertThat(salaryRepository.count()).isEqualTo(count - 1);
    }
}