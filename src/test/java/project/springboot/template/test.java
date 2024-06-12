package project.springboot.template;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.springboot.template.util.json.JsonDateDeserializer;

@ExtendWith(SpringExtension.class)
public class test {
    @Test
    public String testDate() {
        JsonDateDeserializer jsonDateDeserializer = new JsonDateDeserializer();
        String date = "2022-06-12T00:00:00";
        return "";
    }
}
