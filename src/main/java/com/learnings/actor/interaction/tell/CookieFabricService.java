package com.learnings.actor.interaction.tell;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class CookieFabricService extends AbstractBehavior<Request> {

    private int baked = 0;

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
        if(request.getCookies()+baked > 10) {
            request.getReplyTo().tell(new Response("Raw material unavailable to make cookies"));
        } else {
            baked += request.getCookies();
            request.getReplyTo().tell(new Response("Take " + request.getCookies() + " cookies. Enjoy!"));
        }
        return Behaviors.same();
    }

}
