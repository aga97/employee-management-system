package yellowsunn.employee_management.repository.custom;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import yellowsunn.employee_management.entity.DeptEmp;
import yellowsunn.employee_management.entity.QDeptEmp;
import yellowsunn.employee_management.entity.QEmployee;

import javax.persistence.EntityManager;
import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static yellowsunn.employee_management.entity.QDeptEmp.deptEmp;

public class DeptEmpRepositoryCustomImpl implements DeptEmpRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public DeptEmpRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeptEmp> findRecentAll(Pageable pageable) {
        QDeptEmp subDe = new QDeptEmp("subDe");

        List<DeptEmp> results = queryFactory
                .selectFrom(deptEmp)
                .join(deptEmp.employee).fetchJoin()
                .join(deptEmp.department).fetchJoin()
                .where(Expressions.list(deptEmp.employee.empNo, deptEmp.toDate)
                        .in(select(subDe.employee.empNo, subDe.toDate.max())
                                .from(subDe)
                                .groupBy(subDe.employee.empNo)
                        )
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(deptEmp.employee.empNo.asc())
                .fetch();

        long total = queryFactory
                .selectFrom(QEmployee.employee)
                .fetchCount();

        return new PageImpl<>(results, pageable, total);
    }
}
