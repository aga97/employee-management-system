package yellowsunn.employee_management.repository.custom.impl;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import yellowsunn.employee_management.entity.Employee;
import yellowsunn.employee_management.entity.QTitle;
import yellowsunn.employee_management.entity.Title;
import yellowsunn.employee_management.repository.custom.TitleRepositoryCustom;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.querydsl.jpa.JPAExpressions.select;
import static yellowsunn.employee_management.entity.QDeptEmp.deptEmp;
import static yellowsunn.employee_management.entity.QTitle.title;

@Transactional(readOnly = true)
public class TitleRepositoryCustomImpl implements TitleRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public TitleRepositoryCustomImpl(EntityManager em) {
        this.em = em;
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
        if (employee == null) {
            return new ArrayList<>();
        }

        return queryFactory
                .selectFrom(title)
                .where(title.employee.eq(employee))
                .orderBy(title.toDate.desc(), title.id.fromDate.desc())
                .fetch();
    }

    @Override
    public Optional<Title> findLatestByEmployee(Employee employee) {
        if (employee != null) {
            QTitle subTitle = new QTitle("subTitle");

            Title findTitle = queryFactory
                    .selectFrom(title)
                    .where(title.employee.eq(employee),
                            Expressions.list(title.id.fromDate, title.toDate).in(
                                    select(subTitle.id.fromDate.max(), subTitle.toDate.max())
                                    .from(subTitle)
                                    .where(subTitle.employee.eq(employee))
                            )
                    ).fetchOne();
            return Optional.ofNullable(findTitle);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Title> findCurrentByEmployee(Employee employee) {
        if (employee != null) {
            Title findTitle = queryFactory
                    .selectFrom(QTitle.title)
                    .where(QTitle.title.employee.eq(employee),
                            QTitle.title.toDate.eq(LocalDate.of(9999, 1, 1)))
                    .fetchOne();

            return Optional.ofNullable(findTitle);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<String> findTitles(Sort sort) {
        JPAQuery<String> query = queryFactory
                .select(title.id.title)
                .from(title)
                .groupBy(title.id.title);

        Optional<Sort.Order> order = sort.stream().findFirst();
        order.ifPresent(o -> {
            if (o.getProperty().equals("title")) {
                StringPath title = QTitle.title.id.title;
                query.orderBy(o.isAscending() ? title.asc() : title.desc());
            }
        });

        return query.fetch();
    }

    @Override
    @Transactional
    public <S extends Title> void persist(S entity) {
        em.persist(entity);
    }
}
