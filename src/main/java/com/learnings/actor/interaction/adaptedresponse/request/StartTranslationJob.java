package com.learnings.actor.interaction.adaptedresponse.request;

import akka.actor.typed.ActorRef;
import com.learnings.actor.interaction.adaptedresponse.response.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URI;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StartTranslationJob implements Request {
    private int taskId;
    private URI site;
    private ActorRef<Response> replyTo;
}
