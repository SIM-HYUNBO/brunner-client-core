package brunner.client.api;

import com.google.gson.JsonObject;
import mw.launchers.RPCClient;
import mw.utility.StringUtil;

public class AuthorityGroup extends BrunnerClientApi {

	static AuthorityGroup instance;

	public static AuthorityGroup getInstance() {
		return instance == null ? new AuthorityGroup(1000) : instance;
	}

	AuthorityGroup(int requestTimeoutMs) {
		super(requestTimeoutMs);
	}

	/***
	 * 특정 시스템 코드의 권한 그룹 목록 조회
	 * 
	 * @throws Exception
	 * 
	 */
	public JsonObject viewAuthorityGroupList(RPCClient client, JsonObject jConnectionInfo, String systemCode,
			String authorityGroupId) throws Exception {

		JsonObject request = getMessage(
				"RPC Request",
				"com.brunner.service",
				"SVC_AuthorityGroup",
				"TXN_AuthorityGroup_viewAuthorityGroupList",
				10000,
				new String[] {
						"txnId",
						"systemCode",
						"authorityGroupId"
				},
				new Object[] {
						StringUtil.getTxnId(),
						systemCode,
						authorityGroupId
				});

		String queueName = jConnectionInfo.get(BrunnerClientApi.msgFieldName_queueName).getAsString();

		String reply = client.requestSync(
				queueName,
				request.toString(),
				10000);

		JsonObject jReply = (JsonObject) parser.parse(reply);

		return jReply;
	}

	/***
	 * 특정 시스템 코드의 권한 그룹 등록
	 * 
	 * @param client
	 * @param jConnectionInfo
	 * @param systemCode
	 * @param userId
	 * @param authorityGroupId
	 * @param authorityGroupName
	 * @return
	 * @throws Exception
	 */
	public JsonObject registerAuthorityGroup(RPCClient client, JsonObject jConnectionInfo, String systemCode,
			String userId, String authorityGroupId, String authorityGroupName) throws Exception {

		JsonObject request = getMessage(
				"RPC Request",
				"com.brunner.service",
				"SVC_AuthorityGroup",
				"TXN_AuthorityGroup_registerAuthorityGroup",
				10000,
				new String[] {
						"txnId",
						"systemCode",
						"userId",
						"authorityGroupId",
						"authorityGroupName"
				},
				new Object[] {
						StringUtil.getTxnId(),
						systemCode,
						userId,
						authorityGroupId,
						authorityGroupName
				});

		String queueName = jConnectionInfo.get(BrunnerClientApi.msgFieldName_queueName).getAsString();

		String reply = client.requestSync(
				queueName,
				request.toString(),
				10000);

		JsonObject jReply = (JsonObject) parser.parse(reply);

		return jReply;
	}

	/***
	 * 특정 시스템 코드의 권한 그룹 삭제
	 * 
	 * @param client
	 * @param jConnectionInfo
	 * @param systemCode
	 * @param userId
	 * @param authorityGroupId
	 * @return
	 * @throws Exception
	 */
	public JsonObject unregisterAuthorityGroup(RPCClient client, JsonObject jConnectionInfo, String systemCode,
			String userId, String authorityGroupId) throws Exception {

		JsonObject request = getMessage(
				"RPC Request",
				"com.brunner.service",
				"SVC_AuthorityGroup",
				"TXN_AuthorityGroup_unregisterAuthorityGroup",
				10000,
				new String[] {
						"txnId",
						"systemCode",
						"userId",
						"authorityGroupId"
				},
				new Object[] {
						StringUtil.getTxnId(),
						systemCode,
						userId,
						authorityGroupId
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
