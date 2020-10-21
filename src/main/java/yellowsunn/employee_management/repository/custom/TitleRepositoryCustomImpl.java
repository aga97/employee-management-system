package yellowsunn.employee_management.repository.custom;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.transaction.annotation.Transactional;
import yellowsunn.employee_management.entity.Employee;
import yellowsunn.employee_management.entity.QTitle;
import yellowsunn.employee_management.entity.Title;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static yellowsunn.employee_management.entity.QTitle.title;

public class TitleRepositoryCustomImpl implements TitleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public TitleRepositoryCustomImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Title> findCurrentByEmployeeIn(Collection<Employee> employees) {
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
}
