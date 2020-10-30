package yellowsunn.employee_management.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import yellowsunn.employee_management.dto.EmpSearchDto;
import yellowsunn.employee_management.entity.Department;
import yellowsunn.employee_management.entity.DeptEmp;
import yellowsunn.employee_management.entity.Employee;
import yellowsunn.employee_management.entity.Salary;
import yellowsunn.employee_management.entity.id.DeptEmpId;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Sql({
        "/sample_datasets/employees.sql",
        "/sample_datasets/departments.sql",
        "/sample_datasets/dept_emp.sql"
})
@DataJpaTest
class DeptEmpRepositoryTest {

    @Autowired DeptEmpRepository deptEmpRepository;
    @Autowired TestEntityManager em;

    @Test
    public void save_test() throws Exception {
        //given
        Integer empNo = 10017;
        String deptNo = "d003";
        Employee employee = em.find(Employee.class, empNo);
        Department department = em.find(Department.class, deptNo);
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = LocalDate.parse("9999-01-01");

        DeptEmpId deptEmpId = DeptEmpId.builder()
                .empNo(empNo)
                .deptNo(deptNo)
                .build();

        long count = deptEmpRepository.count();

        //when
        deptEmpRepository.save(DeptEmp.builder()
                .id(deptEmpId)          // 이걸로 isNew() 조건이 들어가고 있으면 update(merge), 없으면 insert
                .employee(employee)     // 실제로 들어가는 emp_no
                .department(department) // 실제로 들어가는 dept_no
                .fromDate(fromDate)
                .toDate(toDate)
                .build());

        //then
        assertThat(deptEmpRepository.count()).isEqualTo(count + 1);
    }

    @Test
    public void 기본키중복_테스트() throws Exception {
        //given
        Integer empNo = 10017;
        String deptNo = "d001";
        Employee employee = em.find(Employee.class, empNo);
        Department department = em.find(Department.class, deptNo);
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = LocalDate.parse("9999-01-01");

        DeptEmpId deptEmpId = DeptEmpId.builder()
                .empNo(empNo)
                .deptNo(deptNo)
                .build();

        deptEmpRepository.findById(deptEmpId);

        //when
        //then
        assertThrows(EntityExistsException.class, () -> {
            em.persist(DeptEmp.builder()
                    .id(deptEmpId)
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
        Integer empNo = 10017;
        String deptNo = "d001";
        DeptEmpId deptEmpId = DeptEmpId.builder()
                .empNo(empNo)
                .deptNo(deptNo)
                .build();

        //when
        Optional<DeptEmp> deptEmpOptional = deptEmpRepository.findById(deptEmpId);

        //then
        deptEmpOptional.ifPresentOrElse(deptEmp -> {
            assertThat(deptEmp.getId()).isEqualTo(deptEmpId);
        }, Assertions::fail);
    }

    @Test
    public void delete_test() throws Exception {
        //given
        Integer empNo = 10017;
        String deptNo = "d001";
        long count = deptEmpRepository.count();

        //when
        deptEmpRepository.deleteById(DeptEmpId.builder()
                .empNo(empNo)
                .deptNo(deptNo)
                .build());

        //then
        assertThat(deptEmpRepository.count()).isEqualTo(count - 1);
    }

    @Test
    public void findCurrentAll_test() throws Exception {
        //given
        PageRequest pageRequest = PageRequest.of(0, 100, Sort.by(Sort.Direction.ASC, "emp_no"));

        //when
        Slice<DeptEmp> deptEmpPage = deptEmpRepository.findByCondition(new EmpSearchDto.Condition(), pageRequest);
        List<DeptEmp> content = deptEmpPage.getContent();

        //then
        List<DeptEmpId> deptEmpIds = content.stream().map(DeptEmp::getId).collect(Collectors.toList());
        assertThat(deptEmpIds)
                .contains(DeptEmpId.builder().empNo(10017).deptNo("d001").build())
                .contains(DeptEmpId.builder().empNo(10050).deptNo("d007").build())
                .doesNotContain(DeptEmpId.builder().empNo(10050).deptNo("d002").build())
                .contains(DeptEmpId.builder().empNo(10080).deptNo("d003").build())
                .doesNotContain(DeptEmpId.builder().empNo(10080).deptNo("d002").build());
    }

    @Test
    public void findLatestByEmployee_test() throws Exception {
        //given
        Employee employee1 = em.find(Employee.class, 10143);
        Employee employee2 = em.find(Employee.class, 10144);

        //when
        Optional<DeptEmp> deptEmp1 = deptEmpRepository.findLatestByEmployee(employee1);
        Optional<DeptEmp> deptEmp2 = deptEmpRepository.findLatestByEmployee(employee2);

        //then
        assertThat(deptEmp1.isEmpty()).isTrue();
        assertThat(deptEmp2.isPresent()).isTrue();
        assertThat(deptEmp2.get().getToDate()).isEqualTo(LocalDate.of(1993, 8, 10));
    }

    @Test
    public void findByEmployee_test() throws Exception {
        //given
        Employee employee1 = em.find(Employee.class, 10143);
        Employee employee2 = em.find(Employee.class, 10144);

        //when
        List<DeptEmp> deptEmps1 = deptEmpRepository.findByEmployee(employee1);
        List<DeptEmp> deptEmps2 = deptEmpRepository.findByEmployee(employee2);

        //then
        assertThat(deptEmps1.size()).isEqualTo(0);
        assertThat(deptEmps2.size()).isNotEqualTo(0);

        List<String> collect = deptEmps2.stream()
                .map(salary -> salary.getId().getEmpNo() + "_" + salary.getToDate())
                .collect(Collectors.toList());

        assertThat(collect)
                .contains("10144_1988-09-02")
                .contains("10144_1993-08-10");
    }
}