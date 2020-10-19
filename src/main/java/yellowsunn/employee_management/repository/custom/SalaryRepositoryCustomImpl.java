package yellowsunn.employee_management.repository.custom;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.transaction.annotation.Transactional;
import yellowsunn.employee_management.entity.Employee;
import yellowsunn.employee_management.entity.QSalary;
import yellowsunn.employee_management.entity.Salary;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;

public class SalaryRepositoryCustomImpl implements SalaryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public SalaryRepositoryCustomImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Salary> findCurrentByEmployeeIn(Collection<Employee> employees) {
        QSalary salary = new QSalary("salary");
        QSalary subSalary = new QSalary("subSalary");

        return queryFactory
                .selectFrom(salary)
                .where(Expressions.list(salary.employee, salary.id.fromDate, salary.toDate)
                        .in(select(subSalary.employee, subSalary.id.fromDate.max(), subSalary.toDate.max())
                                .from(subSalary)
                                .where(subSalary.employee.in(employees))
                                .groupBy(subSalary.employee)
                        ), salary.employee.in(employees)
                ).fetch();
    }
}
