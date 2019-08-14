---
title: HttpClientUtil工具类
date: 2019-07-14 09:32:18
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/95799271](https://blog.csdn.net/MOKEXFDGH/article/details/95799271)   
    
  在项目中，我们经常会使用到第三方的一些工具或者应用，如小程序，公众号，腾讯云等。需要对第三方相应的 API 发送请求，以实现具体的功能，因此我们可以把发送请求的不同操作封装成一个工具类，方便调用。

 
--------
 
```
		public class HttpClientUtil{
		
		    private CloseableHttpClient client = null;
		
		    public HttpClientUtil() {
		        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(7000).setSocketTimeout(7000).build();
		        client = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
		    }
		
		    public String post(String url) throws ParseException, ClientProtocolException, IOException {
		        return post(url, null);
		    }
		
		    public String post(String url, String params) throws ParseException, ClientProtocolException, IOException {
		        HttpPost post = new HttpPost(url);
		
		        if (StringUtils.isNotEmpty(params)) {
		            HttpEntity entity = new StringEntity(params,"UTF_8");
		            post.setEntity(entity);
		        }
		        CloseableHttpResponse response = client.execute(post);
		        try {
		            return EntityUtils.toString(response.getEntity(), "UTF_8");
		        } finally {
		            response.close();
		        }
		    }

			public String postJson(String url, String params) throws ParseException, ClientProtocolException, IOException {
		        HttpPost post = new HttpPost(url);
	
		        if (StringUtils.isNotEmpty(params)) {
		            StringEntiy entity = new StringEntity(params,"UTF_8");
		            post.setHeader("Content-type", "application/json");//设置请求头类型
		            post.setEntity(entity);
		        }
		        CloseableHttpResponse response = client.execute(post);
		        try {
		            return EntityUtils.toString(response.getEntity(), "UTF_8");
		        } finally {
		            response.close();
		        }
		    }
		
		    public String get(String url) throws ParseException, ClientProtocolException, IOException {
		        HttpGet get = new HttpGet(url);
		        CloseableHttpResponse response = client.execute(get);
		        try {
		            return EntityUtils.toString(response.getEntity(), Constants.CS_UTF_8);
		        } finally {
		            response.close();
		        }
		    }
		
		    public String getWithRequestConfig(String url,
		                                       RequestConfig requestConfig) throws ParseException, ClientProtocolException, IOException {
		        HttpGet get = new HttpGet(url);
		        get.setConfig(requestConfig);
		        CloseableHttpResponse response = client.execute(get);
		        try {
		            return EntityUtils.toString(response.getEntity(), Constants.CS_UTF_8);
		        } finally {
		            response.close();
		        }
		    }
		
		    /**
		     * 需要调用者关闭 Response
		     * 
		     * @param url
		     * @return
		     * @throws Exception
		     */
		    public CloseableHttpResponse getResponse(String url) throws IOException, ClientProtocolException {
		        HttpGet get = new HttpGet(url);
		        CloseableHttpResponse response = client.execute(get);
		        return response;
		    }
		
		    /**
		     * 需要调用者关闭 Response
		     * 
		     * @param url
		     * @return
		     * @throws Exception
		     */
		    public CloseableHttpResponse postResponse(String url) throws IOException, ClientProtocolException {
		        HttpPost post = new HttpPost(url);
		        CloseableHttpResponse response = client.execute(post);
		        return response;
		    }
		
		}

```
   
  