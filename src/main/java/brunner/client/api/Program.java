package brunner.client.api;

import com.google.gson.JsonObject;

import mw.launchers.RPCClient;
import mw.utility.StringUtil;

public class Program extends BrunnerClientApi {

	static Program instance;

	public static Program getInstance() {
		return instance == null ? new Program(1000) : instance;
	}

	public Program(int requestTimeoutMs) {
		super(requestTimeoutMs);
	}

	/***
	 * 특정 시스템코드 프로그램 목록 조회
	 * 
	 * @throws Exception
	 * 
	 */
	public JsonObject viewProgramList(RPCClient client, JsonObject jConnectionInfo, String systemCode, String programId)
			throws Exception {

		JsonObject request = getMessage(
				"RPC Request",
				"com.brunner.service",
				"SVC_Program",
				"TXN_Program_viewProgramList",
				10000,
				new String[] {
						"txnId",
						"systemCode",
						"programId"
				},
				new Object[] {
						StringUtil.getTxnId(),
						systemCode,
						programId
				});

		String queueName = jConnectionInfo.get(BrunnerClientApi.msgFieldName_queueName).getAsString();

		String reply = client.requestSync(
				queueName,
				request.toString(),
				10000);

		JsonObject jReply = (JsonObject) parser.parse(reply);

		return jReply;
	}

	public JsonObject registerProgram(RPCClient client, JsonObject jConnectionInfo, String systemCode, String userId,
			String programId, String parentProgramId, int displaySeq, String programName, String programClassPath)
			throws Exception {

		JsonObject request = getMessage(
				"RPC Request",
				"com.brunner.service",
				"SVC_Program",
				"TXN_Program_registerProgram",
				10000,
				new String[] {
						"txnId",
						"systemCode",
						"userId",
						"programId",
						"parentProgramId",
						"displaySeq",
						"programName",
						"programClassPath"
				},
				new Object[] {
						StringUtil.getTxnId(),
						systemCode,
						userId,
						programId,
						parentProgramId,
						displaySeq,
						programName,
						programClassPath
				});

		String queueName = jConnectionInfo.get(BrunnerClientApi.msgFieldName_queueName).getAsString();

		String reply = client.requestSync(
				queueName,
				request.toString(),
				10000);

		JsonObject jReply = (JsonObject) parser.parse(reply);

		return jReply;
	}

	public JsonObject unregisterProgram(RPCClient client, JsonObject jConnectionInfo, String systemCode, String userId,
			String programId) throws Exception {

		JsonObject request = getMessage(
				"RPC Request",
				"com.brunner.service",
				"SVC_Program",
				"TXN_Program_unregisterProgram",
				10000,
				new String[] {
						"txnId",
						"systemCode",
						"userId",
						"programId"
				},
				new Object[] {
						StringUtil.getTxnId(),
						systemCode,
						userId,
						programId
				});

		String queueName = jConnectionInfo.get(BrunnerClientApi.msgFieldName_queueName).getAsString();

		String reply = client.requestSync(
				queueName,
				request.toString(),
				10000);

		JsonObject jReply = (JsonObject) parser.parse(reply);

		return jReply;
	}
}
