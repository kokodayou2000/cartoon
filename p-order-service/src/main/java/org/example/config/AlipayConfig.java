package org.example.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import lombok.Data;

@Data
public class AlipayConfig {

    // 使用的app是支付宝沙箱 app
    public static final String APP_ID = "9021000126620458";


    // 应用公钥
    public static final String APP_PUB_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAloYbFX1Jq+pgAMVCSYpgALFqgBc0yUtS3TgeBkxU8eua91vtOfs5EjtqxdmTNwJQTVodMb8oiTP8+3adeMlX7L9eqhg/YBrHTLyhtyb+kbFzq1LGAmuB4+uxG+CKqdbg0JpgfnX4RdWo85Pxs6w8ZgSTqo+9rLbscuCq4A0rwwKL2OEqiGF6nIWzJR5YBWU4afVDzauoKJoMUEWTrGNyNtU2PiEBtTolN4zYGU3s0tFyGoVkkzUxwvDe6W7OCN/WYRwUZnz4u53954daZ0Ie5yB3h1218vsaM5q9kailjk5A3LH2kFYexr3eZ/i95vGMzgfbqd7fzCMPbubhiGVOcwIDAQAB";

    // 应用私钥
    public static final String APP_PRI_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCWhhsVfUmr6mAAxUJJimAAsWqAFzTJS1LdOB4GTFTx65r3W+05+zkSO2rF2ZM3AlBNWh0xvyiJM/z7dp14yVfsv16qGD9gGsdMvKG3Jv6RsXOrUsYCa4Hj67Eb4Iqp1uDQmmB+dfhF1ajzk/GzrDxmBJOqj72stuxy4KrgDSvDAovY4SqIYXqchbMlHlgFZThp9UPNq6gomgxQRZOsY3I21TY+IQG1OiU3jNgZTezS0XIahWSTNTHC8N7pbs4I39ZhHBRmfPi7nf3nh1pnQh7nIHeHXbXy+xozmr2RqKWOTkDcsfaQVh7Gvd5n+L3m8YzOB9up3t/MIw9u5uGIZU5zAgMBAAECggEAPkEOuI+NxbV0qTzsUoARfpfiBUITyeEGT4ojGNW/GCMqf32cmqtlij3axiNbeJfTYHqRsn5uJHjE+7euH0OewsLPQyguJfBNV1Z4x8gv50/uGABu9pdCLGLRaEcw8ld1ubDvkb32xeM9MiiRAY+Nl1/zNsqmVuV823JCGdI4tDkZhMtatnIeCCC1zavQZsdM/X8VybcTwVu/Y7eRv7cn3LOueNUJFcoUXwh3my906i402WbNguc0dB/iufoHVFauXknGA4jPL5XZNVgQf8LGntiL+nwKadehPSEEEq2kjH7GW8wb4sOMbjadRwrTdVqAYN7KsLCr3KJ22XAVgJ2moQKBgQDT+UHhNwov/U9L/611f0BZPgUKIRpKOglIW5XQVBBL7pawbPR3OC/XIkcdUoeQchbfzxuK0gyqIcMuc3LK/FrnnCs91Qp/H0Yo/SN6fW2gaQ5UvV9bBHcC9TF5rloN4BbVS3RK2rFUyy4shjaqYojfzM9x8FKaJRD0IKkTDE0agwKBgQC1yYg6GqhQQhSLTBLXwFrXXNyKLHENTrB+JS+OPAd7b/uMYFeZsjZwVpDDGb8T9+Z9QWCCtHxz1vj6w5ghefcHLyyLhtU7ZPfbKCC+K0BSPOjwhhXruvToV+tHnK6MjaXeVaZIp3j0PaB+D89LTkDIoI09jnxj5rmrvsHkWih5UQKBgQDAx9ldjSQLvxHmKXLFAGUyhJAqxS5WwW99yiV3IWzs0eFkNMMkxgnkW31Xsp4uBUS+aWpAF4aDGE+dqEuHRnkaWVKOWnnAYV36mzYwCXCxSbpJ4Sye53GhVAOaCbJHGj1J4OSzyy8p0dt9jNacoR6gg3FKy2kkXX+Mt0NtWmupJwKBgQCtzeCMRR51lahUwkgltYhk7ikBhpoUMg72nJEKOYSh1SA5R4eZsFTyNSG1F3GkItlhGleuv+dZTcjA4K8cGqM/YDADusc2QUPkakpb57hYqFnCVUCyIA618hFGZ5xPT8Ktf1A7mLtBMYa34F++TTQNXcuMdiWh0l/kv75bmIOn8QKBgQCIQgzZj7bmLOvCog0YUx+mdhOI+/EqwRdBiBvMHNo/qkMN8D0yBYRPLhT4vGLTmKVm/G5fhNo1F6iNs97iYclj5QdcWL3kcJT7SSA7JX84ZP13dLW+v+pP53jk5enMEfdUzWsMV7Jik5kMK+vzc0dEd7htYNxjnEKl9/42a1VfNQ==";

    // 支付宝公钥
    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgzOnZ97e+xPgjG0YVHDR+EoTvK3HiwsGR3VSlVSiFStSgC9Ck1BSBMS7Xo3gMX59YUHHHtxQEooT+rkTv8Xftof45vOFyZxRcRtpEUjrakjNiuaWwOT46cMkgcqcOf/+5gok8RQIr5618SijmBc6WLBdyYwMFmlh0engXaOt88R8OdQGeWd68ie6WWt8drXvawrN98484Xl0oz1J1VtmwCxV8mR9gsM/AR8ceWFCqH0LVYFfGCneExrN8jNeQFB4Vh2s4yCORDmMH1B3Zv5WSxO57SRU76AxQutDSaWNZul/vS5csgwjo5GuaH1Hs+qFn7AnSNmPrQ9kKnVvGnxoUwIDAQAB";

    public static final String SIGN_TYPE = "RSA2";

    public static final String CHARSET = "UTF-8";

    public static final String FORMAT = "JSON";

    public static final String ALIPAY_GATEWAY = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";


    private AlipayConfig(){}

    private volatile static AlipayClient instance = null;

    public static AlipayClient getInstance() {
        if (instance == null){
            synchronized (AlipayConfig.class){
                if (instance == null)  {
                    instance = new DefaultAlipayClient(
                            ALIPAY_GATEWAY,
                            APP_ID,
                            APP_PRI_KEY,
                            "json",
                            "utf-8",
                            ALIPAY_PUBLIC_KEY,
                            "RSA2"
                    );
                }
            }
        }
        return instance;
    }
}
