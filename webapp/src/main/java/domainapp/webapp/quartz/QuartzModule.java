package domainapp.webapp.quartz;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class QuartzModule {

    private static final int REPEAT_INTERVAL_SECS = 60;
    private static final int START_DELAY_SECS = 20;
    private static final int MILLIS_PER_SEC = 1000;

}
