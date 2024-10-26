package com.learnings.actor.interaction.askfromoutside;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.AskPattern;
import com.learnings.actor.interaction.tell.CookieFabricService;
import com.learnings.actor.interaction.tell.Request;
import com.learnings.actor.interaction.tell.Response;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

public class Main {

    public static void main(String[] args) {
        ActorSystem<Request> cookieFabricService =
                ActorSystem.create(CookieFabricService.create(), "cookieFabricService");
        CompletionStage<Response> result =
                AskPattern.ask(
                        cookieFabricService,
                        replyTo -> new Request(3, replyTo),
                        Duration.ofSeconds(3),
                        cookieFabricService.scheduler());

        result.whenComplete(
                (reply, failure) -> {
                    if (reply.getResult() != null)
                        System.out.println("Yay, " +  reply.getResult());
                    else System.out.println("Boo! didn't get cookies in time. " + failure);
                });
    }
}
