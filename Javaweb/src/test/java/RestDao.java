import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class RestDao {
	private Client client;

	public void init() {
		if (client == null) {
			ClientConfig config = new DefaultClientConfig();
			config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,
					true);
			client = Client.create(config);
			client.setConnectTimeout(3000);
		}
	}

	public ReturnValueObj post(String url, String path, Object obj) {
		try {
			return client.resource(completeUrl(url)).path(path)
					.header("hs1", "111").header("hs2", "admin")
					.header("hs3", "a5421c4d4221400ea0ff4c0d3785b776")
					.type(MediaType.APPLICATION_JSON)
					.post(ReturnValueObj.class, obj);
		} catch (Exception e) {
			return null;
		}
	}

	public ReturnValueObj put(String url, String path, Object obj) {
		try {
			return obj == null ? client.resource(completeUrl(url)).path(path)
					.header("hs1", "111").header("hs2", "admin")
					.header("hs3", "a5421c4d4221400ea0ff4c0d3785b776")
					.type(MediaType.APPLICATION_JSON).put(ReturnValueObj.class)
					: client.resource(completeUrl(url)).path(path)
							.header("hs1", "111").header("hs2", "admin")
							.header("hs3", "a5421c4d4221400ea0ff4c0d3785b776")
							.type(MediaType.APPLICATION_JSON)
							.put(ReturnValueObj.class, obj);
		} catch (Exception e) {
			return null;
		}
	}

	public ReturnValueObj delete(String url, String path, Object obj) {
		try {
			return obj == null ? client.resource(completeUrl(url)).path(path)
					.header("hs1", "111").header("hs2", "admin")
					.header("hs3", "a5421c4d4221400ea0ff4c0d3785b776")
					.type(MediaType.APPLICATION_JSON)
					.delete(ReturnValueObj.class) : client
					.resource(completeUrl(url)).path(path).header("hs1", "111")
					.header("hs2", "admin")
					.header("hs3", "a5421c4d4221400ea0ff4c0d3785b776")
					.type(MediaType.APPLICATION_JSON)
					.delete(ReturnValueObj.class, obj);
		} catch (Exception e) {
			return null;
		}
	}

	public ReturnValueObj get(String url, String path,
			MultivaluedMap<String, String> queryParams) {
		try {
			return queryParams == null ? client.resource(completeUrl(url))
					.path(path).header("hs1", "111").header("hs2", "admin")
					.header("hs3", "a5421c4d4221400ea0ff4c0d3785b776")
					.type(MediaType.APPLICATION_JSON).get(ReturnValueObj.class)
					: client.resource(completeUrl(url))
							.queryParams(queryParams)
							.path(path)
							.header("hs1", "111")
							.header("hs2", "admin")
							.header("hs3", "a5421c4d4221400ea0ff4c0d3785b776")
							.type(MediaType.APPLICATION_JSON)
							.get(ReturnValueObj.class);
		} catch (Exception e) {
			return null;
		}
	}

	private String completeUrl(String url) {
		url = url.toLowerCase();
		if (url.startsWith("http://")) {
			return url;
		} else {
			return "http://" + url;
		}
	}

	public static void main(String[] args) {
		RestDao dao = new RestDao();
		dao.init();
		ReturnValueObj ret = dao.get("http://10.180.137.2:8080/",
				"i/rest/log/list/", null);
		System.out.println("请求返回：");
		System.out.println(ret);
	}
}




















