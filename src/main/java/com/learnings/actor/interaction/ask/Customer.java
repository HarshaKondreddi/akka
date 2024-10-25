package com.learnings.actor.interaction.ask;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class Customer extends AbstractBehavior<OpenTheDoorCmd> {

    public Customer(ActorContext<OpenTheDoorCmd> context) {
        super(context);
    }

    public static Behavior<OpenTheDoorCmd> create() {
        return Behaviors.setup(Customer::new);
    }

    @Override
    public Receive<OpenTheDoorCmd> createReceive() {
        return newReceiveBuilder()
                .onMessage(OpenTheDoorCmd.class, this::onOpenTheDoorCmd)
                .build();
    }

    private Behavior<OpenTheDoorCmd> onOpenTheDoorCmd(OpenTheDoorCmd openTheDoorCmd) {
        String response = openTheDoorCmd.getPurpose().equals("delivery") ? "place the order at the door" : "coming";
        System.out.println(response);
        openTheDoorCmd.getReplyTo().tell(new CustomerResponse(response));
        return this;
    }
}