package yellowsunn.employee_management.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import yellowsunn.employee_management.entity.Employee;
import yellowsunn.employee_management.entity.Title;
import yellowsunn.employee_management.entity.id.TitleId;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Sql({
        "/sample_datasets/employees.sql",
        "/sample_datasets/titles.sql"
})
@DataJpaTest
class TitleRepositoryTest {

    @Autowired TitleRepository titleRepository;
    @Autowired TestEntityManager em;

    @Test
    public void save_test() throws Exception {
        //given
        Integer empNo = 10017;
        Employee employee = em.find(Employee.class, empNo);
        String title = "Jobless";
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = LocalDate.parse("9999-01-01");

        TitleId titleId = TitleId.builder()
                .empNo(empNo)
                .title(title)
                .fromDate(fromDate)
                .build();

        long count = titleRepository.count();

        //when
        titleRepository.save(Title.builder()
                .id(titleId)
                .employee(employee)
                .toDate(toDate)
                .build());

        //then
        assertThat(titleRepository.count()).isEqualTo(count + 1);
    }

    @Test
    public void 기본키중복_테스트() throws Exception {
        //given
        Integer empNo = 10017;
        Employee employee = em.find(Employee.class, empNo);
        String title = "Senior Staff";
        LocalDate fromDate = LocalDate.parse("2000-08-03");
        LocalDate toDate = LocalDate.parse("9999-01-01");

        TitleId titleId = TitleId.builder()
                .empNo(empNo)
                .title(title)
                .fromDate(fromDate)
                .build();

        titleRepository.findById(titleId);

        //when
        //then
        assertThrows(EntityExistsException.class, () -> {
            em.persist(Title.builder()
                    .id(titleId)
                    .employee(employee)
                    .toDate(toDate)
                    .build());
        });
    }

    @Test
    public void find_test() throws Exception {
        //given
        Integer empNo = 10017;
        String title = "Senior Staff";
        LocalDate fromDate = LocalDate.parse("2000-08-03");

        TitleId titleId = TitleId.builder()
                .empNo(empNo)
                .title(title)
                .fromDate(fromDate)
                .build();

        //when
        Optional<Title> findTitle = titleRepository.findById(titleId);

        //then
        findTitle.ifPresentOrElse(title1 -> {
            assertThat(title1.getId()).isEqualTo(titleId);
        }, Assertions::fail);
    }

    @Test
    public void delete_test() throws Exception {
        //given
        Integer empNo = 10017;
        String title = "Senior Staff";
        LocalDate fromDate = LocalDate.parse("2000-08-03");

        TitleId titleId = TitleId.builder()
                .empNo(empNo)
                .title(title)
                .fromDate(fromDate)
                .build();

        long count = titleRepository.count();

        //when
        titleRepository.deleteById(titleId);

        //then
        assertThat(titleRepository.count()).isEqualTo(count - 1);
    }
}