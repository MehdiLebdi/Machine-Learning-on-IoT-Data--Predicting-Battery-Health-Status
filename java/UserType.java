package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
/*
 * This service is provided for model serving. Python script is invoked from java layer.
 * Model is assumed to be stored at directory known to system (i.e, model is stored at).
 * 
 * Invoked python script does following:
 * Step-1: Take argument and fetch data from mongodb.
 * Step-2: Load persisted model from specified directory.
 * Step-3: Return predicted value 
 */
@Path("iot")
public class UserType {
	
	public static final String PYTHON_HOME = "D://Softwares//Anaconda3//python";
	public static final String PY_SCRIPT_BASEDIR = "C://Users//tauqe//eclipse-workspace//BatteryHealthProvisioning//src//main//resources//";
	public static final String PY_FILE = "test.py";
	
	@GET
	@Path("/userType")
	@Produces(MediaType.TEXT_PLAIN)
	public String getBatteryHealth(@javax.ws.rs.core.Context HttpServletRequest request,
			@javax.ws.rs.core.Context HttpServletResponse response,
			@QueryParam("userId") String userId) {
		
		UserType userType = new UserType();
		String predictedUserType = userType.invokeModel(userId);
		
		return predictedUserType+" consumption User";
	}
	
	public String invokeModel(String userId) {
		
		StringBuilder outputFromPython = new StringBuilder();
		StringBuilder output1 = new StringBuilder();
		
		String pyFile = PY_SCRIPT_BASEDIR + PY_FILE;
		//System.out.println("pyFile: " + pyFile);
		
		String callAndArgs[] = { PYTHON_HOME , pyFile, userId};
		
		Process process;
		try {
			process = new ProcessBuilder(callAndArgs).start();
			InputStream is = process.getInputStream();
		    InputStreamReader isr = new InputStreamReader(is);
		    BufferedReader br = new BufferedReader(isr);
			BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			System.out.println("  Python execution completed .."); 
			
			stdError.lines().forEach(output1::append);
			//TODO: Loggers should be added here
			//System.out.println(" Error Resp from python ..." + output1.toString());
			
			br.lines().forEach(outputFromPython::append);
			//System.out.println("Resp from python ..." + outputFromPython.toString());
			
			br.close();
			process.destroy();
		} catch (IOException e) {
			
			System.out.println("Error response: " + e.getMessage());
		}		
		return outputFromPython.toString();
	}
	
}
