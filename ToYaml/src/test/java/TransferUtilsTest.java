import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TransferUtilsTest extends TestCase {

    @Test
    public void testYml2Properties() {
        TransferUtils.yml2Properties("src/main/resources/application.yml");
    }

    @Test
    public void testProperties2Yaml() {
        TransferUtils.properties2Yaml("src/main/resources/application.properties");
    }
}