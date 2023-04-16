package brunner.client.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeoutException;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import mw.launchers.RPCClient;
import mw.utility.StringUtil;

public class CommonCode extends BrunnerClientApi {

	static CommonCode instance;

	public static CommonCode getInstance() {
		return instance == null ? new CommonCode(1000) : instance;
	}

	CommonCode(int requestTimeoutMs) {
		super(requestTimeoutMs);
	}

	/***
	 * 특정 시스템 코드의 공통 코드 그룹을 등록
	 * 
	 * @param client
	 * @param jConnectionInfo
	 * @param systemCode
	 * @param commonCodeGroupId
	 * @param commonCodeGroupDesc
	 * @param attribute1
	 * @param attribute2
	 * @param attribute3
	 * @param attribute4
	 * @param attribute5
	 * @return
	 * @throws Exception
	 */
	public JsonObject registerCommonCodeGroup(RPCClient client, JsonObject jConnectionInfo, String systemCode,
			String commonCodeGroupId, String commonCodeGroupDesc, String attribute1, String attribute2,
			String attribute3, String attribute4, String attribute5) throws Exception {

		JsonObject request = getMessage(
				"RPC Request",
				"com.brunner.service",
				"SVC_CommonCode",
				"TXN_CommonCode_registerCommonCodeGroup",
				10000,
				new String[] {
						"txnId",
						"systemCode",
						"commonCodeGroupId",
						"commonCodeGroupDesc",
						"attribute1",
						"attribute2",
						"attribute3",
						"attribute4",
						"attribute5"
				},
				new Object[] {
						StringUtil.getTxnId(),
						systemCode,
						commonCodeGroupId,
						commonCodeGroupDesc,
						attribute1,
						attribute2,
						attribute3,
						attribute4,
						attribute5
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
	 * 특정 시스템 코드의 공통 코드 그룹을 삭제
	 * 
	 * @param client
	 * @param jConnectionInfo
	 * @param systemCode
	 * @param commonCodeGroupId
	 * @return
	 * @throws JsonSyntaxException
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	public JsonObject unregisterCommonCodeGroup(RPCClient client, JsonObject jConnectionInfo, String systemCode,
			String commonCodeGroupId, String userId) throws Exception {

		JsonObject request = getMessage(
				"RPC Request",
				"com.brunner.service",
				"SVC_CommonCode",
				"TXN_CommonCode_unregisterCommonCodeGroup",
				10000,
				new String[] {
						"txnId",
						"systemCode",
						"commonCodeGroupId",
						"userId"
				},
				new Object[] {
						StringUtil.getTxnId(),
						systemCode,
						commonCodeGroupId,
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

	/***
	 * 특정 시스템 코드의 공통 코드 그룹의 공통코드 등록
	 * 
	 * @param client
	 * @param jConnectionInfo
	 * @param systemCode
	 * @param commonCodeGroupId
	 * @param commonCodeId
	 * @param key1
	 * @param key2
	 * @param commonCodeDesc
	 * @param attribute1
	 * @param attribute2
	 * @param attribute3
	 * @param attribute4
	 * @param attribute5
	 * @return
	 * @throws JsonSyntaxException
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	public JsonObject registerCommonCode(RPCClient client, JsonObject jConnectionInfo, String systemCode,
			String commonCodeGroupId, String commonCodeId, String key1, String key2, String commonCodeDesc,
			String attribute1, String attribute2, String attribute3, String attribute4, String attribute5)
			throws Exception {

		JsonObject request = getMessage(
				"RPC Request",
				"com.brunner.service",
				"SVC_CommonCode",
				"TXN_CommonCode_registerCommonCode",
				10000,
				new String[] {
						"txnId",
						"systemCode",
						"commonCodeGroupId",
						"commonCodeId",
						"key1",
						"key2",
						"commonCodeDesc",
						"attribute1",
						"attribute2",
						"attribute3",
						"attribute4",
						"attribute5"
				},
				new Object[] {
						StringUtil.getTxnId(),
						systemCode,
						commonCodeGroupId,
						commonCodeId,
						key1,
						key2,
						commonCodeDesc,
						attribute1,
						attribute2,
						attribute3,
						attribute4,
						attribute5
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
	 * 특정 시스템코드의 공통코드 그룹의 공통 코드 삭제
	 * 
	 * @param client
	 * @param jConnectionInfo
	 * @param systemCode
	 * @param commonCodeGroupId
	 * @param commonCodeId
	 * @return
	 * @throws JsonSyntaxException
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	public JsonObject unregisterCommonCode(RPCClient client, JsonObject jConnectionInfo, String systemCode,
			String commonCodeGroupId, String commonCodeId, String key1, String key2) throws Exception {

		JsonObject request = getMessage(
				"RPC Request",
				"com.brunner.service",
				"SVC_CommonCode",
				"TXN_CommonCode_unregisterCommonCode",
				10000,
				new String[] {
						"txnId",
						"systemCode",
						"commonCodeGroupId",
						"commonCodeId",
						"key1",
						"key2"
				},
				new Object[] {
						StringUtil.getTxnId(),
						systemCode,
						commonCodeGroupId,
						commonCodeId,
						key1,
						key2
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
	 * 특정 시스템 코드의 공통 코드 그룹 목록을 조회
	 * 
	 * @param systemCode
	 * @param commonCodeGroupId 'like' 조회
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws JsonSyntaxException
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * 
	 */
	public JsonObject viewCommonCodeGroupList(RPCClient client, JsonObject jConnectionInfo, String systemCode,
			String commonCodeGroupId) throws Exception {

		JsonObject request = getMessage(
				"RPC Request",
				"com.brunner.service",
				"SVC_CommonCode",
				"TXN_CommonCode_viewCommonCodeGroupList",
				10000,
				new String[] {
						"txnId",
						"systemCode",
						"commonCodeGroupId"
				},
				new Object[] {
						StringUtil.getTxnId(),
						systemCode,
						commonCodeGroupId
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
	 * 특정 시스템 코드의 공통 코드 그룹의 공통 코드 목록 조회
	 * 
	 * @param client
	 * @param jConnectionInfo
	 * @param systemCode
	 * @param commonCodeGroupId
	 * @return
	 * @throws JsonSyntaxException
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	public JsonObject viewCommonCodeItemList(RPCClient client, JsonObject jConnectionInfo, String systemCode,
			String commonCodeGroupId) throws Exception {

		JsonObject request = getMessage(
				"RPC Request",
				"com.brunner.service",
				"SVC_CommonCode",
				"TXN_CommonCode_viewCommonCodeItemList",
				10000,
				new String[] {
						"txnId",
						"systemCode",
						"commonCodeGroupId"
				},
				new Object[] {
						StringUtil.getTxnId(),
						systemCode,
						commonCodeGroupId
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
	 * 특정 시스템 코드의 공통 코드 그룹의 공통 코드 목록 조회
	 * 
	 * @param client
	 * @param jConnectionInfo
	 * @param systemCode
	 * @param commonCodeGroupId
	 * @param key1
	 * @param key2
	 * @return
	 * @throws JsonSyntaxException
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	public JsonObject viewCommonCodeItemListByKey(RPCClient client, JsonObject jConnectionInfo, String systemCode,
			String commonCodeGroupId, String key1, String key2) throws Exception {

		JsonObject request = getMessage(
				"RPC Request",
				"com.brunner.service",
				"SVC_CommonCode",
				"TXN_CommonCode_viewCommonCodeItemListByKey",
				10000,
				new String[] {
						"txnId",
						"systemCode",
						"commonCodeGroupId",
						"key1",
						"key2"
				},
				new Object[] {
						StringUtil.getTxnId(),
						systemCode,
						commonCodeGroupId,
						key1,
						key2
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
