package com.learnings.actor.interaction.ask;

import akka.actor.typed.ActorRef;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OpenTheDoorCmd {

    private String purpose;
    private ActorRef<CustomerResponse> replyTo;
}
