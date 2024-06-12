package project.springboot.template;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import vn.grooo.vietdang.service.BranchService;

@SpringBootTest
@ActiveProfiles("local")
class VietDangProjectApplicationTests {

    @Autowired
    private BranchService branchService;

    @Test
    public void shouldNotNullWhenCallToSynsc() {
        Assertions.assertThat(branchService.synchronousBranch()).isTrue();
    }
}
