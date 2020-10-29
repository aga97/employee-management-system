package yellowsunn.employee_management.repository.custom.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.transaction.annotation.Transactional;
import yellowsunn.employee_management.dto.DeptDto;
import yellowsunn.employee_management.entity.*;
import yellowsunn.employee_management.repository.custom.SalaryRepositoryCustom;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static yellowsunn.employee_management.entity.QDeptEmp.*;
import static yellowsunn.employee_management.entity.QTitle.title;

@Transactional(readOnly = true)
public class SalaryRepositoryCustomImpl implements SalaryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public SalaryRepositoryCustomImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Salary> findLatestByEmployeeIn(Collection<Employee> employees) {
        QSalary salary = new QSalary("salary");
        QSalary subSalary = new QSalary("subSalary");

        return queryFactory
                .selectFrom(salary)
                .where(Expressions.list(salary.employee, salary.id.fromDate, salary.toDate)
                        .in(select(subSalary.employee.empNo, subSalary.id.fromDate.max(), subSalary.toDate.max())
                                .from(subSalary)
                                .where(subSalary.employee.in(employees))
                                .groupBy(subSalary.employee.empNo)
                        ), salary.employee.in(employees)
                ).fetch();
    }

    @Override
    public List<Salary> findCurrentByEmployeeIn(Collection<Employee> employees) {
        QSalary salary = new QSalary("salary");

        return queryFactory
                .selectFrom(salary)
                .where(salary.toDate.eq(LocalDate.of(9999, 1, 1)),
                        salary.employee.in(employees)
                ).fetch();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeptDto.SalaryInfo> findCurByDeptNoGroupByTitle(String deptNo) {
        // 재직 중임을 나타낸다.
        LocalDate inService = LocalDate.of(9999, 1, 1);
        QSalary salary = new QSalary("salary");

        List<Tuple> tuples = queryFactory
                .select(
                        select(title.id.title)
                                .from(title)
                                .where(salary.employee.empNo.eq(title.employee.empNo),
                                        title.toDate.eq(inService)),
                        salary.salary.avg(),
                        salary.count(),
                        salary.salary.min(),
                        salary.salary.max()
                ).from(salary)
                .where(salary.employee.empNo.in(
                        select(deptEmp.employee.empNo)
                                .from(deptEmp)
                                .where(deptEmp.department.deptNo.eq(deptNo),
                                        deptEmp.toDate.eq(inService)
                                )
                        ),
                        salary.toDate.eq(inService)
                ).groupBy(Expressions.ONE)
                .orderBy(Expressions.TWO.desc())
                .fetch();

        List<DeptDto.SalaryInfo> salaryInfoList = new ArrayList<>();
        tuples.forEach(tuple -> {
            Long size = tuple.get(salary.count());
            if (size != null) {
                salaryInfoList.add(DeptDto.SalaryInfo.builder()
                        .title(tuple.get(0, String.class))
                        .minSalary(tuple.get(salary.salary.min()))
                        .maxSalary(tuple.get(salary.salary.max()))
                        .avgSalary(tuple.get(salary.salary.avg()))
                        .size(size)
                        .build()
                );
            }
        });

        return salaryInfoList;
    }
}
