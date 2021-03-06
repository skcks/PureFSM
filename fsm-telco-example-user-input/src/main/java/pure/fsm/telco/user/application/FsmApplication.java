package pure.fsm.telco.user.application;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import pure.fsm.telco.user.application.api.UserActionResource;

import java.time.temporal.ChronoUnit;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.concurrent.TimeUnit.SECONDS;
import static pure.fsm.core.accessor.ContextHistoryFormatter.HISTORY_FORMATTER;

public class FsmApplication extends Application<FsmConfiguration> {

    private TelcoStateMachineBundle stateMachineBundle;

    public static void main(final String[] args) throws Exception {
        new FsmApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<FsmConfiguration> bootstrap) {
        stateMachineBundle = new TelcoStateMachineBundle();

        bootstrap.addBundle(new ViewBundle());
        bootstrap.addBundle(stateMachineBundle);
    }

    @Override
    public void run(FsmConfiguration configuration, Environment environment) throws Exception {
        stateMachineBundle.getTimeoutTicker(10, SECONDS).startTickScheduler();
        stateMachineBundle.getCleaner(newArrayList(HISTORY_FORMATTER), 10, SECONDS, 5, ChronoUnit.SECONDS).startScheduler();

        UserActionResource resource = new UserActionResource(
                stateMachineBundle.getTemplate(),
                stateMachineBundle.getStateMachineViewFactory(),
                stateMachineBundle.getTelcoStateFactory());

        environment.jersey().register(resource);
    }
}
