package brunner.client.api;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonObject;

import mw.launchers.RPCClient;
import mw.utility.StringUtil;

public class UserInfo extends BrunnerClientApi {

	static UserInfo instance;

	public static UserInfo getInstance() {
		return instance == null ? new UserInfo(1000) : instance;
	}

	public UserInfo(int requestTimeoutMs) {
		super(requestTimeoutMs);
	}

	public JsonObject registerUser(RPCClient client, JsonObject jConnectionInfo, String systemCode, String userId,
			String password, String userName, String address, String phoneNumber, String eMailId, String registerNumber,
			String registerName, String salesType, String salesCategory, String useFlag) throws Exception {

		JsonObject request = getMessage(
				"RPC Request",
				"com.brunner.service",
				"SVC_UserInfo",
				"TXN_UserInfo_registerUser",
				10000,
				new String[] {
						"txnId",
						"systemCode",
						"userId",
						"password",
						"password",
						"userName",
						"address",
						"phoneNumber",
						"eMailId",
						"registerNumber",
						"registerName",
						"salesType",
						"salesCategory",
						"useFlag"
				},
				new Object[] {
						StringUtil.getTxnId(),
						systemCode,
						userId,
						password,
						password,
						userName,
						address,
						phoneNumber,
						eMailId,
						registerNumber,
						registerName,
						salesType,
						salesCategory,
						useFlag
				});

		String queueName = jConnectionInfo.get(BrunnerClientApi.msgFieldName_queueName).getAsString();

		String reply = client.requestSync(
				queueName,
				request.toString(),
				10000);

		JsonObject jReply = (JsonObject) parser.parse(reply);

		return jReply;
	}

	public JsonObject login(RPCClient client, JsonObject jConnectionInfo, String systemCode, String userId,
			String password) throws Exception {

		JsonObject request = getMessage(
				"RPC Request",
				"com.brunner.service",
				"SVC_UserInfo",
				"TXN_UserInfo_logIn",
				10000,
				new String[] {
						"txnId",
						"systemCode",
						"userId",
						"password"
				},
				new Object[] {
						StringUtil.getTxnId(),
						systemCode,
						userId,
						password
				});

		String queueName = jConnectionInfo.get(BrunnerClientApi.msgFieldName_queueName).getAsString();

		String reply = client.requestSync(
				queueName,
				request.toString(),
				10000);

		JsonObject jReply = (JsonObject) parser.parse(reply);

		return jReply;
	}

	public JsonObject viewUserList(RPCClient client, JsonObject jConnectionInfo, String systemCode, String userId)
			throws Exception {

		JsonObject request = getMessage(
				"RPC Request",
				"com.brunner.service",
				"SVC_UserInfo",
				"TXN_UserInfo_viewUserList",
				10000,
				new String[] {
						"txnId",
						"systemCode",
						"userId"
				},
				new Object[] {
						StringUtil.getTxnId(),
						systemCode,
						userId
				});

		String queueName = jConnectionInfo.get(BrunnerClientApi.msgFieldName_queueName).getAsString();

		String reply = client.requestSync(
				queueName,
				request.toString(),
				10000);

		JsonObject jReply = (JsonObject) parser.parse(reply);

		return jReply;
	}

	public JsonObject viewUserAuthorityGroupList(RPCClient client, JsonObject jConnectionInfo, String systemCode,
			String userId) throws Exception {

		JsonObject request = getMessage(
				"RPC Request",
				"com.brunner.service",
				"SVC_UserInfo",
				"TXN_UserInfo_viewUserAuthorityGroupList",
				10000,
				new String[] {
						"txnId",
						"systemCode",
						"userId"
				},
				new Object[] {
						StringUtil.getTxnId(),
						systemCode,
						userId
				});

		String queueName = jConnectionInfo.get(BrunnerClientApi.msgFieldName_queueName).getAsString();

		String reply = client.requestSync(
				queueName,
				request.toString(),
				10000);

		JsonObject jReply = (JsonObject) parser.parse(reply);

		return jReply;
	}

	public JsonObject registerUserAuthorityGroup(RPCClient client, JsonObject jConnectionInfo, String systemCode,
			String userId, String authorityGroupId, Date licenseExpireDate, String updateUserId) throws Exception {

		JsonObject request = getMessage(
				"RPC Request",
				"com.brunner.service",
				"SVC_UserInfo",
				"TXN_UserInfo_registerUserAuthorityGroup",
				10000,
				new String[] {
						"txnId",
						"systemCode",
						"userId",
						"authorityGroupId",
						"updateUserId",
						"licenseExpireDate"
				},
				new Object[] {
						StringUtil.getTxnId(),
						systemCode,
						userId,
						authorityGroupId,
						updateUserId,
						new SimpleDateFormat("yyyy-MM-dd").format(licenseExpireDate)
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
