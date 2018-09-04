package utils.okhttp3;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;

import okhttp3.FormBody;
import okhttp3.FormBody.Builder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 
 * 创建时间：2018年7月24日 下午3:40:55
 * 
 * 项目名称：test
 * 
 * @author lizhen
 * 
 * @version 1.0
 * 
 * 类说明：http请求工具类
 * 
 * 使用前請先引用相關的jar包
 * okhttp包
 * <dependency>
 * 		<groupId>com.squareup.okhttp</groupId>
 * 		<artifactId>okhttp</artifactId>
 * 		<version>2.5.0</version>
 * </dependency>
 * json包
 * 	<dependency>
 * 	    <groupId>com.alibaba</groupId>
 * 	    <artifactId>fastjson</artifactId>
 * 	    <version>1.2.47</version>
 * 	</dependency>
 * 
 */
public class HttpUtils {

	private static final String APPLICATION_JSON = "application/json";

	private static final String CONTRNT_TYPE = "Content-Type";

	private static final String DEFAULT_CHARSET = "UTF-8";
	
	private static final String ACCEPT = "Accept";

	/**
	 * get请求
	 * @param url  请求路径
	 * @param req map格式的请求数据
	 * @param type 结果类型
	 * @return
	 * @throws IOException
	 */
	public static <T> T doGet(String url, Class<T> type) throws IOException {
		OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
		
		final Request request = new Request.Builder()
                .url(url)//请求的url e.g"http://localhost:8080/test?name=12&age=25"
                .get()//设置请求方式，get()/post()  查看Builder()方法知，在构建时默认设置请求方式为GET
                .build(); //构建一个请求Request对象

		Response response = client.newCall(request).execute();
		if (!response.isSuccessful()) {
			throw new IOException("服务器端错误: " + response);
		}

		String result = response.body().string();
		
		if (type == null || type.isAssignableFrom(String.class)) {
			return (T) result;
		}
		return (T) JSONObject.parseObject(result, type);

	}
	
	/**
	 * 以jason字符串形式的post请求
	 * @param url  请求路径
	 * @param json json格式的字符串请求数据
	 * @param type 结果类型
	 * @return
	 * @throws IOException
	 */
	public static <T> T doPost(String url, String json, Class<T> type) throws IOException {
		OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
		
		MediaType JSON = MediaType.parse(APPLICATION_JSON);
		RequestBody body = RequestBody.create(JSON, json);

		Request request = new Request.Builder()
				.url(url)
				.header(ACCEPT, APPLICATION_JSON) // 指定accept 为 json 结构体
				.header(CONTRNT_TYPE, APPLICATION_JSON)
				.post(body)
				.build();

		Response response = client.newCall(request).execute();
		if (!response.isSuccessful()) {
			throw new IOException("服务器端错误: " + response);
		}

		String result = response.body().string();
		
		if (type == null || type.isAssignableFrom(String.class)) {
			return (T) result;
		}
		return (T) JSONObject.parseObject(result, type);

	}
	
	/**
	 * 以Form表单的形式的post请求
	 * @param url  请求路径
	 * @param req map格式的请求数据
	 * @param type 结果类型
	 * @return
	 * @throws IOException
	 */
	public static <T> T doFormPost(String url, Map<String, String> req, Class<T> type) throws IOException {
		OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
		
		//post方式提交的数据
        Builder formBodyBuilder = new FormBody.Builder();

        Iterator<Map.Entry<String, String>> entries = req.entrySet().iterator(); 
        while (entries.hasNext()) { 
          Map.Entry<String, String> entry = entries.next(); 
          formBodyBuilder.add(entry.getKey(), entry.getValue());
        }
        
        FormBody formBody = formBodyBuilder.build();
        
		Request request = new Request.Builder()
				.url(url)
				.header(ACCEPT, APPLICATION_JSON) // 指定accept 为 json 结构体
				.header(CONTRNT_TYPE, APPLICATION_JSON)
				.post(formBody)
				.build();

		Response response = client.newCall(request).execute();
		if (!response.isSuccessful()) {
			throw new IOException("服务器端错误: " + response);
		}

		String result = response.body().string();
		
		if (type == null || type.isAssignableFrom(String.class)) {
			return (T) result;
		}
		return (T) JSONObject.parseObject(result, type);

	}
	
}
