import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Testando {

    // Agora não preciso me preocupar em ficar criando classes de testes, toda hora chamando main()

    @Test
    void testarSeIgualADois() {
        int x = 2;
        assertEquals(2, x);

//        fail("Not yet implemented!");
    }

    @Test
    void testarSeIgualATres() {
        int y = 2 + 10 - 9;
        assertEquals(3, y);
    }
}
