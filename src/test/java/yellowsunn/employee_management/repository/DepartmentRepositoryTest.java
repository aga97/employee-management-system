package yellowsunn.employee_management.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import yellowsunn.employee_management.entity.Department;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("/sample_datasets/departments.sql")
@DataJpaTest
class DepartmentRepositoryTest {

    @Autowired DepartmentRepository departmentRepository;

    @Test
    public void save_test() throws Exception {
        //given
        String deptNo = "d010";
        String deptName = "Home";

        long count = departmentRepository.count();

        //when
        departmentRepository.save(Department.builder()
                .deptNo(deptNo)
                .deptName(deptName)
                .build());

        //then
        assertThat(departmentRepository.count()).isEqualTo(count + 1);
    }

    @Test
    public void find_test() throws Exception {
        //given
        String deptNo = "d001";

        //when
        Optional<Department> departmentOptional = departmentRepository.findById(deptNo);

        //then
        departmentOptional.ifPresentOrElse(department -> {
            assertThat(department.getDeptNo()).isEqualTo(deptNo);
        }, Assertions::fail);
    }

    @Test
    public void delete_test() throws Exception {
        //given
        String deptNo = "d001";
        long count = departmentRepository.count();

        //when
        departmentRepository.deleteById(deptNo);

        //then
        assertThat(departmentRepository.count()).isEqualTo(count - 1);
    }
}