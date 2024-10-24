package com.learnings.actor.interaction.fireandforget;


import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;

public class PrinterMain {

    public static void main(String[] args) {
        ActorRef<PrintMessage> ref = ActorSystem.create(Printer.create(), "test");
        ref.tell(new PrintMessage("Hello World!"));
        ref.tell(new PrintMessage("Hello Harsha!"));
    }
}
