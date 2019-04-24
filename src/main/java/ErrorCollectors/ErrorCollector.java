package ErrorCollectors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestResult;

public class ErrorCollector {
	
	private static Map<ITestResult, List<Throwable>> verificationFailuresMap = new HashMap<ITestResult,List<Throwable>>();
	private static Map<ITestResult, List<String>> snapshotMap = new HashMap<ITestResult,List<String>>();
	
	
	public static Map<ITestResult, List<String>> getSnapshotMap(){
		return snapshotMap;
	}
	
	public static void assertTrue(boolean condition){
		Assert.assertTrue(condition);
	}
	
	public static void assertTrue(boolean condition,String message){
		Assert.assertTrue(condition,message);
		
	}
	
}
