import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Table;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

public class DatabaseConnect {
    private static final String DYNAMODB_TABLE = "Feedback";

    private static CognitoCachingCredentialsProvider credentialsProvider;
    private static AmazonDynamoDBClient dbClient;
    private static Table dbTable;

    public static void initializeDynamoDB(Context context) {
        // Create a new credentials provider
        credentialsProvider = new CognitoCachingCredentialsProvider(context, "us-east-2:c6963470-ac6c-4ffe-b98b-e3d5ca7833b2", Regions.US_EAST_2);

        // Create a connection to DynamoDB
        dbClient = new AmazonDynamoDBClient(credentialsProvider);

        // Create a table reference
        dbTable = Table.loadTable(dbClient, DYNAMODB_TABLE);
    }

    public static CognitoCachingCredentialsProvider getCredentialsProvider() {
        return credentialsProvider;
    }

    public static AmazonDynamoDBClient getDbClient() {
        return dbClient;
    }

    public static Table getDbTable() {
        return dbTable;
    }
}