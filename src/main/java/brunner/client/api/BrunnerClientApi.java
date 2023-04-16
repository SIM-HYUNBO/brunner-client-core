package brunner.client.api;

import java.io.IOException;
import java.net.URISyntaxException;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class BrunnerClientApi {
	public static String msgFieldName_hostName = "hostName";
	public static String msgFieldName_portNumber = "portNumber";
	public static String msgFieldName_queueName = "queueName";
	public static String msgFieldName_command = "command";
	public static String msgFieldName_packageName = "packageName";
	public static String msgFieldName_className = "className";
	public static String msgFieldName_methodName = "methodName";
	public static String msgFieldName_requestTimeoutMS = "requestTimeoutMS";
	public static String msgFieldName_inputData = "inputData";
	public static String msgFieldName_resultCode = "resultCode";
	public static String msgFieldName_resultMessage = "resultMessage";
	public static String SYSTEM_CODE_DEFAULT = "00";

	public static String resultCode_success = "0";
	public static String resultCode_systemException = "-1";
	public static String resultCode_noDataFound = "-2";

	protected int requestTimeoutMs;
	protected JsonParser parser;

	public BrunnerClientApi(int requestTimeoutMs) {
		this.requestTimeoutMs = requestTimeoutMs;
		parser = new JsonParser();
	}

	protected JsonObject getMessage(String command, String packageName, String className, String methodName,
			int requestTimeoutMS, String[] inputDataItems) throws JsonSyntaxException, IOException, URISyntaxException {

		JsonObject ret = new JsonObject();

		ret.addProperty("command", command);
		ret.addProperty("packageName", packageName);
		ret.addProperty("className", className);
		ret.addProperty("methodName", methodName);

		JsonObject inputData = new JsonObject();

		for (int i = 0; i < inputDataItems.length; i++) {
			inputData.add(inputDataItems[i], JsonNull.INSTANCE);
		}

		ret.add("inputData", inputData);
		return ret;
	}

	protected JsonObject getMessage(String command, String packageName, String className, String methodName,
			int requestTimeoutMS, String[] inputDataItemsKeys, Object[] inputDataItemValues) throws Exception {

		JsonObject ret = new JsonObject();

		ret.addProperty("command", command);
		ret.addProperty("packageName", packageName);
		ret.addProperty("className", className);
		ret.addProperty("methodName", methodName);
		ret.addProperty("requestTimeoutMS", requestTimeoutMS);

		JsonObject inputData = new JsonObject();

		for (int i = 0; i < inputDataItemsKeys.length; i++) {
			if (inputDataItemValues[i].equals(JsonNull.INSTANCE))
				inputData.add(inputDataItemsKeys[i], JsonNull.INSTANCE);
			else if (inputDataItemValues[i] instanceof String)
				inputData.addProperty(inputDataItemsKeys[i], (String) inputDataItemValues[i]);
			else if (inputDataItemValues[i] instanceof Number)
				inputData.addProperty(inputDataItemsKeys[i], (Number) inputDataItemValues[i]);
			else if (inputDataItemValues[i] instanceof Boolean)
				inputData.addProperty(inputDataItemsKeys[i], (Boolean) inputDataItemValues[i]);
			else if (inputDataItemValues[i] instanceof Character)
				inputData.addProperty(inputDataItemsKeys[i], (Character) inputDataItemValues[i]);
			else
				throw new Exception("Not supported data type");
		}

		ret.add("inputData", inputData);
		return ret;
	}
}
