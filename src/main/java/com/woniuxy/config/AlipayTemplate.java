package com.woniuxy.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.woniuxy.vo.PayVo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "alipay")
@Component
@Data
public class AlipayTemplate {

    //在支付宝创建的应用的id
    private String app_id = "2021000116666737";

    // 商户私钥，您的PKCS8格式RSA2私钥
    private  String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCycruSXqvEmFBnGLBO42ATKX8wMprAT2DK6hNxj/tK29NRunPt1pCdmPfBrhd+vfa/LfWIL7y6Gc7cj0K2M/0ezQfUeatI7MN3ze/KdUtwvbq5PqLtdZReXzWgVY23Rt1PmgsxkvVQOXyetAnRmIaGTu1iiwZt309k6O68OB5LVqvwQkUwrR51n20i2R3K6ij068sYuBpbOOrEE1KZFfIcejhOjFXkJJ9zWFvPf2pIG9bXkBvNpsBnCCX3VdqOANzGxRMcRLEGxvs/+AM2LjooICXDotO4pzTzkwT1iDgjn8GXaW/FMG9aCcQwxfkfQu98xAeCo2Tr9u7dFoBUmZrlAgMBAAECggEAO4hbW3GKDIRkDoEGEMt6eeoWLJY1WoMg35FH+1L1mvvLfDCgDHDQvkMSJONXo0js0MscZtrGM6/4e0ywDOk7QNNWEWSl6CZU79scbIbFLE+fw71Jzb60SOeqlFr2dHaHzs6tfVRx0tna4Xde7zXba2NZfs6yzmT5r8imHNT8BlXL6e1g5oK9lEBg8/DRgcNr+64zEDAi5RW9AM7xcqZ0OKjpCj3huwtq8+Pk7BsecOSQtT7b4LpWB4ldFc9q63fXTYayMEwo3U+MxUtD4KsKGqhekW4fhkd5UpvLLpJ7voUEMp3C2ypAkwhCksnOGl1usOWwLsdfW02UmvOeTQFc4QKBgQD2U0PNx8xZTZXz63OIzXYFp2PpiLTxHFwB0CeGZFrfyTqxavDsu8Aimuc6dwyZq7rNCdEEIRZbofAW4eIthVWu3JHPc9E8WI8tAe9ZKW97xXYxAe6AKMuHQ9hC5iJ18/Gm6JWLj03BnuIx5q4dlgtcCTvpNKERf28ZccCkdcr7LwKBgQC5dPtXnCqguwdyDAHCe8G5F3UpbSExbWjUFNjBmkUngUYQXBRKzEEyksPU0TBHhlTXq0fuXtbBG0cG8bX8VqnV5VpT9+cH6ZmSnIvh1L4+LZ4jqgttpPIR+4rTLI5iWaKlCaok7PNO4Az/SRrwMDBx1+UGvcM20sKo035nPnK2KwKBgQDJGieUHFeAXTBciNC1UpDVgti2M88Nvgp7zHZIaSwr4fzSFi4GCC6P0gmPmNwnpaWHekkz5tN7EhWSiO4AHghbD2bXiIW9j8usghTnpUWINikNT7do2GV51HD+BScDR5r1mpNk8iUYO2AOP3fNXc9+X2eTOE17nJ+WlWEio6yJLwKBgDWlBfMQpC/8CapOmM+11pKAm45RF9HhUPpP0WcnBavos+iVifUc4hsL4wOTQwDddXsw+0yTOFGBX2k9z6NfOh86CYfSZIygYKDNEZVQYXtA3MZ6MHYVyUO0XDK7Z7BBN0+/3Oxzcem122yYq+cgOVMk3QW3LFmWW2JUUvLwWGQjAoGBAMzfqR4YAmqiXbrBwrrHvooA44g6HhdXcuXTgGc0gqJzx3Ye4iYIuUtiXe4yNeqfqJrNst7eWiL2Yo4FeyboX6iGYm/x0hxoIRcL5XHN124CxYgdXYZDv20TXNvz+CmKWBDd8sJBE2FVAxFIAd94KAwlN7so7wGyAKEeUfhUO5IA";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAktrNsHRp5vFAceWBVabuiCez+f0N4vPJ9ctZa9ZPw0NrmA9Ahq6sr8qfP/6tA2FpLbb8mD1YFnObFp/isBu0S4jGPVcjXSoAOldxKoFxawElQLhpmO9Z4zOYkVTAKGL9rd3uizsnKsismMOivUUCCfpMb0sNfTe3lrysEa+IKtZrpXbfS8mfjY5FUNXz0HTjzhNiH6XvmRVj2ijLZ6CHqweyevnSWWmsPcnKZjz10pPah90iqKeUMUFtvClhVosQI4bHjqEuVHKfAHE2Ixf6bYeFLlrl5ehiS5F+wVDU5FlRxSzAz3+5p0CnHyDU+F80uwadjePLhkIGHZhhu7b1MQIDAQAB";
    // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
    private  String notify_url="http://127.0.0.1:9999/order/alipayNotifyNotice";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //同步通知，支付成功，一般跳转到成功页
    //在本地开发的时候可以使用内网穿透的生成的域名来做测试。
    private  String return_url = "http://127.0.0.1:8080/orderDetail";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";
    // 订单超时时间，到达超时时间后自动关闭订单不能再继续支付
    public static String timeout = "30m";

    // 支付宝网关； https://openapi.alipaydev.com/gateway.do
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    public  String pay(PayVo vo) throws AlipayApiException {

        //AlipayClient alipayClient = new DefaultAlipayClient(AlipayTemplate.gatewayUrl, AlipayTemplate.app_id, AlipayTemplate.merchant_private_key, "json", AlipayTemplate.charset, AlipayTemplate.alipay_public_key, AlipayTemplate.sign_type);
        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                app_id, merchant_private_key, "json",
                charset, alipay_public_key, sign_type);

        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = vo.getOut_trade_no();
        //付款金额，必填
        String total_amount = vo.getTotal_amount();
        //订单名称，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String body = vo.getBody();

        // timeout_express 订单支付超时时间
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\"" + timeout + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(alipayRequest).getBody();

        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
//        System.out.println("支付宝的响应："+result);
        return result;
    }
}
