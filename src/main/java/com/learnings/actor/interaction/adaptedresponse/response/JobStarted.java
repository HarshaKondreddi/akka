package com.learnings.actor.interaction.adaptedresponse.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobStarted implements Response {

    private int taskId;
}
