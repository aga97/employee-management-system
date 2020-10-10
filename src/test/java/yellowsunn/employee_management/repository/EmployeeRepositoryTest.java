package yellowsunn.employee_management.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import yellowsunn.employee_management.entity.Employee;
import yellowsunn.employee_management.entity.Gender;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired EmployeeRepository employeeRepository;
    Integer empNo; // employee id

    private Employee saveEmployee() {
        empNo = 10001;
        LocalDate birthDate = LocalDate.parse("1953-09-02");
        String firstName = "Georgi";
        String lastName = "Facello";
        Gender gender = Gender.M;
        LocalDate hireDate = LocalDate.parse("1986-06-26");

        return employeeRepository.save(Employee.builder()
                .empNo(empNo)
                .birthDate(birthDate)
                .firstName(firstName)
                .lastName(lastName)
                .gender(gender)
                .hireDate(hireDate)
                .build());
    }

    @Test
    public void save_test() throws Exception {
        Employee saveEmployee = saveEmployee();

        //then
        assertThat(saveEmployee.getEmpNo()).isEqualTo(empNo);
    }

    @Test
    public void find_test() throws Exception {
        //given
        saveEmployee();

        //when
        Employee findEmployee = employeeRepository.findById(empNo).orElse(null);

        //then
        assertThat(findEmployee).isNotNull();
        assertThat(findEmployee.getEmpNo()).isEqualTo(empNo);
    }

    @Test
    public void delete_test() throws Exception {
        //given
        saveEmployee();
        long count = employeeRepository.count();

        //when
        employeeRepository.deleteById(empNo);

        //then
        assertThat(employeeRepository.count()).isEqualTo(count - 1);
    }
}