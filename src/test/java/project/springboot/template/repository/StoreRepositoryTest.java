package project.springboot.template.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StoreRepositoryTest {

    @Autowired
    private StoreRepository storeRepository;


    @Test
    public void shouldSaveStoreSuccess() {

        Store store = new Store();
        store.setName("ABC");

        Store save = storeRepository.save(store);

        assertEquals(save, store);

    }


}
