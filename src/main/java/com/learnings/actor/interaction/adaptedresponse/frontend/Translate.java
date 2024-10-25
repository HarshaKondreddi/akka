package com.learnings.actor.interaction.adaptedresponse.frontend;

import akka.actor.typed.ActorRef;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URI;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Translate implements Command {

    private URI site;
    private ActorRef<URI> replyTo;
}
