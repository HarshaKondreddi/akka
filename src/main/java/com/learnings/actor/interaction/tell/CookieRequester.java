package com.learnings.actor.interaction.tell;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Props;

public class CookieRequester {

    public static void main(String[] args) throws InterruptedException {
        ActorSystem<Request> cookieFabricService =
                ActorSystem.create(CookieFabricService.create(), "cookieFabricService");

        ActorRef<Response> requester = cookieFabricService.systemActorOf(
                Requester.create(), "requester", Props.empty()
        );

        cookieFabricService.tell(new Request(11, requester));

        System.out.println(cookieFabricService.path());
        System.out.println(requester.path());
    }
}
