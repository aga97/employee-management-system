package yellowsunn.employee_management.repository.custom.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.transaction.annotation.Transactional;
import yellowsunn.employee_management.entity.DeptManager;
import yellowsunn.employee_management.entity.Employee;
import yellowsunn.employee_management.entity.QDeptManager;
import yellowsunn.employee_management.repository.custom.DeptManagerRepositoryCustom;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static yellowsunn.employee_management.entity.QDeptManager.deptManager;

@Transactional(readOnly = true)
public class DeptManagerRepositoryCustomImpl implements DeptManagerRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public DeptManagerRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<DeptManager> findByDeptNo(String deptNo) {
        return queryFactory
                .selectFrom(deptManager)
                .join(deptManager.employee).fetchJoin()
                .where(deptManager.department.deptNo.eq(deptNo))
                .orderBy(deptManager.toDate.desc(), deptManager.fromDate.desc())
                .fetch();
    }

    @Override
    public Optional<DeptManager> findCurrentByDeptNo(String deptNo) {
        DeptManager findDeptManger = queryFactory
                .selectFrom(deptManager)
                .join(deptManager.employee).fetchJoin()
                .where(deptManager.department.deptNo.eq(deptNo),
                        deptManager.toDate.eq(LocalDate.of(9999, 1, 1)))
                .fetchOne();

        return Optional.ofNullable(findDeptManger);
    }

    @Override
    public Optional<DeptManager> findCurrentByEmployee(Employee employee) {
        if (employee != null) {
            DeptManager findDeptManager = queryFactory
                    .selectFrom(deptManager)
                    .where(deptManager.employee.eq(employee),
                            deptManager.toDate.eq(LocalDate.of(9999, 1, 1)))
                    .fetchOne();
            return Optional.ofNullable(findDeptManager);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public <S extends DeptManager> void persist(S entity) {
        em.persist(entity);
    }
}
