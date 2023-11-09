package ca.teambot.it.cave.examination.bot.ui;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Table;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;

public class DatabaseConnect {
    private static final String DYNAMODB_TABLE = "Feedback";

    private static Table dbTable;

    public static void initializeDynamoDB(Context context) {
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(context, "us-east-2:c6963470-ac6c-4ffe-b98b-e3d5ca7833b2", Regions.US_EAST_2);
        AmazonDynamoDBClient dbClient = new AmazonDynamoDBClient(credentialsProvider);
        dbTable = Table.loadTable(dbClient, DYNAMODB_TABLE);
    }
    public void addItemToTable(Document document) {
        // Perform the PutItem operation using the built-in putItem method
        dbTable.putItem(document);
    }

}