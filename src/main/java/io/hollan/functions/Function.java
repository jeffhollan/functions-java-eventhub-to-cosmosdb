package io.hollan.functions;

import java.util.*;
import com.microsoft.azure.serverless.functions.annotation.*;
import com.microsoft.azure.serverless.functions.*;
import org.json.JSONObject;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {

    @FunctionName("ProcessData")
    public void process(
            @EventHubTrigger(name = "data", eventHubName = "iot", connection = "EventHubConnection") String data,
            @DocumentDBOutput(name = "document", databaseName = "main", collectionName = "events", connection = "AzureWebJobsCosmosDBConnectionString") OutputBinding<String> document,
            final ExecutionContext context) {
        context.getLogger().info("Java Event Hub trigger processed a request: " + data);
        JSONObject eventGridMessage = new JSONObject(data);
        eventGridMessage.put("id", java.util.UUID.randomUUID().toString());
        context.getLogger().info("message: " + eventGridMessage.toString());
        document.setValue(eventGridMessage.toString());
    }
}
