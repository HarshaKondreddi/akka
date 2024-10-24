package com.learnings.actor.interaction.fireandforget;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class Printer extends AbstractBehavior<PrintMessage> {

    public Printer(ActorContext<PrintMessage> context) {
        super(context);
    }

    public static Behavior<PrintMessage> create() {
        return Behaviors.setup(Printer::new);
    }

    @Override
    public Receive<PrintMessage> createReceive() {
        return newReceiveBuilder()
                .onMessage(PrintMessage.class, this::onPrintMessage)
                .build();
    }

    private Behavior<PrintMessage> onPrintMessage(PrintMessage message) {
        System.out.println("Printed message: " + message.getMessage());
        return this;
    }


}
