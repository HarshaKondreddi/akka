package com.learnings.actor.interaction.adaptedresponse.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URI;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobCompleted implements Response {

    private int taskId;
    private URI result;
}
