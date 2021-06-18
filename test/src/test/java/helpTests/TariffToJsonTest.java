package helpTests;

import com.ecare.config.Config;
import com.ecare.dao.TariffDAO;
import com.ecare.domain.TariffEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
/*@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = {Config.class})*/
@Transactional
public class TariffToJsonTest {


    @Test
    public void tariffsToJsonTest() {
        TariffEntity t1 = new TariffEntity();
        t1.setTariffName("test1");
        t1.setTariffDescription("test1 desc");
        t1.setPrice(500);
        TariffEntity t2 = new TariffEntity();
        t2.setTariffName("test2");
        t2.setTariffDescription("test2 desc");
        t2.setPrice(666);
        List<TariffEntity> hotTariffs = new ArrayList<>();
        hotTariffs.add(t1);
        hotTariffs.add(t2);
        String json = new Gson().toJson(hotTariffs);
        Assertions.assertNotNull(json);
    }
}
