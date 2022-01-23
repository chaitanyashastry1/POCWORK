package com.listeners;

import com.utils.*;
import org.testng.*;
import org.testng.annotations.ITestAnnotation;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;


public class AnnotationTransformer implements IAnnotationTransformer {
	static DataReader dataReader = null;
	private static boolean isDataInitialized;
	static GlobalVars globalVars;
	private static Map<String, DataElements> testCasesMap=new LinkedHashMap<>();

	private static void initializeExecutionData(){
		try {
			if(!isDataInitialized){
				globalVars=GlobalVars.getInstance();
				dataReader = DataReader.getInstance();
				dataReader.setupDataSheet();
				testCasesMap= dataReader.getTestCaseMapping();
				isDataInitialized=true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		initializeExecutionData();
		if(testCasesMap.containsKey(testMethod.getName())){
			if(!isTestCaseToBeDisabled(testMethod)){
				annotation.setEnabled(false);
			}
			else {
				globalVars.setTestCaseListRunModeTrue(testMethod.getName());
			}
		}
		annotation.setRetryAnalyzer(Retry.class);

	}


	private static boolean isTestCaseToBeDisabled(Method testMethod){
		boolean isTestCaseRunTrue;
		boolean isExecutionTypeTrue=true;
		boolean isPlatformApplicable=true;
		isTestCaseRunTrue=testCasesMap.get(testMethod.getName()).getIsRunTrue();
		if(isTestCaseRunTrue){
			if(globalVars.getExecutionType().equalsIgnoreCase(Constants.SANITY)){
				isExecutionTypeTrue=testCasesMap.get(testMethod.getName()).getIsSanityTrue();
			}
			if(globalVars.getExecutionType().equalsIgnoreCase(Constants.PRODUCTION)){
				isExecutionTypeTrue=testCasesMap.get(testMethod.getName()).getIsProductionTrue();
			}
			if(globalVars.getExecutionType().equalsIgnoreCase(Constants.REGRESSION)){
				isExecutionTypeTrue=testCasesMap.get(testMethod.getName()).getIsRegressionTrue();
			}
			isPlatformApplicable=checkPlatformApplicability(testMethod);
		}
		return isTestCaseRunTrue && isExecutionTypeTrue && isPlatformApplicable;
	}

	private static boolean checkPlatformApplicability(Method testMethod){
		boolean isPlatformApplicable=false;
		switch (globalVars.getPlatformName()){
			case Constants.ANDROID_NATIVE:
				isPlatformApplicable=testCasesMap.get(testMethod.getName()).getIsTrueForAndroidNative();
				break;
			case Constants.ANDROID_AMP:
				isPlatformApplicable=testCasesMap.get(testMethod.getName()).getIsTrueForAndroidAMP();
				break;
			case Constants.ANDROID_WEB:
				isPlatformApplicable=testCasesMap.get(testMethod.getName()).getIsTrueForWebAndroid();
				break;
			case Constants.IOS_NATIVE:
				isPlatformApplicable=testCasesMap.get(testMethod.getName()).getIsTrueForIOSNative();
				break;
			case Constants.IOS_AMP:
				isPlatformApplicable=testCasesMap.get(testMethod.getName()).getIsTrueForIOSAMP();
				break;
			case Constants.IOS_WEB:
				isPlatformApplicable=testCasesMap.get(testMethod.getName()).getIsTrueForWebIOS();
				break;
			case Constants.DESKTOP_WEB:
				isPlatformApplicable=testCasesMap.get(testMethod.getName()).getIsTrueForWebDesktop();
				break;
		}
		return isPlatformApplicable;
	}

}