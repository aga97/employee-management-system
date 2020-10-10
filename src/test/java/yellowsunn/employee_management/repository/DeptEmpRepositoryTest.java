package yellowsunn.employee_management.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import yellowsunn.employee_management.entity.Department;
import yellowsunn.employee_management.entity.DeptEmp;
import yellowsunn.employee_management.entity.Employee;
import yellowsunn.employee_management.entity.Gender;
import yellowsunn.employee_management.entity.id.DeptEmpId;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DeptEmpRepositoryTest {

    @Autowired DeptEmpRepository deptEmpRepository;
    @Autowired TestEntityManager em;
    Integer empNo; // employee id
    String deptNo; // department id
    DeptEmpId deptEmpId;

    @BeforeEach
    public void persist_employee_and_department() {
        /* employee */
        empNo = 10001;
        LocalDate birthDate = LocalDate.parse("1953-09-02");
        String firstName = "Georgi";
        String lastName = "Facello";
        Gender gender = Gender.M;
        LocalDate hireDate = LocalDate.parse("1986-06-26");

        /* department */
        deptNo = "d001";
        String deptName = "Marketing";

        Employee saveEmployee = em.persist(Employee.builder()
                .empNo(empNo)
                .birthDate(birthDate)
                .firstName(firstName)
                .lastName(lastName)
                .gender(gender)
                .hireDate(hireDate)
                .build());

        Department saveDepartment = em.persist(Department.builder()
                .deptNo(deptNo)
                .deptName(deptName)
                .build());

        assertThat(saveEmployee).isNotNull();
        assertThat(saveDepartment).isNotNull();
        assertThat(saveEmployee.getEmpNo()).isEqualTo(empNo);
        assertThat(saveDepartment.getDeptNo()).isEqualTo(deptNo);
    }

    private DeptEmp saveDeptEmp() {
        Employee employee = em.find(Employee.class, empNo);
        Department department = em.find(Department.class, deptNo);
        LocalDate fromDate = LocalDate.parse("1986-06-26");
        LocalDate toDate = LocalDate.parse("9999-01-01");

        deptEmpId = DeptEmpId.builder()
                .employee(employee)
                .department(department)
                .build();

        return deptEmpRepository.save(DeptEmp.builder()
                .id(deptEmpId)
                .fromDate(fromDate)
                .toDate(toDate)
                .build());
    }

    @Test
    public void save_test() throws Exception {
        DeptEmp saveDeptEmp = saveDeptEmp();

        //then
        assertThat(saveDeptEmp.getId()).isEqualTo(deptEmpId);
    }

    @Test
    public void find_test() throws Exception {
        //given
        saveDeptEmp();

        //when
        DeptEmp findDeptEmp = deptEmpRepository.findById(deptEmpId).orElse(null);

        //then
        assertThat(findDeptEmp).isNotNull();
        assertThat(findDeptEmp.getId()).isEqualTo(deptEmpId);
    }

    @Test
    public void delete_test() throws Exception {
        //given
        saveDeptEmp();
        long count = deptEmpRepository.count();

        //when
        deptEmpRepository.deleteById(deptEmpId);

        //then
        assertThat(deptEmpRepository.count()).isEqualTo(count - 1);
    }
}