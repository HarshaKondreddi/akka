package com.learnings.actor.interaction.requestresponse;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class CookieFabricService extends AbstractBehavior<Request> {

    public CookieFabricService(ActorContext<Request> context) {
        super(context);
    }

    public static Behavior<Request> create() {
        return Behaviors.setup(CookieFabricService::new);
    }

    @Override
    public Receive<Request> createReceive() {
        return newReceiveBuilder()
                .onMessage(Request.class, this::onRequest)
                .build();
    }

    private Behavior<Request> onRequest(Request request) {
        request.getReplyTo().tell(new Response("Here are the cookies for " + request.getQuery()));
        return Behaviors.same();
    }

}
