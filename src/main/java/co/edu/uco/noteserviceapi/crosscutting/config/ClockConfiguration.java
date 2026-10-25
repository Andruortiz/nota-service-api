package co.edu.uco.noteserviceapi.crosscutting.config;

import java.time.Clock;
import org.springframework.context.annotation.Bean;

@Conf
public class ClockConfiguration {

    @Bean
    public Clock reloj() {
        return Clock.systemUTC();
    }
}
