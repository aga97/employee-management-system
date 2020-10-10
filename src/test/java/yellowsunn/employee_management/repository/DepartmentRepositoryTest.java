package yellowsunn.employee_management.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import yellowsunn.employee_management.entity.Department;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DepartmentRepositoryTest {

    @Autowired DepartmentRepository departmentRepository;
    String deptNo; // department id

    private Department saveDepartment() {
        deptNo = "d001";
        String deptName = "Marketing";

        return departmentRepository.save(Department.builder()
                .deptNo(deptNo)
                .deptName(deptName)
                .build());
    }

    @Test
    public void save_test() throws Exception {
        Department saveDepartment = saveDepartment();

        //then
        assertThat(saveDepartment.getDeptNo()).isEqualTo(deptNo);
    }

    @Test
    public void find_test() throws Exception {
        //given
        saveDepartment();

        //when
        Department findDepartment = departmentRepository.findById(deptNo).orElse(null);

        //then
        assertThat(findDepartment).isNotNull();
        assertThat(findDepartment.getDeptNo()).isEqualTo(deptNo);
    }

    @Test
    public void delete_test() throws Exception {
        //given
        saveDepartment();
        long count = departmentRepository.count();

        //when
        departmentRepository.deleteById(deptNo);

        //then
        assertThat(departmentRepository.count()).isEqualTo(count - 1);
    }
}