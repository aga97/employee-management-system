package yellowsunn.employee_management.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@Sql({
        "/sample_datasets/employees.sql",
        "/sample_datasets/departments.sql",
        "/sample_datasets/dept_emp.sql",
        "/sample_datasets/dept_manager.sql",
        "/sample_datasets/salaries.sql",
        "/sample_datasets/titles.sql"
})
@DataJpaTest
public class IntegrationRepositoryTest {
    @Test
    public void test() throws Exception {

    }
}
