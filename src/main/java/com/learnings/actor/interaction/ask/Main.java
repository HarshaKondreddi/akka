package com.learnings.actor.interaction.ask;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Behaviors;

public class Main {

    public static void main(String[] args) {
        // Create the Actor System
        ActorSystem<Void> actorSystem = ActorSystem.create(Behaviors.setup(context -> {
            // Create the Customer Actor
            ActorRef<OpenTheDoorCmd> customer = context.spawn(Customer.create(), "customer");

            // Create the Caller Actor and pass the Customer reference
            context.spawn(Caller.create(customer), "caller");

            return Behaviors.empty();
        }), "actorSystem");

        // Add a shutdown hook to terminate the actor system gracefully
        Runtime.getRuntime().addShutdownHook(new Thread(actorSystem::terminate));
    }
}
