package com.minglog.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Base64;

@Data
@ConfigurationProperties("minglog")
public class AppConfig {

    private byte[] jwtkey;

    public void setJwtkey(String jwtkey) {
        this.jwtkey = Base64.getDecoder().decode(jwtkey);
    }

    public byte[] getJwtkey() {
        return jwtkey;
    }
}
