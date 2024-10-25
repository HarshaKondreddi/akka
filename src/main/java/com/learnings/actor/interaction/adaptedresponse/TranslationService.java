package com.learnings.actor.interaction.adaptedresponse;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.learnings.actor.interaction.adaptedresponse.frontend.Command;
import com.learnings.actor.interaction.adaptedresponse.frontend.Translate;
import com.learnings.actor.interaction.adaptedresponse.frontend.WrappedBackendResponse;
import com.learnings.actor.interaction.adaptedresponse.request.Request;
import com.learnings.actor.interaction.adaptedresponse.request.StartTranslationJob;
import com.learnings.actor.interaction.adaptedresponse.response.JobCompleted;
import com.learnings.actor.interaction.adaptedresponse.response.JobProgress;
import com.learnings.actor.interaction.adaptedresponse.response.JobStarted;
import com.learnings.actor.interaction.adaptedresponse.response.Response;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class TranslationService extends AbstractBehavior<Command> {

    private ActorRef<Request> backend;
    private ActorRef<Response> backendResponseAdaptor;

    private int taskIdCounter = 0;
    private Map<Integer, ActorRef<URI>> inProgress = new HashMap<>();

    public static Behavior<Command> create(ActorRef<Request> backend) {
        return Behaviors.setup(context -> new TranslationService(context, backend));
    }

    public TranslationService(ActorContext<Command> context, ActorRef<Request> backend) {
        super(context);
        this.backend = backend;
        this.backendResponseAdaptor = context.messageAdapter(Response.class, WrappedBackendResponse::new);
    }

    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
                .onMessage(Translate.class, this::onTranslate)
                .onMessage(WrappedBackendResponse.class, this::onWrappedBackendResponse)
                .build();
    }

    private Behavior<Command> onTranslate(Translate command) {
        taskIdCounter += 1;
        inProgress.put(taskIdCounter, command.getReplyTo());
        System.out.println("Processing task:" + taskIdCounter);
        backend.tell(new StartTranslationJob(taskIdCounter, command.getSite(), backendResponseAdaptor));
        return Behaviors.same();
    }

    private Behavior<Command> onWrappedBackendResponse(WrappedBackendResponse backendResponse) {
        Response response = backendResponse.getResponse();
        if (response instanceof JobStarted) {
            JobStarted rsp = (JobStarted) response;
            System.out.println("Started " + rsp.getTaskId());
        } else if (response instanceof JobProgress) {
            JobProgress rsp = (JobProgress) response;
            System.out.println("Progress " + rsp.getTaskId());
        } else if (response instanceof JobCompleted) {
            JobCompleted rsp = (JobCompleted) response;
            System.out.println("Completed " + rsp.getTaskId());
            inProgress.get(rsp.getTaskId()).tell(rsp.getResult());
            inProgress.remove(rsp.getTaskId());
        } else {
            return Behaviors.unhandled();
        }

        return this;
    }
}
