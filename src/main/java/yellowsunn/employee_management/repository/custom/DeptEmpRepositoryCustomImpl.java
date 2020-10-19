package yellowsunn.employee_management.repository.custom;

import com.querydsl.core.types.dsl.ComparableExpressionBase;
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

    private final JPAQueryFactory queryFactory;

    public DeptEmpRepositoryCustomImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeptEmp> findCurrentAll(Pageable pageable) {
        QDeptEmp subDe = new QDeptEmp("subDe");

        JPAQuery<DeptEmp> query = queryFactory
                .selectFrom(deptEmp)
                .join(deptEmp.employee).fetchJoin()
                .join(deptEmp.department).fetchJoin()
                .where(Expressions.list(deptEmp.employee, deptEmp.fromDate, deptEmp.toDate)
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
            ComparableExpressionBase path = null;

            switch (property) {
                case "emp_no":
                    path = deptEmp.employee.empNo;
                    break;
                case "dept_name":
                    path = deptEmp.department.deptName;
                    break;
                case "birth_date":
                    path = deptEmp.employee.birthDate;
                    break;
                case "first_name":
                    path = deptEmp.employee.firstName;
                    break;
                case "last_name":
                    path = deptEmp.employee.lastName;
                    break;
                case "hire_date":
                    path = deptEmp.employee.hireDate;
                    break;
            }

            if (path != null) {
                query.orderBy(order.isAscending() ? path.asc() : path.desc());
            }
        });

        List<DeptEmp> results = query.fetch();

        long total = queryFactory
                .selectFrom(QEmployee.employee)
                .fetchCount();

        return new PageImpl<>(results, pageable, total);
    }
}
