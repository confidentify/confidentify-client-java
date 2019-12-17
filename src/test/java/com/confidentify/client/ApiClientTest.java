package com.confidentify.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.confidentify.client.api.AuthApi;
import com.confidentify.client.api.ProcessApi;
import com.confidentify.client.model.AuthRequest;
import com.confidentify.client.model.AuthResponse;
import com.confidentify.client.model.EmailRequest;
import com.confidentify.client.model.EmailRequestRecord;
import com.confidentify.client.model.EmailResponse;
import com.confidentify.client.model.EmailResponseRecord;
import com.confidentify.client.model.IdentifyRequest;
import com.confidentify.client.model.IdentifyRequestRecord;
import com.confidentify.client.model.IdentifyResponse;
import com.confidentify.client.model.IdentifyResponseRecord;
import com.confidentify.client.model.IdentifyResponseRecordEntity;
import com.confidentify.client.model.ProcessorVerdict;

public class ApiClientTest {

    private static final String USERNAME = getProperty("confidentify.username");
    private static final String PASSWORD = getProperty("confidentify.password");

    @Test
    public void testVanillaScenario() throws ApiException {
        assertNotNull("Username is not set. Use the 'confidentify.username' system property or 'CONFIDENTIFY_USERNAME' environment variable.", USERNAME);
        assertNotNull("Password is not set. Use the 'confidentify.password' system property or 'CONFIDENTIFY_PASSWORD' environment variable.", PASSWORD);

        final ApiClient apiClient = new ApiClient();

        // Authenticate
        final AuthApi authApi = new AuthApi(apiClient);
        final AuthResponse authResponse = authApi.authPost(new AuthRequest().username(USERNAME).password(PASSWORD));
        final String accessToken = authResponse.getAccessToken();
        apiClient.setAccessToken(accessToken);

        final ProcessApi processApi = new ProcessApi(apiClient);
        
        // Email processing
        final String[] emailInputs = { "info[AT]gmali.com", "conf.identify+4897392@confidentify.com" };

        final EmailRequest emailRequest = new EmailRequest();
        for (String emailInput : emailInputs) {
            emailRequest.addRecordsItem(new EmailRequestRecord().email(emailInput));
        }
        final EmailResponse emailResponse = processApi.emailPost(emailRequest);
        assertEquals(emailInputs.length, emailResponse.getRecords().size());

        for (int i = 0; i < emailInputs.length; i++) {
            final String emailInput = emailInputs[i];
            final EmailResponseRecord emailResponseRecord = emailResponse.getRecords().get(i);
            final ProcessorVerdict verdict = emailResponseRecord.getOutcome().getVerdict();

            System.out.println();
            System.out.println("Input email:      " + emailInput);
            System.out.println("Formatted email:  " + emailResponseRecord.getEmailFormatted());
            System.out.println("Simplified email: " + emailResponseRecord.getEmailSimplified());
            System.out.println("Suggested email:  " + emailResponseRecord.getEmailSuggested());
            System.out.println("Outcome verdict:  " + verdict);
            System.out.println("Outcome inf       " + emailResponseRecord.getOutcome().getInfo());
            System.out.println("Outcome warn:     " + emailResponseRecord.getOutcome().getWarn());
        }

        // Identify processing
        final IdentifyRequestRecord identifyRequestRecord = new IdentifyRequestRecord().id("fdlkjs321l").text("Dr Kim Johannesson - Medical Doctor at Hamlet Hospital Inc.");
        final IdentifyResponse identifyResponse = processApi.identifyPost(new IdentifyRequest().addRecordsItem(identifyRequestRecord));
        final IdentifyResponseRecord identifyResponseRecord = identifyResponse.getRecords().get(0);
        for (IdentifyResponseRecordEntity entity : identifyResponseRecord.getEntities()) {
            System.out.println(entity.getEntityType() + " entity found: " + entity.getText());
        }
    }

    private static String getProperty(String key) {
        final String value = System.getProperty(key);
        if (value == null || value.isEmpty()) {
            final String envKey = key.toUpperCase().replace('.', '_');
            return System.getenv(envKey);
        }
        return value;
    }
}
