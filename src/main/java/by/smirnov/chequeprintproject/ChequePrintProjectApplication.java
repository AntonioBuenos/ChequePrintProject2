package by.smirnov.chequeprintproject;

import by.smirnov.chequeprintproject.config.MapperConfiguration;
import by.smirnov.chequeprintproject.config.PersistenceProvidersConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "by.smirnov.chequeprintproject")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import({PersistenceProvidersConfiguration.class, MapperConfiguration.class})
public class ChequePrintProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChequePrintProjectApplication.class, args);
    }

}
