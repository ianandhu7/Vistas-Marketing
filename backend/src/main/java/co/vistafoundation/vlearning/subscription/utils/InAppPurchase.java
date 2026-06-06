package co.vistafoundation.vlearning.subscription.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Set;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;
import java.nio.charset.StandardCharsets;

import com.apple.itunes.storekit.client.APIException;
import com.apple.itunes.storekit.client.AppStoreServerAPIClient;
import com.apple.itunes.storekit.model.Environment;
import com.apple.itunes.storekit.model.JWSTransactionDecodedPayload;
import com.apple.itunes.storekit.model.TransactionInfoResponse;
import com.apple.itunes.storekit.verification.SignedDataVerifier;
import com.apple.itunes.storekit.verification.VerificationException;


@Component
public class InAppPurchase {

	@Value("${aws.s3.bucket}")
	private String bucket;
	
	@Value("${ios.in-app-purchase.issuer.id}")
	private String issuerId;
	
	@Value("${ios.in-app-purchase.key.id}")
	private String keyId;
	
	@Value("${ios.in-app-purchase.bundle.id}")
	private String bundleId;
	
	@Value("${ios.in-app-purchase.environment}")
	private String environment;

	@Value("${ios.in-app-purchase.key.path}")
	private Resource iapKeyResource;
	
	public JWSTransactionDecodedPayload  inAppPurchaseResponse(String transactionId) {
		

		try {
		
	        File rootCA1=ResourceUtils.getFile("classpath:In-App-purchase/AppleComputerRootCertificate.cer");
	        File rootCA2=ResourceUtils.getFile("classpath:In-App-purchase/AppleIncRootCertificate.cer");
	        File rootCA3=ResourceUtils.getFile("classpath:In-App-purchase/AppleRootCA-G2.cer");		
	        File rootCA4=ResourceUtils.getFile("classpath:In-App-purchase/AppleRootCA-G3.cer");		

	        String encodedKey;
	        try (InputStream is = iapKeyResource.getInputStream()) {
	            encodedKey = new String(StreamUtils.copyToByteArray(is), StandardCharsets.UTF_8);
	        }
	   
	        
			 Environment environment1=null;
			if(environment.equals("production")) {
				
	            environment1 = Environment.PRODUCTION;
	          	
			}else {
				environment1 = Environment.SANDBOX;		
			}
				
				
	        AppStoreServerAPIClient client =
	                new AppStoreServerAPIClient(encodedKey, keyId, issuerId, bundleId, environment1);
	    
	        TransactionInfoResponse response=null;
			try {
				response = client.getTransactionInfo(transactionId);
			} catch (APIException | IOException e) {
			
				e.printStackTrace();
			}
		  
	        Set<InputStream> rootCAs=null;
			try {
				rootCAs = Set.of(
				        new FileInputStream(rootCA1),
				        new FileInputStream(rootCA2),
				        new FileInputStream(rootCA3),
				        new FileInputStream(rootCA4)
				);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	        
	        
	        SignedDataVerifier signedPayloadVerifier = new SignedDataVerifier(rootCAs, bundleId, null, environment1, true);
	      
			JWSTransactionDecodedPayload transactionDecodedPayload = null;
			try {
				transactionDecodedPayload = signedPayloadVerifier.verifyAndDecodeTransaction(response.getSignedTransactionInfo());
			} catch (VerificationException e) {
             	e.printStackTrace();
             	
			}
			return transactionDecodedPayload;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
           
	      
	       
	}
}
