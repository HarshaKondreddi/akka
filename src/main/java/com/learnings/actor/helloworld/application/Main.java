package com.learnings.actor.helloworld.application;

import akka.actor.typed.ActorSystem;
import com.learnings.actor.helloworld.models.SayHello;

public class Main {

    public static void main(String[] args) {
        //use guardian
        final ActorSystem<SayHello> system =
                ActorSystem.create(GreetingService.create(), "hello");

        system.tell(new SayHello("World"));
        system.tell(new SayHello("Akka"));
    }
}
