package yellowsunn.employee_management.repository.custom.impl;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.transaction.annotation.Transactional;
import yellowsunn.employee_management.entity.Employee;
import yellowsunn.employee_management.entity.QDeptEmp;
import yellowsunn.employee_management.entity.QTitle;
import yellowsunn.employee_management.entity.Title;
import yellowsunn.employee_management.repository.custom.TitleRepositoryCustom;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.querydsl.jpa.JPAExpressions.select;
import static yellowsunn.employee_management.entity.QDeptEmp.deptEmp;
import static yellowsunn.employee_management.entity.QTitle.title;

@Transactional(readOnly = true)
public class TitleRepositoryCustomImpl implements TitleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public TitleRepositoryCustomImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Title> findLatestByEmployeeIn(Collection<Employee> employees) {
        QTitle subTitle = new QTitle("subTitle");

        return queryFactory
                .selectFrom(title)
                .where(Expressions.list(title.employee, title.id.fromDate, title.toDate)
                        .in(select(subTitle.employee.empNo, subTitle.id.fromDate.max(), subTitle.toDate.max())
                                .from(subTitle)
                                .where(subTitle.employee.in(employees))
                                .groupBy(subTitle.employee.empNo)
                        ), title.employee.in(employees)
                ).fetch();
    }

    @Override
    public List<Title> findCurrentByEmployeeIn(Collection<Employee> employees) {
        return queryFactory
                .selectFrom(title)
                .where(title.toDate.eq(LocalDate.of(9999, 1, 1)),
                        title.employee.in(employees)
                ).fetch();
    }

    @Override
    public List<Title> findByEmployee(Employee employee) {
        return queryFactory
                .selectFrom(title)
                .where(title.employee.eq(employee))
                .orderBy(title.toDate.desc())
                .fetch();
    }

    @Override
    public Optional<Title> findLatestByEmployee(Employee employee) {
        if (employee != null) {
            Title findTitle = queryFactory
                    .selectFrom(QTitle.title)
                    .where(QTitle.title.employee.eq(employee),
                            QTitle.title.toDate.eq(
                                    select(deptEmp.toDate.max())
                                            .from(deptEmp)
                                            .where(deptEmp.employee.eq(employee))
                                            .groupBy(deptEmp.employee)
                            )
                    ).fetchOne();
            return Optional.ofNullable(findTitle);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<String> findTitles() {
        return queryFactory
                .select(title.id.title)
                .from(title)
                .groupBy(title.id.title)
                .fetch();
    }
}
