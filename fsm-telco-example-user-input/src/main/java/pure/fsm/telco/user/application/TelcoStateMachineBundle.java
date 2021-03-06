package pure.fsm.telco.user.application;

import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import pure.fsm.core.state.StateFactory;
import pure.fsm.dropwizard.hazelcast.bundle.StateMachineBundle;
import pure.fsm.telco.user.application.api.StateMachineViewFactory;
import pure.fsm.telco.user.domain.state.TelcoStateFactory;
import pure.fsm.telco.user.infra.TelcoGateway;

public class TelcoStateMachineBundle extends StateMachineBundle {

    private StateMachineViewFactory stateMachineViewFactory;
    private TelcoStateFactory telcoStateFactory;

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
    }

    @Override
    public void run(Environment environment) {
        super.run(environment);
        stateMachineViewFactory = new StateMachineViewFactory();
    }

    protected StateFactory createStateFactory() {
        TelcoGateway telcoGateway = new TelcoGateway(getTemplate());
        telcoStateFactory = new TelcoStateFactory(getDistributedResourceFactory(), telcoGateway);
        return telcoStateFactory;
    }

    public TelcoStateFactory getTelcoStateFactory() {
        return telcoStateFactory;
    }

    public StateMachineViewFactory getStateMachineViewFactory() {
        return stateMachineViewFactory;
    }
}
