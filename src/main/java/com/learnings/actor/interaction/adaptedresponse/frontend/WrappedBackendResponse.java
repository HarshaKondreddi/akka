package com.learnings.actor.interaction.adaptedresponse.frontend;

import com.learnings.actor.interaction.adaptedresponse.response.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WrappedBackendResponse implements Command {

    private Response response;
}
