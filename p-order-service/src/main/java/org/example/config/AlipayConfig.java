package org.example.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import lombok.Data;

@Data
public class AlipayConfig {
    // 支付宝app id
    public static final String APP_ID = "9021000126620458";

    // 应用私钥
    public static final String APP_PRI_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCccUbQhJAP5OiXHyYVDX3n3227cfKY1I0DN3/1DzGNBtaY0Vqk8pcgCmyz0p8o7zW1aPw0uOS0wmsdytSzfLAGM+mY1kV876JTleRCKxZfvTLnbO0RV7IkbOvbLDD2fcwem6/QrVO/2lBI1Q63gZSs1GSLrJdTqv/ZL9BgciGuIZogfhODLpCvb39/WBk+q3mtqSm7dSonFVK6QL8kLHHhCsDVuUt1vBQOb6mNE+ZJy5tvjIEPW2PhUAgxuCD5HNTaOBQNqnTD8yxl0cNsNrVTpmgW3Ofmfy8Ur0XsJahau2Rm11+IG6/O1UoajZhHk1X02G2HYQteDPQQeETpEccNAgMBAAECggEAUt2Fnwm4rVKZCQNMzrX4zQuCOlmfIOjlUKDjPFEx8/t9TDEIjHqbJjOfZJjaCXq7vj2/6cVOD5kz1/IKPJB9RKvWvkgxQn5EQ3YQFCRyk3/KKN9/q0ICcFzpiYyc2tPDfSsGM8i7pdHj2NBSHU2cDPfr0jg3Z/AZ2VcETzHlOTBpF/3I9rWIkVGcmHgK3JjYNvZTjqlOTgDcBa/85Y5DcZQeh8eopMzwdxlABJx4cHnkianl034EGxbENDqroW43MrbDp4rgxtwHsfhFrb7RaAgTdoM5HQNz4oqFbg/XkIfpiMVhet6KZG22/Jm8qc++H9kf7bbjPpJGHp7MyH3CWQKBgQDnHIH+p54Ro+PdcuEe6d4DYl1FPoAlPQpcNwg57/dy9jX9YJF4BDsIVp7IcU84N2hrp4g+xWDJjIwXiKQk9y7Uxe3hSEDddaBmR6uLbWsp06HD6EwT9wfONwdrCCymLHqJRL2yWNI67pLFtbfD/OJTFKWnJ01RvK/zhoXJ1DI8owKBgQCtSjpncu5C9zwP8Y7F6IRUgWCcdgTHYtgUENOloDTAnklashFjs2u9Hq2e6mJ8zYlGrNh4/3PyVK9qIGvYhT3zVEWMtXb7GanCjGnrbsB1uWGUP6AYj6qy12JjFE+S3dSVGXcf+Z1mKf1gsuHTYXJBGu9MqNuZBimCB3/a8Oz4jwKBgQC2vi74Grf9gNcT6tuhYxFfIa9YqnQPffWsNwO1NE84wwCr6mj8OnQnQr7pBde7Puy9e0pbQB0pIY5AqZYJeL66FgvkAAryUilT1sYJSKrc9u/JhexqOQIeCBtAgcU0XKVD12wcEumhGbZKyUaWnWakhQX7ZzNXN1j1ueAeWECk+wKBgH/wp8Is5qBmbN/Qlnqjut+xTaT2tXyIC8R0z+4epBP6SWo+wfv8QfU1KzaDEgfWsB2QC5r5757gy0ntefI4HY8Djv3eFGQ7hExw4trBwDKqldKgTxETMRItVySToFRsl0Eltf/TiVQeeEXmjDhGX9aVjkvo94i5NfgclVcOPccdAoGBAKI67wZoIcVPIf0w6Pv+XVJYw1gK3dsIliOk62D2fW4xLjB0ijEKv/Jylt+qZx40WWnLNIsGHPjaUpIXywUApFe5Wq88o+UeNEMSKGOUQ6sLD8g9a3j8TVYbXsb7xzZJjKmT0IMuLJsASk+NUdUYshtw4i4V3JxChHSN4GdXBclv";

    // 应用公钥
    public static final String APP_PUB_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnHFG0ISQD+Tolx8mFQ19599tu3HymNSNAzd/9Q8xjQbWmNFapPKXIApss9KfKO81tWj8NLjktMJrHcrUs3ywBjPpmNZFfO+iU5XkQisWX70y52ztEVeyJGzr2yww9n3MHpuv0K1Tv9pQSNUOt4GUrNRki6yXU6r/2S/QYHIhriGaIH4Tgy6Qr29/f1gZPqt5rakpu3UqJxVSukC/JCxx4QrA1blLdbwUDm+pjRPmScubb4yBD1tj4VAIMbgg+RzU2jgUDap0w/MsZdHDbDa1U6ZoFtzn5n8vFK9F7CWoWrtkZtdfiBuvztVKGo2YR5NV9Nhth2ELXgz0EHhE6RHHDQIDAQAB";
    public static final String SIGN_TYPE = "RSA2";

    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgzOnZ97e+xPgjG0YVHDR+EoTvK3HiwsGR3VSlVSiFStSgC9Ck1BSBMS7Xo3gMX59YUHHHtxQEooT+rkTv8Xftof45vOFyZxRcRtpEUjrakjNiuaWwOT46cMkgcqcOf/+5gok8RQIr5618SijmBc6WLBdyYwMFmlh0engXaOt88R8OdQGeWd68ie6WWt8drXvawrN98484Xl0oz1J1VtmwCxV8mR9gsM/AR8ceWFCqH0LVYFfGCneExrN8jNeQFB4Vh2s4yCORDmMH1B3Zv5WSxO57SRU76AxQutDSaWNZul/vS5csgwjo5GuaH1Hs+qFn7AnSNmPrQ9kKnVvGnxoUwIDAQAB";

    public static final String CHARSET = "UTF-8";

    public static final String FORMAT = "JSON";

    public static final String ALIPAY_GATEWAY = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";    // 支付宝app id

//    public static final String APP_ID = "xxxx";
//
//    // 应用私钥
//    public static final String APP_PRI_KEY = "xxx";
//
//    // 应用公钥
//    public static final String APP_PUB_KEY = "xxxx";
//    public static final String SIGN_TYPE = "RSA2";
//
//    public static final String ALIPAY_PUBLIC_KEY = "";
//
//    public static final String CHARSET = "UTF-8";
//
//    public static final String FORMAT = "JSON";
//
//    public static final String ALIPAY_GATEWAY = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";

    private AlipayConfig(){}

    private volatile static AlipayClient instance = null;

    public static AlipayClient getInstance() {
        if (instance == null){
            synchronized (AlipayConfig.class){
                if (instance == null)  {
                    instance = new DefaultAlipayClient(ALIPAY_GATEWAY,APP_ID,APP_PRI_KEY,FORMAT,CHARSET,APP_PUB_KEY,SIGN_TYPE);
                }
            }
        }
        return instance;
    }
}
