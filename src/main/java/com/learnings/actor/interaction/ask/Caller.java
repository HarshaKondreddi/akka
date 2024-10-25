package com.learnings.actor.interaction.ask;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.time.Duration;

public class Caller extends AbstractBehavior<AdaptedResponse> {

    public Caller(ActorContext<AdaptedResponse> context, ActorRef<OpenTheDoorCmd> customer) {
        super(context);

        Duration timeout  = Duration.ofSeconds(5);
        context.ask(
                CustomerResponse.class,
                customer,
                timeout,
                (ActorRef<CustomerResponse> ref) -> new OpenTheDoorCmd("test", ref),
                (response, throwable) -> {
                    if (response != null) {
                        return new AdaptedResponse(response.getMessage());
                    } else {
                        return new AdaptedResponse("Request failed");
                    }
                }
        );
    }

    public static Behavior<AdaptedResponse> create(ActorRef<OpenTheDoorCmd> customer) {
        return Behaviors.setup(context -> new Caller(context, customer));
    }

    public static Behavior<AdaptedResponse> create() {
        return Behaviors.setup(context -> new Caller(context, null));
    }

    @Override
    public Receive<AdaptedResponse> createReceive() {
        return newReceiveBuilder()
                .onMessage(AdaptedResponse.class, this::onReceive)
                .build();
    }

    private Behavior<AdaptedResponse> onReceive(AdaptedResponse response) {
        return Behaviors.stopped();
    }
}
