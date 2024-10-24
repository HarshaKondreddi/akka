package com.learnings.actor.helloworld.application;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.learnings.actor.helloworld.models.Greet;
import com.learnings.actor.helloworld.models.Greeted;

public class Greeter extends AbstractBehavior<Greet> {

    public Greeter(ActorContext<Greet> context) {
        super(context);
    }

    public static Behavior<Greet> create() {
        return Behaviors.setup(Greeter::new);
    }

    @Override
    public Receive<Greet> createReceive() {
        return newReceiveBuilder().onMessage(Greet.class, this::OnGreet).build();
    }

    private Behavior<Greet> OnGreet(Greet greet) {
        System.out.println("Hello! " + greet.getWhom());
        greet.getReplyTo().tell(new Greeted(greet.getWhom(), getContext().getSelf()));
        return this;
    }
}
