package com.learnings.actor.interaction.requestresponse;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class Requester extends AbstractBehavior<Response> {

    public Requester(ActorContext<Response> context) {
        super(context);
    }

    public static Behavior<Response> create() {
        return Behaviors.setup(Requester::new);
    }

    @Override
    public Receive<Response> createReceive() {
        return newReceiveBuilder()
                .onMessage(Response.class, this::onResponse)
                .build();
    }

    private Behavior<Response> onResponse(Response response) {
        System.out.println(response.getResult());
        return Behaviors.stopped();
    }
}
