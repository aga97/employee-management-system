package yellowsunn.employee_management.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import yellowsunn.employee_management.entity.Employee;
import yellowsunn.employee_management.entity.Gender;
import yellowsunn.employee_management.entity.Title;
import yellowsunn.employee_management.entity.id.TitleId;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TitleRepositoryTest {

    @Autowired TitleRepository titleRepository;
    @Autowired TestEntityManager em;

    Integer empNo; //employee id
    TitleId titleId;

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

    private Title saveTitle() {
        Employee employee = em.find(Employee.class, empNo);
        String title = "Senior Engineer";
        LocalDate fromDate = LocalDate.parse("1986-06-26");
        LocalDate toDate = LocalDate.parse("9999-01-01");

        titleId = TitleId.builder()
                .employee(employee)
                .title(title)
                .fromDate(fromDate)
                .build();

        //when
        return titleRepository.save(Title.builder()
                .id(titleId)
                .toDate(toDate)
                .build());
    }

    @Test
    public void save_test() throws Exception {
        Title saveTitle = saveTitle();

        //then
        assertThat(saveTitle.getId()).isEqualTo(titleId);
    }

    @Test
    public void find_test() throws Exception {
        //given
        saveTitle();

        //when
        Title title = titleRepository.findById(titleId).orElse(null);

        //then
        assertThat(title).isNotNull();
        assertThat(title.getId()).isEqualTo(titleId);
    }

    @Test
    public void delete_test() throws Exception {
        //given
        saveTitle();
        long count = titleRepository.count();

        //when
        titleRepository.deleteById(titleId);

        //then
        assertThat(titleRepository.count()).isEqualTo(count - 1);
    }
}