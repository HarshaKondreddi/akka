package com.learnings.actor.helloworld.application;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.learnings.actor.helloworld.models.Greet;
import com.learnings.actor.helloworld.models.Greeted;
import com.learnings.actor.helloworld.models.SayHello;

public class GreetingService extends AbstractBehavior<SayHello> {

    // Singleton initialization for the application
    private final ActorRef<Greet> greeter;

    public GreetingService(ActorContext<SayHello> context) {
        super(context);
        // Spawns child actor greeter
        greeter = context.spawn(Greeter.create(), "greeter");
    }

    public static Behavior<SayHello> create() {
        return Behaviors.setup(GreetingService::new);
    }

    @Override
    public Receive<SayHello> createReceive() {
        return newReceiveBuilder().onMessage(SayHello.class, this::onSayHello).build();
    }

    private Behavior<SayHello> onSayHello(SayHello command) {
        // On every new message, spawns a child actor -> GreetingDelegator
        ActorRef<Greeted> replyTo =
                getContext().spawn(GreetingDelegator.create(3), command.getName());
        greeter.tell(new Greet(command.getName(), replyTo));
        return this;
    }
}
