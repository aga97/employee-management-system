package yellowsunn.employee_management.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import yellowsunn.employee_management.entity.Employee;
import yellowsunn.employee_management.entity.Gender;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("/sample_datasets/employees.sql")
@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired EmployeeRepository employeeRepository;

    @Test
    public void save_test() throws Exception {
        //given
        LocalDate birthDate = LocalDate.parse("1995-10-07");
        String firstName = "Hankook";
        String lastName = "Cho";
        Gender gender = Gender.M;
        LocalDate hireDate = LocalDate.now();

        long count = employeeRepository.count();

        //when
        Employee saveEmployee = employeeRepository.save(Employee.builder()
                .birthDate(birthDate)
                .firstName(firstName)
                .lastName(lastName)
                .gender(gender)
                .hireDate(hireDate)
                .build());

        //then
        assertThat(saveEmployee.getEmpNo()).isNotNull(); //id는 자동 생성 (IDENTIFY)
        assertThat(employeeRepository.count()).isEqualTo(count + 1);
    }

    @Test
    public void find_test() throws Exception {
        //given
        Integer empNo = 10017;

        //when
        Optional<Employee> employeeOptional = employeeRepository.findById(empNo);

        //then
        employeeOptional.ifPresentOrElse(employee -> {
            assertThat(employee.getEmpNo()).isEqualTo(empNo);
        }, Assertions::fail);
    }

    @Test
    public void delete_test() throws Exception {
        //given
        Integer empNo = 10017;
        long count = employeeRepository.count();

        //when
        employeeRepository.deleteById(empNo);

        //then
        assertThat(employeeRepository.count()).isEqualTo(count - 1);
    }
}