package brunner.client.api;

import com.google.gson.JsonObject;

import mw.launchers.RPCClient;
import mw.utility.StringUtil;

public class AuthorityGroupProgram extends BrunnerClientApi {

	static AuthorityGroupProgram instance;

	public static AuthorityGroupProgram getInstance() {
		return instance == null ? new AuthorityGroupProgram(1000) : instance;
	}

	AuthorityGroupProgram(int requestTimeoutMs) {
		super(requestTimeoutMs);
	}

	/***
	 * 특정 시스템 코드의 권한 그룹에 프로그램 등록
	 * 
	 * @param client
	 * @param jConnectionInfo
	 * @param systemCode
	 * @param authorityGroupId
	 * @param programId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public JsonObject registerAuthorityGroupProgram(RPCClient client, JsonObject jConnectionInfo, String systemCode,
			String authorityGroupId, String programId, String userId) throws Exception {

		JsonObject request = getMessage(
				"RPC Request",
				"com.brunner.service",
				"SVC_AuthorityGroupProgram",
				"TXN_AuthorityGroupProgram_registerAuthorityGroupProgram",
				10000,
				new String[] {
						"txnId",
						"systemCode",
						"authorityGroupId",
						"programId",
						"userId" },
				new Object[] {
						StringUtil.getTxnId(),
						systemCode,
						authorityGroupId,
						programId,
						userId });

		String queueName = jConnectionInfo.get(BrunnerClientApi.msgFieldName_queueName).getAsString();

		String reply = client.requestSync(
				queueName,
				request.toString(),
				10000);

		JsonObject jReply = (JsonObject) parser.parse(reply);

		return jReply;
	}

	/***
	 * 특정 시스템 코드의 권한 그룹에 프로그램 삭제
	 * 
	 * @param client
	 * @param jConnectionInfo
	 * @param systemCode
	 * @param authorityGroupId
	 * @param programId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public JsonObject unregisterAuthorityGroupProgram(RPCClient client, JsonObject jConnectionInfo, String systemCode,
			String authorityGroupId, String programId, String userId) throws Exception {

		JsonObject request = getMessage(
				"RPC Request",
				"com.brunner.service",
				"SVC_AuthorityGroupProgram",
				"TXN_AuthorityGroupProgram_unregisterAuthorityGroupProgram",
				10000,
				new String[] {
						"txnId",
						"systemCode",
						"authorityGroupId",
						"programId",
						"userId" },
				new Object[] {
						StringUtil.getTxnId(),
						systemCode,
						authorityGroupId,
						programId,
						userId });

		String queueName = jConnectionInfo.get(BrunnerClientApi.msgFieldName_queueName).getAsString();

		String reply = client.requestSync(
				queueName,
				request.toString(),
				10000);

		JsonObject jReply = (JsonObject) parser.parse(reply);

		return jReply;
	}

	/***
	 * 특정 시스템 코드의 권한 그룹에 프로그램 목록 조회
	 * 
	 * @param client
	 * @param jConnectionInfo
	 * @param systemCode
	 * @param authorityGroupId
	 * @param programId
	 * @return
	 * @throws Exception
	 */
	public JsonObject viewAuthorityGroupProgramList(RPCClient client, JsonObject jConnectionInfo, String systemCode,
			String authorityGroupId, String programId) throws Exception {

		JsonObject request = getMessage(
				"RPC Request",
				"com.brunner.service",
				"SVC_AuthorityGroupProgram",
				"TXN_AuthorityGroupProgram_viewAuthorityGroupProgramList",
				10000,
				new String[] {
						"txnId",
						"systemCode",
						"authorityGroupId",
						"programId" },
				new Object[] {
						StringUtil.getTxnId(),
						systemCode,
						authorityGroupId,
						programId });

		String queueName = jConnectionInfo.get(BrunnerClientApi.msgFieldName_queueName).getAsString();

		String reply = client.requestSync(
				queueName,
				request.toString(),
				10000);

		JsonObject jReply = (JsonObject) parser.parse(reply);

		return jReply;
	}

	/***
	 * 특정 시스템 코드의 사용자의 프로그램 목록 조회
	 * 
	 * @param client
	 * @param jConnectionInfo
	 * @param systemCode
	 * @param userId
	 * @param parentProgramId
	 * @return
	 * @throws Exception
	 */
	public JsonObject viewUserProgramList(RPCClient client, JsonObject jConnectionInfo, String systemCode,
			String userId, String parentProgramId) throws Exception {

		JsonObject request = getMessage(
				"RPC Request",
				"com.brunner.service",
				"SVC_AuthorityGroupProgram",
				"TXN_AuthorityGroupProgram_viewUserProgramList",
				10000,
				new String[] {
						"txnId",
						"systemCode",
						"userId",
						"parentProgramId" },
				new Object[] {
						StringUtil.getTxnId(),
						systemCode,
						userId,
						parentProgramId });

		String queueName = jConnectionInfo.get(BrunnerClientApi.msgFieldName_queueName).getAsString();

		String reply = client.requestSync(
				queueName,
				request.toString(),
				10000);

		JsonObject jReply = (JsonObject) parser.parse(reply);

		return jReply;
	}
}
