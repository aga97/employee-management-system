package yellowsunn.employee_management.repository.custom;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
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
    public Page<DeptEmp> findCurrentAll(Pageable pageable) {
        QDeptEmp subDe = new QDeptEmp("subDe");

        JPAQuery<DeptEmp> query = queryFactory
                .selectFrom(deptEmp)
//                .join(deptEmp.employee).fetchJoin()
//                .join(deptEmp.department).fetchJoin()
                .where(Expressions.list(deptEmp.employee.empNo, deptEmp.fromDate, deptEmp.toDate)
                        .in(select(subDe.employee.empNo, subDe.fromDate.max(), subDe.toDate.max())
                                .from(subDe)
                                .groupBy(subDe.employee.empNo)
                        )
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        if (pageable.getSort().isEmpty()) {
            query.orderBy(deptEmp.employee.empNo.asc());
        }

        pageable.getSort().forEach(order -> {
            String property = order.getProperty();

            if (property.equals("emp_no")) {
                query.orderBy(order.isAscending() ? deptEmp.employee.empNo.asc() : deptEmp.employee.empNo.desc());
            } else if (property.equals("dept_name")) {
                query.orderBy(order.isAscending() ? deptEmp.department.deptName.asc() : deptEmp.department.deptName.desc());
            } else if (property.equals("birth_date")) {
                query.orderBy(order.isAscending() ? deptEmp.employee.birthDate.asc() : deptEmp.employee.birthDate.desc());
            } else if (property.equals("first_name")) {
                query.orderBy(order.isAscending() ? deptEmp.employee.firstName.asc() : deptEmp.employee.firstName.desc());
            } else if (property.equals("last_name")) {
                query.orderBy(order.isAscending() ? deptEmp.employee.lastName.asc() : deptEmp.employee.lastName.desc());
            } else if (property.equals("hire_date")) {
                query.orderBy(order.isAscending() ? deptEmp.employee.hireDate.asc() : deptEmp.employee.hireDate.desc());
            }
        });

        List<DeptEmp> results = query.fetch();

        long total = queryFactory
                .selectFrom(QEmployee.employee)
                .fetchCount();

        return new PageImpl<>(results, pageable, total);
    }
}
