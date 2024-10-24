package com.learnings.actor.helloworld.models;

import akka.actor.typed.ActorRef;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Greeted {

    private String whom;
    private ActorRef<Greet> from;
}
