package com.maxtropy.arch.openplatform.sdk.core.http;

import com.maxtropy.arch.openplatform.sdk.core.exception.HttpStatusNoScOKException;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author luwang
 */
@SuppressWarnings("Duplicates")
public class HttpClientUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    private static final String ENCODING_UTF_8 = "UTF-8";
    private static final String CONTENT_TYPE_XML = "application/xml";
    private static final int DEFAULT_TIMEOUT_MS = 10000;
    public static final int MAX_RESULT_BYTES = 1048576;

    private HttpClientUtil() {
    }

    //delete
    public static String doDeleteByJson(String url,  Map<String, String> headerMap, List<NameValuePair> pairs, String content, int timeout) {
        return doDeleteByContent(url, headerMap, pairs, content, ContentType.APPLICATION_JSON, ENCODING_UTF_8,
                timeout);
    }

    public static String doDeleteByContent(String url, Map<String, String> headerMap, List<NameValuePair> pairs, String content,
                                        ContentType contentType, String encoding, int timeout) {

        if (!StringUtils.hasText(content)) {
            content = "";
        }
        logger.info("content:" + content);
        String httpEncoding = ENCODING_UTF_8;
        if (StringUtils.hasText(encoding)) {
            httpEncoding = encoding;
        }
        HttpEntity httpEntity = EntityBuilder.create().setContentType(contentType)
                .setContentEncoding(httpEncoding).setText(content).build();
        return doDeleteByEntity(url, headerMap, pairs, httpEntity, timeout);
    }

    private static String doDeleteByEntity(String url, Map<String, String> headerMap, List<NameValuePair> pairs,
                                        HttpEntity httpEntity, int timeout) {
        long startTime = System.currentTimeMillis();
        String result = "";
        CloseableHttpClient httpClient;
        if (timeout > 0) {
            httpClient = httpClient(timeout);
        } else {
            httpClient = httpClient();
        }

        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (pairs != null && pairs.size() > 0) {
                uriBuilder.setParameters(pairs);
            }

            MyHttpDelete httpDelete = new MyHttpDelete(uriBuilder.build());
            if (headerMap != null && !headerMap.isEmpty()) {
                StringBuilder headerLogStr = new StringBuilder();
                Set<Map.Entry<String, String>> heads = headerMap.entrySet();
                for (Map.Entry<String, String> head : heads) {
                    httpDelete.setHeader(head.getKey(), head.getValue());
                    headerLogStr.append("[").append(head.getKey()).append(" : ")
                            .append(head.getValue()).append("]");
                }
                logger.info("header参数:" + headerLogStr);
            }


           // httpDelete.set
            httpDelete.setEntity(httpEntity);

            CloseableHttpResponse response = httpClient.execute(httpDelete);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), ENCODING_UTF_8);
            } else {
                result = EntityUtils.toString(response.getEntity(), ENCODING_UTF_8);
                writeResultLog(result, response.getStatusLine().getStatusCode());
                throw new HttpStatusNoScOKException(response.getStatusLine().getStatusCode(),
                        result, "返回结果内容" + result);
            }
            writeResultLog(result, response.getStatusLine().getStatusCode());
        } catch (ClientProtocolException e) {
            logger.error("请求异常", e);
        } catch (IOException e) {
            logger.error("请求异常", e);
        } catch (URISyntaxException e) {
            logger.error("请求异常", e);
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    logger.error("请求异常", e);
                }
            }
            writeTimeLog(startTime);
        }
        return result;
    }

    // ---Put
    public static String doPutByJson(String url,  Map<String, String> headerMap, List<NameValuePair> pairs, String content, int timeout) {
        return doPutByContent(url, headerMap, pairs, content, ContentType.APPLICATION_JSON, ENCODING_UTF_8,
                timeout);
    }

    public static String doPutByContent(String url, Map<String, String> headerMap, List<NameValuePair> pairs, String content,
                                        ContentType contentType, String encoding, int timeout) {

        if (!StringUtils.hasText(content)) {
            content = "";
        }
        logger.info("content:" + content);
        String httpEncoding = ENCODING_UTF_8;
        if (StringUtils.hasText(encoding)) {
            httpEncoding = encoding;
        }
        HttpEntity httpEntity = EntityBuilder.create().setContentType(contentType)
                .setContentEncoding(httpEncoding).setText(content).build();
        return doPutByEntity(url, headerMap, pairs, httpEntity, timeout);
    }

    private static String doPutByEntity(String url, Map<String, String> headerMap, List<NameValuePair> pairs,
                                         HttpEntity httpEntity, int timeout) {
        long startTime = System.currentTimeMillis();
        String result = "";
        CloseableHttpClient httpClient;
        if (timeout > 0) {
            httpClient = httpClient(timeout);
        } else {
            httpClient = httpClient();
        }

        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (pairs != null && pairs.size() > 0) {
                uriBuilder.setParameters(pairs);
            }
            HttpPut httpPut = new HttpPut(uriBuilder.build());
            if (headerMap != null && !headerMap.isEmpty()) {
                StringBuilder headerLogStr = new StringBuilder();
                Set<Map.Entry<String, String>> heads = headerMap.entrySet();
                for (Map.Entry<String, String> head : heads) {
                    httpPut.setHeader(head.getKey(), head.getValue());
                    headerLogStr.append("[").append(head.getKey()).append(" : ")
                            .append(head.getValue()).append("]");
                }
                logger.info("header参数:" + headerLogStr);
            }
            httpPut.setEntity(httpEntity);
            CloseableHttpResponse response = httpClient.execute(httpPut);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), ENCODING_UTF_8);
            } else {
                result = EntityUtils.toString(response.getEntity(), ENCODING_UTF_8);
                writeResultLog(result, response.getStatusLine().getStatusCode());
                throw new HttpStatusNoScOKException(response.getStatusLine().getStatusCode(),
                        result, "返回结果内容" + result);
            }
            writeResultLog(result, response.getStatusLine().getStatusCode());
        } catch (ClientProtocolException e) {
            logger.error("请求异常", e);
        } catch (IOException e) {
            logger.error("请求异常", e);
        } catch (URISyntaxException e) {
            logger.error("请求异常", e);
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    logger.error("请求异常", e);
                }
            }
            writeTimeLog(startTime);
        }
        return result;
    }

    //-- doPost
    public static String doPostByJson(String url, List<NameValuePair> pairs, String content) {
        return doPostByContent(url, null, pairs, content, ContentType.APPLICATION_JSON, ENCODING_UTF_8, 0);
    }

    public static String doPostByJson(String url,  Map<String, String> headerMap, List<NameValuePair> pairs, String content, int timeout) {
        return doPostByContent(url, headerMap, pairs, content, ContentType.APPLICATION_JSON, ENCODING_UTF_8,
                timeout);
    }

    public static String doPostByXml(String url, List<NameValuePair> pairs, String content) {
        return doPostByContent(url, null, pairs, content,
                ContentType.create(CONTENT_TYPE_XML, ENCODING_UTF_8), ENCODING_UTF_8, 0);
    }

    public static String doPostByXml(String url, List<NameValuePair> pairs, String content, int timeout) {
        return doPostByContent(url, null, pairs, content,
                ContentType.create(CONTENT_TYPE_XML, ENCODING_UTF_8), ENCODING_UTF_8,
                timeout);
    }

    public static String doPostByContent(String url, Map<String, String> headerMap, List<NameValuePair> pairs, String content,
                                         ContentType contentType, String encoding, int timeout) {
        if (!StringUtils.hasText(content)) {
            content = "";
        }
        logger.info("content:" + content);
        String httpEncoding = ENCODING_UTF_8;
        if (StringUtils.hasText(encoding)) {
            httpEncoding = encoding;
        }
        HttpEntity httpEntity = EntityBuilder.create().setContentType(contentType)
                .setContentEncoding(httpEncoding).setText(content).build();
        return doPostByEntity(url, headerMap, pairs, httpEntity, timeout);
    }

    public static String doPostByKV(String url, Map<String, String> headerMap, List<NameValuePair> pairs,
                                    Map<String, String> paramsMap) throws UnsupportedEncodingException {
        return doPostByKV(url, headerMap, pairs, paramsMap, 0);
    }

    public static String doPostMultipart(String url, Map<String, String> headerMap, List<NameValuePair> pairs,  Map<String, File> files, Map<String, String> paramsMap, int timeout) {
        if (files == null || files.isEmpty()) {
            logger.error("文件为空");
            return "";
        }
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.setCharset(Consts.UTF_8);
        for (Map.Entry<String, File> file : files.entrySet()) {
            multipartEntityBuilder.addBinaryBody(file.getKey(), file.getValue());
        }
        if (paramsMap != null && !paramsMap.isEmpty()) {
            for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                multipartEntityBuilder.addTextBody(param.getKey(), param.getValue(), ContentType.create("text/plain", Charset.forName(ENCODING_UTF_8)));
            }
        }
        HttpEntity httpEntity = multipartEntityBuilder.build();
        return doPostByEntity(url, headerMap, pairs, httpEntity, timeout);
    }

    public static String doPostByKV(String url, Map<String, String> headerMap, List<NameValuePair> pairs,
                                    Map<String, String> paramsMap, int timeout) throws UnsupportedEncodingException {
        if (paramsMap == null || paramsMap.isEmpty()) {
            logger.error("参数为空");
            return "";
        }
        StringBuilder paramsLogStr = new StringBuilder();
        Set<Map.Entry<String, String>> entries = paramsMap.entrySet();
        List<NameValuePair> namePairs = new ArrayList<>();
        for (Map.Entry<String, String> param : entries) {
            namePairs.add(new BasicNameValuePair(param.getKey(), param.getValue()));
            paramsLogStr.append("[").append(param.getKey()).append(" : ")
                    .append(param.getValue()).append("]");
        }
        logger.info("参数:" + paramsLogStr);
        HttpEntity httpEntity = new UrlEncodedFormEntity(namePairs, ENCODING_UTF_8);
        return doPostByEntity(url, headerMap, pairs, httpEntity, timeout);
    }

    public static String doPostByKV(String url, Map<String, String> headerMap, List<NameValuePair> pairs,
                                    int timeout) throws UnsupportedEncodingException {
        HttpEntity httpEntity = new UrlEncodedFormEntity(new ArrayList<>(), ENCODING_UTF_8);
        return doPostByEntity(url, headerMap, pairs, httpEntity, timeout);
    }

    private static String doPostByEntity(String url, Map<String, String> headerMap, List<NameValuePair> pairs,
                                         HttpEntity httpEntity, int timeout) {
        long startTime = System.currentTimeMillis();
        String result = "";
        CloseableHttpClient httpClient;
        if (timeout > 0) {
            httpClient = httpClient(timeout);
        } else {
            httpClient = httpClient();
        }

        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (pairs != null && pairs.size() > 0) {
                uriBuilder.setParameters(pairs);
            }
            HttpPost httpPost = new HttpPost(uriBuilder.build());
            if (headerMap != null && !headerMap.isEmpty()) {
                StringBuilder headerLogStr = new StringBuilder();
                Set<Map.Entry<String, String>> heads = headerMap.entrySet();
                for (Map.Entry<String, String> head : heads) {
                    httpPost.setHeader(head.getKey(), head.getValue());
                    headerLogStr.append("[").append(head.getKey()).append(" : ")
                            .append(head.getValue()).append("]");
                }
                logger.info("header参数:" + headerLogStr);
            }
            httpPost.setEntity(httpEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), ENCODING_UTF_8);
            } else {
                result = EntityUtils.toString(response.getEntity(), ENCODING_UTF_8);
                writeResultLog(result, response.getStatusLine().getStatusCode());
                throw new HttpStatusNoScOKException(response.getStatusLine().getStatusCode(),
                        result, "返回结果内容" + result);
            }
            writeResultLog(result, response.getStatusLine().getStatusCode());
        } catch (ClientProtocolException e) {
            logger.error("请求异常", e);
        } catch (IOException e) {
            logger.error("请求异常", e);
        } catch (URISyntaxException e) {
            logger.error("请求异常", e);
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    logger.error("请求异常", e);
                }
            }
            writeTimeLog(startTime);
        }
        return result;
    }



    //-- doGet

    public static String doGetByEntity(String url, Map<String, String> headerMap) {
        return doGetByEntity(url, null, headerMap, 0);
    }

    public static String doGetByEntity(String url, List<NameValuePair> pairs, Map<String, String> headerMap, int timeout) {
        long startTime = System.currentTimeMillis();
        String result = "";
        CloseableHttpClient httpClient;
        if (timeout > 0) {
            httpClient = httpClient(timeout);
        } else {
            httpClient = httpClient();
        }

        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (pairs != null && pairs.size() > 0) {
                uriBuilder.setParameters(pairs);
            }
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            if (headerMap != null && !headerMap.isEmpty()) {
                StringBuilder headerLogStr = new StringBuilder();
                Set<Map.Entry<String, String>> heads = headerMap.entrySet();
                for (Map.Entry<String, String> head : heads) {
                    httpGet.setHeader(head.getKey(), head.getValue());
                    headerLogStr.append("[").append(head.getKey()).append(" : ")
                            .append(head.getValue()).append("]");
                }
                logger.info("header参数:" + headerLogStr);
            }
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), ENCODING_UTF_8);
            } else {
                result = EntityUtils.toString(response.getEntity(), ENCODING_UTF_8);
                writeResultLog(result, response.getStatusLine().getStatusCode());
                throw new HttpStatusNoScOKException(response.getStatusLine().getStatusCode(),
                        result, "返回结果内容" + result);
            }
            writeResultLog(result, response.getStatusLine().getStatusCode());
        } catch (ClientProtocolException e) {
            logger.error("请求异常", e);
        } catch (IOException e) {
            logger.error("请求异常", e);
        } catch (URISyntaxException e) {
            logger.error("请求异常", e);
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    logger.error("请求异常", e);
                }
            }
            writeTimeLog(startTime);
        }
        return result;
    }

    private static CloseableHttpClient httpClient() {
        return httpClient(DEFAULT_TIMEOUT_MS);
    }

    private static CloseableHttpClient httpClient(int timeout) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(timeout)
                .setSocketTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .build();
        return HttpClientBuilder.create().setDefaultRequestConfig(requestConfig)
                .build();
    }

    private static void writeResultLog(String result, int statusCode) {
        if (statusCode != HttpStatus.SC_OK) {
            logger.error("状态码不是200:" + statusCode);
        }
        if (!StringUtils.hasText(result) || "null".equals(result)) {
            logger.info("请求结果为空");
        } else {
            logger.info("返回值字节大小:" + result.length());
            if (result.length() > MAX_RESULT_BYTES) {
                logger.info("结果超过" + MAX_RESULT_BYTES + "bytes,不打印");
            } else {
                logger.info("结果:" + result);
            }
        }
    }

    private static void writeTimeLog(long startTime) {
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        logger.info("请求结束 - time:[" + time + "]");
    }

}
