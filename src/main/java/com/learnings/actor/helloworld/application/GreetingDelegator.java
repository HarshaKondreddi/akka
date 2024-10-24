package com.learnings.actor.helloworld.application;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.learnings.actor.helloworld.models.Greet;
import com.learnings.actor.helloworld.models.Greeted;

public class GreetingDelegator extends AbstractBehavior<Greeted> {

    private final int max;
    private int greetingCounter;

    public GreetingDelegator(ActorContext<Greeted> context, int max) {
        super(context);
        this.max = max;
    }

    public static Behavior<Greeted> create(int max) {
        return Behaviors.setup(context -> new GreetingDelegator(context, max));
    }

    @Override
    public Receive<Greeted> createReceive() {
        return newReceiveBuilder().onMessage(Greeted.class, this::onGreeted).build();
    }

    private Behavior<Greeted> onGreeted(Greeted message) {
        greetingCounter++;
        getContext().getLog().info("Greeting {} for {}", greetingCounter, message.getFrom());
        if (greetingCounter == max) {
            return Behaviors.stopped();
        } else {
            message.getFrom().tell(new Greet(message.getWhom(), getContext().getSelf()));
            return this;
        }
    }
}
