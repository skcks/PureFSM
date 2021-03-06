package pure.fsm.telco.state;

import pure.fsm.core.Context;
import pure.fsm.core.event.Event;
import pure.fsm.core.event.TimeoutTickEvent;
import pure.fsm.core.state.BaseNonFinalState;
import pure.fsm.core.state.State;
import pure.fsm.core.state.StateFactory;
import pure.fsm.core.state.TimedOutFinalState;
import pure.fsm.telco.TelcoRechargeContext;
import pure.fsm.telco.event.CancelRechargeEvent;
import pure.fsm.telco.event.TelcoEventVisitor;
import pure.fsm.telco.event.RechargeAcceptedEvent;
import pure.fsm.telco.event.RequestRechargeEvent;

public class BaseTelcoState extends BaseNonFinalState implements TelcoEventVisitor {

    @Override
    public StateFactory factory() {
        return new TelcoStateFactory();
    }

    @Override
    @SuppressWarnings("unchecked")
    public State handle(Context context, Event event) {
        return event.accept(context, this);
    }

    @Override
    public State visit(TelcoRechargeContext context, RequestRechargeEvent requestRechargeEvent) {
        return nonHandledEvent(context, requestRechargeEvent);
    }

    @Override
    public State visit(TelcoRechargeContext context, CancelRechargeEvent cancelRechargeEvent) {
        return nonHandledEvent(context, cancelRechargeEvent);
    }

    @Override
    public State visit(TelcoRechargeContext context, RechargeAcceptedEvent rechargeAcceptedEvent) {
        return nonHandledEvent(context, rechargeAcceptedEvent);
    }

    @Override
    public State visit(Context context, TimeoutTickEvent timeoutTickEvent) {
        System.out.println("In " + getClass().getSimpleName() + ", processing TimeoutTickEvent event ");

        context.setMessage("because timedout");

        return isTimeout(context) ? new TimedOutFinalState() : this;
    }
}
