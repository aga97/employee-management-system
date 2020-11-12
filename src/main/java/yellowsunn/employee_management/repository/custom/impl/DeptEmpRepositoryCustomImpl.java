package yellowsunn.employee_management.repository.custom.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import yellowsunn.employee_management.dto.EmpSearchDto;
import yellowsunn.employee_management.entity.*;
import yellowsunn.employee_management.repository.custom.DeptEmpRepositoryCustom;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.querydsl.jpa.JPAExpressions.select;
import static yellowsunn.employee_management.entity.QDepartment.department;
import static yellowsunn.employee_management.entity.QDeptEmp.deptEmp;
import static yellowsunn.employee_management.entity.QEmployee.employee;

@Transactional(readOnly = true)
public class DeptEmpRepositoryCustomImpl implements DeptEmpRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public DeptEmpRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Slice<DeptEmp> findByCondition(EmpSearchDto.Condition condition, Pageable pageable) {
        int pageSize = pageable.getPageSize();

        QDeptEmp subDe = new QDeptEmp("subDe");

        JPAQuery<DeptEmp> query = queryFactory
                .selectFrom(deptEmp)
                .join(deptEmp.employee, employee).fetchJoin()
                .join(deptEmp.department, department).fetchJoin()
                .where(
                        empNoStartsWith(condition.getEmpNo()),
                        firstNameStartsWith(condition.getFirstName()),
                        lastNameStartsWith(condition.getLastName()),
                        deptNameStartsWith(condition.getDeptName()),
                        genderEq(condition.getGender()),
                        birthDateStartsWith(condition.getBirthDate()),
                        hireDateEq(condition.getHireDate()),

                        Expressions.list(deptEmp.employee, deptEmp.fromDate, deptEmp.toDate)
                                .in(select(subDe.employee.empNo, subDe.fromDate.max(), subDe.toDate.max())
                                        .from(subDe)
                                        .groupBy(subDe.employee.empNo)
                                )
                )
                .offset(pageable.getOffset())
                .limit(pageSize + 1);
        orderBy(query, pageable);

        List<DeptEmp> content = query.fetch();

        boolean hasNext = pageable.isPaged() && content.size() > pageSize;

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Slice<DeptEmp> findCurrentByCondition(EmpSearchDto.Condition condition, Pageable pageable) {
        int pageSize = pageable.getPageSize();

        JPAQuery<DeptEmp> query = queryFactory
                .selectFrom(deptEmp)
                .join(deptEmp.employee, employee).fetchJoin()
                .join(deptEmp.department, department).fetchJoin()
                .where(
                        deptEmp.toDate.eq(LocalDate.of(9999, 1, 1)),

                        empNoStartsWith(condition.getEmpNo()),
                        firstNameStartsWith(condition.getFirstName()),
                        lastNameStartsWith(condition.getLastName()),
                        deptNameStartsWith(condition.getDeptName()),
                        genderEq(condition.getGender()),
                        birthDateStartsWith(condition.getBirthDate()),
                        hireDateEq(condition.getHireDate())
                )
                .offset(pageable.getOffset())
                .limit(pageSize + 1);
        orderBy(query, pageable);

        List<DeptEmp> content = query.fetch();

        boolean hasNext = pageable.isPaged() && content.size() > pageSize;

        return new SliceImpl<>(hasNext ? content.subList(0, pageSize) : content, pageable, hasNext);
    }

    @Override
    public Optional<DeptEmp> findLatestByEmployee(Employee employee) {
        if (employee != null) {
            QDeptEmp subDeptEmp = new QDeptEmp("subDeptEmp");

            DeptEmp findDeptEmp = queryFactory
                    .selectFrom(deptEmp)
                    .join(deptEmp.department).fetchJoin()
                    .where(deptEmp.employee.eq(employee),
                            Expressions.list(deptEmp.fromDate, deptEmp.toDate).in(
                                    select(subDeptEmp.fromDate.max(), subDeptEmp.toDate.max())
                                            .from(subDeptEmp)
                                            .where(subDeptEmp.employee.eq(employee))
                                            .groupBy(subDeptEmp.employee)
                            )
                    ).fetchOne();

            return Optional.ofNullable(findDeptEmp);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<DeptEmp> findCurrentByEmployee(Employee employee) {
        if (employee != null) {
            DeptEmp findDeptEmp = queryFactory
                    .selectFrom(deptEmp)
                    .join(deptEmp.department).fetchJoin()
                    .where(deptEmp.employee.eq(employee),
                            deptEmp.toDate.eq(LocalDate.of(9999, 1, 1)))
                    .fetchOne();

            return Optional.ofNullable(findDeptEmp);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<DeptEmp> findByEmployee(Employee employee) {
        if (employee == null) {
            return new ArrayList<>();
        }

        return queryFactory
                .selectFrom(deptEmp)
                .join(deptEmp.department).fetchJoin()
                .where(deptEmp.employee.eq(employee))
                .orderBy(deptEmp.toDate.desc())
                .fetch();
    }

    @Override
    public long countCurrentByDeptNo(String deptNo) {
        return queryFactory
                .selectFrom(deptEmp)
                .where(deptEmp.department.deptNo.eq(deptNo),
                        deptEmp.toDate.eq(LocalDate.of(9999, 1, 1)))
                .fetchCount();
    }

    @Override
    @Transactional
    public <S extends DeptEmp> void persist(S entity) {
        em.persist(entity);
    }

    /**
     * Pageable로 전달받은 정렬 순서 적용
     */
    private void orderBy(JPAQuery<DeptEmp> query, Pageable pageable) {
        if (pageable.getSort().isEmpty()) {
            query.orderBy(deptEmp.employee.empNo.asc());
        }

        pageable.getSort().forEach(order -> {
            String property = order.getProperty();
            ComparableExpressionBase path = null;

            switch (property) {
                case "empNo":
                    path = deptEmp.employee.empNo;
                    break;
                case "deptName":
                    path = deptEmp.department.deptName;
                    break;
                case "birthDate":
                    path = deptEmp.employee.birthDate;
                    break;
                case "firstName":
                    path = deptEmp.employee.firstName;
                    break;
                case "lastName":
                    path = deptEmp.employee.lastName;
                    break;
                case "hireDate":
                    path = deptEmp.employee.hireDate;
                    break;
            }

            if (path != null) {
                query.orderBy(order.isAscending() ? path.asc() : path.desc());
            }
        });
    }

    private BooleanExpression empNoStartsWith(Integer empNo) {
        return empNo != null ? employee.empNo.stringValue().startsWith(empNo.toString()) : null;
    }

    private BooleanExpression genderEq(Gender gender) {
        return gender != null ? employee.gender.eq(gender) : null;
    }

    private BooleanExpression deptNameStartsWith(String deptName) {
        return StringUtils.hasText(deptName) ? department.deptName.startsWith(deptName) : null;
    }

    private BooleanExpression firstNameStartsWith(String firstName) {
        return StringUtils.hasText(firstName) ? employee.firstName.startsWith(firstName) : null;
    }

    private BooleanExpression lastNameStartsWith(String lastName) {
        return StringUtils.hasText(lastName) ? employee.lastName.startsWith(lastName) : null;
    }

    private BooleanExpression birthDateStartsWith(String birthDate) {
        return birthDate != null ? employee.birthDate.stringValue().startsWith(birthDate) : null;
    }

    private BooleanExpression hireDateEq(LocalDate hireDate) {
        return hireDate != null ? employee.birthDate.eq(hireDate) : null;
    }
}
