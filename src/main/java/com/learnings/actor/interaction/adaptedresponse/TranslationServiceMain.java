package com.learnings.actor.interaction.adaptedresponse;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Behaviors;
import com.learnings.actor.interaction.adaptedresponse.frontend.Command;
import com.learnings.actor.interaction.adaptedresponse.frontend.Translate;
import com.learnings.actor.interaction.adaptedresponse.request.Request;

import java.net.URI;

public class TranslationServiceMain {

    public static void main(String[] args) {
        ActorSystem<Command> actorSystem = ActorSystem.create(Behaviors.setup(context -> {
            ActorRef<Request> backend = context.spawn(Behaviors.empty(), "backend");
            ActorRef<Command> translationService = context.spawn(TranslationService.create(backend), "translationService");
            ActorRef<URI> receiver = context.spawn(Behaviors.receiveMessage(uri -> {
                System.out.println("Translation result received: " + uri);
                return Behaviors.same();
            }), "resultReceiver");
            URI siteToTranslate = URI.create("http://example.com");
            translationService.tell(new Translate(siteToTranslate, receiver));
            return Behaviors.empty();
                }
        ), "translationServiceSystem");

        // Terminate the actor system after a delay to allow for processing
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            actorSystem.terminate();
        }
    }
}
