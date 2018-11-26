package com.cst.resources.api.issue;

import com.cst.model.JiraResponse;
import com.cst.model.SQSMessage;
import com.cst.service.JiraServiceAccessor;
import com.cst.service.SQSAccessor;
import com.cst.transform.SQSMessageTransformer;
import com.google.inject.Inject;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/api/issue")
@Produces(MediaType.TEXT_PLAIN)
public class IssueResource {


    private final JiraServiceAccessor jiraServiceAccessor;
    private final SQSMessageTransformer sqsMessageTransformer;
    private final SQSAccessor sqsAccessor;


    @Inject
    public IssueResource(JiraServiceAccessor jiraServiceAccessor, SQSMessageTransformer sqsMessageTransformer, SQSAccessor sqsAccessor) {
        this.jiraServiceAccessor = jiraServiceAccessor;
        this.sqsMessageTransformer = sqsMessageTransformer;
        this.sqsAccessor = sqsAccessor;
    }

    @GET
    @Path("/sum")
    public Response sendSum(@QueryParam("query") String query, @QueryParam("name") String name) throws IOException {

        List<JiraResponse> jiraResponses = jiraServiceAccessor.getJiraResponses(query);
        SQSMessage sqsMessage = sqsMessageTransformer.getSQSMessage(jiraResponses, name);
        sqsAccessor.sendMessage(sqsMessage);
        return Response.ok().entity("Message Posted Successfully").build();
    }

    @GET
    @Path("/receive")
    public Response receiveSum() {
        return Response.ok().entity(sqsAccessor.readMessage()).build();
    }


}
