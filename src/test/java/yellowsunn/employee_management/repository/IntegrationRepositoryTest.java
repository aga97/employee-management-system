package yellowsunn.employee_management.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import yellowsunn.employee_management.entity.DeptEmp;
import yellowsunn.employee_management.entity.QDeptEmp;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static com.querydsl.jpa.JPAExpressions.select;
import static org.assertj.core.api.Assertions.assertThat;
import static yellowsunn.employee_management.entity.QDeptEmp.deptEmp;

@Sql({
        "/sample_datasets/employees.sql",
        "/sample_datasets/departments.sql",
        "/sample_datasets/dept_emp.sql",
        "/sample_datasets/dept_manager.sql",
        "/sample_datasets/salaries.sql",
        "/sample_datasets/titles.sql"
})
@DataJpaTest
public class IntegrationRepositoryTest {

    @Autowired EntityManager em;
    JPAQueryFactory queryFactory;

    @BeforeEach
    public void initQueryFactory() {
        queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 직원의 가장 최근 부서와 join
     * - empNo와 toDate 를 조합한 문자열로 만든 다음
     * 서브쿼리의 가장 최근 시간을 가진 문자열과 비교
     */
    @Test
    @Transactional(readOnly = true)
    public void 직원_부서_join_test() throws Exception {
        QDeptEmp subDe = new QDeptEmp("subDe");
        List<DeptEmp> deptEmps = queryFactory
                .selectFrom(deptEmp)
                .join(deptEmp.employee).fetchJoin()       // h2에서는 2개 조인하니까 성능 저하 심함
                .join(deptEmp.department).fetchJoin()
                .where(Expressions.list(deptEmp.employee.empNo, deptEmp.toDate)
                        .in(select(subDe.employee.empNo, subDe.toDate.max())
                                .from(subDe)
                                .groupBy(subDe.employee.empNo)
                        )
                )
                .offset(0)
                .limit(1000)
                .orderBy(deptEmp.employee.empNo.asc())
                .fetch();

        List<String> collect = deptEmps.stream()
                .map(deptEmp1 -> deptEmp1.getEmployee().getEmpNo() + "_" + deptEmp1.getToDate())
                .collect(Collectors.toList());

        assertThat(collect)
                .contains("10108_2001-10-20")
                .doesNotContain("10108_1999-12-06")
                .contains("10042_2000-08-10")
                .contains("10144_1993-08-10")
                .doesNotContain("10144_1988-09-02")
                .contains("10050_9999-01-01")
                .doesNotContain("10050_1992-11-05");
    }
}
