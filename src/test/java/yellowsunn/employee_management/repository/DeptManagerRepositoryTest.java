package yellowsunn.employee_management.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import yellowsunn.employee_management.entity.Department;
import yellowsunn.employee_management.entity.DeptManager;
import yellowsunn.employee_management.entity.Employee;
import yellowsunn.employee_management.entity.id.DeptManagerId;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Sql({
        "/sample_datasets/departments.sql",
        "/sample_datasets/employees.sql",
        "/sample_datasets/dept_manager.sql"
})
@DataJpaTest
class DeptManagerRepositoryTest {

    @Autowired DeptManagerRepository deptManagerRepository;
    @Autowired TestEntityManager em;

    @Test
    public void save_test() throws Exception {
        //given
        Integer empNo = 10017;
        String deptNo = "d001";
        Employee employee = em.find(Employee.class, empNo);
        Department department = em.find(Department.class, deptNo);
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = LocalDate.parse("9999-01-01");

        //when
        DeptManagerId deptManagerId = DeptManagerId.builder()
                .empNo(empNo)
                .deptNo(deptNo)
                .build();

        long count = deptManagerRepository.count();

        deptManagerRepository.save(DeptManager.builder()
                .id(deptManagerId)      // 이걸로 isNew() 조건이 들어가고 있으면 update(merge), 없으면 insert
                .employee(employee)     // 실제로 들어가는 emp_no
                .department(department) // 실제로 들어가는 dept_no
                .fromDate(fromDate)
                .toDate(toDate)
                .build());
        //then
        assertThat(deptManagerRepository.count()).isEqualTo(count + 1);
    }

    @Test
    public void 기본키중복_테스트() throws Exception {
        //given
        Integer empNo = 110022;
        String deptNo = "d001";
        Employee employee = em.find(Employee.class, empNo);
        Department department = em.find(Department.class, deptNo);
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = LocalDate.parse("9999-01-01");

        DeptManagerId deptManagerId = DeptManagerId.builder()
                .empNo(empNo)
                .deptNo(deptNo)
                .build();

        deptManagerRepository.findById(deptManagerId);

        //when
        //then
        assertThrows(EntityExistsException.class, () -> {
            em.persist(DeptManager.builder()
                    .id(deptManagerId)
                    .employee(employee)
                    .department(department)
                    .fromDate(fromDate)
                    .toDate(toDate)
                    .build());
        });
    }

    @Test
    public void find_test() throws Exception {
        //given
        Integer empNo = 110022;
        String deptNo = "d001";

        DeptManagerId deptManagerId = DeptManagerId.builder()
                .empNo(empNo)
                .deptNo(deptNo)
                .build();

        //when
        Optional<DeptManager> findDeptManager = deptManagerRepository.findById(deptManagerId);

        //then
        findDeptManager.ifPresentOrElse(deptManager -> {
            assertThat(deptManager.getId()).isEqualTo(deptManagerId);
        }, Assertions::fail);
    }

    @Test
    public void delete_test() throws Exception {
        //given
        Integer empNo = 110022;
        String deptNo = "d001";

        DeptManagerId deptManagerId = DeptManagerId.builder()
                .empNo(empNo)
                .deptNo(deptNo)
                .build();

        long count = deptManagerRepository.count();

        //when
        deptManagerRepository.deleteById(deptManagerId);

        //then
        assertThat(deptManagerRepository.count()).isEqualTo(count - 1);
    }
}